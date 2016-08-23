package com.wenjiehe.android_study;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by wenjie on 16/08/23.
 */
class adapter extends MyAdapter{
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_PIC = 1;

    //private ArrayList<Object> mData;
    //private int mLayoutRes;           //布局id

    adapter(ArrayList<Object> mData, int mLayoutRes){
        super(mData,mLayoutRes);
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof SignText)
            return TYPE_TEXT;
        else if (mData.get(position) instanceof SignPic)
            return TYPE_PIC;
        else
            return super.getItemViewType(position);
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if(convertView == null){
            switch (type){
                case TYPE_PIC:
                    holder1 = ViewHolder1.bind(parent.getContext(),convertView,parent,R.layout.pic_list,position);
                    bindView(holder1,mData.get(position),type);
                    convertView = holder1.item;
                    convertView.setTag(R.id.Tag_Pic,holder1);
                    /*holder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_list, parent, false);
                    holder1.img_icon = (ImageView) convertView.findViewById(R.id.im);
                    holder1.txt_aname = (TextView) convertView.findViewById(R.id.pic_content);
                    convertView.setTag(R.id.Tag_Pic,holder1);*/
                    break;
                case TYPE_TEXT:
                    holder2 = new ViewHolder2();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_list, parent, false);
                    holder2.txt_bname = (TextView) convertView.findViewById(R.id.txt_content);
                    holder2.txt_bauthor = (TextView) convertView.findViewById(R.id.txt_content2);
                    convertView.setTag(R.id.Tag_Text,holder2);
                    break;
            }
        }else{
            switch (type){
                case TYPE_PIC:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.Tag_Pic);
                    break;
                case TYPE_TEXT:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.Tag_Text);
                    break;
            }
        }

        Object obj = mData.get(position);
        //设置下控件的值
        switch (type){
            case TYPE_PIC:
                SignPic app = (SignPic) obj;
                if(app != null){
                    holder1.img_icon.setImageResource(R.drawable.imageview_scale);
                    holder1.txt_aname.setText(app.getSign());
                }
                break;
            case TYPE_TEXT:
                SignText book = (SignText) obj;
                if(book != null){
                    holder2.txt_bname.setText(book.getSign());
                    holder2.txt_bauthor.setText(book.getSign());
                }
                break;
        }
        return convertView;
    }


    public void bindView(ViewHolder1 holder, Object obj,int type) {
        if(type==TYPE_TEXT) {
            //(ViewHolder2)holder.setText(R.id.txt_content,"TYPE_TEXT");
    }
        else{
            (holder).img_icon = (ImageView) holder.item.findViewById(R.id.im);
            (holder).txt_aname = (TextView) holder.item.findViewById(R.id.pic_content);
            //holder.item.setTag(R.id.Tag_Pic,holder);
            //((ViewHolder1)holder).setText(R.id.pic_content,"TYPE_PIC");
            //holder.setImageResource(R.id.im,R.drawable.imageview_scale);
        }

    }


    //两个不同的ViewHolder
    private static class ViewHolder1{
        ImageView img_icon;
        TextView txt_aname;
        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文


        //构造方法，完成相关初始化
        public ViewHolder1(Context context, ViewGroup parent, int layoutRes) {

            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            //convertView.setTag(R.id.Tag_Pic,this);
            item = convertView;
        }

        //绑定ViewHolder与item
        public static ViewHolder1 bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder1 holder;
            if (convertView == null) {
                holder = new ViewHolder1(context, parent, layoutRes);
            } else {
                holder = (ViewHolder1) convertView.getTag(R.id.Tag_Pic);
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }
    }

    private static class ViewHolder2{
        TextView txt_bname;
        TextView txt_bauthor;
    }
}
