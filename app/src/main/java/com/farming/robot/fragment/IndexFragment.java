package com.farming.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farming.robot.IndexRecycleAdapter;
import com.farming.robot.MainActivity;
import com.farming.robot.R;
import com.farming.robot.entity.IndexEntity;
import com.farming.robot.util.ByteUtil;
import com.farming.robot.util.CommandUtil;
import com.farming.robot.view.GridDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class IndexFragment extends Fragment {
    View view;
    RecyclerView index_recycler_view;
    List<IndexEntity> indexEntities = new ArrayList<>();
    IndexRecycleAdapter indexRecycleadapter;
    Switch  open_ap,open_station;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.index_layout,container,false);
        index_recycler_view=view.findViewById(R.id.index_recycler_view);
        open_ap=view.findViewById(R.id.open_ap);
        open_station=view.findViewById(R.id.open_station);
        open_ap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MainActivity) getActivity()).sendCommand(ByteUtil.getRobotCommand(CommandUtil.GET_INDEX_STR));
//                ((MainActivity) getActivity()).sendCommand(ByteUtil.getRobotCommand(CommandUtil.GET_RESPONSE_STR));
            }
        });
        open_station.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MainActivity) getActivity()).sendCommand(ByteUtil.getRobotCommand(CommandUtil.GET_SOIL_STR));
            }
        });
      initList();
        return view;
    }
    private void initList(){
        indexRecycleadapter=new IndexRecycleAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        index_recycler_view.setLayoutManager(gridLayoutManager);
        index_recycler_view.addItemDecoration(new GridDivider(getActivity(),2, ContextCompat.getColor(getActivity(),R.color.main_gray)));
        index_recycler_view.setAdapter(indexRecycleadapter);
        indexEntities.clear();
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));

        indexRecycleadapter.setNewInstance(indexEntities);
    }
    public void setResponseData(String data){
//1,5,1,5,1,5,10,0,10,0,1,5,0,0,3
        /*
        1-2空气湿度  (单位0.1%RH) 如：292 H(十六进制)=658=>湿度= 65.8%RH；
3-4空气温度  (单位0.1℃)如：0110H(十六进制)= 272=>温度=27.2℃，低于0℃时以补码形式上传；
5-6空气co2  (单位1ppm) 如：5B4(十六进制)=1460=>CO2=1460ppm；
7-10光照度  (单位1Lux) 如：000206F6 H(十六进制) = 132854=>光照度=132854Lux；
11-12 新增加臭氧(单位1ppm)
13水箱水位   0=缺水、1=有水、2=满、3=注水；
14工作状态  0=停机、1=强风、2=清风，3=微风，4=强喷，5=中喷，6=弱喷；
15网络模式  1=STA、2=AP、3=AP+STA；

         */
        String[] datas=data.split(",");

        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(0).setStr3(datas[2]+datas[3]);//温度
        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(1).setStr3(datas[0]+datas[1]);//湿度
        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(2).setStr3(datas[4]+datas[5]);//二氧化碳
        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(3).setStr3(datas[6]+datas[7]+datas[8]+datas[9]);//光照度
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(4).setStr3(datas[8]+datas[9]);//氮
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(5).setStr3(datas[10]+datas[11]);//磷
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(6).setStr3(datas[12]+datas[13]);//钾
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(7).setStr3(datas[14]+datas[15]);//电导率
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(8).setStr3(datas[16]+datas[17]);//热通量
//        ((List<IndexEntity> ) indexRecycleadapter.getData()).get(9).setStr3(datas[18]);//滴罐阀门
         indexRecycleadapter.notifyDataSetChanged();

    }
}
