package com.android.laundryservice.register;


import com.android.laundryservice.data.authentication.AuthenticationRepository;
import com.android.laundryservice.model.User;
import com.android.laundryservice.usecase.AuthenticationUseCaseHandler;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View mRegistrationView;
    private final AuthenticationUseCaseHandler mUseCaseHandler;

    public RegisterPresenter(RegisterContract.View registrationView, AuthenticationUseCaseHandler useCaseHandler) {
        mRegistrationView = registrationView;
        mUseCaseHandler = useCaseHandler;

        mRegistrationView.setPresenter(this);
    }

    @Override
    public void registerNewUser(User user, AuthenticationRepository.RegistrationCallback callback) {
        mUseCaseHandler.registerNewUser(user, callback);
    }

    @Override
    public boolean validateUserData(User user) {
        boolean validate = true;
        if (user.getName() == null || user.getName().isEmpty()) {
            validate = false;
            mRegistrationView.setNameInputTextErrorMessage();
        }

        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            validate = false;
            mRegistrationView.setUserNameInputTextErrorMessage();
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            validate = false;
            mRegistrationView.setPasswordInputTextErrorMessage("Password can't be empty");
        }

        if (user.getPassword().length() < 6) {
            validate = false;
            mRegistrationView.setPasswordInputTextErrorMessage("Password must be at least 6 characters");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            validate = false;
            mRegistrationView.setEmailInputTextErrorMessage();
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            validate = false;
            mRegistrationView.setPhoneInputTextErrorMessage();
        }
        return validate;
    }

    @Override
    public void start() {

    }
}
