package com.app.yalahwy.ui.activity_sub_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.yalahwy.R;
import com.app.yalahwy.adapters.ProductAdapter2;
import com.app.yalahwy.adapters.SubCategories2Adapter;
import com.app.yalahwy.databinding.ActivityProductsBinding;
import com.app.yalahwy.databinding.ActivitySubCategoryBinding;
import com.app.yalahwy.language.Language;
import com.app.yalahwy.models.CategorySubCategoryModel;
import com.app.yalahwy.models.ProductDataModel;
import com.app.yalahwy.models.SubCategoryModel;
import com.app.yalahwy.mvp.activity_product_mvp.ActivityProductPresenter;
import com.app.yalahwy.remote.Api;
import com.app.yalahwy.tags.Tags;
import com.app.yalahwy.ui.activity_products.ProductsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {

    private ActivitySubCategoryBinding binding;
    private String lang;
    private CategorySubCategoryModel categorySubCategoryModel;
    private List<SubCategoryModel> subCategoryModelList;
    private SubCategories2Adapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        categorySubCategoryModel = (CategorySubCategoryModel) intent.getSerializableExtra("data");

    }

    private void initView() {
        subCategoryModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setModel(categorySubCategoryModel);
        binding.recView.setLayoutManager(new GridLayoutManager(this,2));
        subCategoryModelList.addAll(categorySubCategoryModel.getSubs());
        adapter = new SubCategories2Adapter(subCategoryModelList, this);
        binding.recView.setAdapter(adapter);

        if (subCategoryModelList.size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }


        binding.llBack.setOnClickListener(view -> finish());

    }


    public void setItemModel(SubCategoryModel subCategoryModel) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("data", subCategoryModel);
        startActivity(intent);
    }
}