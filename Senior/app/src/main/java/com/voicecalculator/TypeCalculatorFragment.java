package com.voicecalculator;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TypeCalculatorFragment extends Fragment implements View.OnClickListener{

    private type_fragment_calculator_listener type_fragment_calculator_listener;
    public static String Expression = "";
    public static TextView tv_expression;

    public TypeCalculatorFragment() {
        // Required empty public constructor
    }

    //interface to send data between fragment and activity
    public interface type_fragment_calculator_listener {
        void onTypeInputSent(CharSequence input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv_0;
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;
        TextView tv_8;
        TextView tv_9;
        ImageView iv_clear;
        TextView tv_reset;
        TextView tv_divide;
        TextView tv_multiply;
        TextView tv_module;
        TextView tv_equal;
        TextView tv_decimal;
        TextView tv_bracket;
        TextView tv_add;
        TextView tv_subtract;


        //number textViews
        tv_0 = getActivity().findViewById(R.id.tv_0);
        tv_0.setOnClickListener(this);
        tv_1 = getActivity().findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        tv_2 = getActivity().findViewById(R.id.tv_2);
        tv_2.setOnClickListener(this);
        tv_3 = getActivity().findViewById(R.id.tv_3);
        tv_3.setOnClickListener(this);
        tv_4 = getActivity().findViewById(R.id.tv_4);
        tv_4.setOnClickListener(this);
        tv_5 = getActivity().findViewById(R.id.tv_5);
        tv_5.setOnClickListener(this);
        tv_6 = getActivity().findViewById(R.id.tv_6);
        tv_6.setOnClickListener(this);
        tv_7 = getActivity().findViewById(R.id.tv_7);
        tv_7.setOnClickListener(this);
        tv_8 = getActivity().findViewById(R.id.tv_8);
        tv_8.setOnClickListener(this);
        tv_9 = getActivity().findViewById(R.id.tv_9);
        tv_9.setOnClickListener(this);

        //operation textViews
        iv_clear = getActivity().findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        tv_reset = getActivity().findViewById(R.id.tv_reset);
        tv_reset.setOnClickListener(this);
        tv_divide = getActivity().findViewById(R.id.tv_divide);
        tv_divide.setOnClickListener(this);
        tv_multiply = getActivity().findViewById(R.id.tv_multiply);
        tv_multiply.setOnClickListener(this);
        tv_subtract = getActivity().findViewById(R.id.tv_subtract);
        tv_subtract.setOnClickListener(this);
        tv_add = getActivity().findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        tv_bracket = getActivity().findViewById(R.id.tv_bracket);
        tv_bracket.setOnClickListener(this);
        tv_bracket.setOnLongClickListener(v -> {
            Expression += ')';
            tv_expression.setText(Expression);
            return true;
        });
        tv_decimal = getActivity().findViewById(R.id.tv_decimal);
        tv_decimal.setOnClickListener(this);
        tv_module = getActivity().findViewById(R.id.tv_module);
        tv_module.setOnClickListener(this);
        tv_equal = getActivity().findViewById(R.id.tv_equal);
        tv_equal.setOnClickListener(this);

        //expression textView to be calculated
        tv_expression = getActivity().findViewById(R.id.tv_expression_type);
        tv_expression.setMovementMethod(new ScrollingMovementMethod());
        tv_expression.setText(Expression);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_equal:
                //send expression string to activity to calculate the result ,then update expression
                type_fragment_calculator_listener.onTypeInputSent(Expression);
                Expression = "";
                break;
            case R.id.iv_clear:
                //if able,delte the last input character
                if (Expression.length() > 0) {
                    Expression = Expression.substring(0, Expression.length() - 1);
                    tv_expression.setText(Expression);
                }
                break;
            case R.id.tv_reset:
                //reset the expression and update the textview
                Expression = "";
                tv_expression.setText(Expression);
                break;
            default:
                //maximum number of elements if not exceeded then add value of textview to expression string
                if(Expression.length()>25){
                    Toast.makeText(v.getContext(),"You reached maximum number of characters allowed",Toast.LENGTH_SHORT).show();
                    break;
                }
                TextView textview = (TextView) v;
                Expression += textview.getText().toString();
                tv_expression.setText(Expression);
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof type_fragment_calculator_listener) {
            type_fragment_calculator_listener = (type_fragment_calculator_listener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        type_fragment_calculator_listener = null;
    }

}