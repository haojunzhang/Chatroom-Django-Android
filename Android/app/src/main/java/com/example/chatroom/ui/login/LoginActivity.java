package com.example.chatroom.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.chatroom.R;
import com.example.chatroom.base.BaseActivity;
import com.example.chatroom.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;

    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        super.onCreate(savedInstanceState);

        initView();
        initViewModel();
    }

    private void initView() {

    }

    private void initViewModel() {
        mLoginViewModel = obtainViewModel(LoginViewModel.class);
        observeBaseLiveData(mLoginViewModel);

        mLoginViewModel.loginSuccess().observe(this, success -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick(View view) {
        mLoginViewModel.login(etUserName.getText().toString());
    }
}
