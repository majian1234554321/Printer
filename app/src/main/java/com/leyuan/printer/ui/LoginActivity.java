package com.leyuan.printer.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.leyuan.printer.R;

/**
 * Created by user on 2016/12/19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private String mobile;
    private String code;
    private CountDownTimer timeCount;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private EditText getEidtTelephone() {
        return (EditText) findViewById(R.id.edit_input_phone);
    }

    private EditText getEditInputVerifyCode() {
        return (EditText) findViewById(R.id.edit_input_verify_code);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get_identify:
                String tel = getEidtTelephone().getText().toString().trim();
//                if (StringUtils.isMatchTel(tel)) {
//                    showImageIdentifyDialog(tel);
//                } else {
//                    getEidtTelephone().setError("请输入正确手机号");
//                }

                break;
            case R.id.bt_login:
                if (verifyEdit()) {
//                    DialogUtils.showDialog(this, "", false);
//                    presenter.login(mobile, code);
                }
                break;
        }
    }

    private boolean verifyEdit() {
        mobile = getEidtTelephone().getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            getEidtTelephone().setError("请输入正确手机号");
            return false;
        }

        code = getEditInputVerifyCode().getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            getEditInputVerifyCode().setError("请输入验证码");
            return false;
        }
        return true;
    }

    private void showImageIdentifyDialog(final String tel) {
//           if(mDialogImageIdentify == null){
//        mDialogImageIdentify = new DialogImageIdentify(this);
//        mDialogImageIdentify.setOnInputCompleteListener(new DialogImageIdentify.OnInputCompleteListener() {
//            @Override
//            public void inputIdentify(String imageIndentify) {
//                DialogUtils.showDialog(LoginActivity.this, "", false);
//                presenter.getIdentify(tel, imageIndentify);
//            }
//
//            @Override
//            public void refreshImage() {
//                mDialogImageIdentify.refreshImage(tel);
//            }
//        });
////           }
//
//        mDialogImageIdentify.show();
//        mDialogImageIdentify.refreshImage(tel);
    }

//    @Override
//    public void loginResult(boolean success) {
//        DialogUtils.dismissDialog();
//        if (success) {
//            if (bundle != null) {
//                UiManager.activityJump(this, bundle, MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            } else {
//                UiManager.activityJump(this, MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }

//            finish();
//        }
//    }

//    @Override
//    public void onGetIdetify(boolean success, String mobile) {
//        DialogUtils.dismissDialog();
//        if (success) {
//            //获取验证码成功
//            ToastUtil.show(App.context, "验证码已发送,请查看");
//            if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
//                mDialogImageIdentify.dismiss();
//            }
//            timeCount = new TimeCountUtil(60000, 1000, (Button) findViewById(R.id.bt_get_identify));
//            timeCount.start();
//
//        } else if (mDialogImageIdentify != null && mDialogImageIdentify.isShowing()) {
//
//            mDialogImageIdentify.clearContent();
//            mDialogImageIdentify.refreshImage(mobile);
//        }
//    }
//
//    @Override
//    public void onLoginOut(boolean success) {
//
//    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        //don't finish when back pressed
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
//            exitApp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.w(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.w(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DialogUtils.releaseDialog();
        if (timeCount != null)
            timeCount.cancel();
        LogUtil.w(TAG, "onDestroy");
    }

}
