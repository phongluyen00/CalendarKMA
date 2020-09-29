package com.example.retrofitrxjava.common.average;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutAverageTranscriptBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.pre.PrefUtils;

import java.util.ArrayList;

public class AverageTranscriptFragment extends BFragment<LayoutAverageTranscriptBinding> implements AverageContract.View {
    private AveragePresenter presenter;
    private boolean isAverage;
    private BAdapter<ScoreMediumResponse.Datum> adapter;
    private BAdapter<DetailScoreModel.Data> dataBAdapter;

    @Override
    protected void initLayout() {
        binding.progressLoadData.setVisibility(View.VISIBLE);
        presenter = new AveragePresenter(this);
        if (isAverage) {
            presenter.retrieveScore(compositeDisposable, myAPI, PrefUtils.loadData(getActivity()).getToken());
        } else {
            presenter.retrieveDetailScore(myAPI, PrefUtils.loadData(getActivity()).getToken());
        }
    }

    public void checkStatus(boolean isAverage) {
        this.isAverage = isAverage;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_average_transcript;
    }

    @Override
    public int getTitle() {
        return R.string.average_transcript;
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> responses) {
        binding.progressLoadData.setVisibility(View.GONE);
        LoginResponse.Data data = PrefUtils.loadData(getActivity());
        data.setMediumScore(responses.get(responses.size() - 1).getTbcH4N1());
        PrefUtils.saveData(getActivity(), data);
        adapter = new BAdapter<>(getContext(), R.layout.item_score);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        binding.lvScore.setLayoutManager(gridLayoutManager);
        binding.lvScore.setAdapter(adapter);
        binding.rlHeader.setVisibility(View.GONE);
        adapter.setData(responses);
        if (responses != null && responses.size() > 0){
            binding.groupNoData.setVisibility(View.GONE);
        }else {
            binding.groupNoData.setVisibility(View.VISIBLE);
            binding.rlHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void retrieveScoreFailed() {
        binding.progressLoadData.setVisibility(View.GONE);
        binding.groupNoData.setVisibility(View.VISIBLE);
        binding.rlHeader.setVisibility(View.GONE);
    }

    @Override
    public void retrieveDetailScoreSuccess(DetailScoreModel detailScoreModel) {
        binding.progressLoadData.setVisibility(View.GONE);
        ArrayList<DetailScoreModel.Data> data = (ArrayList<DetailScoreModel.Data>) detailScoreModel.getData();
        dataBAdapter = new BAdapter<>(getContext(), R.layout.item_detail_score);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.lvScore.setLayoutManager(layoutManager);
        binding.rlHeader.setVisibility(View.VISIBLE);
        binding.lvScore.setAdapter(dataBAdapter);
        dataBAdapter.setData(data);
        if (data != null && data.size() > 0){
            binding.groupNoData.setVisibility(View.GONE);
        }else {
            binding.groupNoData.setVisibility(View.VISIBLE);
            binding.rlHeader.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onBackPressed() {
    }

}
