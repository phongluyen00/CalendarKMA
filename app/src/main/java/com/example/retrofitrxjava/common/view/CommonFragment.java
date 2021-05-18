package com.example.retrofitrxjava.common.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseFragment;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.common.adapter.TabLayOut;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.utils.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.Objects;

import static com.example.retrofitrxjava.utils.AppUtils.DO_AN_TN_S;
import static com.example.retrofitrxjava.utils.AppUtils.END_DN_C;
import static com.example.retrofitrxjava.utils.AppUtils.END_DN_TN;
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
import static com.example.retrofitrxjava.utils.AppUtils.START_DA_C;
import static com.example.retrofitrxjava.utils.AppUtils.START_DA_TN;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS5;
import static com.example.retrofitrxjava.utils.Constant.IS_FACE_ID;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public class CommonFragment extends BaseFragment<LayoutPersonalBinding>  {

    private static final int CAMERA_PIC_REQUEST = 100;
    public static final int MAN_HINH_LICH_HOC = 10;
    public static final int MAN_HINH_XEM_DIEM = 20;
    public static final int MAN_HINH_THONG_TIN = 30;

    private TabLayOut tabLayOut;
    private DataResponse userModel;
    public static final String SHARED_PREFERENCE_NAME = "SettingGame";
    private SharedPreferences sharedPreferences;
    private int isView;

    public void setSetView(int isView) {
        this.isView = isView;
    }

    @Override
    protected void initLayout() {
        userModel = PrefUtils.loadCacheData(Objects.requireNonNull(getContext()));

        if (MAN_HINH_LICH_HOC == isView) {
            initCalendar();
        } else if (MAN_HINH_THONG_TIN == isView) {
            initInformation();
        }else if (MAN_HINH_XEM_DIEM == isView){
            initScoreAccount();
        }
    }

    private void initScoreAccount() {
        binding.floatingButton.setVisibility(View.GONE);
        binding.groupTabLayout.setVisibility(View.VISIBLE);
        binding.myCalendar.setVisibility(View.GONE);
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

    private void initCalendar() {
        binding.myCalendar.deleteAllEvent();
        binding.myCalendars.deleteAllEvent();
        if (!AppUtils.isNullOrEmpty(userModel)
                && !AppUtils.isNullOrEmpty(userModel.getSchedule())
                && !AppUtils.isNullOrEmpty(userModel.getSchedule().getSchedules())) {
            for (ResponseSchedule.Schedule schedule : userModel.getSchedule().getSchedules()) {
                String addressAndTeacher = AppUtils.isNullOrEmpty(schedule.getTeacher())
                        ? schedule.getAddress() : schedule.getAddress() + "_" + schedule.getTeacher();
                putData(schedule.getSchoolShift(), schedule.getCalendarDays(), schedule.getSubjectName(), addressAndTeacher);
            }
            binding.floatingButton.setVisibility(View.VISIBLE);
            binding.myCalendars.setVisibility(View.GONE);
            binding.myCalendar.setVisibility(View.VISIBLE);
            binding.myCalendar.showMonthViewWithBelowEvents();
        }
    }

    private void initInformation(){
        binding.floatingButton.setVisibility(View.GONE);
        binding.groupMenuLayout.setVisibility(View.VISIBLE);
        binding.myCalendar.setVisibility(View.GONE);
        binding.groupTabLayout.setVisibility(View.GONE);
        binding.setData(userModel);

        binding.viewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
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
                (compoundButton, b) -> {
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
                    editor.apply();
                });
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
            File finalFile = new File(AppUtils.getRealPathFromURI(getActivity(), tempUri));
//            userModel.setAvata(finalFile);
//            PrefUtils.saveData(getActivity(),userModel);
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

    private void putData(String time, String date, String name, String address) {
        if (MAN_HINH_LICH_HOC == isView) {
            if (HOURS1.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS1, END_HOURS1, name, address);
            } else if (HOURS2.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS2, END_HOURS2, name, address);
            } else if (HOURS3.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, AppUtils.START_HOURS3, END_HOURS3, name, address);
            } else if (HOURS4.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS4, END_HOURS4, name, address);
            } else if (HOURS5.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_HOURS5, END_HOURS5, name, address);
            } else if (DO_AN_TN_S.equals(time)) {
                AppUtils.putData(binding.myCalendar, date, START_DA_TN, END_DN_TN, name, address);
            } else {
                AppUtils.putData(binding.myCalendar, date, START_DA_C, END_DN_C, name, address);
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
            } else if (DO_AN_TN_S.equals(time)) {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_DA_TN, END_DN_TN, name);
            } else {
                AppUtils.putDataMyCalendars(binding.myCalendars, date, START_DA_C, END_DN_C, name);
            }
        }

    }

}
