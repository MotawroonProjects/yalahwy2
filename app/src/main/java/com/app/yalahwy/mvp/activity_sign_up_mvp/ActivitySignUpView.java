package com.app.yalahwy.mvp.activity_sign_up_mvp;


import com.app.yalahwy.models.UserModel;

public interface ActivitySignUpView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
