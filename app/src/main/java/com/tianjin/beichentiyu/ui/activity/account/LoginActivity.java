package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.dialog.WebViewDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.LocationManager;
import com.tianjin.beichentiyu.presenter.LoginPresenter;
import com.tianjin.beichentiyu.presenter.contract.LoginContract;
import com.tianjin.beichentiyu.ui.MainActivity;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.SpUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.LoginCustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/29 22:32
 * E-Mail Address：673236072@qq.com
 * 登陆
 */
public class LoginActivity extends BaseMvpActivity<LoginContract.Presenter> implements LoginContract.View {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    @BindView(R.id.toolbar)
    LoginCustomToolbar mToolbar;
    //账号
    @BindView(R.id.et_name)
    EditText mEtName;
    //密码
    @BindView(R.id.et_password)
    EditText mEtPassword;
    //忘记密码
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    //登陆
    @BindView(R.id.rel_login)
    RelativeLayout mRelLogin;
    //注册
    @BindView(R.id.rel_register)
    RelativeLayout mRelRegister;

    private LodingDialog lodingDialog;



    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {
        //关闭除登陆外所有页面
        App.get().finishAllActivity(this);
        if (!requestPermissions()) {
            ToastUtil.showToast("请授权定位权限!");
            return;
        }
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().isCompleteInformation()) {
                MainActivity.toActivity(this);
            } else {
                UserInfoCompleteActivity.toCompleteInfoActivity(this);
            }
            finish();
        }

    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this, "登陆中...");
        showDialog();
    }

    @Override
    protected void bindEvent() {
        //mToolbar.setLeftOnClick(v -> TestActivity.toActivity(this));
        mToolbar.setRightOnClick(v -> SmsLoginActivity.toActivity(this, 1));
        mRelRegister.setOnClickListener(v -> RegisterActivity.toActivity(this));
        mTvForgetPassword.setOnClickListener(v -> ForgetPasswordActivity.toActivity(this));
        mRelLogin.setOnClickListener(v -> login());
    }

    private void showDialog(){
        if (SpUtil.getBoolean("first_open",true)) {
            new WebViewDialog(this, "appSixEdge/showPrivacyView")
                    .setBtnConfirmStr("同意")
                    .cancelClick(v -> finish())
                    .confirmClick(v -> {
                        SpUtil.putBoolean("first_open", false);
                    })
                    .show();


        }
    }

    private void login() {
        String tel = mEtName.getText().toString();
        String pwd = mEtPassword.getText().toString();
        if (!AccountCommonUtil.checkPhoneAndPwd(tel, pwd)) {
            return;
        }

        double lat = LocationManager.getInstance().getLatitude();
        double lon = LocationManager.getInstance().getLongitude();
        if (lat == 0 || lon == 0) {
            ToastUtil.showToast("未获取到位置信息!");
            return;
        }
        lodingDialog.show();
        mPresenter.login(tel, pwd, lat, lon);

    }

    @Override
    public void loginSuccess() {
        lodingDialog.dismiss();
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().isCompleteInformation()) {
                MainActivity.toActivity(this);
                ToastUtil.showToast("登录成功");
            } else {
                UserInfoCompleteActivity.toCompleteInfoActivity(this);
            }
            finish();
        }
    }

    @Override
    public void loginError(String msg) {
        lodingDialog.dismiss();
        ToastUtil.showToast(msg);
    }

    /**
     * 获取定位权限
     *
     * @return
     */
    private boolean requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            initData();
            LocationManager.getInstance().startLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            finish();
        }

    }
}
