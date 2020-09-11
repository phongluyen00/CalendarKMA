package com.example.retrofitrxjava.b;

import com.example.retrofitrxjava.model.ModelResponse;

public interface ScoreListener<T extends ModelResponse>
        extends BAdapter.ListItemListener {
    void onItemMediaClick(T t);
}
