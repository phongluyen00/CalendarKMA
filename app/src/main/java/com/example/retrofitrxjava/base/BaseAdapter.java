package com.example.retrofitrxjava.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitrxjava.BR;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class BaseAdapter<T>
        extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private @LayoutRes
    int resId;
    private LayoutInflater inflater;
    private ArrayList<T> data;
    private ListItemListener listener;
    private ItemDeleteListener itemDeleteListener;

    public BaseAdapter(Context context, @LayoutRes int resId) {
        this.resId = resId;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setListener(ListItemListener listener) {
        this.listener = listener;
    }

    public void setItemDeleteListener(ItemDeleteListener itemDeleteListener) {
        this.itemDeleteListener = itemDeleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, resId,
                viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder viewHolder, int i) {
        T t = data.get(i);
        viewHolder.binding.setVariable(BR.item, t);
        viewHolder.binding.setVariable(BR.listener, listener);
        viewHolder.binding.setVariable(BR.onListener,itemDeleteListener);
        viewHolder.binding.executePendingBindings();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ListItemListener {
    }
}
