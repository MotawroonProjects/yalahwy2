package com.app.yalahwy.mvp.activity_menu_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.yalahwy.R;
import com.app.yalahwy.models.AddFavoriteDataModel;
import com.app.yalahwy.models.LogoutModel;
import com.app.yalahwy.models.MenuDataModel;
import com.app.yalahwy.models.MenuModel;
import com.app.yalahwy.models.ProductDataModel;
import com.app.yalahwy.models.ProductModel;
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


public class ActivityMenuPresenter {
    private Context context;
    private ActivityMenuView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityMenuPresenter(Context context, ActivityMenuView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }


    public void getMenus()
    {
        if (userModel == null) {
            return;
        }
        String user_id = String.valueOf(userModel.getData().getUser().getId());
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getMenu(userModel.getData().getToken(),user_id)
                .enqueue(new Callback<MenuDataModel>() {
                    @Override
                    public void onResponse(Call<MenuDataModel> call, Response<MenuDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
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
                    public void onFailure(Call<MenuDataModel> call, Throwable t) {
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


    public void remove_favorite(MenuModel model)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String user_id = String.valueOf(userModel.getData().getUser().getId());
        Api.getService(Tags.base_url)
                .deleteITemMenu(userModel.getData().getToken(), String.valueOf(model.getId()))
                .enqueue(new Callback<LogoutModel>() {
                    @Override
                    public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null&&response.body().getStatus()==200) {
                                getMenus();
                                view.onRemoveFavoriteSuccess();
                            }


                        } else {

                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
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
