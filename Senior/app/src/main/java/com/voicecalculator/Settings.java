package com.voicecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Settings extends Fragment implements View.OnClickListener {

    private SharedPreferences userSetting;
    private boolean isDark_theme;
    private boolean isType_calculator;
    private ImageView iv_search;
    private SharedPreferences.Editor editor;


    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //shared-preferences
        userSetting = getActivity().getSharedPreferences("User-Settings", Context.MODE_PRIVATE);
        isDark_theme = userSetting.getBoolean("darkTheme", false);
        isType_calculator = userSetting.getBoolean("typeCalculator", false);
        editor = userSetting.edit();

        //define views
        iv_search = (ImageView) view.findViewById(R.id.iv_calculator);
        ImageView iv_help = (ImageView) view.findViewById(R.id.iv_help);
        ImageView iv_history = (ImageView) view.findViewById(R.id.iv_history);
        ImageView iv_theme = (ImageView) view.findViewById(R.id.iv_theme);

        //theme_icon
        if (userSetting.getBoolean("darkTheme", false)) {

            iv_theme.setImageResource(R.drawable.ic_sunny_35);
        } else {
            iv_theme.setImageResource(R.drawable.ic_moon_35);

        }

        //calculator_icon
        if (userSetting.getBoolean("typeCalculator", false)) {

            iv_search.setImageResource(R.drawable.ic_voice_35);
        } else {
            iv_search.setImageResource(R.drawable.ic_calculate_35);

        }

        //theme changing onclick
        iv_theme.setOnClickListener(this);
        //calculator changing onclick
        iv_search.setOnClickListener(this);
        //settings sheet onclick
        iv_help.setOnClickListener(this);
        //use previous result onclick
        iv_history.setOnClickListener(this);
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_theme:
                //change the theme boolean in shared preferences
                if (!isDark_theme) {
                    Toast.makeText(getActivity().getApplicationContext(), "Light Theme will be applied after restarting the application", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("darkTheme", true);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Dark Theme will be applied after restarting the application", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("darkTheme", false);
                }
                editor.apply();
                isDark_theme = userSetting.getBoolean("darkTheme", false);
                break;

            case R.id.iv_calculator:
                //change the calculator type and inflate the corresponding fragment and change the icon
                if (!isType_calculator) {
                    ((MainActivity) getActivity()).typeCalculatorInflate();
                    iv_search.setImageResource(R.drawable.ic_voice_35);
                    editor.putBoolean("typeCalculator", true);
                } else {
                    ((MainActivity) getActivity()).voiceCalculatorInflate();
                    iv_search.setImageResource(R.drawable.ic_calculate_35);
                    editor.putBoolean("typeCalculator", false);
                }
                editor.apply();
                isType_calculator = userSetting.getBoolean("typeCalculator", false);
                break;
            case R.id.iv_help:
                //expand the bottom sheet
                View bottomSheet = getActivity().findViewById(R.id.bottom_sheet_frame_layout_fragment);
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.iv_history:
                //check if user using type input calculator
                //if yes,check if the number of max characters is not exceeded if the last result is entered
                //if yes then take the last result from shared preferences and update the testview and expression string
                if(userSetting.getBoolean("typeCalculator",false)) {
                    Boolean flag = true;
                    if(TypeCalculatorFragment.Expression.length() + userSetting.getString("result","(0)").length() > 25) {
                        Toast.makeText(v.getContext(), "Can't enter previous answer, you will exceed maximum number of characters allowed", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                    if(flag) {
                        TypeCalculatorFragment.Expression += userSetting.getString("result", "(0)");
                        TypeCalculatorFragment.tv_expression.setText(TypeCalculatorFragment.Expression);
                    }
                }

                break;
        }
    }


}