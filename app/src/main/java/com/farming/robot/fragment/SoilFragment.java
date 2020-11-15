package com.farming.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farming.robot.IndexRecycleAdapter;
import com.farming.robot.R;
import com.farming.robot.adapter.SoilRecycleAdapter;
import com.farming.robot.entity.IndexEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class SoilFragment extends Fragment {
    View view;
    RecyclerView soil_recycler_view;
    List<IndexEntity> indexEntities = new ArrayList<>();
    SoilRecycleAdapter soilRecycleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.soil_layout,container,false);
        soil_recycler_view=view.findViewById(R.id.soil_recycler_view);
      initList();
        return view;
    }
    private void initList(){
        soilRecycleAdapter=new SoilRecycleAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        soil_recycler_view.setLayoutManager(gridLayoutManager);
        soil_recycler_view.setAdapter(soilRecycleAdapter);
        indexEntities.clear();
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));

        soilRecycleAdapter.setNewInstance(indexEntities);
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

        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(0).setStr3(datas[2]+datas[3]);//温度
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(1).setStr3(datas[0]+datas[1]);//湿度
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(2).setStr3(datas[4]+datas[5]);//盐分
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(3).setStr3(datas[6]+datas[7]);//PH值
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(4).setStr3(datas[8]+datas[9]);//氮
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(5).setStr3(datas[10]+datas[11]);//磷
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(6).setStr3(datas[12]+datas[13]);//钾
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(7).setStr3(datas[14]+datas[15]);//电导率
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(8).setStr3(datas[16]+datas[17]);//热通量
        ((List<IndexEntity> ) soilRecycleAdapter.getData()).get(9).setStr3(datas[18]);//滴罐阀门
        soilRecycleAdapter.notifyDataSetChanged();

    }
}
