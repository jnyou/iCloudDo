package org.jnyou.nettyserver.handler;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 振动数据位实体
 *
 * @since 1.0.0
 */
public class PacketData {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    private static BigDecimal bigDecimal;

    private static int DATA_SIZE=40;

    private byte chanid;
    private short sampleFreq;
    private Long sec;
    private Long usec;
    private String[] data;


    /**
     * 解析数据
     * @param buffer
     * @Author JnYou
     */
    public void decodeFrom(ByteBuffer buffer) {
        chanid = buffer.get();
        sampleFreq = buffer.getShort();
        sec = (long) buffer.getInt();
        usec = (long) buffer.getInt();
        data = new String[DATA_SIZE];
        for (int i = 0; i < DATA_SIZE; i++) {
            bigDecimal = new BigDecimal(buffer.getFloat());
            data[i]= bigDecimal.setScale(6,BigDecimal.ROUND_HALF_UP).toString();
        }
    }

    public static void main(String[] args) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{(byte) 0xb8, 0x77, (byte) 0xed, 0x5b});
        float anInt = wrap.getFloat();
//        String s = Float.toHexString(1.1f);
        System.out.println(new BigDecimal(anInt).setScale(6,BigDecimal.ROUND_DOWN).toString());
//        System.out.println(strToDouble(bytesToHexString(new byte[]{(byte) 0xb8, 0x77, (byte) 0xed, 0x5b}),"#.00000000"));
    }

    public synchronized String getTime(){//时间处理
        Date date =new Date((sec * 1000000 + usec )/1000);
        return simpleDateFormat.format(date);
    }

    public byte getChanid() {
        return chanid;
    }

    public void setChanid(byte chanid) {
        this.chanid = chanid;
    }

    public short getSampleFreq() {
        return sampleFreq;
    }

    public void setSampleFreq(short sampleFreq) {
        this.sampleFreq = sampleFreq;
    }

    public Long getSec() {
        return sec;
    }

    public void setSec(Long sec) {
        this.sec = sec;
    }

    public Long getUsec() {
        return usec;
    }

    public void setUsec(Long usec) {
        this.usec = usec;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}