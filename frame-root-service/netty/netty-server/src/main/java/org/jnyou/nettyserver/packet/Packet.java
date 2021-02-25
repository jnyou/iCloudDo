package org.jnyou.nettyserver.packet;

import com.alibaba.fastjson.JSONObject;

public class Packet {
    private Integer id;

    private byte chanid;

    private short sampleFreq;

    private Long sec;

    private Long usec;

    private String data;

    private String deviceId;

    private String addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.sec = sec == null ? null : sec;
    }

    public Long getUsec() {
        return usec;
    }

    public void setUsec(Long usec) {
        this.usec = usec == null ? null : usec;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public static Packet set(PacketData packetData) {
        Packet packet = new Packet();
        packet.setId(Math.abs((packet.hashCode())));
        packet.setChanid(packetData.getChanid());
        packet.setData(JSONObject.toJSONString(packetData.getData()));
        packet.setSampleFreq(packetData.getSampleFreq());
        packet.setSec(packetData.getSec());
        packet.setUsec(packetData.getUsec());
        packet.setAddTime(packetData.getTime());
        return packet;
    }
}