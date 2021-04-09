package com.example.retrofitrxjava.common.dialog;

import android.view.View;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogSyncBinding;

public class DialogSync extends BDialogFragment<DialogSyncBinding> {

    private itemOnClick listener;

    public DialogSync(itemOnClick listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.dialog_sync;
    }

    @Override
    protected void initLayout() {
        binding.synDtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSynDTB();
            }
        });
        binding.syncScore.setOnClickListener(view -> {  listener.onClickSynScore(); });
        binding.tvSyncSchedule.setOnClickListener(view -> { listener.onClickSynSchedule(); });
        binding.syncMoney.setOnClickListener(view -> {  listener.onClickSyncMoney(); });
        binding.tvSyncWaring.setOnClickListener(view -> listener.onClickSyncWaring());
        binding.closeDialog.setOnClickListener(view -> listener.close());
        binding.tvUpdateCc.setOnClickListener(view -> listener.onClickSyncCC());
    }

    public interface itemOnClick{
        void onClickSynDTB();
        void onClickSynScore();
        void onClickSynSchedule();
        void onClickSyncMoney();
        void onClickSyncWaring();
        void onClickSyncCC();
        void close();
    }

}
