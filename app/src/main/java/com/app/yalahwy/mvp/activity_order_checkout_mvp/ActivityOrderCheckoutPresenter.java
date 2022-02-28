package com.app.yalahwy.mvp.activity_order_checkout_mvp;

import android.content.Context;

import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.mvp.activity_cart_mvp.CartActivityView;
import com.app.yalahwy.preferences.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOrderCheckoutPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private OrderCheckoutActivityView view;
    private Context context;

    public ActivityOrderCheckoutPresenter(OrderCheckoutActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



}
