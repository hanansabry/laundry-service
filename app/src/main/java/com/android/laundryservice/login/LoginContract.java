package com.android.laundryservice.login;


import com.android.laundryservice.BasePresenter;
import com.android.laundryservice.BaseView;
import com.android.laundryservice.data.authentication.AuthenticationRepository;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void setEmailInputTextErrorMessage();

        void setPasswordInputTextErrorMessage();

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter extends BasePresenter {
        void login(String email, String password, AuthenticationRepository.LoginCallback callback);

        boolean validateLoginData(String email, String password);
    }
}
