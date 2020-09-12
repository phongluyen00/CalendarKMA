package com.example.retrofitrxjava.persional;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;

public class PersonalFragment extends BFragment<LayoutPersonalBinding> implements PersonalContract.View {

    private PersonalPresenter presenter;
    private ArrayList<ScoreMediumResponse.Datum> data = new ArrayList<>();

    public void setData(ArrayList<ScoreMediumResponse.Datum> data) {
        this.data = data;
    }

    @Override
    protected void initLayout() {
        presenter = new PersonalPresenter(this);
        retrieveScoreSuccess(data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_personal;
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> data) {
        adapter = new BAdapter<>(getContext(), R.layout.item_score);
        binding.lvScore.setAdapter(adapter);
        adapter.setData(data);
    }
}
