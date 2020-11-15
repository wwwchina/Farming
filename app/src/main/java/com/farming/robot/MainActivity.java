package com.farming.robot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.utils.SLog;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClient;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientIOCallback;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IClientPool;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerManager;
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.IServerShutdown;
import com.xuhao.didi.socket.server.action.ServerActionAdapter;
import com.xuhao.didi.socket.server.impl.OkServerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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
    public IClient iClient;
    private OkSocketOptions mOkOptions;
    public int nowPosition=0;



    private PopupWindow mPopupWindow;

    private LinearLayout green_house_ll;

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
        findViewById(R.id.default_back).setVisibility(View.INVISIBLE);
        top_right = findViewById(R.id.top_right);
        findViewById(R.id.default_right).setVisibility(View.VISIBLE);
        findViewById(R.id.green_house_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        top_title= findViewById(R.id.top_title);
        top_title.setText("地上信息状态");

        initView();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ServerReceviedByTcp();
//            }
//        }).start();


        toIndexFragment();



        OkServerOptions.setIsDebug(false);

        OkSocketOptions.setIsDebug(false);

        SLog.setIsDebug(true);



        mServerManager = OkSocket.server(mPort).registerReceiver(new ServerActionAdapter() {
            @Override
            public void onServerListening(int serverPort) {
                Log.i("ServerCallback监听", Thread.currentThread().getName() + " onServerListening,serverPort:" + serverPort);
//                top_right.setText("已开启监听");
                setRightText("已开启监听");
            }

            @Override
            public void onClientConnected(IClient client, int serverPort, IClientPool clientPool) {
                Log.i("ServerCallback连接", Thread.currentThread().getName() + " onClientConnected,serverPort:" + serverPort + "--ClientNums:" + clientPool.size() + "--ClientTag:" + client.getUniqueTag());
                client.addIOCallback(MainActivity.this);
//                top_right.setText("已连接");
                setRightText("已连接");
                handler.sendEmptyMessage(CommandUtil.HANDLER_WHAT_COMMAND);
//                client.setReaderProtocol(new IReaderProtocol() {
//                    @Override
//                    public int getHeaderLength() {
//                        return 5;
//                    }
//
//                    @Override
//                    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
//                        ByteBuffer bb=ByteBuffer.allocate(header.length);
//                        bb.order(byteOrder);
//                        bb.put(header);
//                        int val=bb.getInt(1);
//
//                        return val;
//                    }
//                });
                iClient =client;
            }

            @Override
            public void onClientDisconnected(IClient client, int serverPort, IClientPool clientPool) {
                Log.i("ServerCallback断开", Thread.currentThread().getName()
                        + " onClientDisconnected,serverPort:" + serverPort + "--ClientNums:" + clientPool.size() + "--ClientTag:"
                        +"原因"+ client.getUniqueTag());
//                top_right.setText("断开");
                setRightText("断开");
//                                client.removeIOCallback(DemoActivity.this);
//                mServerManager.listen();
            }

            @Override
            public void onServerWillBeShutdown(int serverPort, IServerShutdown shutdown, IClientPool clientPool, Throwable throwable) {
                Log.i("ServerCallback快要断开", Thread.currentThread().getName() + " onServerWillBeShutdown,serverPort:" + serverPort + "--ClientNums:" + clientPool
                        .size());
                shutdown.shutdown();
//                top_right.setText("即将断开");
                setRightText("即将断开");
            }

            @Override
            public void onServerAlreadyShutdown(int serverPort) {
                Log.i("ServerCallback已经关了", Thread.currentThread().getName() + " onServerAlreadyShutdown,serverPort:" + serverPort);
//                top_right.setText("服务器关闭了");
                setRightText("服务器关闭了");
            }
        });
        if (!mServerManager.isLive()) {
            top_right.setText("服务未开启");
        } else {
            top_right.setText("已启动");
        }
        top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServer();
            }
        });
   startServer();

    }

    private void initPopuWindow(){
        // 设置布局文件
        mPopupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popup_layout, null));
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(iv_add, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });


    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
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
        mServerManager.shutdown();
        handler.removeCallbacksAndMessages(null);
        super.onStop();
    }

    public  void sendCommand(String cmd){
        if(iClient==null){
          Log.e("ServerCallback","client为空");
            return;
        }
        iClient.send(new MsgDataBean(
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
}