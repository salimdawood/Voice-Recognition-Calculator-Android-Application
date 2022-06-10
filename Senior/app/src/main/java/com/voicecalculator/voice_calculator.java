package com.voicecalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class voice_calculator extends Fragment {

    private voice_fragment_calculator_listener voice_fragment_calculator_listener;
    private TextView tv_expression_voice;


    public voice_calculator() {
        // Required empty public constructor
    }
    //interface to send data to activity
    public interface voice_fragment_calculator_listener{
        void onVoiceInputSent(CharSequence input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_expression_voice = getActivity().findViewById(R.id.tv_expression_voice);
        tv_expression_voice.setMovementMethod(new ScrollingMovementMethod());
        //open voice input intent
        ImageView iv_voice_api = getActivity().findViewById(R.id.iv_voice_api);
        iv_voice_api.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            startActivityForResult(intent, 10);
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            //shared preferences to get the last result saved
            SharedPreferences userSettings = this.getActivity().getSharedPreferences("User-Settings",MODE_PRIVATE);
            ArrayList<String> intFound = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //replace some strings for best results and wider operations
            String expression = intFound.get(0).toLowerCase().replace('x', '*').
                    replace("cosine","cos").replace("tangent", "tan").
                    replace("sign", "sin").replace("sine", "sin").
                    replace("left","(").replace("right",")").
                    replace("clothes",")").replace("power","^").
                    replace("add","+").replace("module","%").
                    replace("model","%"). replace("divide","/").
                    replace("subtract","-").replace("product","*").
                    replace("answer",userSettings.getString("result","(0)"));
            //update the textview
            tv_expression_voice.setText(expression);

            //send the expression to activity to calculate the result
            voice_fragment_calculator_listener.onVoiceInputSent(expression);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TypeCalculatorFragment.type_fragment_calculator_listener){
            voice_fragment_calculator_listener = (voice_fragment_calculator_listener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        voice_fragment_calculator_listener = null;
    }
}