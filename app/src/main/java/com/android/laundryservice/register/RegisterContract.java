package com.android.laundryservice.register;


import com.android.laundryservice.BasePresenter;
import com.android.laundryservice.BaseView;
import com.android.laundryservice.data.authentication.AuthenticationRepository;
import com.android.laundryservice.model.User;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void setNameInputTextErrorMessage();

        void setUserNameInputTextErrorMessage();

        void setPasswordInputTextErrorMessage(String errorMessage);

        void setEmailInputTextErrorMessage();

        void setPhoneInputTextErrorMessage();

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter extends BasePresenter {

        void registerNewUser(User user, AuthenticationRepository.RegistrationCallback callback);

        boolean validateUserData(User user);

    }

}
