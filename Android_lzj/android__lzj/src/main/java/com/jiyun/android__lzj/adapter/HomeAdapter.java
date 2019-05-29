package com.jiyun.android__lzj.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.android__lzj.R;
import com.jiyun.android__lzj.bean.HomeBean;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    ArrayList<HomeBean.NewslistBean>list;
    Context context;
    private SetOnClick setOnClick;

    public HomeAdapter(ArrayList<HomeBean.NewslistBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_list, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final HomeBean.NewslistBean bean = list.get(position);
        RoundedCorners corners = new RoundedCorners(50);
        RequestOptions options = RequestOptions.bitmapTransform(corners);
        Glide.with(context).load(bean.getPicUrl()).apply(options).into(holder.iv);
        holder.tv.setText(bean.getTitle());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (setOnClick!=null){
                    setOnClick.SetOn(position,bean);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public void setList(ArrayList<HomeBean.NewslistBean> list) {
        this.list = list;
    }
    public interface SetOnClick{
        void SetOn(int position,HomeBean.NewslistBean bean);
    }
    public void OnClick(SetOnClick setOnClick){
        this.setOnClick = setOnClick;
    }
}
