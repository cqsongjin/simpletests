package com.song.test.netty.model;

public class OFProtocol {
    private short version;
    private short type;
    private int length;
    private int xid;

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }

    public int getMpDeviceType() {
        return MpDeviceType;
    }

    public void setMpDeviceType(int mpDeviceType) {
        MpDeviceType = mpDeviceType;
    }

    public int getMpDeviceId() {
        return MpDeviceId;
    }

    public void setMpDeviceId(int mpDeviceId) {
        MpDeviceId = mpDeviceId;
    }

    private int MpDeviceType;
    private int MpDeviceId;

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "OFProtocol{" +
                "version=" + version +
                ", type=" + type +
                ", length=" + length +
                ", xid=" + xid +
                ", MpDeviceType=" + MpDeviceType +
                ", MpDeviceId=" + MpDeviceId +
                '}';
    }
}
