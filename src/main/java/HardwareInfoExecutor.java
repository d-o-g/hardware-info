import jaclib.hardware_info.HardwareInfo;
import jag.Buffer;

import java.nio.file.Paths;
import java.util.HashMap;

public class HardwareInfoExecutor {

  static {
    System.load(Paths.get("jaclib.dll").toAbsolutePath().toString());
  }

  public static void main(String[] args) {
    int processorInfoInt = 0;
    int processorCount = 0;
    int szDriverDateP1 = 0;
    int szDriverDateP2 = 0;
    int processorInfoInt2 = 0;
    String processorInfoString = null;
    String unusedString = null;
    String directXVersion = null;
    String unusedString2 = null;
    String szDescription = null;
    int processorInfoInt4 = 0;
    int processorInfoInt3 = 0;
    String processorInfoString2 = null;
    int[] processorInfoInts = new int[3];
    int processorInfoInt5 = 0;
    try {
      int[] info = HardwareInfo.getCPUInfo();
      if (null != info && info.length == 3) {
        processorInfoInt3 = info[0];
        processorInfoInt2 = info[1];
        processorInfoInt = info[2];
      }

      int[] raw = HardwareInfo.getRawCPUInfo();
      if (raw != null && raw.length % 5 == 0) {
        HashMap<Integer, ProcessorInformation> hashmap = new HashMap<>();
        for (int index = 0; index < raw.length / 5; index++) {
          int i_8_ = raw[5 * index];
          int i_9_ = raw[1 + index * 5];
          int i_10_ = raw[5 * index + 2];
          int i_11_ = raw[3 + index * 5];
          int i_12_ = raw[4 + 5 * index];

          ProcessorInformation class665 = new ProcessorInformation(i_8_, i_9_, i_10_, i_11_, i_12_);
          hashmap.put(i_8_, class665);
        }

        ProcessorInformation class665 = hashmap.get(0);
        if (class665 != null) {
          Buffer buffer = new Buffer(13);
          buffer.writeLEInt(class665.anInt8395);
          buffer.writeLEInt(class665.anInt8393);
          buffer.writeLEInt(class665.anInt8397);
          buffer.caret = 0;
          processorInfoString = buffer.readString();
          System.out.println("ProcessorInfoString: " + processorInfoString);
        }

        ProcessorInformation processorInformation = hashmap.get(1);
        if (processorInformation != null) {
          processorInfoInt5 = processorInformation.anInt8394;
          int i_14_ = processorInformation.anInt8395;
          processorInfoInt4 = (i_14_ >> 16 & 0xff);
          processorInfoInts[0] = processorInformation.anInt8397;
          processorInfoInts[1] = processorInformation.anInt8393;
        }

        ProcessorInformation class665_15_ = hashmap.get(-2147483647);
        if (class665_15_ != null) {
          processorInfoInts[2] = class665_15_.anInt8393;
        }

        Buffer buffer = new Buffer(49);
        for (int i_16_ = -2147483646; i_16_ <= -2147483644; i_16_++) {
          ProcessorInformation class665_17_ = hashmap.get(i_16_);
          if (class665_17_ != null) {
            buffer.writeLEInt(class665_17_.anInt8394);
            buffer.writeLEInt(class665_17_.anInt8395);
            buffer.writeLEInt(class665_17_.anInt8397);
            buffer.writeLEInt(class665_17_.anInt8393);
          }
        }

        buffer.caret = 0;
        processorInfoString2 = buffer.readString();
      }

      String[][] strings = HardwareInfo.getDXDiagDisplayDevicesProps();
      if (strings != null && strings.length > 0 && null != strings[0]) {
        for (int index = 0; index < strings[0].length; index += 2) {
          if (strings[0][index].equalsIgnoreCase("szDescription")) {
            szDescription = strings[0][1 + index];
          } else if (strings[0][index].equalsIgnoreCase("szDriverDateEnglish")) {
            String string = strings[0][1 + index];

            try {
              int i_19_ = string.indexOf("/");
              int i_20_ = string.indexOf("/", i_19_ + 1);
              szDriverDateP1 = Integer.parseInt(string.substring(0, i_19_));
              szDriverDateP2 = Integer.parseInt(string.substring(i_20_ + 1, string.indexOf(" ", i_20_)));
            } catch (Exception exception) {

            }
          }
        }
      }

      String[] props = HardwareInfo.getDXDiagSystemProps();
      if (props != null) {
        String majorVersion = "";
        String minorVersion = "";
        String letterVersion = "";

        for (int index = 0; index < props.length; index += 2) {
          if (props[index].equalsIgnoreCase("dwDirectXVersionMajor")) {
            majorVersion = props[1 + index];
          } else if (props[index].equalsIgnoreCase("dwDirectXVersionMinor")) {
            minorVersion = props[1 + index];
          } else if (props[index].equalsIgnoreCase("dwDirectXVersionLetter")) {
            letterVersion = props[index + 1];
          }
        }

        directXVersion = majorVersion + "." + minorVersion + letterVersion;
      }

    } catch (Throwable throwable) {
      processorInfoInt = 0;
    }

    System.out.println(processorInfoInt);
    System.out.println(processorInfoInt2);
    System.out.println(szDescription);
    System.out.println(unusedString);
    System.out.println(directXVersion);
    System.out.println(unusedString2);
    System.out.println(szDriverDateP1);
    System.out.println(szDriverDateP2);
    System.out.println(processorInfoString);
    System.out.println(processorInfoString2);
    System.out.println(processorInfoInt3);
    System.out.println(processorInfoInt4);

    for (int i : processorInfoInts) {
      System.out.println(i);
    }

    System.out.println(processorInfoInt5);
    //        System.out.println(unusedString3);
  }

  public static class ProcessorInformation {

    int anInt8393;
    int anInt8394;
    int anInt8395;
    int anInt8396;
    int anInt8397;

    public ProcessorInformation(int i, int i_0_, int i_1_, int i_2_, int i_3_) {
      anInt8396 = i;
      anInt8394 = i_0_;
      anInt8395 = i_1_;
      anInt8397 = i_2_;
      anInt8393 = i_3_;
    }

    @Override
    public int hashCode() {
      return anInt8396;
    }

  }
}