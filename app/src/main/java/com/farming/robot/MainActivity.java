package com.farming.robot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.farming.robot.data.MsgDataBean;
import com.farming.robot.fragment.Camera2BasicFragment;
import com.farming.robot.fragment.ControlFragment;
import com.farming.robot.fragment.IndexFragment;
import com.farming.robot.fragment.SettingFragment;
import com.farming.robot.fragment.SoilFragment;
import com.farming.robot.fragment.VideoFragment;
import com.farming.robot.util.ByteUtil;
import com.farming.robot.util.CommandUtil;
import com.farming.robot.util.NetUtils;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.utils.BytesUtils;
import com.xuhao.didi.core.utils.SLog;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClient;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientIOCallback;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientPool;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerManager;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerShutdown;
import com.xuhao.didi.socket.server.action.ServerActionAdapter;
import com.xuhao.didi.socket.server.impl.OkServerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IClientIOCallback {
private   TextView top_title;
private   TextView top_right;
private IndexFragment indexFragment;
private SoilFragment soilFragment;
private ControlFragment controlFragment;
private VideoFragment videoFragment;
private SettingFragment settingFragment;
private Camera2BasicFragment camera2BasicFragment;



private TextView main_control_index
        ,main_control_soil
        ,main_control_control
        ,main_control_video
        ,main_control_settting;
private LinearLayout main_control_index_layout
    ,main_control_soil_layout
            ,main_control_control_layout
    ,main_control_video_layout
            ,main_control_settting_layout;

private ImageView main_control_settting_img
    ,bottom_text_size_img
            ,main_control_control_img
    ,main_control_soil_img
            ,main_control_index_img;

    private IServerManager mServerManager;

    private TextView mIPTv;

    private int mPort = 6000;
    public String firstIp = "192.168.0.1";


    public IClient iClient;
    private OkSocketOptions mOkOptions,mOkOptions2;
    public int nowPosition=0;



    private PopupWindow mPopupWindow;

    private LinearLayout green_house_ll;


    private float bgAlpha = 1f;
    private boolean bright = false;

    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;


    private TextView toggle_house_1,toggle_house_2,toggle_house_3,toggle_house_4,toggle_house_5,toggle_house_6;

    Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
             switch (msg.what){
                 case CommandUtil.HANDLER_WHAT_RECEIVED:
                     OriginalData originalData= ((OriginalData) msg.obj);
                     
                     String str = ByteUtil.byte2StrByASCII(originalData.getBodyBytes());
                     Log.i("onClientIOServer接收",str);

                     String s="";
                     for (byte b:
                             originalData.getBodyBytes()) {
                         s+=b+" ";
                     }
                     Log.i("onClientIOServer接收数组",s);
                     Log.i("onClientIOServer接收数据",ByteUtil.getRobotReponse(originalData.getBodyBytes()));
                     if(str.contains("aa00")&&indexFragment!=null){
                         indexFragment.setResponseData(ByteUtil.getRobotReponse(originalData.getBodyBytes()));
                     }
                     if(str.contains("aa01")&&soilFragment!=null){
                         soilFragment.setResponseData(ByteUtil.getRobotReponse(originalData.getBodyBytes()));
                     }
                     break;
                  case CommandUtil.HANDLER_WHAT_COMMAND:
                      if(BuildConfig.DEBUG){
                          break;
                      }
                      if(iClient==null){
                          Log.e("client","没有客户端连接");
                          return;
                      }
                        if(nowPosition==0){
                           sendCommand(ByteUtil.getRobotCommand(CommandUtil.GET_INDEX_STR));
                           sendEmptyMessageDelayed(CommandUtil.HANDLER_WHAT_COMMAND,1000*30);
                        }else if(nowPosition==1){
                            sendCommand(ByteUtil.getRobotCommand(CommandUtil.GET_SOIL_STR));
                            sendEmptyMessageDelayed(CommandUtil.HANDLER_WHAT_COMMAND,1000*30);
                        }else {

                        }
                     break;
             }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.default_back).setVisibility(View.INVISIBLE);
        top_right = findViewById(R.id.top_right);
        findViewById(R.id.default_right).setVisibility(View.VISIBLE);
        green_house_ll = findViewById(R.id.green_house_ll);
        mPopupWindow =new PopupWindow();
        initPopuWindow();
        green_house_ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(!mPopupWindow.isShowing()){
                            mPopupWindow.showAsDropDown(green_house_ll, -100, 0);
                        }else{
                            mPopupWindow.dismiss();
                        }

                        return true;
                }

                return true;
            }
        });
        top_title= findViewById(R.id.top_title);
        top_title.setText("地上信息状态");
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        initView();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ServerReceviedByTcp();
//            }
//        }).start();


        toIndexFragment();

        ArrayList<String> connectedIP = BytesUtils.getConnectedIP();
        StringBuilder resultList = new StringBuilder();
        for (String ip : connectedIP) {
            resultList.append(ip);
            resultList.append("\n");
        }
         Log.e("连接上wifi的ip地址",getIPAddress());
        OkServerOptions.setIsDebug(false);

        OkSocketOptions.setIsDebug(false);

        SLog.setIsDebug(true);



