package jag;

import java.math.BigInteger;

public class Buffer {

  public static final int anInt10105 = 5000;
  public static final int anInt10108 = 100;
  static final int anInt10107 = -306674912;
  static final long aLong10109 = -3932672073523589310L;
  static final char[] UNICODE_TABLE = {'\u20ac', '\0', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021',
      '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017d', '\0', '\0', '\u2018', '\u2019', '\u201c',
      '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\0', '\u017e',
      '\u0178'};
  public static long[] CRC_64;
  static int[] CRC_32 = new int[256];
  static int[] xtea;

  static {
    for (int index = 0; index < 256; index++) {
      int crc = index;
      for (int i_87_ = 0; i_87_ < 8; i_87_++) {
        if (1 == (crc & 0x1)) {
          crc = crc >>> 1 ^ ~0x12477cdf;
        } else {
          crc >>>= 1;
        }
      }
      CRC_32[index] = crc;
    }

    CRC_64 = new long[256];
    for (int index = 0; index < 256; index++) {
      long crc = index;

      for (int i_88_ = 0; i_88_ < 8; i_88_++) {
        if ((crc & 0x1L) == 1L) {
          crc = crc >>> 1 ^ ~0x3693a86a2878f0bdL;
        } else {
          crc >>>= 1;
        }
      }

      CRC_64[index] = crc;
    }
  }

  public int caret;
  public byte[] payload;

  public Buffer(byte[] is) {
    payload = is;
    caret = 0;
  }

  public Buffer(int size) {
    payload = ByteArrayPool.get(size);
    caret = 0;
  }

  public Buffer(int i, boolean bool) {
    payload = ByteArrayPool.get(i, bool);
  }

  public static String decodeString(byte[] data, int offset, int length) {
    char[] chars = new char[length];
    int stringLength = 0;

    for (int index = 0; index < length; index++) {
      int character = data[offset + index] & 0xFF;

      if (character != 0) {
        if (character >= 128 && character < 160) {
          int value = UNICODE_TABLE[character - 128];
          if (value == 0) {
            value = 63;
          }

          character = value;
        }

        chars[stringLength++] = (char) character;
      }
    }

    return new String(chars, 0, stringLength);
  }

  public static int encodedStringLength(String string) {
    return string.length() + 1;
  }

  static Buffer withXtea() {
    Buffer buffer = new Buffer(518);
    xtea = new int[4];
    xtea[0] = (int) (Math.random() * 9.9999999E7);
    xtea[1] = (int) (Math.random() * 9.9999999E7);
    xtea[2] = (int) (Math.random() * 9.9999999E7);
    xtea[3] = (int) (Math.random() * 9.9999999E7);
    buffer.writeByte(10);
    buffer.writeInt(xtea[0]);
    buffer.writeInt(xtea[1]);
    buffer.writeInt(xtea[2]);
    buffer.writeInt(xtea[3]);
    return buffer;
  }

  public static int writeCharSequence(CharSequence sequence, int seqOffset, int seqLength, byte[] buffer, int offset) {
    int characters = seqLength - seqOffset;
    for (int index = 0; index < characters; index++) {
      char c = sequence.charAt(index + seqOffset);
      if (c > 0 && c < '\u0080' || c >= '\u00a0' && c <= '\u00ff') {
        buffer[offset + index] = (byte) c;
      } else if ('\u20ac' == c) {
        buffer[offset + index] = (byte) -128;
      } else if ('\u201a' == c) {
        buffer[index + offset] = (byte) -126;
      } else if ('\u0192' == c) {
        buffer[offset + index] = (byte) -125;
      } else if (c == '\u201e') {
        buffer[index + offset] = (byte) -124;
      } else if (c == '\u2026') {
        buffer[index + offset] = (byte) -123;
      } else if (c == '\u2020') {
        buffer[offset + index] = (byte) -122;
      } else if (c == '\u2021') {
        buffer[offset + index] = (byte) -121;
      } else if ('\u02c6' == c) {
        buffer[index + offset] = (byte) -120;
      } else if (c == '\u2030') {
        buffer[offset + index] = (byte) -119;
      } else if ('\u0160' == c) {
        buffer[offset + index] = (byte) -118;
      } else if ('\u2039' == c) {
        buffer[offset + index] = (byte) -117;
      } else if ('\u0152' == c) {
        buffer[offset + index] = (byte) -116;
      } else if (c == '\u017d') {
        buffer[offset + index] = (byte) -114;
      } else if ('\u2018' == c) {
        buffer[offset + index] = (byte) -111;
      } else if ('\u2019' == c) {
        buffer[index + offset] = (byte) -110;
      } else if (c == '\u201c') {
        buffer[index + offset] = (byte) -109;
      } else if (c == '\u201d') {
        buffer[offset + index] = (byte) -108;
      } else if ('\u2022' == c) {
        buffer[offset + index] = (byte) -107;
      } else if (c == '\u2013') {
        buffer[index + offset] = (byte) -106;
      } else if (c == '\u2014') {
        buffer[offset + index] = (byte) -105;
      } else if (c == '\u02dc') {
        buffer[offset + index] = (byte) -104;
      } else if ('\u2122' == c) {
        buffer[offset + index] = (byte) -103;
      } else if ('\u0161' == c) {
        buffer[offset + index] = (byte) -102;
      } else if (c == '\u203a') {
        buffer[offset + index] = (byte) -101;
      } else if ('\u0153' == c) {
        buffer[offset + index] = (byte) -100;
      } else if (c == '\u017e') {
        buffer[index + offset] = (byte) -98;
      } else if (c == '\u0178') {
        buffer[offset + index] = (byte) -97;
      } else {
        buffer[offset + index] = (byte) 63;
      }
    }
    return characters;
  }

