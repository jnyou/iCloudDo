package org.jnyou.nettyserver.handler;

/**
 * 振动数据位的数据类型
 * @since 1.0.0
 */
public interface DataType {

    short HEAD=0x55AA;
    short CMD_ReportData=0x0E;
    short CMD_Login=0x00;

}