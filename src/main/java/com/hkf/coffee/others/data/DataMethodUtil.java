package com.hkf.coffee.others.data;

import static com.hkf.coffee.others.data.DataTypeUtil.binaryToDecimal;
import static com.hkf.coffee.others.data.DataTypeUtil.hexToBinary;

/**
 * Created by huangkangfa on 2017/6/18.
 */

public class DataMethodUtil {
    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2IPString(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 将16进制的字符串转换成带点的ip字符串
     * @param data
     * @return
     */
    public static String hex2IPString(String data){
        String[] temp_ip = data.split("2e");
        StringBuffer ip = new StringBuffer("");
        for (int i = 0; i < temp_ip.length; i++) {
            if (i != temp_ip.length - 1)
                ip.append(getIpNum(temp_ip[i]));
            else ip.append(getIpNum(getDataEndWithoutZero((temp_ip[i]))));
            if (i != temp_ip.length - 1)
                ip.append(".");
        }
        return ip.toString();
    }

    /**
     * 信号强度计算  db单位
     * 获取有符号的单字节数据:信号强度为0，信号强度总为负值，最高位代表符号位，1为负，数值越大信号越好，理论范围0~-127（上电初始上报为0）
     **/
    public static String getDbByString(String data){
        String temp=hexToBinary(data,true);
        String t1=temp.substring(0,1);
        String t2="0"+temp.substring(1,temp.length());
        return "1".equals(t1)?"-"+(128-binaryToDecimal(t2)):""+binaryToDecimal(t2);
    }

    //加法和校验值
    public static String getAddCheck(String checkStr) {
        byte[] data = DataTypeUtil.hexStringToBytes(checkStr);
        byte addSum = 0;
        for (int i = 0; i < data.length; i++) {
            addSum += data[i];
        }
        return String.format("%02x", addSum & 0xff);
    }

    //异或和校验
    public static String getAddCheckByXOR(String checkStr) {
        byte[] data = DataTypeUtil.hexStringToBytes(checkStr);
        byte[] temp=new byte[1];
        temp[0]=data[0];
        for (int i = 1; i <data.length; i++) {
            temp[0] ^=data[i];
        }
        return DataTypeUtil.bytes2HexString(temp,temp.length);
    }

    /**
     * 指定字节长度补全
     */
    public static String setBytesLen(String s, int bytesNum,boolean zeroAtAfter) {
        int length = bytesNum * 2;
        if ("".equals(s))
            return "";
        int len = s.length();
        if(len==length)
            return s;
        if (len < length) {
            int temp = length - len;
            for (int i = 0; i < temp; i++){
                s=zeroAtAfter?s+"0":"0"+s;
            }
        } else if (len > length) {
            s = s.substring(len - length, len);
        }
        return s;
    }

    /**
     * 子字符串在父字符串中出现的次数
     */
    public static int stringSub(String str, String substr) {
        int index = 0;
        int count = 0;
        int fromindex = 0;
        while ((index = str.indexOf(substr, fromindex)) != -1) {
            fromindex = index + substr.length();
            count++;
        }
        return count;
    }

    /**
     * 字符串处理->获取偶数位字符串
     **/
    private static String getIpNum(String s) {
        StringBuffer S = new StringBuffer("");
        StringBuffer S_ = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++) {
            if ((i + 1) % 2 == 0) {
                S.append(S_.substring(i, i + 1));
            }
        }
        return S.toString();
    }

    /**
     * 字符串处理->结尾去00
     */
    private static String getDataEndWithoutZero(String s) {
        StringBuffer S = new StringBuffer(s);
        while (true) {
            if (S.length() <= 2 || !S.substring(S.length() - 2, S.length()).equals("00")) {
                return S.toString();
            }
            S.delete(S.length() - 2, S.length());
        }
    }

    /**
     * 黏包处理
     **/
    private static String tempCmd = "";  //临时半包指令内容
    //获取正式的数据
    public static String[] getTrueData(String data,String head,String end) {
        data = data.toLowerCase();
        boolean endFlag = head.equals(data.substring(data.length() - 2, data.length()));
        String[] s = new String[stringSub(data, end + head) + 1];
        int num = 0;
        for (int i = 0; i < s.length; i++) {
            data = data.substring(num);
            num = data.indexOf(end + head) + 2;
            if (num == 1) {
                num = data.length();
            }
            if (i == 0) {
                String t = data.substring(0, num);
                if (t.substring(0, 2).equals(head)) {
                    tempCmd = "";
                }
                s[0] = tempCmd + t;
            } else {
                s[i] = data.substring(0, num);
                if (i == (s.length - 1) && (!endFlag)) {
                    tempCmd = s[i];
                }
            }
        }
        return s;
    }

}
