package com.leyuan.printer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.printer.R;

import java.lang.reflect.Method;

/**
 * Created by user on 2017/4/11.
 */
public class AppointCodeActivity extends BaseActivity implements View.OnClickListener {


    private TextView txtHint;
    private ImageView imgTag;
    private EditText txtAppointCode;
    private LinearLayout linearSecond;
    private StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_code);

        txtHint = (TextView) findViewById(R.id.txt_hint);
        imgTag = (ImageView) findViewById(R.id.img_tag);
        txtAppointCode = (EditText) findViewById(R.id.txt_appoint_code);
        txtAppointCode.setInputType(InputType.TYPE_NULL);

//        initEditText();

        findViewById(R.id.bt_one).setOnClickListener(this);
        findViewById(R.id.bt_two).setOnClickListener(this);
        findViewById(R.id.bt_three).setOnClickListener(this);
        findViewById(R.id.bt_delete).setOnClickListener(this);
        linearSecond = (LinearLayout) findViewById(R.id.linear_second);
        findViewById(R.id.bt_four).setOnClickListener(this);
        findViewById(R.id.bt_five).setOnClickListener(this);
        findViewById(R.id.bt_six).setOnClickListener(this);
        findViewById(R.id.bt_seven).setOnClickListener(this);
        findViewById(R.id.bt_eight).setOnClickListener(this);
        findViewById(R.id.bt_nine).setOnClickListener(this);
        findViewById(R.id.bt_clear).setOnClickListener(this);
        findViewById(R.id.bt_zero).setOnClickListener(this);
        findViewById(R.id.bt_ok).setOnClickListener(this);
        findViewById(R.id.bt_back).setOnClickListener(this);
    }

    private void initEditText() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(txtAppointCode, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_one:
                buffer.append("1");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_two:
                buffer.append("2");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_three:
                buffer.append("3");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_four:
                buffer.append("4");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_five:
                buffer.append("5");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_six:
                buffer.append("6");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_seven:
                buffer.append("7");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_eight:
                buffer.append("8");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_nine:
                buffer.append("9");
                txtAppointCode.setText(buffer.toString());
                break;
            case R.id.bt_zero:
                buffer.append("0");
                txtAppointCode.setText(buffer.toString());
                break;

            case R.id.bt_delete:
                if (buffer.length() > 0) {
                    buffer.deleteCharAt(buffer.length() - 1);
                    txtAppointCode.setText(buffer.toString());
                }
                break;

            case R.id.bt_clear:
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                    txtAppointCode.setText(buffer.toString());
                }

                break;

            case R.id.bt_ok:
                startActivity(new Intent(this,PrintActivity.class));
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }
}
