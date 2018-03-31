package com.pda.hf;

public class ISO15693CardInfo
{
  private byte[] uid;
  private byte flags;
  private byte dsfid;

  public byte[] getUid()
  {
    return this.uid;
  }
  public void setUid(byte[] uid) {
    this.uid = uid;
  }
  public byte getFlags() {
    return this.flags;
  }
  public void setFlags(byte flags) {
    this.flags = flags;
  }
  public byte getDsfid() {
    return this.dsfid;
  }
  public void setDsfid(byte dsfid) {
    this.dsfid = dsfid;
  }
}