//        mServerManager = OkSocket.server(mPort).registerReceiver(new ServerActionAdapter() {
//            @Override
//            public void onServerListening(int serverPort) {
//                Log.i("ServerCallback监听", Thread.currentThread().getName() + " onServerListening,serverPort:" + serverPort);
////                top_right.setText("已开启监听");
//                setRightText("已开启监听");
//            }
//
//            @Override
//            public void onClientConnected(IClient client, int serverPort, IClientPool clientPool) {
//                Log.i("ServerCallback连接", Thread.currentThread().getName() + " onClientConnected,serverPort:" + serverPort + "--ClientNums:" + clientPool.size() + "--ClientTag:" + client.getUniqueTag());
//                client.addIOCallback(MainActivity.this);
////                top_right.setText("已连接");
//                setRightText("已连接");
//                handler.sendEmptyMessage(CommandUtil.HANDLER_WHAT_COMMAND);
////                client.setReaderProtocol(new IReaderProtocol() {
////                    @Override
////                    public int getHeaderLength() {
////                        return 5;
////                    }
////
////                    @Override
////                    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
////                        ByteBuffer bb=ByteBuffer.allocate(header.length);
////                        bb.order(byteOrder);
////                        bb.put(header);
////                        int val=bb.getInt(1);
////
////                        return val;
////                    }
////                });
//            }
//
//            @Override
//            public void onClientDisconnected(IClient client, int serverPort, IClientPool clientPool) {
//                Log.i("ServerCallback断开", Thread.currentThread().getName()
//                        + " onClientDisconnected,serverPort:" + serverPort + "--ClientNums:" + clientPool.size() + "--ClientTag:"
//                        +"原因"+ client.getUniqueTag());
////                top_right.setText("断开");
//                setRightText("断开");
////                                client.removeIOCallback(DemoActivity.this);
////                mServerManager.listen();
//            }
//
//            @Override
//            public void onServerWillBeShutdown(int serverPort, IServerShutdown shutdown, IClientPool clientPool, Throwable throwable) {
//                Log.i("ServerCallback快要断开", Thread.currentThread().getName() + " onServerWillBeShutdown,serverPort:" + serverPort + "--ClientNums:" + clientPool
//                        .size());
//                shutdown.shutdown();
////                top_right.setText("即将断开");
//                setRightText("即将断开");
//            }
//
//            @Override
//            public void onServerAlreadyShutdown(int serverPort) {
//                Log.i("ServerCallback已经关了", Thread.currentThread().getName() + " onServerAlreadyShutdown,serverPort:" + serverPort);
////                top_right.setText("服务器关闭了");
//                setRightText("服务器关闭了");
//            }
//        });

