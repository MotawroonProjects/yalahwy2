package com.app.yalahwy.mvp.activity_product_mvp;

import com.app.yalahwy.models.CartDataModel;
import com.app.yalahwy.models.ProductModel;

import java.util.List;

public interface ActivityProductView {
    void onSuccess(List<ProductModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();
    void onAddToMenuSuccess();
    void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList);

    void onCartCountUpdated(int count);
    void onAmountSelectedFromCart(int amount);

    void onRemoveFavoriteSuccess();
    void onUserNotRegister(String msg,ProductModel productModel,int position,String type);
    void onFavoriteActionSuccess(ProductModel productModel,int position,String type);
}
