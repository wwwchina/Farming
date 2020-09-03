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
import com.farming.robot.adapter.SettingAdapter;
import com.farming.robot.adapter.SettingTopAdapter;
import com.farming.robot.adapter.SettingUpAdapter;
import com.farming.robot.entity.IndexEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class SettingFragment extends Fragment {
    View view;
    RecyclerView setting_recycler_view,setting_recycler_view_top
    ,setting_recycler_view_up;
    List<IndexEntity> indexEntities = new ArrayList<>();
    List<IndexEntity> indexEntitiesUp = new ArrayList<>();
    List<IndexEntity> indexEntitiesTop = new ArrayList<>();
    SettingAdapter settingAdapter;
    SettingUpAdapter settingUpAdapter;
    SettingTopAdapter settingTopAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.setting_layout,container,false);
        setting_recycler_view=view.findViewById(R.id.setting_recycler_view);
        setting_recycler_view_top=view.findViewById(R.id.setting_recycler_view_top);
        setting_recycler_view_up=view.findViewById(R.id.setting_recycler_view_up);

        initTopList();
        initUpList();
        initList();
        return view;
    }
    private void initList(){
        settingAdapter=new SettingAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),3);
        setting_recycler_view.setLayoutManager(gridLayoutManager);
        setting_recycler_view.setAdapter(settingAdapter);
        indexEntities.clear();
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));

        settingAdapter.setNewInstance(indexEntities);
    }

    private void initTopList(){
        settingUpAdapter=new SettingUpAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        setting_recycler_view_up.setLayoutManager(gridLayoutManager);
        setting_recycler_view_up.setAdapter(settingUpAdapter);
        indexEntitiesUp.clear();
        indexEntitiesUp.add(new IndexEntity("a","b","c"));
        indexEntitiesUp.add(new IndexEntity("a","b","c"));
        indexEntitiesUp.add(new IndexEntity("a","b","c"));
        indexEntitiesUp.add(new IndexEntity("a","b","c"));
        indexEntitiesUp.add(new IndexEntity("a","b","c"));
        indexEntitiesUp.add(new IndexEntity("a","b","c"));

        settingUpAdapter.setNewInstance(indexEntitiesUp);
    }

    private void initUpList(){
        settingTopAdapter=new SettingTopAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        setting_recycler_view_top.setLayoutManager(gridLayoutManager);
        setting_recycler_view_top.setAdapter(settingTopAdapter);
        indexEntitiesTop.clear();
        indexEntitiesTop.add(new IndexEntity("a","b","c"));
        indexEntitiesTop.add(new IndexEntity("a","b","c"));
        indexEntitiesTop.add(new IndexEntity("a","b","c"));
        indexEntitiesTop.add(new IndexEntity("a","b","c"));
        indexEntitiesTop.add(new IndexEntity("a","b","c"));
        indexEntitiesTop.add(new IndexEntity("a","b","c"));

        settingTopAdapter.setNewInstance(indexEntitiesTop);
    }
}
