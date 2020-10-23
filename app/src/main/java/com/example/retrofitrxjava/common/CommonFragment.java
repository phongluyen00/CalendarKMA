package com.example.retrofitrxjava.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.common.dialog.DialogFullScreen;
import com.example.retrofitrxjava.common.dialog.DialogFullScreenXLHV;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.common.adapter.TabLayOut;
import com.example.retrofitrxjava.common.dialog.DialogSync;
import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.dialog.DialogConfirmShowCalendar;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;

import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS3;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS5;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS3;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS5;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS5;
import static com.example.retrofitrxjava.utils.Constant.IS_FACE_ID;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public class CommonFragment extends BFragment<LayoutPersonalBinding> implements CommonContract.View {

    private static final int CAMERA_PIC_REQUEST = 100 ;
    private TabLayOut tabLayOut;
    private boolean isType;
    private boolean isMenu;
    private CommonPresenter presenter;
    private String token, password;
    DialogSync dialogSync;
    private boolean isShowView;
    private boolean isDays;
    private LoginResponse.Data userModel;
    private DialogConfirmShowCalendar confirmShowCalendar;
    public static final String SHARED_PREFERENCE_NAME = "SettingGame";
    private SharedPreferences sharedPreferences;

    public void setType(boolean type) {
        isType = type;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    @Override
    protected void initLayout() {
        presenter = new CommonPresenter(this);
        userModel = PrefUtils.loadData(getActivity());
        token = userModel.getToken();
        password = userModel.getPassword();
        if (isDays){
            // code ông đẩy vào đây nhé
        }
        if (!isShowView) {
            binding.myCalendars.setVisibility(View.GONE);
            binding.myCalendar.setVisibility(View.VISIBLE);
            binding.myCalendar.showMonthViewWithBelowEvents();
        } else {
            binding.myCalendar.setVisibility(View.VISIBLE);
            binding.myCalendar.showWeekView();
        }
        if (isMenu) {
            binding.groupMenuLayout.setVisibility(View.VISIBLE);
            binding.myCalendar.setVisibility(View.GONE);
            binding.groupTabLayout.setVisibility(View.GONE);
            binding.setData(userModel);
            binding.llTuition.setOnClickListener(view -> {
                DialogFullScreen dialogFullScreen = new DialogFullScreen();
                dialogFullScreen.show(getFragmentManager(), "");
            });

            binding.car4.setOnClickListener(v -> {
                        DialogFullScreenXLHV dialogFullScreenXLHV = new DialogFullScreenXLHV();
                        dialogFullScreenXLHV.show(getChildFragmentManager(), dialogFullScreenXLHV.getTag());
                    }
            );

            binding.openCamera.setOnClickListener(v -> {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
                }
            });
            sharedPreferences = getActivity().
                    getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            boolean isVolume = sharedPreferences.getBoolean(IS_FACE_ID, false);
            binding.switchCompat.setChecked(isVolume);
            binding.switchCompat.setTrackTintList(binding.switchCompat.isChecked() ?
                    (ColorStateList.valueOf(Color.parseColor("#0CEBF3"))) :
                    (ColorStateList.valueOf(Color.parseColor("#929697"))));
            binding.switchCompat.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton,
                                                     boolean b) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (binding.switchCompat.isChecked()) {
                                editor.putBoolean(IS_FACE_ID, true);
                                binding.switchCompat.setTrackTintList
                                        (ColorStateList.valueOf(Color.parseColor("#0CEBF3")));
                            } else {
                                editor.putBoolean(IS_FACE_ID, false);
                                binding.switchCompat.setTrackTintList
                                        (ColorStateList.valueOf(Color.parseColor("#929697")));
                            }
                            editor.commit();
                        }
                    });

            binding.updateData.setOnClickListener(view -> {
                dialogSync = new DialogSync(new DialogSync.itemOnClick() {
                    @Override
                    public void onClickSynDTB() {
                        validateUpdate();
                        presenter.syncDTB(token, password, myAPI);
                    }

                    @Override
                    public void onClickSynScore() {
                        validateUpdate();
                        presenter.updateScore(token, password, myAPI);
                    }

                    @Override
                    public void onClickSynSchedule() {
                        validateUpdate();
                        presenter.updateSchedule(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncMoney() {
                        validateUpdate();
                        presenter.updateMoney(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncWaring() {
                        validateUpdate();
                        presenter.syncHandlingService(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncCC() {
                        validateUpdate();
                        presenter.syncCertificate(token, password, myAPI);
                    }

                    @Override
                    public void close() {
                        dialogSync.dismiss();
                    }
                });
                dialogSync.show(getFragmentManager(), "");
            });
            return;
        }

        if (isType) {
            binding.groupTabLayout.setVisibility(View.VISIBLE);
            binding.myCalendar.setVisibility(View.GONE);
        } else {
            binding.floatingButton.setOnClickListener(v -> {
                confirmShowCalendar = new DialogConfirmShowCalendar(new DialogConfirmShowCalendar.itemOnClick() {
                    @Override
                    public void onClickMonth() {
                        confirmShowCalendar.dismiss();
                        isShowView = false;
                        initLayout();
                    }

                    @Override
                    public void onClickWeek() {
                        confirmShowCalendar.dismiss();
                        isShowView = true;
                        initLayout();
                    }

                    @Override
                    public void onClickDays() {
                        confirmShowCalendar.dismiss();
                        isDays = true;
                        initLayout();
                    }
                });
                confirmShowCalendar.show(getFragmentManager(), "");
            });
            if (!NetworkUtils.isConnect(getActivity())) {
                if (userModel.getModelResponse() != null && userModel.getModelResponse().getData().size() > 0) {
                    binding.myCalendar.deleteAllEvent();
                    for (ScheduleModelResponse.Data schedule : userModel.getModelResponse().getData()) {
                        putData(schedule.getCaHoc(), AppUtils.formatDate(schedule.getDatetime()),
                                schedule.getTitle()
                                        + "\n" + "Giảng viên : " + schedule.getTeacher());
                    }
                    if (!isShowView) {
                        binding.myCalendar.showMonthViewWithBelowEvents();
                    } else {
                        binding.myCalendar.showWeekView();
                        binding.myCalendar.showAgendaView();
                    }
                    binding.groupTabLayout.setVisibility(View.GONE);
                    binding.myCalendar.setVisibility(View.VISIBLE);
                }
            }else {
                presenter.retrieveSchedule(token, myAPI);
                binding.groupTabLayout.setVisibility(View.GONE);
                binding.myCalendar.setVisibility(View.VISIBLE);
            }
        }

        tabLayOut = new TabLayOut(getActivity());
        binding.pager.setAdapter(tabLayOut);
        binding.pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.score_medium);
            } else if (position == 1) {
                tab.setText(R.string.detail_score);
            }
        }).attach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.imgAccount.setImageBitmap(photo);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = AppUtils.getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(AppUtils.getRealPathFromURI(getActivity(),tempUri));
            userModel.setAvata(finalFile);
            PrefUtils.saveData(getActivity(),userModel);
        }
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
    protected void onBackPressed() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void retrieveSuccess(ScheduleModelResponse data) {
        if (data != null && data.getData().size() > 0) {
            userModel.setModelResponse(data);
            PrefUtils.saveData(getActivity(), userModel);
            for (ScheduleModelResponse.Data schedule : data.getData()) {
                putData(schedule.getCaHoc(), AppUtils.formatDate(schedule.getDatetime()), schedule.getTitle()
                        + "\n" + "Giảng viên :" + schedule.getTeacher());
            }
            if (!isShowView) {
                binding.myCalendars.setVisibility(View.GONE);
                binding.myCalendar.setVisibility(View.VISIBLE);
                binding.myCalendar.showMonthViewWithBelowEvents();
            } else {
                binding.myCalendar.setVisibility(View.VISIBLE);
                binding.myCalendar.showWeekView();
            }
        }
    }

    private void putData(String time, String date, String name) {
        if (!isShowView) {
            if (HOURS1.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS1, END_HOURS1, name);
            } else if (HOURS2.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS2, END_HOURS2, name);
            } else if (HOURS3.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, AppUtils.START_HOURS3, END_HOURS3, name);
            } else if (HOURS4.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS4, END_HOURS4, name);
            } else if (HOURS5.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS5, END_HOURS5, name);
            }
        } else {
            if (HOURS1.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_HOURS1, END_HOURS1, name);
            } else if (HOURS2.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_HOURS2, END_HOURS2, name);
            } else if (HOURS3.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, AppUtils.START_HOURS3, END_HOURS3, name);
            } else if (HOURS4.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_HOURS4, END_HOURS4, name);
            } else if (HOURS5.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_HOURS5, END_HOURS5, name);
            }
        }

    }

    @Override
    public void updateSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        binding.updateData.setEnabled(true);
        binding.progressUpdate.setVisibility(View.GONE);
    }

    private void validateUpdate() {
        dialogSync.dismiss();
        binding.updateData.setEnabled(false);
        binding.progressUpdate.setVisibility(View.VISIBLE);
    }
}
