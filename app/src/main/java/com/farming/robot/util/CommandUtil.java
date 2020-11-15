package com.farming.robot.util;

/**
 * @author: lenovo
 * @date: 2020/9/28
 */
public class CommandUtil {
    public static final String OPEN_DEVICE="CMD_KEYA022C1END";
    public static final String CLOSE_DEVICE="CMD_KEYA022C0END";


    public static final int HANDLER_WHAT_RECEIVED= 32;
    public static final int HANDLER_WHAT_COMMAND= 33;


    public static final String OPEN_DEVICE_COMMAND_HEX="434D445F4B4559413032020C01454E44";
    public static final String GET_INDEX_STR ="CMD_KEYA000END";
    public static final String GET_SOIL_STR ="CMD_KEYA010END";
//    public static final String GET_CONTROL_STR ="CMD_KEYA010END";
    /*

    1-2空气湿度  (单位0.1%RH) 如：292 H(十六进制)=658=>湿度= 65.8%RH；
3-4空气温度  (单位0.1℃)如：0110H(十六进制)= 272=>温度=27.2℃，低于0℃时以补码形式上传；
5-6空气co2  (单位1ppm) 如：5B4(十六进制)=1460=>CO2=1460ppm；
7-10光照度  (单位1Lux) 如：000206F6 H(十六进制) = 132854=>光照度=132854Lux；
11-12 新增加臭氧(单位1ppm)
13水箱水位   0=缺水、1=有水、2=满、3=注水；
14工作状态  0=停机、1=强风、2=清风，3=微风，4=强喷，5=中喷，6=弱喷；
15网络模式  1=STA、2=AP、3=AP+STA；

                0  0  长度    空气湿度    空气温度                                                e  n
 99 109 100 95 107 101 121 97 97 48 48   15      0 0         0 0      0 1 0 0 0 0 0 0 0 2 0 3     101 110
     */
}
