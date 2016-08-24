package com.wenjiehe.android_study;

import java.util.ArrayList;

/**
 * Created by wenjie on 16/08/24.
 */
public class SpinnerAdapter  extends MyAdapter{

    public SpinnerAdapter(ArrayList mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    @Override
    public void bindView(Object holder, Object obj, int type) {
        ((ViewHolder)holder).setText(R.id.txt_name, "000");
    }
}
