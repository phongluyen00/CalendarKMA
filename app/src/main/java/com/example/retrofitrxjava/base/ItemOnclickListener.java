package com.example.retrofitrxjava.base;

import com.example.retrofitrxjava.model.ModelResponse;

public interface ItemOnclickListener<T extends ModelResponse>
        extends BAdapter.ListItemListener {
    void onItemMediaClick(T t);
}
