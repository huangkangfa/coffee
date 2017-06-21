package com.hkf.coffee.others.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.hkf.coffee.others.data.DataMethodUtil.setBytesLen;

/**
 * 数据类型转化工具
 *
 * @author Administrator
 */
public class DataTypeUtil {
    private static final int DEFAULT_COVERING_POSITION_LENGTH = 4;

    //16-->2
    public static String hexToBinary(String str) {
        return hexToBinary(str, true);
    }

    //16-->2 是否缺位补0
    public static String hexToBinary(String str, boolean isCoveringPosition) {
        int decimal = hexToDecimal(str).intValue();
        String binary = decimalToBinary(decimal);

        if (isCoveringPosition) {
            int len = binary.length();
            if (len < DEFAULT_COVERING_POSITION_LENGTH) {
                for (int i = 0; i < DEFAULT_COVERING_POSITION_LENGTH - len; i++) {
                    binary = "0" + binary;
                }
            }
        }
        return binary;
    }

    //16-->10
    public static Integer hexToDecimal(String str) {
        try {
            int dec = Integer.valueOf(str, 16).intValue();
            return Integer.valueOf(dec);
        } catch (Exception localException) {
        }
        return 0;
    }

    //16-->8
    public static String hexToOctal(String str) {
        int dec = hexToDecimal(str).intValue();
        return decimalToOctal(dec);
    }

    //10-->2
    public static String decimalToBinary(int dec) {
        return Integer.toBinaryString(dec);
    }

    //10->8
    public static String decimalToOctal(int dec) {
        return Integer.toOctalString(dec);
    }

    //10->16
    public static String decimalToHex(int dec) {
        String s=Integer.toHexString(dec);
        return s.length()==1?"0"+s:s;
    }

    //2-->8
    public static String binaryToOctal(String binary) {
        int dec = binaryToDecimal(binary);
        return decimalToOctal(dec);
    }

    //2-->16
    public static String binaryToHex(String binary) {
        int dec = binaryToDecimal(binary);
        return decimalToHex(dec);
    }

    //2-->10
    public static int binaryToDecimal(String binary) {
        return Integer.valueOf(binary, 2).intValue();
    }

    //8-->2
    public static String octalToBinary(String octal) {
        int dec = octalToDecimal(octal);
        return Integer.toBinaryString(dec);
    }

    //8-->10
    public static int octalToDecimal(String octal) {
        return Integer.valueOf(octal, 8).intValue();
    }

    //8==>16
    public static String octalToHex(String octal) {
        int dec = octalToDecimal(octal);
        return decimalToHex(dec);
    }

    //bytes-->16
    public static String bytes2HexString(byte[] b, int vLen) {
        String ret = "";
        for (int i = 0; i < vLen; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex;
        }
        return ret;
    }

    //16-->bytes
    public static byte[] hexStringToBytes(String hexString) {
        if ((hexString == null) || (hexString.equals(""))) {
            return null;
        }

        hexString = hexString.toUpperCase();

        int length = hexString.length() / 2;

        char[] hexChars = hexString.toCharArray();

        byte[] d = new byte[length];

        for (int i = 0; i < length; i++) {
            int pos = i * 2;

            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)]));
        }

        return d;
    }

    //char->byte
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //ASCII转换为字符串
    public static String asciiToString(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "ASCII");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    //字符串(16进制)转换为ASCII码
    public static String stringToAscii(String data) {
        StringBuilder result=new StringBuilder("");
        char[] chars=data.toCharArray();
        for(int i=0;i<chars.length;i++){
            int temp=chars[i];
            result.append(setBytesLen(decimalToHex(temp),1,false));
        }
        return result.toString();
    }

    //中文转gb2312
    public static String chineseToGb2312(String s) throws UnsupportedEncodingException{
        return URLEncoder.encode(s,"gb2312").replaceAll("%","");
    }

    //gb2312转中文
    public static String gb2312ToChinese(String s) throws UnsupportedEncodingException{
        return new String(hexStringToBytes(s),"gb2312");
    }

}
