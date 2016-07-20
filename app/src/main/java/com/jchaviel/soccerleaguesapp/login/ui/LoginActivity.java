package com.jchaviel.soccerleaguesapp.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jchaviel.soccerleaguesapp.R;
import com.jchaviel.soccerleaguesapp.SoccerLeaguesApp;
import com.jchaviel.soccerleaguesapp.login.LoginPresenter;
import com.jchaviel.soccerleaguesapp.main.ui.MainActivity;

import javax.inject.Inject;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;

/**
 * Created by jchavielreyes on 7/4/16.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.btnSignin)           Button btnSignIn;
    @Bind(R.id.btnSignup)           Button btnSignUp;
    @Bind(R.id.editTxtEmail)        EditText inputEmail;
    @Bind(R.id.editTxtPassword)     EditText inputPassword;
    @Bind(R.id.progressBar)         ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer) RelativeLayout container;

    private SoccerLeaguesApp app;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupToolbar();
        app = (SoccerLeaguesApp)getApplication();
        setupInjection();
        loginPresenter.onCreated();
        loginPresenter.validateLogin(null, null);
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }

    private void setupToolbar() {
        //Set action bar
        Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.action_bar);
        actionBarToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
        actionBarToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        //de esta forma evito el memory leak
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(true);
        inputPassword.setEnabled(true);
        btnSignIn.setEnabled(true);
        btnSignUp.setEnabled(true);
    }
}

