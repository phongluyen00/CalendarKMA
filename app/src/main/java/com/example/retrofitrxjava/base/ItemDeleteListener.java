package com.example.retrofitrxjava.base;

import com.example.retrofitrxjava.model.ModelResponse;

public interface ItemDeleteListener<T extends ModelResponse>
        extends BaseAdapter.ListItemListener {
    void onDeleteAccount(T t);
    void onEditAccount(T t);
}
