package com.app.yalahwy.ui.activity_favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.yalahwy.R;
import com.app.yalahwy.adapters.FavoriteProductAdapter;
import com.app.yalahwy.adapters.ProductAdapter2;
import com.app.yalahwy.databinding.ActivityFavoriteBinding;
import com.app.yalahwy.databinding.ActivityProductsBinding;
import com.app.yalahwy.language.Language;
import com.app.yalahwy.models.ChildModel;
import com.app.yalahwy.models.ProductModel;
import com.app.yalahwy.models.SingleCategoryModel;
import com.app.yalahwy.models.SubCategoryModel;
import com.app.yalahwy.mvp.activity_favorite_mvp.ActivityFavoritePresenter;
import com.app.yalahwy.mvp.activity_favorite_mvp.ActivityFavoriteView;
import com.app.yalahwy.mvp.activity_product_mvp.ActivityProductPresenter;
import com.app.yalahwy.share.Common;
import com.app.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FavoriteActivity extends AppCompatActivity implements ActivityFavoriteView {
    private ActivityFavoriteBinding binding;
    private String lang;
    private List<ProductModel> productModelList;
    private FavoriteProductAdapter adapter;
    private ActivityFavoritePresenter presenter;
    private boolean isDataChanged = false;
    private int selectedPos=-1;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        initView();

    }
    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        productModelList = new ArrayList<>();
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteProductAdapter(productModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityFavoritePresenter(this,this);
        presenter.getProducts();
        binding.llBack.setOnClickListener(view -> onBackPressed());


    }

    public void setProductItemModel(ProductModel model) {
        Intent intent = new Intent(FavoriteActivity.this, ProductDetailsActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }


    @Override
    public void onSuccess(List<ProductModel> data)
    {

        if (data.size()>0){
            productModelList.addAll(data);
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onRemoveFavoriteSuccess()
    {
        isDataChanged = true;
        if (productModelList.size()>0&&selectedPos!=-1){
            productModelList.remove(selectedPos);
            adapter.notifyItemRemoved(selectedPos);
            if (productModelList.size()>0){
                binding.tvNoData.setVisibility(View.GONE);
            }else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            selectedPos=-1;
        }
    }
    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onProgressShow()
    {
        productModelList.clear();
        binding.progBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onProgressHide()
    {
        binding.progBar.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed()
    {
        if (isDataChanged){
            setResult(RESULT_OK);
        }
        finish();
    }

    public void removeFavorite(ProductModel model, int adapterPosition) {
        selectedPos = adapterPosition;
        presenter.remove_favorite(model);

    }
}