package com.farming.robot.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.farming.robot.R;
import com.farming.robot.entity.IndexEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class SettingTopAdapter extends BaseQuickAdapter<IndexEntity, BaseViewHolder>  {
    public SettingTopAdapter() {
        super(R.layout.setting_top_item,null);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IndexEntity indexEntity) {
        switch (baseViewHolder.getLayoutPosition()){
            case 0:
                baseViewHolder.setText(R.id.setting_bottom_top,"温度");
                baseViewHolder.getView(R.id.setting_top_img).setBackground(ContextCompat.getDrawable(
                        getContext(),R.drawable.setting_circle_gray
                ));
                break;
            case 1:
                baseViewHolder.setText(R.id.setting_bottom_top,"湿度");
                break;
            case 2:
                baseViewHolder.setText(R.id.setting_bottom_top,"PH值");
                break;
            case 3:
                baseViewHolder.setText(R.id.setting_bottom_top,"氮磷钾");
                break;
            case 4:
                baseViewHolder.setText(R.id.setting_bottom_top,"电导率");
                break;
            case 5:
                baseViewHolder.setText(R.id.setting_bottom_top,"热通量");
                break;
        }
    }
}
