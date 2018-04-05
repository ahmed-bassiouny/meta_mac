package com.ntam.tech.metamac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.model.About;
import com.ntam.tech.metamac.model.WebViewModel;
import com.ntam.tech.metamac.utils.Constant;

public class WebViewRequestsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private int key;
    private String url;
    private WebView webView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_requests);
        key = getIntent().getIntExtra("key", 0);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        progress = (ProgressBar) findViewById(R.id.progress);
        setSupportActionBar(mToolbar);

        switch (key) {
            case Constant.LEADERSHIP_KEY:
                // leadership_principles
                getSupportActionBar().setTitle("Leadership Principles");
                url = "leadership_principles.php";
                break;
            case Constant.DINNER_KEY:
                // dinners.php
                getSupportActionBar().setTitle("Dinners");
                url = "dinners.php";
                break;
            case Constant.INFORMATION_KEY:
                // general_information
                getSupportActionBar().setTitle("General Information");
                url = "general_information.php";
                break;
            default:
                finish();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // get data
        RetrofitRequest.getNewRequests(url, new RetrofitResponse<WebViewModel>() {
            @Override
            public void onSuccess(WebViewModel webViewModel) {
                webView.loadDataWithBaseURL("", webViewModel.getBody(), "text/html", "utf-8", "");
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(WebViewRequestsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });
    }
}
