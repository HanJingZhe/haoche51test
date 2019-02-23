package com.example.test_viewpage2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test_viewpage2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author peng_wang
 * @date 2019/2/23
 */
public class MAdapter extends RecyclerView.Adapter<MAdapter.MViewHolder> {

    private Context mContext;
    private List<String> mList;

    public MAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MViewHolder(View.inflate(mContext, R.layout.adapter_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        holder.tvName.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
