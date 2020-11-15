package com.farming.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farming.robot.IndexRecycleAdapter;
import com.farming.robot.MainActivity;
import com.farming.robot.R;
import com.farming.robot.adapter.ControlRecycleAdapter;
import com.farming.robot.data.MsgDataBean;
import com.farming.robot.entity.IndexEntity;
import com.farming.robot.util.CommandUtil;

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
    TextView open_device;
    TextView  control_speed_l,
               control_speed_m,
               control_speed_h,
               control_speed_s;
    TextView control_wind_l,
             control_wind_m,
            control_wind_h,
            control_wind_s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.control_layout,container,false);
        control_recycler_view=view.findViewById(R.id.control_recycler_view);

        open_device=view.findViewById(R.id.open_device);
        control_speed_l=view.findViewById(R.id.control_speed_l);
        control_speed_m=view.findViewById(R.id.control_speed_m);
        control_speed_h=view.findViewById(R.id.control_speed_h);
        control_speed_s=view.findViewById(R.id.control_speed_s);
        control_wind_l=view.findViewById(R.id.control_wind_l);
        control_wind_m=view.findViewById(R.id.control_wind_m);
        control_wind_h=view.findViewById(R.id.control_wind_h);
        control_wind_s=view.findViewById(R.id.control_wind_s);
        open_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020C01454E44");
                ((MainActivity) getActivity()).sendCommand(CommandUtil.OPEN_DEVICE_COMMAND_HEX);
            }
        });
        control_speed_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_speed_l.setSelected(true);
                control_speed_m.setSelected(false);
                control_speed_h.setSelected(false);
                control_speed_s.setSelected(false);
                //低速
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020101454E44");

            }
        });
        control_speed_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_speed_l.setSelected(false);
                control_speed_m.setSelected(true);
                control_speed_h.setSelected(false);
                control_speed_s.setSelected(false);
                //中速
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020102454E44");
            }
        });
        control_speed_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_speed_l.setSelected(false);
                control_speed_m.setSelected(false);
                control_speed_h.setSelected(true);
                control_speed_s.setSelected(false);
                //高速
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020103454E44");
            }
        });


        control_wind_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_wind_l.setSelected(true);
                control_wind_m.setSelected(false);
                control_wind_h.setSelected(false);
                control_wind_s.setSelected(false);
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020201454E44");

            }
        });

        control_wind_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_wind_l.setSelected(false);
                control_wind_m.setSelected(true);
                control_wind_h.setSelected(false);
                control_wind_s.setSelected(false);
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020202454E44");
            }
        });
        control_wind_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_wind_l.setSelected(false);
                control_wind_m.setSelected(false);
                control_wind_h.setSelected(true);
                control_wind_s.setSelected(false);
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020203454E44");
            }

        });
        control_wind_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control_wind_l.setSelected(false);
                control_wind_m.setSelected(false);
                control_wind_h.setSelected(false);
                control_wind_s.setSelected(true);
                ((MainActivity) getActivity()).sendCommand("434D445F4B4559413032020200454E44");

            }
        });
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
