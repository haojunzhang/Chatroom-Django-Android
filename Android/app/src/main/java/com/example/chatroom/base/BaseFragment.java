package com.example.chatroom.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.chatroom.data.factory.ViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment {

    @Inject
    ViewModelFactory mViewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    protected abstract int getLayoutResource();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
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

    public void showLoadingView() {
        if (getBaseActivity() != null) {
            getBaseActivity().showLoadingView();
        }
    }

    public void dismissLoadingView() {
        if (getBaseActivity() != null) {
            getBaseActivity().dismissLoadingView();
        }
    }

    public void toast(int resId) {
        toast(getString(resId));
    }

    public void toast(String text) {
        if (getBaseActivity() != null) {
            getBaseActivity().toast(text);
        }
    }

    public void alert(int resId) {
        alert(getString(resId));
    }

    public void alert(String text) {
        if (getBaseActivity() != null) {
            getBaseActivity().alert(text);
        }
    }

    public void hideKeyboard() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideKeyboard();
        }
    }

    public void openActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    public void finishAffinity() {
        if (getBaseActivity() != null) {
            getBaseActivity().finishAffinity();
        }
    }

    public void logout() {
        if (getBaseActivity() != null) {
            getBaseActivity().logout();
        }
    }
}
