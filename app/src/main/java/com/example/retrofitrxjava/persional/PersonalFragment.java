package com.example.retrofitrxjava.persional;

import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.b.ScoreListener;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.persional.model.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PersonalFragment extends BFragment<LayoutPersonalBinding> implements PersonalContract.View, ScoreListener<Data> {

    private PersonalPresenter presenter;
    private ArrayList<ScoreMediumResponse.Datum> data = new ArrayList<>();
    private BAdapter<Data> adapterYear;
    private ArrayList<Data> dataListYear = new ArrayList<>();

    public void setData(ArrayList<ScoreMediumResponse.Datum> data) {
        this.data = data;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initLayout() {
        presenter = new PersonalPresenter(this);
        retrieveScoreSuccess(data);
        for (ScoreMediumResponse.Datum data : data) {
            dataListYear.add(new Data(data.getNamHoc()));
        }
        adapterYear = new BAdapter<>(getContext(), R.layout.item_year);
        binding.rclYear.setAdapter(adapterYear);
        Set<Data> set = new LinkedHashSet<>(dataListYear);
        dataListYear.clear();
        dataListYear.addAll(set);
        adapterYear.setData(dataListYear);
        adapterYear.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_personal;
    }

    @Override
    public int getTitle() {
        return R.string.personal;
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> data) {
        adapter = new BAdapter<>(getContext(), R.layout.item_score);
        binding.lvScore.setAdapter(adapter);
        adapter.setData(data);
    }

    @Override
    public void onItemMediaClick(Data datum) {
        Log.d("aaaaaaaaaa", datum.getYear());
        for (ScoreMediumResponse.Datum data : data) {
            if (datum.getYear().equals(data.getNamHoc())){
                Log.d("AAAAAAAAAA", data.getH4N1());
            }
        }
    }
}
