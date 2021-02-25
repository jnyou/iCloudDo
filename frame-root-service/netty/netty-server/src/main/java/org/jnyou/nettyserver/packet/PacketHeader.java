
package org.jnyou.nettyserver.packet;

import java.nio.ByteBuffer;

/**
 * @since 1.0.0
 */
public class PacketHeader {
    public static final int SIZE = 28;
    public static final int DEVICE_ID_SIZE = 19;
    private short head;
    private short len;
    private byte version;
    private byte cmd;
    private short serial;;
    private byte[] deviceID;


    public void decodeFrom(ByteBuffer buffer) {
        this.head = buffer.getShort();
        this.len = buffer.getShort();
        this.version = buffer.get();
        this.cmd = buffer.get();
        this.serial = buffer.getShort();
        byte[] deviceID = new byte[DEVICE_ID_SIZE];
        buffer.get(deviceID, 0, deviceID.length);
        this.deviceID = deviceID;
    }


    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public short getSerial() {
        return serial;
    }

    public void setSerial(short serial) {
        this.serial = serial;
    }

    public byte[] getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(byte[] deviceID) {
        this.deviceID = deviceID;
    }
}