package com.app.yalahwy.mvp.activity_order_details_mvp;

import com.app.yalahwy.models.CartDataModel;
import com.app.yalahwy.models.ProductModel;
import com.app.yalahwy.models.SingleOrderModel;

public interface ActivityOrderDetailsView {

    void onSuccess(SingleOrderModel data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();


}
