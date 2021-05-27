package com.example.retrofitrxjava.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseAdapter;
import com.example.retrofitrxjava.base.BaseFragment;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.base.ItemDeleteListener;
import com.example.retrofitrxjava.base.ItemOnclickListener;
import com.example.retrofitrxjava.databinding.FragmentAccountBinding;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.main.dialog.DialogAddTeacher;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.utils.AppUtils;
import com.example.retrofitrxjava.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;
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

public class AccountFragment extends BaseFragment<FragmentAccountBinding> implements ItemOnclickListener<Account>, ItemDeleteListener<Account> {

    BaseAdapter<Account> accountAdapter;
    DataResponse dataResponse;
    private List<Account> accountList = new ArrayList<>();

    @Override
    protected void onBackPressed() {
        if (dataResponse.getPermission() != 1) {
            binding.myCalendar.deleteAllEvent();
            binding.myCalendar.setVisibility(View.GONE);
        } else {
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    protected void initLayout() {
        binding.myCalendar.deleteAllEvent();
        dataResponse = PrefUtils.loadCacheData(Objects.requireNonNull(getContext()));
        if (dataResponse.getPermission() == 2) {
            callApiAccount();
        } else {
            callApiSchedule(dataResponse.getName());
        }
    }

    private void callApiSchedule(String name) {
        AppUtils.HandlerRXJava(requestAPI.scheduleTeacher(name),
                new BaseObserver<ResponseSchedule>() {
                    @Override
                    public void onSuccess(ResponseSchedule responseSchedule) {
                        if (!AppUtils.isNullOrEmpty(responseSchedule.getSchedules())) {
                            for (ResponseSchedule.Schedule schedule : responseSchedule.getSchedules()) {
                                putData(schedule.getSchoolShift(), schedule.getCalendarDays(), schedule.getSubjectName(), schedule.getAddress());
                            }
                            binding.myCalendar.setVisibility(View.VISIBLE);
                            binding.myCalendar.showMonthViewWithBelowEvents();
                        } else {
                            Toast.makeText(getContext(), "Chưa có dữ liệu lịch học !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
    }

    private void callApiAccount() {
        AppUtils.HandlerRXJava(requestAPI.retrieveAccount(), new BaseObserver<List<Account>>() {
            @Override
            public void onSuccess(List<Account> accounts) {
                if (!AppUtils.isNullOrEmpty(accounts)) {
                    accountList = accounts;
                    accountAdapter.setData((ArrayList<Account>) accountList);
                    binding.rclAccount.setAdapter(accountAdapter);
                }
            }

            @Override
            public void onFailed(String message) {

            }
        });
        accountAdapter = new BaseAdapter<>(getContext(), R.layout.item_account);
        accountAdapter.setListener(this);
        accountAdapter.setItemDeleteListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.myCalendar.deleteAllEvent();
    }

    @Override
    public int getTitle() {
        return R.string.tknd;
    }

    @Override
    public void onItemMediaClick(Account account) {
        String username = account.getPermission() == 0 ? account.getUsername() : account.getName();
        if (account.getPermission() == 1) {
            callApiSchedule(username);
        } else {
            AppUtils.HandlerRXJava(requestAPI.retrieveSchedule(username, account.getPassword()),
                    new BaseObserver<ResponseSchedule>() {
                        @Override
                        public void onSuccess(ResponseSchedule responseSchedule) {
                            if (!AppUtils.isNullOrEmpty(responseSchedule.getSchedules()) && responseSchedule.isSuccess()) {
                                for (ResponseSchedule.Schedule schedule : responseSchedule.getSchedules()) {
                                    putData(schedule.getSchoolShift(), schedule.getCalendarDays(), schedule.getSubjectName(), schedule.getAddress());
                                }
                                binding.myCalendar.setVisibility(View.VISIBLE);
                                binding.myCalendar.showMonthViewWithBelowEvents();
                            } else {
                                Toast.makeText(getContext(), "Chưa có dữ liệu lịch học !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailed(String message) {

                        }
                    });
        }
    }

    private void putData(String schoolShift, String calendarDays, String subjectName, String address) {
        if (HOURS1.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, START_HOURS1, END_HOURS1, subjectName, address);
        } else if (HOURS2.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, START_HOURS2, END_HOURS2, subjectName, address);
        } else if (HOURS3.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, AppUtils.START_HOURS3, END_HOURS3, subjectName, address);
        } else if (HOURS4.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, START_HOURS4, END_HOURS4, subjectName, address);
        } else if (HOURS5.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, START_HOURS5, END_HOURS5, subjectName, address);
        } else if (DO_AN_TN_S.equals(schoolShift)) {
            AppUtils.putData(binding.myCalendar, calendarDays, START_DA_TN, END_DN_TN, subjectName, address);
        } else {
            AppUtils.putData(binding.myCalendar, calendarDays, START_DA_C, END_DN_C, subjectName, address);
        }
    }

    public void refreshData() {
        callApiAccount();
    }

    @Override
    public void onDeleteAccount(Account account) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.HandlerRXJava(requestAPI.removeTeacher(account.getUsername()),
                                new BaseObserver<DataResponse>() {
                                    @Override
                                    public void onSuccess(DataResponse dataResponse) {
                                        if (!AppUtils.isNullOrEmpty(dataResponse)) {
                                            accountList.remove(account);
                                            Toast.makeText(getContext(), dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                            accountAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailed(String message) {

                                    }
                                });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onEditAccount(Account account) {
        DialogAddTeacher dialogAddTeacher = new DialogAddTeacher((username, password, name, faculty) -> {
            password = AppUtils.isNullOrEmpty(password) ? account.getPassword() : password;
            name = AppUtils.isNullOrEmpty(name) ? account.getName() : name;
            faculty = AppUtils.isNullOrEmpty(faculty) ? account.getFaculty() : faculty;
            AppUtils.HandlerRXJava(requestAPI.updateTeacher(account.getUsername(), password, name, faculty),
                    new BaseObserver<DataResponse>() {
                        @Override
                        public void onSuccess(DataResponse dataResponse) {
                            refreshData();
                            Toast.makeText(getContext(), "Cập nhật dữ liệu thành công !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getContext(), "Cập nhật dữ liệu thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dialogAddTeacher.setIsChangeAccount(2);
        dialogAddTeacher.setAccount(account);
        dialogAddTeacher.show(getChildFragmentManager(), dialogAddTeacher.getTag());
    }
}
