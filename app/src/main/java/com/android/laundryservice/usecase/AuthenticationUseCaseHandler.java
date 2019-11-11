package com.android.laundryservice.usecase;

import com.android.laundryservice.data.authentication.AuthenticationRepository;
import com.android.laundryservice.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationUseCaseHandler {

    private AuthenticationRepository mRepository;

    public AuthenticationUseCaseHandler(AuthenticationRepository repository) {
        mRepository = repository;
    }

    public void registerNewUser(User user, AuthenticationRepository.RegistrationCallback callback){
        mRepository.registerNewUser(user, callback);
    }

    public void login(String email, String password, AuthenticationRepository.LoginCallback callback) {
        mRepository.login(email, password, callback);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        } else {
            return "";
        }
    }
}
