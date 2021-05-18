package com.example.retrofitrxjava.common.view;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseAdapter;
import com.example.retrofitrxjava.base.BaseFragment;
import com.example.retrofitrxjava.databinding.LayoutAverageTranscriptBinding;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.utils.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreUserFragment extends BaseFragment<LayoutAverageTranscriptBinding> {
    public static final int BANG_DIEM_TB = 0;
    public static final int BANG_DIEM_CT = 1;
    private int isView;

    public void setIsView(int isView) {
        this.isView = isView;
    }

    @Override
    protected void initLayout() {
        DataResponse userModel = PrefUtils.loadCacheData(Objects.requireNonNull(getContext()));
        binding.progressLoadData.setVisibility(View.VISIBLE);
        if (BANG_DIEM_TB == isView) {
//            if (!AppUtils.isNullOrEmpty(userModel)
//                    && !AppUtils.isNullOrEmpty(userModel.getResponseBDCT())
//                    && userModel.getResponseBDCT().getBangDiemCTS().size() > 0) {
//            }
            fillDataScore((ArrayList<DataResponse.ResponseBDTB>) userModel.getResponseBDTBS());

        } else {
//            if (!AppUtils.isNullOrEmpty(userModel)
//                    && !AppUtils.isNullOrEmpty(userModel.getResponseBDCT())
//            && !AppUtils.isNullOrEmpty(userModel.getResponseBDCT().getBangDiemCTS())){
//            }
            fillData((ArrayList<ResponseBDCT.BangDiemCT>) userModel.getResponseBDCT().getBangDiemCTS());

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_average_transcript;
    }

    @Override
    public int getTitle() {
        return R.string.average_transcript;
    }

    // bdtb
    private void fillDataScore(ArrayList<DataResponse.ResponseBDTB> data) {
        binding.progressLoadData.setVisibility(View.GONE);
        BaseAdapter<DataResponse.ResponseBDTB> adapterBDTB = new BaseAdapter<>(getContext(), R.layout.item_score);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.lvScore.setLayoutManager(gridLayoutManager);
        binding.lvScore.setAdapter(adapterBDTB);
        adapterBDTB.setData(data);
        binding.groupNoData.setVisibility(!AppUtils.isNullOrEmpty(data) ?View.GONE : View.VISIBLE);
    }

    // BDCT
    private void fillData(ArrayList<ResponseBDCT.BangDiemCT> responseBDCTS) {
        BaseAdapter<ResponseBDCT.BangDiemCT> adapterBDCT = new BaseAdapter<>(getContext(), R.layout.item_detail_score);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.lvScore.setLayoutManager(layoutManager);
        binding.lvScore.setAdapter(adapterBDCT);
        binding.progressLoadData.setVisibility(View.GONE);
        adapterBDCT.setData(responseBDCTS);
        binding.groupNoData.setVisibility(!AppUtils.isNullOrEmpty(responseBDCTS) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onBackPressed() {
    }

}
