package com.android.laundryservice;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
