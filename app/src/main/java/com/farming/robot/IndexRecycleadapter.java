package com.farming.robot;

import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.farming.robot.entity.IndexEntity;

import org.jetbrains.annotations.NotNull;

/**
 * @author: lenovo
 * @date: 2020/8/26
 */
public class IndexRecycleadapter extends BaseQuickAdapter<IndexEntity, BaseViewHolder>  {
    public IndexRecycleadapter() {
        super(R.layout.index_item,null);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IndexEntity indexEntity) {
        switch (baseViewHolder.getLayoutPosition()){
            case 0:
                //温度
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.tempature));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  温度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("37");
                break;
            case 1:
                //湿度
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.humidity));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  湿度  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("288%rh");
                break;
            case 2:
                //光照
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.light));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  光照  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("377lx");
                break;
            case 3:
                //臭氧
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ozone));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  臭氧  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("44ppm");
                break;
            case 4:
                //二氧化碳
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.carbon));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("二氧化碳");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("55ppm");
                break;
            case 5:
                //水位
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.liquid));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("  水位  ");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("高");
                break;
            case 6:
                //工作状态
                ((ImageView) baseViewHolder.getView(R.id.index_img))
                        .setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.status));
                ((TextView) baseViewHolder.getView(R.id.index_right_top)).setText("工作状态");
                ((TextView) baseViewHolder.getView(R.id.index_right_bottom)).setText("正常");
                break;
        }
    }
}
