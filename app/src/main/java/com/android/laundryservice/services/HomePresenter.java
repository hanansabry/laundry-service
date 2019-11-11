package com.android.laundryservice.services;

import com.android.laundryservice.Injection;
import com.android.laundryservice.usecase.AuthenticationUseCaseHandler;

public class HomePresenter implements HomeContract.Presenter {

    private final AuthenticationUseCaseHandler useCaseHandler;
    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view, AuthenticationUseCaseHandler useCaseHandler) {
        this.useCaseHandler = useCaseHandler;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void logout() {
        useCaseHandler.logout();
    }

    @Override
    public String getUserEmail() {
        return useCaseHandler.getUserEmail();
    }

    @Override
    public void start() {
        useCaseHandler.logout();
    }
}
