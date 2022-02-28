package com.app.yalahwy.mvp.activity_editprofile_mvp;


import com.app.yalahwy.models.UserModel;

public interface EditprofileActivityView {
    void onFinished();

    void onFailed(String msg);


    void onLoad();

    void onFinishload();

    void onSuccess(UserModel body);

}
