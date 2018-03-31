package com.pda.hf;

public class HfNative
{
  static
  {
    System.loadLibrary("devapi");
    System.loadLibrary("fxjni");
  }

  public native int open(int paramInt1, int paramInt2, int paramInt3);

  public native int close(int paramInt);

  public native int findM1Card(byte[] paramArrayOfByte);

  public native int authM1(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, int paramInt3, byte[] paramArrayOfByte2, int paramInt4);

  public native int readM1Block(int paramInt, byte[] paramArrayOfByte);

  public native int writeM1Block(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public native int find15693(byte[] paramArrayOfByte);

  public native int get15693PICC(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);

  public native int readBlock15693(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2);

  public native int writeBlock15693(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3);
}