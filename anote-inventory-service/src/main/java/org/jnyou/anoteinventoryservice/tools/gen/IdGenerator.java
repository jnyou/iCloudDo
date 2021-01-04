package org.jnyou.anoteinventoryservice.tools.gen;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashSet;
import java.util.Set;

/**
 * snowflake算法 这是twitter的一个id生成算法
 * Twitter-Snowflake算法产生的背景相当简单，为了满足Twitter每秒上万条消息的请求，每条消息都必须分配一条唯一的id，这些id还需要一些大致的顺序（方便客户端排序），
 * 并且在分布式系统中不同机器产生的id必须不同。
 * 
 * 首先我们需要一个long类型的变量来保存这个生成的id，第一位固定为0，因为id都是正数嘛，还剩63位，用x位表示毫秒时间戳，用y位表示进程id，用z位表示同一个时间戳下的序列号，x+y+z=63。
 * 
 *
 * @author jnyou
 */
public class IdGenerator {
	
	/*
	 * String str = "20170101";  
	 * System.out.println(new SimpleDateFormat("YYYYMMDD").parse(str).getTime());
	 */
	private final static long beginTs = 1483200000000L;  
	  
    private long lastTs = 0L;  
  
    private long processId;  
    private int processIdBits = 10;  
  
    private long sequence = 0L;  
    private int sequenceBits = 12;  
  
    
    private static IdGenerator idGen = null; 
    
    public static IdGenerator getInstance() {  
        if (idGen == null) {    
            synchronized (IdGenerator.class) {    
               if (idGen == null) {    
                  idGen = new IdGenerator();   
               }    
            }    
        }    
        return idGen;   
    }
    
    // 10位进程ID标识  
    private IdGenerator() {
    	// JVM id
    	RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    	long processId = Long.valueOf(runtimeMXBean.getName().split("@")[0]).longValue()%1000;
        if (processId > ((1 << processIdBits) - 1)) {  
            throw new RuntimeException("进程ID超出范围，设置位数" + processIdBits + "，最大" + ((1 << processIdBits) - 1));  
        }  
        this.processId = processId;  
    }  
  
    public synchronized long nextId() {  
        long ts = timeGen();  
        if (ts < lastTs) {// 刚刚生成的时间戳比上次的时间戳还小，出错  
            throw new RuntimeException("时间戳顺序错误");  
        }  
        if (ts == lastTs) {// 刚刚生成的时间戳跟上次的时间戳一样，则需要生成一个sequence序列号  
            // sequence循环自增  
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);  
            // 如果sequence=0则需要重新生成时间戳  
            if (sequence == 0) {  
                // 且必须保证时间戳序列往后  
                ts = nextTs(lastTs);  
            }  
        } else {// 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0  
            sequence = 0L;  
        }  
        lastTs = ts;// 更新lastTs时间戳  
        return ((ts - beginTs) << (processIdBits + sequenceBits)) | (processId << sequenceBits) | sequence;  
    }  
  
    protected long nextTs(long lastTs) {  
        long ts = timeGen();  
        while (ts <= lastTs) {  
            ts = timeGen();  
        }  
        return ts;  
    }  
    
    protected long timeGen() {  
        return System.currentTimeMillis();  
    }
  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
        IdGenerator ig = IdGenerator.getInstance().idGen;
        Set<Long> set = new HashSet<Long>();  
        long begin = System.nanoTime();  
        for (int i = 0; i < 100; i++) {  
            set.add(ig.nextId());  
        }  
        System.out.println("time=" + (System.nanoTime() - begin)/1000.0/1000.0 + " us");
        System.out.println(set.size()); 
        System.out.println(set);
    }
}

