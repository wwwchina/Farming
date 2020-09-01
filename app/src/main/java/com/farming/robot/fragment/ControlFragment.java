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
import com.farming.robot.adapter.ControlRecycleAdapter;
import com.farming.robot.entity.IndexEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class ControlFragment extends Fragment {
    View view;
    RecyclerView control_recycler_view;
    List<IndexEntity> indexEntities = new ArrayList<>();
    ControlRecycleAdapter controlRecycleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.control_layout,container,false);
        control_recycler_view=view.findViewById(R.id.control_recycler_view);
      initList();
        return view;
    }
    private void initList(){
        controlRecycleAdapter=new ControlRecycleAdapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        control_recycler_view.setLayoutManager(gridLayoutManager);
        control_recycler_view.setAdapter(controlRecycleAdapter);
        indexEntities.clear();
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));

        controlRecycleAdapter.setNewInstance(indexEntities);
    }
}
