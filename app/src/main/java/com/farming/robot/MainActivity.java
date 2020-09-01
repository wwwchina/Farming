package com.farming.robot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.farming.robot.fragment.ControlFragment;
import com.farming.robot.fragment.IndexFragment;
import com.farming.robot.fragment.SettingFragment;
import com.farming.robot.fragment.SoilFragment;
import com.farming.robot.fragment.VideoFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private   TextView top_title;
private IndexFragment indexFragment;
private SoilFragment soilFragment;
private ControlFragment controlFragment;
private VideoFragment videoFragment;
private SettingFragment settingFragment;
private TextView main_control_index,main_control_soil,main_control_control,main_control_video,main_control_settting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.default_back).setVisibility(View.INVISIBLE);
        top_title= findViewById(R.id.top_title);
        top_title.setText("地上信息状态");
        initView();


        toIndexFragment();


    }
private void initView(){
    main_control_index=findViewById(R.id.main_control_index);
    main_control_soil=findViewById(R.id.main_control_soil);
    main_control_control=findViewById(R.id.main_control_control);
    main_control_video=findViewById(R.id.main_control_video);
    main_control_settting=findViewById(R.id.main_control_settting);


    main_control_index.setOnClickListener(this);
    main_control_soil.setOnClickListener(this);
    main_control_control.setOnClickListener(this);
    main_control_video.setOnClickListener(this);
    main_control_settting.setOnClickListener(this);
}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_control_index://首页
                top_title.setText("地上信息状态");
                toIndexFragment();
                break;
            case R.id.main_control_soil://土壤
                top_title.setText("土壤信息状态");
                toSoilFragment();
                break;
            case R.id.main_control_control://控制
                top_title.setText("机器人及大鹏设备控制");
                break;
            case R.id.main_control_video://视频
                top_title.setText("视频");
                break;
            case R.id.main_control_settting://设置
                top_title.setText("设置");
                break;
        }
    }
    private void toIndexFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(indexFragment==null){
            indexFragment=new IndexFragment();
//            transaction.add(R.id.main_activity_fragment,indexFragment,"a");
        }

        transaction.replace(R.id.main_activity_fragment,indexFragment);
        transaction.commit();
    }
    private void toSoilFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(soilFragment==null){
            soilFragment=new SoilFragment();
//            transaction.add(R.id.main_activity_fragment,soilFragment,"b");
        }
        transaction.replace(R.id.main_activity_fragment,soilFragment);
//        transaction.show(soilFragment);
        transaction.commit();
    }
}