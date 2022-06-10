package com.voicecalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;






/*
##resultSave(Double result):
method called to save the last result calculated into sharedPreferences as key "result"
##userSettings():
method called to retrieve the user settings(theme,calculatorInput : "voice" or "type") from sharedPreferences
##onTypeInputSent(CharSequence input):
method from TypeCalculatorFragment.type_fragment_calculator_listener interface defined here to take
input and -using thirdParty library- calculate the result and show it
##onVoiceInputSent(CharSequence input):
method from voice_calculator.voice_fragment_calculator_listener interface defined here to take
input and -using thirdParty library- calculate the result and show it
##typeCalculatorInflate():
method used to inflate type calculator fragment
##voiceCalculatorInflate():
method used to inflate voice calculator fragment
 */


public class MainActivity extends AppCompatActivity implements TypeCalculatorFragment.type_fragment_calculator_listener,voice_calculator.voice_fragment_calculator_listener {
    Boolean isDarkTheme;
    Boolean isTypeCalculator;
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userSettings();
        //theme on startup
        if(!isDarkTheme){
            setTheme(R.style.Dark_Theme);
        }
        else{
            setTheme(R.style.Theme_VoiceCalculator);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //bottom sheet dialog setup
        View bottomSheet = findViewById(R.id.bottom_sheet_frame_layout_fragment);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //bottom sheet dialog fragment inflate
        Settings settings = new Settings();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_sheet_frame_layout_fragment,settings).commit();
        
        //calculator inflate on startup according to shared preferences user setting
        if(!isTypeCalculator){
            voiceCalculatorInflate();
        }
        else{
            typeCalculatorInflate();
        }

        tv_result = findViewById(R.id.tv_result);
    }

    //function to inflate voice calculator
    public void voiceCalculatorInflate(){
        voice_calculator voice_calculator = new voice_calculator();
        getSupportFragmentManager().beginTransaction().replace(R.id.calculator_frame_layout_fragment,voice_calculator).commit();
    }

    //function to inflate type calculator
    public void typeCalculatorInflate(){
        TypeCalculatorFragment typeCalculatorFragment = new TypeCalculatorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.calculator_frame_layout_fragment,typeCalculatorFragment).commit();
    }

    @Override
    public void onTypeInputSent(CharSequence input) {
        if(input.length()!=0)
        {
            try {
                ExpressionBuilder eb = new ExpressionBuilder((String) input);
                Expression ex = eb.build();
                double result = ex.evaluate();
                tv_result.setText(Double.toString(result));
                //save result into shared preferences
                resultSave(result);
            } catch (Exception e) {
                tv_result.setText(R.string.error);
            }
        }
    }

    @Override
    public void onVoiceInputSent(CharSequence input) {
        if(input.length()!=0)
        {
            try {
                ExpressionBuilder eb = new ExpressionBuilder((String) input);
                Expression ex = eb.build();
                double result = ex.evaluate();
                tv_result.setText(Double.toString(result));
                //save result into shared preferences
                resultSave(result);
            } catch (Exception e) {
                tv_result.setText(R.string.error);
            }
        }
    }
    public void userSettings(){
        SharedPreferences userSettings = getSharedPreferences("User-Settings",MODE_PRIVATE);
        isDarkTheme =  userSettings.getBoolean("darkTheme",false);
        isTypeCalculator =  userSettings.getBoolean("typeCalculator",false);
    }
    public void resultSave(Double result){
        SharedPreferences userSettings = getSharedPreferences("User-Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = userSettings.edit();
        editor.putString("result","("+result+")");
        editor.apply();
    }

}