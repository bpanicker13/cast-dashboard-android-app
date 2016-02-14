package com.example.dan.castdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.dan.castdemo.databinding.FragmentAppSettingsLayoutBinding;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppSettingsLayout extends AppSettingsHelperFragment {

    @Bind(R.id.seekBar)
    SeekBar columnCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_settings_layout, container, false);
        ButterKnife.bind(this, view);

        viewModel = FragmentAppSettingsLayoutBinding.bind(view);
        bindings = new AppSettingsBindings();
        bindings.init(this);
        ((FragmentAppSettingsLayoutBinding) viewModel).setSettings(bindings);

        columnCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    bindings.setNumberOfColumns(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        final MainActivity activity = (MainActivity) getActivity();

        activity.setDrawerItemChecked(MainActivity.NAV_VIEW_OPTIONS_LAYOUT_ITEM);
        super.onResume();
    }
}