package com.example.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import com.example.ui.mynotification.Constants;
import com.example.ui.mynotification.NotificationHelper;
import com.example.ui.mynotification.PreferenceHelper;

public class SettingFragment extends Fragment {
    CompoundButton switchActivateNotify;

    private void initSwitchLayout(final WorkManager workManager) {

        switchActivateNotify.setChecked(PreferenceHelper.getBoolean(getActivity().getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY));
        switchActivateNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean isChannelCreated = NotificationHelper.isNotificationChannelCreated(getActivity().getApplicationContext());
                    if (isChannelCreated) {
                        PreferenceHelper.setBoolean(getActivity().getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY, true);
                        NotificationHelper.setScheduledNotification(workManager);
                    } else {
                        NotificationHelper.createNotificationChannel(getActivity().getApplicationContext());
                    }
                } else {
                    PreferenceHelper.setBoolean(getActivity().getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY, false);
                    workManager.cancelAllWork();
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        switchActivateNotify = (CompoundButton) view.findViewById(R.id.switch_second_notify);



        initSwitchLayout(WorkManager.getInstance(getActivity().getApplicationContext()));
        return view;
    }
}