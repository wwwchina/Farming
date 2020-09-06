package com.farming.robot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
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
import com.farming.robot.fragment.Camera2BasicFragment;
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
}