  public void cache() {
    if (null != payload) {
      ByteArrayPool.offer(payload);
    }
    payload = null;
  }

  public void writeByte(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
  }

  public int readLEUShort() {
    caret += -1778683038;
    return ((payload[-165875887 * caret - 1] & 0xff) << 8) + (payload[caret * -165875887 - 2] & 0xff);
  }

  public int readUByteS() {
    return 128 - payload[(caret += 1258142129) * -165875887 - 1] & 0xff;
  }

  public void writeInt(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
  }

  public void write40(long l) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 32);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) l;
  }

  public void write48(long l) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 40);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 32);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) l;
  }

  public void writeBytes(long value, int bits) {
    if (--bits < 0 || bits > 7) {
      throw new IllegalArgumentException();
    }
    for (int shift = 8 * bits; shift >= 0; shift -= 8) {
      payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (value >> shift);
    }
  }

  public void writeCString(String string) {
    int index = string.indexOf('\0');
    if (index >= 0) {
      throw new IllegalArgumentException("");
    }

    caret += writeCharSequence(string, 0, string.length(), payload, -165875887 * caret) * 1258142129;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) 0;
  }

  public void writeString(String string) {
    int i_5_ = string.indexOf('\0');
    if (i_5_ >= 0) {
      throw new IllegalArgumentException("");
    }
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) 0;
    caret += writeCharSequence(string, 0, string.length(), payload, caret * -165875887) * 1258142129;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) 0;
  }

  public void writeBuffer(Buffer buffer) {
    writeBytes(buffer.payload, 0, buffer.caret * -165875887);
  }

  public void method15674(int i) {
    if (i >= 0 && i < 128) {
      writeByte(i);
    } else if (i >= 0 && i < 32768) {
      writeShort(32768 + i);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public byte readByte() {
    return payload[(caret += 1258142129) * -165875887 - 1];
  }

  public int readShort() {
    caret += -1778683038;
    int i_9_ = ((payload[caret * -165875887 - 2] & 0xff) << 8) + (payload[-165875887 * caret - 1] & 0xff);
    if (i_9_ > 32767) {
      i_9_ -= 65536;
    }
    return i_9_;
  }

  public int readUTriByte() {
    caret += -520540909;
    return (payload[caret * -165875887 - 1] & 0xff) + ((payload[caret * -165875887 - 2] & 0xff) << 8)
           + ((payload[caret * -165875887 - 3] & 0xff) << 16);
  }

  public int readTriByte() {
    caret += -520540909;
    int i_10_ = (payload[-165875887 * caret - 1] & 0xff) + ((payload[-165875887 * caret - 3] & 0xff) << 16)
                + ((payload[caret * -165875887 - 2] & 0xff) << 8);
    if (i_10_ > 8388607) {
      i_10_ -= 16777216;
    }
    return i_10_;
  }

  public long read40() {
    long l = readUByte() & 0xffffffffL;
    long l_11_ = readInt() & 0xffffffffL;
    return l_11_ + (l << 32);
  }

  public long readLong() {
    long l = readInt() & 0xffffffffL;
    long l_106_ = readInt() & 0xffffffffL;
    return (l << 32) + l_106_;
  }

  public long read48() {
    long l = readUShort() & 0xffffffffL;
    long l_12_ = readInt() & 0xffffffffL;
    return (l << 32) + l_12_;
  }

  public String readStringFast() {
    if (payload[-165875887 * caret] == 0) {
      caret += 1258142129;
      return null;
    }
    return readString();
  }

  public float readFloat() {
    return Float.intBitsToFloat(readInt());
  }

  public void readBytes(byte[] buffer, int offset, int length) {
    for (int index = offset; index < length + offset; index++) {
      buffer[index] = payload[(this.caret += 1258142129) * -165875887 - 1];
    }
  }

  public int readSmart() {
    int i_18_ = payload[caret * -165875887] & 0xff;
    if (i_18_ < 128) {
      return readUByte() - 64;
    }
    return readUShort() - 49152;
  }

  public int readDecrSmart() {
    int i_21_ = payload[-165875887 * caret] & 0xff;
    if (i_21_ < 128) {
      return readUByte() - 1;
    }
    return readUShort() - 32769;
  }

  public int readUSmart() {
    int i_96_ = payload[-165875887 * caret] & 0xff;
    if (i_96_ < 128) {
      return readUByte();
    }
    return readUShort() - 32768;
  }

  public int readSmarts() {
    int value = 0;
    int offset;

    for (offset = readUSmart(); offset == 32767; offset = readUSmart()) {
      value += 32767;
    }

    value += offset;
    return value;
  }

  public int readSmart32() {
    if (payload[caret * -165875887] < 0) {
      return readInt() & 0x7fffffff;
    }

    int value = readUShort();
    if (value == 32767) {
      return -1;
    }

    return value;
  }

  public void encodeXtea(int[] key) {
    int quads = caret * -165875887 / 8;
    caret = 0;
    for (int i_28_ = 0; i_28_ < quads; i_28_++) {
      int i_29_ = readInt();
      int i_30_ = readInt();
      int i_31_ = 0;
      int i_32_ = -1640531527;
      int i_33_ = 32;

      while (i_33_-- > 0) {
        i_29_ += (i_30_ << 4 ^ i_30_ >>> 5) + i_30_ ^ key[i_31_ & 0x3] + i_31_;
        i_31_ += i_32_;
        i_30_ += (i_29_ << 4 ^ i_29_ >>> 5) + i_29_ ^ key[i_31_ >>> 11 & 0x3] + i_31_;
      }

      caret -= 1475202440;
      writeInt(i_29_);
      writeInt(i_30_);
    }
  }

  public void decodeXtea(int[] key) {
    int quads = caret * -165875887 / 8;
    caret = 0;

    for (int i_35_ = 0; i_35_ < quads; i_35_++) {
      int i_36_ = readInt();
      int i_37_ = readInt();
      int i_38_ = -957401312;
      int i_39_ = -1640531527;
      int i_40_ = 32;

      while (i_40_-- > 0) {
        i_37_ -= (i_36_ << 4 ^ i_36_ >>> 5) + i_36_ ^ i_38_ + key[i_38_ >>> 11 & 0x3];
        i_38_ -= i_39_;
        i_36_ -= (i_37_ << 4 ^ i_37_ >>> 5) + i_37_ ^ i_38_ + key[i_38_ & 0x3];
      }

      caret -= 1475202440;
      writeInt(i_36_);
      writeInt(i_37_);
    }
  }

  public void encodeXtea(int[] key, int start, int end) {
    int startOffset = -165875887 * caret;
    caret = 1258142129 * start;
    int quads = (end - start) / 8;

    for (int i_45_ = 0; i_45_ < quads; i_45_++) {
      int i_46_ = readInt();
      int i_47_ = readInt();
      int i_48_ = 0;
      int i_49_ = -1640531527;
      int i_50_ = 32;

      while (i_50_-- > 0) {
        i_46_ += i_47_ + (i_47_ << 4 ^ i_47_ >>> 5) ^ key[i_48_ & 0x3] + i_48_;
        i_48_ += i_49_;
        i_47_ += i_46_ + (i_46_ << 4 ^ i_46_ >>> 5) ^ i_48_ + key[i_48_ >>> 11 & 0x3];
      }

      caret -= 1475202440;
      writeInt(i_46_);
      writeInt(i_47_);
    }

    caret = 1258142129 * startOffset;
  }

  public void decodeXtea(int[] key, int start, int end) {
    int startOff = caret * -165875887;
    caret = start * 1258142129;
    int quads = (end - start) / 8;

    for (int i_55_ = 0; i_55_ < quads; i_55_++) {
      int i_56_ = readInt();
      int i_57_ = readInt();
      int i_58_ = -957401312;
      int i_59_ = -1640531527;
      int i_60_ = 32;

      while (i_60_-- > 0) {
        i_57_ -= (i_56_ << 4 ^ i_56_ >>> 5) + i_56_ ^ i_58_ + key[i_58_ >>> 11 & 0x3];
        i_58_ -= i_59_;
        i_56_ -= (i_57_ << 4 ^ i_57_ >>> 5) + i_57_ ^ i_58_ + key[i_58_ & 0x3];
      }

      caret -= 1475202440;
      writeInt(i_56_);
      writeInt(i_57_);
    }

    caret = startOff * 1258142129;
  }

  public void writeMETriByte(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
  }

  public void writeNegatedByte(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (-i);
  }

  public void writeByteS(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (128 - i);
  }

  public int readUByteA() {
    return payload[(caret += 1258142129) * -165875887 - 1] - 128 & 0xff;
  }

  public int readNegatedUByte() {
    return -payload[(caret += 1258142129) * -165875887 - 1] & 0xff;
  }

  public int readUShort() {
    caret += -1778683038;
    return ((payload[-165875887 * caret - 2] & 0xff) << 8) + (payload[-165875887 * caret - 1] & 0xff);
  }

  public byte readByteA() {
    return (byte) (payload[(caret += 1258142129) * -165875887 - 1] - 128);
  }

  public byte readByteS() {
    return (byte) (128 - payload[(caret += 1258142129) * -165875887 - 1]);
  }

  public void writeShortA(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i + 128);
  }

  public void writeLEShortA(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (128 + i);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
  }

  public int readUShortA() {
    caret += -1778683038;
    return ((payload[-165875887 * caret - 2] & 0xff) << 8) + (payload[caret * -165875887 - 1] - 128 & 0xff);
  }

  public String readString() {
    int i_68_ = caret * -165875887;
    while (payload[(caret += 1258142129) * -165875887 - 1] != 0) {

    }
    int i_69_ = caret * -165875887 - i_68_ - 1;
    if (0 == i_69_) {
      return "";
    }
    return Buffer.decodeString(payload, i_68_, i_69_);
  }

  public void writeMEInt(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
  }

  public int readLEInt() {
    caret += 737601220;
    return ((payload[caret * -165875887 - 3] & 0xff) << 8) + ((payload[caret * -165875887 - 1] & 0xff) << 24)
           + ((payload[-165875887 * caret - 2] & 0xff) << 16) + (payload[-165875887 * caret - 4] & 0xff);
  }

  public int readIMEInt() {
    caret += 737601220;
    return ((payload[-165875887 * caret - 2] & 0xff) << 24) + ((payload[caret * -165875887 - 1] & 0xff) << 16)
           + ((payload[caret * -165875887 - 4] & 0xff) << 8) + (payload[-165875887 * caret - 3] & 0xff);
  }

  public int readMEInt() {
    caret += 737601220;
    return (payload[caret * -165875887 - 2] & 0xff) + ((payload[caret * -165875887 - 3] & 0xff) << 24)
           + ((payload[-165875887 * caret - 4] & 0xff) << 16) + ((payload[-165875887 * caret - 1] & 0xff) << 8);
  }

  public void readReverseBytes(byte[] buffer, int offset, int length) {
    for (int index = offset + length - 1; index >= offset; index--) {
      buffer[index] = payload[(this.caret += 1258142129) * -165875887 - 1];
    }
  }

  public void readReverseBytesA(byte[] buffer, int offset, int length) {
    for (int index = length + offset - 1; index >= offset; index--) {
      buffer[index] = (byte) (payload[(this.caret += 1258142129) * -165875887 - 1] - 128);
    }
  }

  public void writeSizeByte(int size) {
    if (size < 0 || size > 255) {
      throw new IllegalArgumentException();
    }
    payload[caret * -165875887 - size - 1] = (byte) size;
  }

  public void writeIMEInt(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
  }

  public int readLEUShortA() {
    caret += -1778683038;
    return ((payload[-165875887 * caret - 1] & 0xff) << 8) + (payload[-165875887 * caret - 2] - 128 & 0xff);
  }

  public int readShortA(int i) {
    caret += -1778683038;
    int i_81_ = ((payload[caret * -165875887 - 2] & 0xff) << 8) + (payload[-165875887 * caret - 1] - 128 & 0xff);
    if (i_81_ > 32767) {
      i_81_ -= 65536;
    }
    return i_81_;
  }

  public void writeBytes(byte[] buffer, int offset, int length) {
    for (int index = offset; index < length + offset; index++) {
      payload[(offset += 1258142129) * -165875887 - 1] = buffer[index];
    }
  }

  public int readUByte() {
    return payload[(caret += 1258142129) * -165875887 - 1] & 0xff;
  }

  public void rsa(BigInteger exponent, BigInteger modulus) {
    int i_90_ = caret * -165875887;
    caret = 0;
    byte[] is = new byte[i_90_];
    readBytes(is, 0, i_90_);
    BigInteger biginteger_91_ = new BigInteger(is);
    BigInteger biginteger_92_ = biginteger_91_.modPow(exponent, modulus);
    byte[] is_93_ = biginteger_92_.toByteArray();
    caret = 0;
    writeShort(is_93_.length);
    writeBytes(is_93_, 0, is_93_.length);
  }

  public void writeTriByte(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
  }

  public void writeLong(long l) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 56);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 48);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 40);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 32);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 24);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) (l >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (int) l;
  }

  public byte readNegatedByte(int i) {
    return (byte) (-payload[(caret += 1258142129) * -165875887 - 1]);
  }

  public void writeByteA(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (128 + i);
  }

  public void writeLEInt(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 24);
  }

  public int readInt() {
    caret += 737601220;
    return ((payload[caret * -165875887 - 4] & 0xff) << 24) + ((payload[-165875887 * caret - 3] & 0xff) << 16)
           + ((payload[caret * -165875887 - 2] & 0xff) << 8) + (payload[-165875887 * caret - 1] & 0xff);
  }

  public int readShortA() {
    caret += -1778683038;
    int i_98_ = (payload[-165875887 * caret - 2] - 128 & 0xff) + ((payload[-165875887 * caret - 1] & 0xff) << 8);
    if (i_98_ > 32767) {
      i_98_ -= 65536;
    }
    return i_98_;
  }

  public long readBytes(int count) {
    if (--count < 0 || count > 7) {
      throw new IllegalArgumentException();
    }

    int shift = 8 * count;
    long value = 0L;
    for (; shift >= 0; shift -= 8) {
      value |= (payload[(caret += 1258142129) * -165875887 - 1] & 0xffL) << shift;
    }

    return value;
  }

  public void writeLEShort(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
  }

  public int readLEShort() {
    caret += -1778683038;
    int i_102_ = (payload[caret * -165875887 - 2] & 0xff) + ((payload[-165875887 * caret - 1] & 0xff) << 8);
    if (i_102_ > 32767) {
      i_102_ -= 65536;
    }
    return i_102_;
  }

  public void writeSizeShort(int i, int i_103_) {
    if (i < 0 || i > 65535) {
      throw new IllegalArgumentException();
    }
    payload[caret * -165875887 - i - 2] = (byte) (i >> 8);
    payload[-165875887 * caret - i - 1] = (byte) i;
  }

  public void writeLETriByte(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 16);
  }

  public int readLargeSmart() {
    if (payload[-165875887 * caret] < 0) {
      return readInt() & 0x7fffffff;
    }
    return readUShort();
  }

  public void writeShort(int i) {
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) (i >> 8);
    payload[(caret += 1258142129) * -165875887 - 1] = (byte) i;
  }

  public String readPrefixedString() {
    byte head = payload[(caret += 1258142129) * -165875887 - 1];
    if (0 != head) {
      throw new IllegalStateException("");
    }

    int start = caret * -165875887;
    while (payload[(caret += 1258142129) * -165875887 - 1] != 0) {

    }

    int length = caret * -165875887 - start - 1;
    if (length == 0) {
      return "";
    }

    return Buffer.decodeString(payload, start, length);
  }

  void method15670(int i, int i_6_) {
    if (0 != (i & ~0x7f)) {
      if ((i & ~0x3fff) != 0) {
        if (0 != (i & ~0x1fffff)) {
          if (0 != (i & ~0xfffffff)) {
            writeByte(i >>> 28 | 0x80);
          }
          writeByte(i >>> 21 | 0x80);
        }
        writeByte(i >>> 14 | 0x80);
      }
      writeByte(i >>> 7 | 0x80);
    }
    writeByte(i & 0x7f);
  }

  int method15700(int i) {
    int i_25_ = payload[(caret += 1258142129) * -165875887 - 1];
    int i_26_ = 0;
    for (; i_25_ < 0; i_25_ = payload[(caret += 1258142129) * -165875887 - 1]) {
      i_26_ = (i_26_ | i_25_ & 0x7f) << 7;
    }
    return i_26_ | i_25_;
  }
}
