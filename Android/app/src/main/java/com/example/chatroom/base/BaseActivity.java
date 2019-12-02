package com.example.chatroom.base;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.chatroom.R;
import com.example.chatroom.data.factory.ViewModelFactory;
import com.example.chatroom.utils.SystemUtils;
import com.example.chatroom.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {


    // loading alert
    private AlertDialog loadingDialog;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;


    @Inject
    ViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // view binding
        ButterKnife.bind(this);

        // DI inject
        AndroidInjection.inject(this);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(v -> {
                hideKeyboard();
                finish();
            });
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    protected <T extends ViewModel> T obtainViewModel(Class<T> clazz) {
        return ViewModelProviders.of(this, mViewModelFactory).get(clazz);
    }

    protected void observeBaseLiveData(BaseViewModel mBaseViewModel) {
        // is loading
        mBaseViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                showLoadingView();
            } else {
                dismissLoadingView();
            }
        });
    }


    protected void logout() {
    }

    // 封裝開啟Activity
    public void openActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    // 封裝toast
    public void toast(int resId) {
        ToastUtils.show(this, resId);
    }

    // 封裝toast
    public void toast(String text) {
        ToastUtils.show(this, text);
    }

    // 顯示讀取中的alert
    public void showLoadingView() {
        if (loadingDialog == null) {
            loadingDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(LayoutInflater.from(this).inflate(R.layout.loading_dialog, null))
                    .create();
        }
        if (!isFinishing()) {
            loadingDialog.show();
        }
    }

    // 消失讀取中的alert
    public void dismissLoadingView() {
        if (loadingDialog != null && loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.dismiss();
        }
    }

    // 隱藏鍵盤
    public void hideKeyboard() {
        SystemUtils.hideKeyboard(this);
    }

    // 顯示alert
    public void alert(int resId) {
        alert(getString(resId), null);
    }

    // 顯示alert
    public void alert(String msg) {
        alert(msg, null);
    }

    // 顯示alert
    public void alert(int resId, DialogInterface.OnClickListener onPositiveClickListener) {
        alert(getString(resId), onPositiveClickListener);
    }

    // 顯示alert
    public void alert(String msg, DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.confirm, onPositiveClickListener);
        if (!isFinishing()) {
            builder.show();
        }
    }
}
