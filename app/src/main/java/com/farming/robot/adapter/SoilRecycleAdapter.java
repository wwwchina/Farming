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
public class SoilRecycleAdapter extends BaseQuickAdapter<IndexEntity, BaseViewHolder>  {
    public SoilRecycleAdapter() {
        super(R.layout.index_item,null);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IndexEntity indexEntity) {
        switch (baseViewHolder.getLayoutPosition()){
            case 0:
                //温度
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_temperature));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  温度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("37");
                break;
            case 1:
                //湿度
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_humidity));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  湿度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("288%rh");
                break;
            case 2:
                //光照
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_oxygen));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  盐溶解度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("377lx");
                break;
            case 3:
                //臭氧
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_ph));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  酸碱度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("44ppm");
                break;
            case 4:
                //二氧化碳
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_nitrogen));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  氮  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("55ppm");
                break;
            case 5:
                //水位
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_phosphorus));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  磷  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("高");
                break;
            case 6:
                //工作状态
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_potassium));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("钾");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("正常");
                break;
            case 7:
                //工作状态
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_electrical));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("电导率");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("正常");
                break;
            case 8:
                //工作状态
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_traffic));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("热通量");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("正常");
                break;
            case 9:
                //工作状态
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.soil_water));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("滴灌");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("正常");
                break;
        }
    }
}
