package com.app.yalahwy.ui.activity_sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.preferences.Preferences;
import com.app.yalahwy.ui.activity_confirm_code.ConfirmCodeActivity;
import com.app.yalahwy.ui.activity_home.HomeActivity;
import com.app.yalahwy.ui.activity_login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.app.yalahwy.R;
import com.app.yalahwy.databinding.ActivitySignUpBinding;
import com.app.yalahwy.databinding.DialogSelectImageBinding;
import com.app.yalahwy.language.Language;
import com.app.yalahwy.models.SignUpModel;
import com.app.yalahwy.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.app.yalahwy.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.app.yalahwy.share.Common;

import io.paperdb.Paper;

public class SignUpActivity extends AppCompatActivity implements ActivitySignUpView {
    private ActivitySignUpBinding binding;
    private ActivitySignUpPresenter presenter;
    private SignUpModel model;
    private Preferences preference;
    private double lat = 0.0, lng = 0.0;
    private String lang;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);

    }

    private void initView() {
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        preference = Preferences.getInstance();
        model = new SignUpModel();
        binding.setModel(model);

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().startsWith("0")){
                    binding.edtPhone.setText(null);
                }
            }
        });

        presenter = new ActivitySignUpPresenter(this, this);
        binding.btnSignUp.setOnClickListener(view -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
               // presenter.sign_up(model);
                navigateToConfirmCodeActivity();
            }
        });
        binding.tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);
            finish();
        });

    }

    private void navigateToConfirmCodeActivity() {
        Intent intent = new Intent(this, ConfirmCodeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        startActivityForResult(intent, 100);
    }


    @Override
    public void onSuccess(UserModel userModel) {
        preference.create_update_userdata(this, userModel);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            presenter.sign_up(model);

        }
    }
}