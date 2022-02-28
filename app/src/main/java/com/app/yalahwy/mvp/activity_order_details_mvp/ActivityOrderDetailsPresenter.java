package com.app.yalahwy.mvp.activity_order_details_mvp;

import android.content.Context;
import android.util.Log;

import com.app.yalahwy.R;
import com.app.yalahwy.models.AddFavoriteDataModel;
import com.app.yalahwy.models.OrderModel;
import com.app.yalahwy.models.ProductDataModel;
import com.app.yalahwy.models.ProductModel;
import com.app.yalahwy.models.SingleOrderModel;
import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.mvp.activity_favorite_mvp.ActivityFavoriteView;
import com.app.yalahwy.preferences.Preferences;
import com.app.yalahwy.remote.Api;
import com.app.yalahwy.share.Common;
import com.app.yalahwy.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityOrderDetailsPresenter {
    private Context context;
    private ActivityOrderDetailsView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityOrderDetailsPresenter(Context context, ActivityOrderDetailsView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }


    public void getProduct(OrderModel orderModel) {
        if (userModel == null) {
            return;
        }
        String user_id = String.valueOf(userModel.getData().getUser().getId());
        Log.e("user",userModel.getData().getToken());
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getSingleOrders(userModel.getData().getToken(),String.valueOf(orderModel.getId()))
                .enqueue(new Callback<SingleOrderModel>() {
                    @Override
                    public void onResponse(Call<SingleOrderModel> call, Response<SingleOrderModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getOrder() != null) {
                                view.onSuccess(response.body());

                            }


                        } else {
                            view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleOrderModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }





}
