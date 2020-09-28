package com.farming.robot.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * Author by Winds on 2016/10/18.
 * Email heardown@163.com.
 */
public class ByteUtil {



    /**
     * 字节数组转换成对应的16进制表示的字符串
     *
     * @param src
     * @return
     */
    public static String bytes2HexStr(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            builder.append(buffer);
        }
        return builder.toString().toUpperCase();
    }

    public static  String[] byte2StrByASCII(byte[] buffer, int size){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buffer.length; i++) {
            sb.append(((char) buffer[i]));
        }
        return null;
    }
    
    public static String[] bytes2HexStrArray(byte[] src, int size) {
        String[] strings=new String[size];

        if (src == null || src.length <= 0) {
            return strings;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < size; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            StringBuilder builder = new StringBuilder();
            builder.append(buffer);
            strings[i]=builder.toString().toUpperCase();
        }
        return strings;
    }
    /**
     * 十六进制字节数组转字符串
     *
     * @param src 目标数组
     * @param dec 起始位置
     * @param length 长度
     * @return
     */
    public static String bytes2HexStr(byte[] src, int dec, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(src, dec, temp, 0, length);
        return bytes2HexStr(temp);
    }

    /**
     * 16进制字符串转10进制数字
     *
     * @param hex
     * @return
     */
    public static long hexStr2decimal(String hex) {
        return Long.parseLong(hex, 16);
    }

    /**
     * 把十进制数字转换成足位的十六进制字符串,并补全空位
     *
     * @param num
     * @return
     */
    public static String decimal2fitHex(long num) {
        String hex = Long.toHexString(num).toUpperCase();
        if (hex.length() % 2 != 0) {
            return "0" + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * 把十进制数字转换成足位的十六进制字符串,并补全空位
     *
     * @param num
     * @param strLength 字符串的长度
     * @return
     */
    public static String decimal2fitHex(long num, int strLength) {
        String hexStr = decimal2fitHex(num);
        StringBuilder stringBuilder = new StringBuilder(hexStr);
        while (stringBuilder.length() < strLength) {
            stringBuilder.insert(0, '0');
        }
        return stringBuilder.toString();
    }

    public static String fitDecimalStr(int dicimal, int strLength) {
        StringBuilder builder = new StringBuilder(String.valueOf(dicimal));
        while (builder.length() < strLength) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRobotCommand("CMD_KEYA022C1END"));
//        System.out.println(hexStr2Str("434D445F4B4559413032020C01454E44"));
    }
    public static String getRobotCommand(String string){
        String first=string.substring(0,string.indexOf("END")-3);
        String middle=string.substring(string.indexOf("END")-3,string.indexOf("END"));
        String last=string.substring(string.indexOf("END"));
        return str2HexString(first)+ strTo16(middle)+str2HexString(last);
    }
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            str+="0"+s.charAt(i);
        }
        return str;
    }
    /**
     * 字符串转十六进制字符串
     *
     * @param str
     * @return
     */
    public static String str2HexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = null;
        try {

            bs = str.getBytes("utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        try {
            return new String(bytes, Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//    public static void main(String[] args) {
//        String testStr="0D0ABDD3CAD5B3C9B9A6A1FACAFDBEDDB4A6C0ED0D0AD1A1D4F1C0E0D0CDCEAA30A1FAC7EBC7F3B7B4C0A1A1FAB4F3B1C3B5E7C1F7B2C9BCAFA1FAB5C8B4FDB7B4C0A10D0A0D0AB1EAD6BECEBB3A310D0AC7E5B3FDB1EAD6BECEBB3A300D0ACEB4BFAAC6F449443A310D0AB7A2CBCDB5C4CAFDBEDDD2D1CCEDBCD3D0A3D1E93A310D0AD2AAB7A2CBCDB5C4CAFDBEDD3A32303030306361340D" ;
//        String testStr2=        "0100000000000001";
////        String str2 = convertEncodingFormat(str1, "iso-8859-1", "UTF-8");
//        String testStr3="0D0A";
//        String testStr4="BDD3CAD5";
//        try {
////            System.out.println(new String(ByteUtil.hexStr2StrNoSpace(testStr).getBytes("iso-8859-1"), Charset.defaultCharset()));
//            System.out.println(new String(hexStr2bytes(testStr4),"GBK"));
////            System.out.println("哈哈哈");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////char s='接';
////System.out.print( s+"kk"+(int) s);
//    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static String hexStr2StrNoSpace(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        try {

            return new String(bytes,"GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 把十六进制表示的字节数组字符串，转换成十六进制字节数组
     *
     * @param
     * @return byte[]
     */
    public static byte[] hexStr2bytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (hexChar2byte(achar[pos]) << 4 | hexChar2byte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * 把16进制字符[0123456789abcde]（含大小写）转成字节
     *
     * @param c
     * @return
     */
    private static int hexChar2byte(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
            default:
                return -1;
        }
    }

    /*
    与单片机通讯汇总
     */
//将GB2312转化为中文,如BAFAC2DCB2B7→胡萝卜,两个字节合成一个文字
    public static String stringToGbk(String string) throws Exception {
        byte[] bytes = new byte[string.length() / 2];
        for (int j = 0; j < bytes.length; j++) {
            byte high = Byte.parseByte(string.substring(j * 2, j * 2 + 1), 16);
            byte low = Byte.parseByte(string.substring(j * 2 + 1, j * 2 + 2),
                    16);
            bytes[j] = (byte) (high << 4 | low);
        }
        String result = new String(bytes, "GBK");
        return result;
    }
    //将中文转化为GB2312,并且结果以byte[]形式返回,如胡萝卜→new byte[]{BA  FA C2 DC B2 B7}，一个字被分为两个字节
    public static byte[] gbkToString(String str) throws Exception {
        return new String(str.getBytes("GBK"), "gb2312").getBytes("gb2312");
    }
    //将十六进制的byte[]原封不动的转化为string，如byte[]{0x7e，0x80,0x11,0x20}→7e801120,可用于log打印
    public static String bytesToHexStringTwo(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
//将十六进制的byte[]原封不动的转化为string，并且每个byte之间用空格分开，如byte[]{0x7e，0x80,0x11,0x20}→7e 80 11 20，,可用于log打印
    public static StringBuilder byte2HexStr(byte[] data) {

        if (data != null && data.length > 0) {
            StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            return stringBuilder;
        }
        return null;
    }
//将byte[]数组转化为8、10、16等各种进制，例如byte[0x11,0x20]→4384，约等于1120（16进制）→4384，radix代表进制
    public static String bytesToAllHex(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
//将String的十六进制原封不动转化为byte的十六进制，例如7e20→new byte[]{0x7e，x20}
    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < tmp.length / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }
    //上边的方法里调用了这个方法
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
}
