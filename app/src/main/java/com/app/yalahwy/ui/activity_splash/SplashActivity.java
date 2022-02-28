package com.app.yalahwy.ui.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.yalahwy.R;
import com.app.yalahwy.databinding.ActivitySplashBinding;
import com.app.yalahwy.language.Language;
import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.preferences.Preferences;
import com.app.yalahwy.ui.activity_home.HomeActivity;
import com.app.yalahwy.ui.activity_login.LoginActivity;

import io.paperdb.Paper;
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new TransitionSet();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(500);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        new Handler().postDelayed(() -> {
            Intent intent;
            if (userModel == null) {
                intent = new Intent(this, LoginActivity.class);
            } else {
                intent = new Intent(this, HomeActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }

}






