package com.app.yalahwy.mvp.fragment_order_mvp;

import com.app.yalahwy.models.CommentModel;
import com.app.yalahwy.models.OrderModel;

import java.util.List;

public interface FragmentOrderView {
    void onSuccess(List<OrderModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
