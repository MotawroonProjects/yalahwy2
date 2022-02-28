package com.app.yalahwy.mvp.activity_confirm_code_mvp;


import com.app.yalahwy.models.UserModel;

public interface ActivityConfirmCodeView {
    void onSuccessCode();
    void onCounterStarted(String time);
    void onCounterFinished();
    void onCodeFailed(String msg);

}