//        if (!mServerManager.isLive()) {
//            top_right.setText("服务未开启");
//        } else {
//            top_right.setText("已启动");
//        }
//        top_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startServer();
//            }
//        });
//   startServer();

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        boolean connected = NetUtils.isWifiConnected(mWifiManager);
        StateResult stateResult = check();
        Log.e("wifi结果",wifiInfo.getSSID()+"");
        initManager();
        initManager2();
        if (mManager == null) {
            return;
        }
        if (!mManager.isConnect()) {
            initManager();
            mManager.connect();
            top_right.setText("正在连接\n"+firstIp);
            initManager2();
            mManager2.connect();
//            mIPET.setEnabled(false);
//            mPortET.setEnabled(false);
        } else {
//            mConnect.setText("Disconnecting");
            mManager.disconnect();
        }
    }
    private StateResult check() {
        StateResult result = checkPermission();
//        if (!result.permissionGranted) {
//            return result;
//        }
//        result = checkLocation();
//        result.permissionGranted = true;
//        if (result.locationRequirement) {
//            return result;
//        }
        result = checkWifi();
        result.permissionGranted = true;
        result.locationRequirement = false;
        return result;
    }
    private void initPopuWindow(){
        // 设置布局文件
        View view=LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        mPopupWindow.setContentView(view);

      List<TextView> lts=new ArrayList<>();


        toggle_house_1=view.findViewById(R.id.toggle_house_1);
        lts.add(toggle_house_1);
        toggle_house_2=view.findViewById(R.id.toggle_house_2);
        lts.add(toggle_house_2);
        toggle_house_3=view.findViewById(R.id.toggle_house_3);
        lts.add(toggle_house_3);
        toggle_house_4=view.findViewById(R.id.toggle_house_4);
        lts.add(toggle_house_4);
        toggle_house_5=view.findViewById(R.id.toggle_house_5);
        lts.add(toggle_house_5);
        toggle_house_6=view.findViewById(R.id.toggle_house_6);
        lts.add(toggle_house_6);
        for (int i = 0; i < lts.size(); i++) {
            lts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent();
                    if(android.os.Build.VERSION.SDK_INT >= 11){
                        //Honeycomb
                        i.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
                    }else{
                        //other versions
                        i.setClassName("com.android.settings"
                                , "com.android.settings.wifi.WifiSettings");
                    }
                    startActivity(i);


//                    Intent intent = new Intent();
//                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                    intent.setAction("android.intent.action.MAIN");
//                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$TetherSettingsActivity");
//                    intent.setComponent(cn);
//                    startActivity(intent);
                    mPopupWindow.dismiss();
                }
            });
        }
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(false);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
//        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量

        // 设置pop关闭监听，用于改变背景透明度



    }



