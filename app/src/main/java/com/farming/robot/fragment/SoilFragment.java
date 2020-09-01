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

import com.farming.robot.IndexRecycleadapter;
import com.farming.robot.R;
import com.farming.robot.entity.IndexEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class SoilFragment extends Fragment {
    View view;
    RecyclerView index_recycler_view;
    List<IndexEntity> indexEntities = new ArrayList<>();
    IndexRecycleadapter indexRecycleadapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.index_layout,container,false);
        index_recycler_view=view.findViewById(R.id.index_recycler_view);
      initList();
        return view;
    }
    private void initList(){
        indexRecycleadapter=new IndexRecycleadapter();
        GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(),2);
        index_recycler_view.setLayoutManager(gridLayoutManager);
        index_recycler_view.setAdapter(indexRecycleadapter);
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));
        indexEntities.add(new IndexEntity("a","b","c"));

        indexRecycleadapter.setNewInstance(indexEntities);
    }
}
