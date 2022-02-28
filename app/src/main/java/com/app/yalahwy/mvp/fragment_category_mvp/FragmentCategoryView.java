package com.app.yalahwy.mvp.fragment_category_mvp;

import com.app.yalahwy.models.CategoryModel;
import com.app.yalahwy.models.ProductModel;
import com.app.yalahwy.models.SingleCategoryModel;
import com.app.yalahwy.models.SliderDataModel;
import com.app.yalahwy.models.SubCategoryModel;

import java.util.List;

public interface FragmentCategoryView {
    void onSuccess(List<SingleCategoryModel> data);
    void onSubCategorySuccess(List<CategoryModel> data);
    void onFailed(String msg);
    void onProgressCategoryShow();
    void onProgressCategoryHide();
    void onProgressSubCategoryShow();
    void onProgressSubCategoryHide();
}