private void initView(){
    main_control_index=findViewById(R.id.main_control_index);
    main_control_soil=findViewById(R.id.main_control_soil);
    main_control_control=findViewById(R.id.main_control_control);
    main_control_video=findViewById(R.id.main_control_video);
    main_control_settting=findViewById(R.id.main_control_settting);


    main_control_index_layout=findViewById(R.id.main_control_index_layout);
            main_control_soil_layout=findViewById(R.id.main_control_soil_layout);
    main_control_control_layout=findViewById(R.id.main_control_control_layout);
            main_control_video_layout=findViewById(R.id.main_control_video_layout);
    main_control_settting_layout=findViewById(R.id.main_control_settting_layout);



    main_control_settting_img=findViewById(R.id.main_control_settting_img);
            bottom_text_size_img=findViewById(R.id.bottom_text_size_img);
    main_control_control_img=findViewById(R.id.main_control_control_img);
            main_control_soil_img=findViewById(R.id.main_control_soil_img);
    main_control_index_img=findViewById(R.id.main_control_index_img);





    main_control_index_layout.setOnClickListener(this);
            main_control_soil_layout.setOnClickListener(this);
    main_control_control_layout.setOnClickListener(this);
            main_control_video_layout.setOnClickListener(this);
    main_control_settting_layout.setOnClickListener(this);

}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_control_index_layout://首页
                top_title.setText("地上信息状态");
                toIndexFragment();
                break;
            case R.id.main_control_soil_layout://土壤
                top_title.setText("土壤信息状态");
                toSoilFragment();
                break;
            case R.id.main_control_control_layout://控制
                top_title.setText("机器人及大棚设备控制");
                toControlFragment();
                break;
            case R.id.main_control_video_layout://视频
                top_title.setText("视频");
                toVideoFragment();
                break;
            case R.id.main_control_settting_layout://设置
                top_title.setText("设置");
                toSettingFragment();
                break;
        }
    }
    private void toIndexFragment(){
        nowPosition=0;
        handler.sendEmptyMessage(CommandUtil.HANDLER_WHAT_COMMAND);
       scalBottom(0,main_control_index_img
               ,main_control_index
               ,main_control_soil_img
               ,main_control_soil
               ,main_control_control_img
               ,main_control_control
               ,bottom_text_size_img
               ,main_control_video
               ,main_control_settting_img
               ,main_control_settting);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(indexFragment==null){
            indexFragment=new IndexFragment();
//            transaction.add(R.id.main_activity_fragment,indexFragment,"a");
        }

        transaction.replace(R.id.main_activity_fragment,indexFragment);
        transaction.commit();
    }
    private void toSoilFragment(){
        nowPosition=1;
        handler.sendEmptyMessage(CommandUtil.HANDLER_WHAT_COMMAND);
        scalBottom(2,main_control_index_img
                ,main_control_index
                ,main_control_soil_img
                ,main_control_soil
                ,main_control_control_img
                ,main_control_control
                ,bottom_text_size_img
                ,main_control_video
                ,main_control_settting_img
                ,main_control_settting);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(soilFragment==null){
            soilFragment=new SoilFragment();
//            transaction.add(R.id.main_activity_fragment,soilFragment,"b");
        }
        transaction.replace(R.id.main_activity_fragment,soilFragment);
//        transaction.show(soilFragment);
        transaction.commit();
    }
    private void toControlFragment(){
        nowPosition=2;
        scalBottom(4,main_control_index_img
                ,main_control_index
                ,main_control_soil_img
                ,main_control_soil
                ,main_control_control_img
                ,main_control_control
                ,bottom_text_size_img
                ,main_control_video
                ,main_control_settting_img
                ,main_control_settting);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(controlFragment==null){
            controlFragment=new ControlFragment();
//            transaction.add(R.id.main_activity_fragment,soilFragment,"b");
        }
        transaction.replace(R.id.main_activity_fragment,controlFragment);
//        transaction.show(soilFragment);
        transaction.commit();
    }
    private void toVideoFragment(){

        nowPosition=3;
        scalBottom(6,main_control_index_img
                ,main_control_index
                ,main_control_soil_img
                ,main_control_soil
                ,main_control_control_img
                ,main_control_control
                ,bottom_text_size_img
                ,main_control_video
                ,main_control_settting_img
                ,main_control_settting);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(camera2BasicFragment==null){
            camera2BasicFragment=new Camera2BasicFragment();
//            transaction.add(R.id.main_activity_fragment,soilFragment,"b");
        }
        transaction.replace(R.id.main_activity_fragment,camera2BasicFragment);
//        transaction.show(soilFragment);
        transaction.commit();
    }
    private void toSettingFragment(){
        nowPosition=4;
        scalBottom(8,main_control_index_img
                ,main_control_index
                ,main_control_soil_img
                ,main_control_soil
                ,main_control_control_img
                ,main_control_control
                ,bottom_text_size_img
                ,main_control_video
                ,main_control_settting_img
                ,main_control_settting);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(settingFragment==null){
            settingFragment=new SettingFragment();
//            transaction.add(R.id.main_activity_fragment,soilFragment,"b");
        }
        transaction.replace(R.id.main_activity_fragment,settingFragment);
//        transaction.show(soilFragment);
        transaction.commit();
    }

    private void scalBottom(int postion,View... views){



        for (int i = 0; i < views.length; i++) {

            if (i==postion||i==(postion+1)){
              if(views[i] instanceof TextView){
                  ((TextView) views[i]).setTextSize(px2sp(getResources().getDimensionPixelSize(R.dimen.bottom_text_size_big)));
                  ((TextView) views[i]).setTextColor(ContextCompat.getColor(MainActivity.this,R.color.switch_on));
              }else{
                    ObjectAnimator     objectAnimatorX =ObjectAnimator.ofFloat(views[i],"scaleX",1f,1.5f);
                    ObjectAnimator  objectAnimatorY =ObjectAnimator.ofFloat(views[i],"scaleY",1f,1.5f);
                    AnimatorSet animatorSet =new AnimatorSet();
                    animatorSet.setDuration(300);
                    animatorSet.playTogether(objectAnimatorX,objectAnimatorY);
                    animatorSet.start();
                    if(views[i] instanceof ImageView){
                        DrawableCompat.setTintList(((ImageView) views[i]).getDrawable()
                                , ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this,R.color.switch_on)));
                    }

                }



            }else {
                if(views[i] instanceof TextView){
                    ((TextView) views[i]).setTextSize(px2sp(getResources().getDimensionPixelSize(R.dimen.bottom_text_size)));

                    ((TextView) views[i]).setTextColor(ContextCompat.getColor(MainActivity.this,R.color.main_gray));
                }else{
                    ObjectAnimator objectAnimatorX =ObjectAnimator.ofFloat(views[i],"scaleX",1f);
                    ObjectAnimator objectAnimatorY =ObjectAnimator.ofFloat(views[i],"scaleY",1f);

                    AnimatorSet animatorSet =new AnimatorSet();
                    animatorSet.setDuration(300);
                    animatorSet.playTogether(objectAnimatorX,objectAnimatorY);
                    animatorSet.start();

                    if(views[i] instanceof ImageView){
                        DrawableCompat.setTintList(((ImageView) views[i]).getDrawable()
                                , ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this,R.color.main_gray)));
                    }

                }

            }
            }





    }
    public  int px2sp(float pxValue){
        float fontScale=getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    public void ServerReceviedByTcp() {
        // 声明一个ServerSocket对象
        ServerSocket serverSocket = null;
        try {
            // 创建一个ServerSocket对象，并让这个Socket在1989端口监听
            serverSocket = new ServerSocket(6000);
            // 调用ServerSocket的accept()方法，接受客户端所发送的请求，
            // 如果客户端没有发送数据，那么该线程就停滞不继续
            Socket socket = serverSocket.accept();
            // 从Socket当中得到InputStream对象
            InputStream inputStream = socket.getInputStream();
            byte buffer[] = new byte[1024 * 4];
            int temp = 0;
            // 从InputStream当中读取客户端所发送的数据
            while ((temp = inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, temp));
                Log.e("socket_farm",new String(buffer, 0, temp));
                Log.e("socket_farm2",  serverSocket.getInetAddress().toString());
            }
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClientRead(OriginalData originalData, IClient client, IClientPool<IClient, String> clientPool) {
        
        Message message =handler.obtainMessage();
        message.what= CommandUtil.HANDLER_WHAT_RECEIVED;
        message.obj=originalData;
        handler.sendMessage(message);
   
    }

    @Override
    public void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool) {
        byte[] bytes = sendable.parse();
        bytes = Arrays.copyOfRange(bytes, 4, bytes.length);
        String str = ByteUtil.bytes2HexStr(bytes);
        Log.i("onClientIOServer发送",str);
    }
    private void startServer(){
        if (!mServerManager.isLive()) {
            mServerManager.listen();
        } else {
            mServerManager.shutdown();
        }
    }

    @Override
    protected void onStop() {
//        mServerManager.shutdown();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public  void sendCommand(String cmd){
        if(mManager==null){
          Log.e("ServerCallback","client为空");
            return;
        }
        mManager.send(new MsgDataBean(
//                        "43 4D 44 5F 4B 45 59 41 30 32 02 0C 01 45 4E 44"));
                cmd));
    }
    public void setRightText(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                top_right.setText(str);
            }
        });
    }

    public String getIPAddress() {
        NetworkInfo info = ((ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
//            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
//                try {
//                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
//                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                        NetworkInterface intf = en.nextElement();
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                                return inetAddress.getHostAddress();
//                            }
//                        }
//                    }
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//
//            } else
//                if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                if (ipAddress == 0) return "未连接wifi";
                return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                        + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff))+wifiInfo.getBSSID()+wifiInfo.getSSID();
//            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return "当前无网络连接,请在设置中打开网络";
        }
