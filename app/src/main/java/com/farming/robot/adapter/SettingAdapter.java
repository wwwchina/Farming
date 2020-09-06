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
public class SettingAdapter extends BaseQuickAdapter<IndexEntity, BaseViewHolder>  {
    public SettingAdapter() {
        super(R.layout.setting_item,null);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IndexEntity indexEntity) {
        switch (baseViewHolder.getLayoutPosition()){
            case 0:
                baseViewHolder.setText(R.id.setting_bottom,"卷帘");
                break;
            case 1:
                baseViewHolder.setText(R.id.setting_bottom,"补光");
                break;
            case 2:
                baseViewHolder.setText(R.id.setting_bottom,"滴罐");
                break;
            case 3:
                baseViewHolder.setText(R.id.setting_bottom,"音乐");
                break;
            case 4:
                baseViewHolder.setText(R.id.setting_bottom,"二氧化碳");
                break;
            case 5:
                baseViewHolder.setText(R.id.setting_bottom,"臭氧");
                break;
        }
  }
}
