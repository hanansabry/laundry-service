package com.android.laundryservice.services;

import com.android.laundryservice.BasePresenter;
import com.android.laundryservice.BaseView;
import com.android.laundryservice.model.Service;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void onServiceClicked(Service service);
    }

    interface Presenter extends BasePresenter {
        void logout();

        String getUserEmail();
    }
}