//        return "IP获取失败";
    }


    private ConnectionInfo mInfo,mInfo2;
    private IConnectionManager mManager,mManager2;
    private SocketActionAdapter adapter = new SocketActionAdapter() {

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            MsgDataBean msgDataBean =        new MsgDataBean(
//                        "43 4D 44 5F 4B 45 59 41 30 32 02 0C 01 45 4E 44"));
                    "434D445F4B4559413032020C01454E44");
            mManager.send(msgDataBean);
            Log.e("作为客户端","连接成功,当前IP"+info.getIp());
            setRightText("连接成功,IP:"+info.getIp());

//            mManager.send(new HandShakeBean());
//            mConnect.setText("DisConnect");
//            mIPET.setEnabled(false);
//            mPortET.setEnabled(false);
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            Log.e("作为客户端","连接断开");
//            if (e != null) {
//                logSend("异常断开(Disconnected with exception):" + e.getMessage());
//            } else {
//                logSend("正常断开(Disconnect Manually)");
//            }
//            mConnect.setText("Connect");
//            mIPET.setEnabled(true);
//            mPortET.setEnabled(true);
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
//            logSend("连接失败(Connecting Failed)");
//            mConnect.setText("Connect");
//            mIPET.setEnabled(true);
//            mPortET.setEnabled(true);

            Log.e("作为客户端111","连接失败,当前IP"+info.getIp());

   int ipLast =   ( Integer.parseInt(firstIp.split("\\.")[3])+1)  ;
   if(ipLast>255){
       Log.e("扫描失败","扫描失败");
   }
   firstIp=firstIp.split("\\.")[0]+"."
           +firstIp.split("\\.")[1]+"."
           + firstIp.split("\\.")[2]+"."
        +ipLast;
            initManager();
            mManager.connect();
            setRightText("正在连接\n"+firstIp);
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
            Log.e("作为客户端1收到",str);
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Log.e("作为客户端1发送",str);
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Log.e("作为客户端",str);
        }
    };

    private SocketActionAdapter adapter2 = new SocketActionAdapter() {

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            MsgDataBean msgDataBean =        new MsgDataBean(
//                        "43 4D 44 5F 4B 45 59 41 30 32 02 0C 01 45 4E 44"));
                    "434D445F4B4559413032020C01454E44");
            mManager.send(msgDataBean);
            Log.e("作为客户端2","连接成功");


//            mManager.send(new HandShakeBean());
//            mConnect.setText("DisConnect");
//            mIPET.setEnabled(false);
//            mPortET.setEnabled(false);
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            Log.e("作为客户端2","连接断开");
//            if (e != null) {
//                logSend("异常断开(Disconnected with exception):" + e.getMessage());
//            } else {
//                logSend("正常断开(Disconnect Manually)");
//            }
//            mConnect.setText("Connect");
//            mIPET.setEnabled(true);
//            mPortET.setEnabled(true);
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
//            logSend("连接失败(Connecting Failed)");
//            mConnect.setText("Connect");
//            mIPET.setEnabled(true);
//            mPortET.setEnabled(true);

            Log.e("作为客户端2","连接失败");
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
            Log.e("作为客户端",str);
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Log.e("作为客户端",str);
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Log.e("作为客户端",str);
        }
    };


    private void initManager() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo(firstIp, 6000);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setConnectTimeoutSecond(10)

                .setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
                    @Override
                    public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                        handler.post(runnable);
                    }
                })
                .build();

        mManager = OkSocket.open(mInfo).option(mOkOptions);

        mManager.registerReceiver(adapter);
    }
    private void initManager2() {
        final Handler handler = new Handler();
        mInfo2 = new ConnectionInfo("192.168.0.103", 6000);
        mOkOptions2 = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setConnectTimeoutSecond(2)

                .setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
                    @Override
                    public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                        handler.post(runnable);
                    }
                })
                .build();

        mManager2 = OkSocket.open(mInfo2).option(mOkOptions2);

        mManager.registerReceiver(adapter2);
    }

    protected static class StateResult {
        public CharSequence message = null;

        public boolean permissionGranted = false;

        public boolean locationRequirement = false;

        public boolean wifiConnected = false;
        public boolean is5G = false;
        public InetAddress address = null;
        public String ssid = null;
        public byte[] ssidBytes = null;
        public String bssid = null;
    }
    protected StateResult checkPermission() {
        StateResult result = new StateResult();
        result.permissionGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean locationGranted = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
            if (!locationGranted) {
                String[] splits = "APP require Location permission to get Wi-Fi information. \nClick to request permission".split("\n");
                if (splits.length != 2) {
                    throw new IllegalArgumentException("Invalid String @RES esptouch_message_permission");
                }
                SpannableStringBuilder ssb = new SpannableStringBuilder(splits[0]);
                ssb.append('\n');
                SpannableString clickMsg = new SpannableString(splits[1]);
                ForegroundColorSpan clickSpan = new ForegroundColorSpan(0xFF0022FF);
                clickMsg.setSpan(clickSpan, 0, clickMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                ssb.append(clickMsg);
                result.message = ssb;
                return result;
            }
        }

        result.permissionGranted = true;
        return result;
    }

    protected StateResult checkLocation() {
        StateResult result = new StateResult();
        result.locationRequirement = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            LocationManager manager = getSystemService(LocationManager.class);
            boolean enable = manager != null && LocationManagerCompat.isLocationEnabled(manager);
            if (!enable) {
                result.message = "Please turn on GPS to get Wi-Fi information.";
                return result;
            }
        }

        result.locationRequirement = false;
        return result;
    }
    private WifiManager mWifiManager;
    protected StateResult checkWifi() {
        StateResult result = new StateResult();
        result.wifiConnected = false;
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        boolean connected = NetUtils.isWifiConnected(mWifiManager);
//        if (!connected) {
//            result.message = getString(R.string.esptouch_message_wifi_connection);
//            return result;
//        }

        String ssid = NetUtils.getSsidString(wifiInfo);
        int ipValue = wifiInfo.getIpAddress();
        if (ipValue != 0) {
            result.address = NetUtils.getAddress(wifiInfo.getIpAddress());
        } else {
            result.address = NetUtils.getIPv4Address();
            if (result.address == null) {
                result.address = NetUtils.getIPv6Address();
            }
        }

        result.wifiConnected = true;
        result.message = "";
        result.is5G = NetUtils.is5G(wifiInfo.getFrequency());
        if (result.is5G) {
            result.message = "";
        }
        result.ssid = ssid;
        result.ssidBytes = NetUtils.getRawSsidBytesOrElse(wifiInfo, ssid.getBytes());
        result.bssid = wifiInfo.getBSSID();

        return result;
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(ev.getAction()==MotionEvent.ACTION_DOWN){
//            if(ev.getRawX()>green_house_ll.getX()
//            &&ev.getRawY()<green_house_ll.getY()
//            ){
//                return true;
//            }
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}