package com.android.laundryservice.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.laundryservice.Injection;
import com.android.laundryservice.R;
import com.android.laundryservice.data.authentication.AuthenticationRepository;
import com.android.laundryservice.login.LoginActivity;
import com.android.laundryservice.model.User;
import com.android.laundryservice.services.HomeActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, AuthenticationRepository.RegistrationCallback {

    private RegisterContract.Presenter mPresenter;
    private TextInputLayout nameTextInput, userNameTextInput, passwordTextInput, emailTextInput,phoneTextInput;
    private EditText nameEditText, userNameEditText, passwordEditText, emailEditText, phoneEditText;
    private ProgressBar progressBar;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPresenter = new RegisterPresenter(this, Injection.provideAuthenticationUseCaseHandler());
        initializeView();
    }

    private void initializeView() {
        nameTextInput = findViewById(R.id.name_text_input_edittext);
        userNameTextInput = findViewById(R.id.username_text_input_edittext);
        passwordTextInput = findViewById(R.id.password_text_input_edittext);
        emailTextInput = findViewById(R.id.email_text_input_edittext);
        phoneTextInput = findViewById(R.id.phone_text_input_edittext);

        nameEditText = findViewById(R.id.name_edit_text);
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nameTextInput.setError(null);
                nameTextInput.setErrorEnabled(false);
            }
        });
        userNameEditText = findViewById(R.id.username_edit_text);
        userNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                userNameTextInput.setError(null);
                userNameTextInput.setErrorEnabled(false);
            }
        });
        passwordEditText = findViewById(R.id.password_edit_text);
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                passwordTextInput.setError(null);
                passwordTextInput.setErrorEnabled(false);
            }
        });
        emailEditText = findViewById(R.id.email_edit_text);
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailTextInput.setError(null);
                emailTextInput.setErrorEnabled(false);
            }
        });
        phoneEditText = findViewById(R.id.phone_edit_text);
        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                phoneTextInput.setError(null);
                phoneTextInput.setErrorEnabled(false);
            }
        });

        progressBar = findViewById(R.id.progress_bar);
        registerButton = findViewById(R.id.register);
    }

    private User getUserInputData() {
        User user = new User();
        user.setName(nameEditText.getText().toString().trim());
        user.setUserName(userNameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString().trim());
        user.setEmail(emailEditText.getText().toString().trim());
        user.setPhone(phoneEditText.getText().toString().trim());
        return user;
    }

    @Override
    public void setNameInputTextErrorMessage() {
        nameTextInput.setError(getString(R.string.name_empty_err_msg));
    }

    @Override
    public void setUserNameInputTextErrorMessage() {
        userNameTextInput.setError(getString(R.string.username_empty_err_msg));
    }

    @Override
    public void setPasswordInputTextErrorMessage(String errorMessage) {
        passwordTextInput.setError(errorMessage);
    }

    @Override
    public void setEmailInputTextErrorMessage() {
        emailTextInput.setError(getString(R.string.email_empty_err_msg));
    }

    @Override
    public void setPhoneInputTextErrorMessage() {
        phoneTextInput.setError(getString(R.string.phone_empty_err_msg));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void goToHomePage() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    public void OnRegisterClicked(View view) {
        showProgressBar();
        User user = getUserInputData();
        if (mPresenter.validateUserData(user)) {
            mPresenter.registerNewUser(user, this);
        } else {
            hideProgressBar();
        }
    }

    @Override
    public void onSuccessfulRegistration(FirebaseUser firebaseUser) {
        hideProgressBar();
        Toast.makeText(this, "Registration is successfully completed\n Welcome " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
        goToHomePage();
    }

    @Override
    public void onFailedRegistration(String errmsg) {
        hideProgressBar();
        Toast.makeText(this, "Registration Failed\n" + errmsg, Toast.LENGTH_LONG).show();
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }
}
