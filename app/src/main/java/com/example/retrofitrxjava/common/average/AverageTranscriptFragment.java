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
    private LoginResponse.Data userModel;

    @Override
    protected void initLayout() {
        userModel = PrefUtils.loadData(getActivity());
        DetailScoreModel detailScoreModel = userModel.getDetailScoreModel();
        ScoreMediumResponse scoreResponse = userModel.getScoreMediumResponse();
        binding.progressLoadData.setVisibility(View.VISIBLE);
        presenter = new AveragePresenter(this);
        if (isAverage) {
            if (scoreResponse != null) {
                ArrayList<ScoreMediumResponse.Datum> response = new ArrayList<>();
                for (int i = 1; i < scoreResponse.getData().size(); i++) {
                    response.add(scoreResponse.getData().get(i));
                }
                fillDataScore(response);
            } else {
                presenter.retrieveScore(compositeDisposable, myAPI, PrefUtils.loadData(getActivity()).getToken());
            }
        } else {
            if (detailScoreModel != null && detailScoreModel.getData().size() > 0) {
                ArrayList<DetailScoreModel.Data> data = (ArrayList<DetailScoreModel.Data>) detailScoreModel.getData();
                fillData(data);
            } else {
                presenter.retrieveDetailScore(myAPI, PrefUtils.loadData(getActivity()).getToken());
            }
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
    public void retrieveScoreSuccess(ScoreMediumResponse scoreMediumResponse, ArrayList<ScoreMediumResponse.Datum> responses) {
        userModel.setScoreMediumResponse(scoreMediumResponse);
        userModel.setMediumScore(scoreMediumResponse.getData().
                get(scoreMediumResponse.getData().size() - 1).getH4N1());
        PrefUtils.saveData(getActivity(), userModel);
        fillDataScore(responses);
    }

    private void fillDataScore(ArrayList<ScoreMediumResponse.Datum> data) {
        binding.progressLoadData.setVisibility(View.GONE);
        adapter = new BAdapter<>(getContext(), R.layout.item_score);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.lvScore.setLayoutManager(gridLayoutManager);
        binding.lvScore.setAdapter(adapter);
        binding.rlHeader.setVisibility(View.GONE);
        adapter.setData(data);
        if (data != null && data.size() > 0) {
            binding.groupNoData.setVisibility(View.GONE);
        } else {
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

        if (detailScoreModel != null) {
            LoginResponse.Data loadData = PrefUtils.loadData(getActivity());
            loadData.setDetailScoreModel(detailScoreModel);
            PrefUtils.saveData(getActivity(), loadData);
        }

        ArrayList<DetailScoreModel.Data> data = (ArrayList<DetailScoreModel.Data>) detailScoreModel.getData();
        fillData(data);
    }

    private void fillData(ArrayList<DetailScoreModel.Data> data) {
        dataBAdapter = new BAdapter<>(getContext(), R.layout.item_detail_score);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.lvScore.setLayoutManager(layoutManager);
        binding.rlHeader.setVisibility(View.VISIBLE);
        binding.lvScore.setAdapter(dataBAdapter);
        binding.progressLoadData.setVisibility(View.GONE);
        dataBAdapter.setData(data);
        if (data != null && data.size() > 0) {
            binding.groupNoData.setVisibility(View.GONE);
        } else {
            binding.groupNoData.setVisibility(View.VISIBLE);
            binding.rlHeader.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onBackPressed() {
    }

}
