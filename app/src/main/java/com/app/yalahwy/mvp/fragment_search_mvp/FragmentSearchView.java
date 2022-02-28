package com.app.yalahwy.mvp.fragment_search_mvp;

import com.app.yalahwy.models.ProductModel;

import java.util.List;

public interface FragmentSearchView {
    void onSuccess(List<ProductModel> data);
    void onUserNotRegister(String msg,ProductModel productModel,int position);
    void onFavoriteActionSuccess(ProductModel productModel,int position);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();


}
