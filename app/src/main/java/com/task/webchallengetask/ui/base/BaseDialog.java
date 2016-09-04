package com.task.webchallengetask.ui.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.dialogs.presenters.BaseDialogPresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseDialog<P extends BaseDialogPresenter> extends DialogFragment
        implements BaseDialogView<P> {

    private P mPresenter;
    private int mContentResource = getLayoutResource();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        super.onCreateView(_inflater, _container, _savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = _inflater.inflate(R.layout.dialog_base_layout, _container, false);

        getPresenter().bindView(this);
        if (mContentResource != 0) {
            FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.content_container_BDL);
            frameLayout.removeAllViews();
            frameLayout.addView(_inflater.inflate(mContentResource, frameLayout, false));
        }
        findUI(rootView);
        return rootView;
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        setupUI();
        getPresenter().onViewCreated();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        getPresenter().onDestroyView();
        getPresenter().unbindView();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    public void setTitle(String _title) {
        new UnsupportedOperationException();
    }

    public void setMessage(String _message) {
        new UnsupportedOperationException();
    }

    public void setOnClickListener(View.OnClickListener _listener) {
        new UnsupportedOperationException();
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }
}
