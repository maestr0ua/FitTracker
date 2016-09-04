package com.task.webchallengetask.ui.modules.login;


import android.content.IntentSender;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginPresenter.LoginView {

    private SignInButton btnSignIn;
    private LoginButton loginButton;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void findUI(View _rootView) {
        btnSignIn = (SignInButton) findViewById(R.id.btnSignIn_AL);
        loginButton = (LoginButton) findViewById(R.id.btnFacebookLogin_AL);
    }

    @Override
    public void setupUI() {
        btnSignIn.setStyle(SignInButton.SIZE_STANDARD, SignInButton.COLOR_DARK);
        setGooglePlusButtonText(btnSignIn, "Sign up with google +");
        RxUtils.click(btnSignIn, o -> getPresenter().onGoogleSignInClicked());
    }

    @Override
    public void startSenderIntent(IntentSender _intentSender, int _const) throws IntentSender.SendIntentException {
        this.startIntentSenderForResult(_intentSender,
                _const, null, 0, 0, 0);
    }

    @Override
    public void setLoginPermission(String _loginPermission) {
        loginButton.setPublishPermissions(_loginPermission);
    }

    @Override
    public void setCallbackManager(CallbackManager _callbackManager) {
        loginButton.registerCallback(_callbackManager, getPresenter());
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(16);
                tv.setAllCaps(true);
                return;
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        getPresenter().onActivityResult(requestCode, resultCode, data);
//    }

}
