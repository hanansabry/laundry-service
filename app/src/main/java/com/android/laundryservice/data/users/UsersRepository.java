package com.android.laundryservice.data.users;

import com.android.laundryservice.model.User;

public interface UsersRepository {

    interface UserInsertionCallback {
        void onUserInsertedSuccessfully();

        void onUserInsertedFailed(String errmsg);
    }

    void insertNewUser(User user, UserInsertionCallback callback);
}
