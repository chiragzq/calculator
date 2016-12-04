package com.chirag.calculator.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    String mEquation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEquation = "";

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                clickedButton(view.getId());
            }
        };
        findViewById(R.id.btn_0).setOnClickListener(listener);
        findViewById(R.id.btn_1).setOnClickListener(listener);
        findViewById(R.id.btn_2).setOnClickListener(listener);
        findViewById(R.id.btn_3).setOnClickListener(listener);
        findViewById(R.id.btn_4).setOnClickListener(listener);
        findViewById(R.id.btn_5).setOnClickListener(listener);
        findViewById(R.id.btn_6).setOnClickListener(listener);
        findViewById(R.id.btn_7).setOnClickListener(listener);
        findViewById(R.id.btn_8).setOnClickListener(listener);
        findViewById(R.id.btn_9).setOnClickListener(listener);
        findViewById(R.id.btn_plus).setOnClickListener(listener);
        findViewById(R.id.btn_minus).setOnClickListener(listener);
        findViewById(R.id.btn_mul).setOnClickListener(listener);
        findViewById(R.id.btn_div).setOnClickListener(listener);
        findViewById(R.id.btn_dot).setOnClickListener(listener);

        findViewById(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEquation.isEmpty()) {
                    return;
                }
                mEquation = mEquation.substring(0, mEquation.length() - 1);
                updateDisplay();
            }
        });

        findViewById(R.id.btn_del).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clearEquation();
                return true;
            }
        });


        findViewById(R.id.btn_eq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEquation.isEmpty()) {
                    return;
                }
                if("/*+-.".contains(mEquation.charAt(mEquation.length()-1) + "")) {
                    return;
                }
                mEquation = evaluate(mEquation);
                if(mEquation.charAt(mEquation.length()-1) == '0' && mEquation.charAt(mEquation.length()-2) == '.') {
                    mEquation = mEquation.substring(0, mEquation.length() - 2);
                }
                updateDisplay();
            }
        });

    }
    public void updateDisplay() {
        ((TextView) findViewById(R.id.display_text)).setText(mEquation);
    }
    public void clickedButton(int id) {
        String str = ((Button) findViewById(id)).getText().toString();
        if(mEquation != "") {
            if ("/*-+".contains(str)) {
                if("/*-+".contains(mEquation.charAt(mEquation.length()-1) + "")) {
                    return;
                }
            }
            if (".".contains(str)) {
                if(".".contains(mEquation.charAt(mEquation.length()-1) + "")) {
                    return;
                }
            }
            if (str == ".") {
                if(mEquation.charAt(mEquation.length()-1) == '.') {
                    return;
                }
            }
        }
        mEquation+=str;
        Log.i("clickListeneder", "Button " + str + " was clicked, equation is " + mEquation + ".");
        updateDisplay();
    }
    public void clearEquation() {
        mEquation = "";
        updateDisplay();
    }

    public String evaluate(String eq) {
        int s = rightMostSum(eq);
        int m = rightMostMul(eq);
        if (s > 0) {
            if (eq.charAt(s) == '+') return String.valueOf(Float.parseFloat(evaluate(eq.substring(0, s))) + Float.parseFloat(evaluate(eq.substring(s + 1))));
            else return String.valueOf(Float.parseFloat(evaluate(eq.substring(0, s))) - Float.parseFloat(evaluate(eq.substring(s + 1))));
        }
        if (m > 0) {
            if (eq.charAt(m) == '*') return String.valueOf(Float.parseFloat(evaluate(eq.substring(0, m))) * Float.parseFloat(evaluate(eq.substring(m + 1))));
            else return String.valueOf(Float.parseFloat(evaluate(eq.substring(0, m))) / Float.parseFloat(evaluate(eq.substring(m + 1))));
        }
        if(eq.equals(".")) eq = "0";
        return eq;

    }
    public int rightMostSum(String eq) {
        for (int i = eq.length()-1; i >= 0; i--) {
            if (eq.charAt(i) == '-' || eq.charAt(i) == '+') return i;
        }
        return -1;
    }
    public int rightMostMul(String eq) {
        for (int i = eq.length()-1; i >= 0; i--) {
            if (eq.charAt(i) == '/' || eq.charAt(i) == '*') return i;
        }
        return -1;
    }
}
