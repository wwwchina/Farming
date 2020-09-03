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

  }
}
