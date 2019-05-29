package com.jiyun.android_lzj.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.jiyun.android_lzj.R;
import com.jiyun.android_lzj.bean.HomeBean;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<HomeBean.ResultsBean>list;
        private Context context;
    private SetOnClick setOnClick;
    private SetLongClick setLongClick;


    public HomeAdapter(ArrayList<HomeBean.ResultsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(context);
        if (viewType==0){
            View inflate = from.inflate(R.layout.layout_list2, null);
            ViewHolder_b viewHolder_b = new ViewHolder_b(inflate);
            return viewHolder_b;
        }else{
            View inflate = from.inflate(R.layout.layout_list1, null);
            ViewHolder_a viewHolder_a = new ViewHolder_a(inflate);
            return viewHolder_a;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HomeBean.ResultsBean bean = list.get(position);
        int type = getItemViewType(position);
        if (type==0){
            ViewHolder_b holder2 = (ViewHolder_b) holder;
            RequestOptions options = RequestOptions.circleCropTransform();
            Glide.with(context).load(bean.getUrl()).apply(options).into(holder2.iv1);
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        setOnClick.SetOn(position,bean);
                }
            });
        }else if (type==1){

            ViewHolder_a holder1 = (ViewHolder_a) holder;
            RoundedCorners corners = new RoundedCorners(50);
            RequestOptions options = RequestOptions.bitmapTransform(corners);
            holder1.tv.setText(bean.getDesc());
            Glide.with(context).load(bean.getUrl()).apply(options).into(holder1.iv);
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("con.xiaotu");
                    intent.putExtra("position",position);
                    context.sendBroadcast(intent);
                    }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder_a extends RecyclerView.ViewHolder {


        private final ImageView iv;
        private final TextView tv;
        public ViewHolder_a(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    public class ViewHolder_b extends RecyclerView.ViewHolder {

        private final ImageView iv1;

        public ViewHolder_b(View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.iv1);
        }
    }

    public void setList(ArrayList<HomeBean.ResultsBean> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==2||position==9||position==17){
            return 0;
        }else {
            return 1;
        }
    }
    public interface SetOnClick{
        void SetOn(int position,HomeBean.ResultsBean bean);
    }
    public void OnClick(SetOnClick setOnClick){
        this.setOnClick = setOnClick;
    }
    public interface SetLongClick{
        void SetLong(int position,HomeBean.ResultsBean bean);
    }
    public void LongClick(SetLongClick setLongClick){

        this.setLongClick = setLongClick;
    }
}
