package com.app.yalahwy.ui.activity_contact_us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.yalahwy.R;
import com.app.yalahwy.databinding.ActivityContactUsBinding;
import com.app.yalahwy.language.Language;
import com.app.yalahwy.models.ContactUsModel;
import com.app.yalahwy.models.UserModel;
import com.app.yalahwy.preferences.Preferences;
import com.app.yalahwy.remote.Api;
import com.app.yalahwy.share.Common;
import com.app.yalahwy.tags.Tags;

import java.io.IOException;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    private ActivityContactUsBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private ContactUsModel contactUsModel;
    private String lang="ar";
    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        Paper.init(this);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        contactUsModel = new ContactUsModel();
        if (userModel!=null){
            contactUsModel.setName(userModel.getData().getUser().getFull_name());
            contactUsModel.setEmail(userModel.getData().getUser().getEmail());
            contactUsModel.setPhone(userModel.getData().getUser().getPhone());
        }

        binding.setContactModel(contactUsModel);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.btnSend.setOnClickListener(view -> {
            if (contactUsModel.isDataValid(this)){
                contactUs();
            }
        });
        binding.llBack.setOnClickListener(view -> finish());
    }

    private void contactUs()
    {
        ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .contactUs(contactUsModel.getName(),contactUsModel.getEmail(),contactUsModel.getPhone(),contactUsModel.getMessage())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(ContactUsActivity.this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("error", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(ContactUsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ContactUsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(ContactUsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ContactUsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
}