package com.app.yalahwy.ui.activity_product_details.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.yalahwy.R;
import com.app.yalahwy.adapters.CommentsAdapter;
import com.app.yalahwy.adapters.DataAdapter;
import com.app.yalahwy.adapters.SliderAdapter;
import com.app.yalahwy.databinding.FragmentCommentsBinding;
import com.app.yalahwy.databinding.FragmentHomeBinding;
import com.app.yalahwy.models.BankDataModel;
import com.app.yalahwy.models.CommentModel;
import com.app.yalahwy.models.ProductModel;
import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.mvp.fragment_comments_mvp.FragmentCommentPresenter;
import com.app.yalahwy.mvp.fragment_comments_mvp.FragmentCommentView;
import com.app.yalahwy.preferences.Preferences;
import com.app.yalahwy.ui.activity_home.HomeActivity;
import com.app.yalahwy.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class Fragment_Comments extends Fragment implements FragmentCommentView {
    private FragmentCommentsBinding binding;
    private ProductDetailsActivity activity;
    private ProductModel productModel;
    private List<CommentModel> commentModelList;
    private String lang;
    private CommentsAdapter adapter;
    private FragmentCommentPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;


    public static Fragment_Comments newInstance(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", productModel);
        Fragment_Comments fragment_comments = new Fragment_Comments();
        fragment_comments.setArguments(bundle);
        return fragment_comments;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        commentModelList = new ArrayList<>();
        activity = (ProductDetailsActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        presenter = new FragmentCommentPresenter(activity, this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            productModel = (ProductModel) bundle.getSerializable("data");
        }
        adapter = new CommentsAdapter(commentModelList, activity);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(adapter);
        if (productModel.getComments().size() > 0) {
            commentModelList.addAll(productModel.getComments());
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);

        } else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }

        if (userModel == null) {
            binding.llComment.setVisibility(View.GONE);
        } else {
            binding.llComment.setVisibility(View.VISIBLE);

        }


        binding.btnSend.setOnClickListener(view -> {
            String comment = binding.edtComment.getText().toString();
            if (!comment.isEmpty()) {
                binding.edtComment.setText(null);
                presenter.add_comment(comment, productModel);
            }
        });

    }


    @Override
    public void onSuccess(CommentModel commentModel) {
        commentModelList.add(0, commentModel);

        adapter.notifyDataSetChanged();
        activity.updateCommentsCount(commentModelList.size());
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
