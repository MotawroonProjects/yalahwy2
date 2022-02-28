package com.app.yalahwy.mvp.activity_my_address_mvp;

import com.app.yalahwy.models.AddressModel;
import com.app.yalahwy.models.ProductModel;

import java.util.List;

public interface ActivityMyAddressView {
    void onSuccess(List<AddressModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();
    void onRemovedSuccess();


}
