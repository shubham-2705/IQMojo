package com.iqmojo.base.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.iqmojo.base.utils.PermissionUtil;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public static String TAG="----IQMOJO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void setToolbar(int toolbarColor, Toolbar myToolbar, String title) {
//        myToolbar.setBackgroundColor(ContextCompat.getColor(this, toolbarColor));
//
//        myToolbar.setTitle("");
//        setSupportActionBar(myToolbar);
//
//        TextView toolbar_title = (TextView) myToolbar.findViewById(R.id.toolbar_title);
//        if (TextUtils.isEmpty(title)) {
//            toolbar_title.setBackgroundResource(R.drawable.oxigen_wallet);
//        } else {
//            toolbar_title.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
//        }
//        toolbar_title.setText(title);



        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        if (BuildConfig.DEBUG && this instanceof EnterMobileActivity) {
//            toolbar_title.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    showLinkDialog();
//                    return false;
//                }
//            });
//        }
    }

//    public void setToolbar(Toolbar myToolbar, String title) {
//        setToolbar(R.color.white_color, myToolbar, title);
//    }

    public void showKeypad() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void hideDialogKeypad() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void hideKeypad() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void openUrlinBrowser(String url)
    {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public void showProgressdialog(String message) {
        showProgressdialog(message, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.with(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showProgressdialog(String message, boolean isCancelable) {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(isCancelable);
//        progressDialog.setProgress(0);
        progressDialog.show();
    }

    public void hideProgressDialog() {
//        removeProgressDialog(false);
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }
}
