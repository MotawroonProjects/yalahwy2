package com.app.yalahwy.mvp.activity_login_presenter;

import com.app.yalahwy.models.UserModel;

public interface ActivityLoginView {
    void onLoginSuccess(UserModel userModel);
    void onFailed(String msg);

}
