package com.ntam.tech.metamac.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.utils.Constant;

public class ViewImageWithZoomActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_with_zoom);
        SubsamplingScaleImageView imageView = findViewById(R.id.imageView);
        //imageView.setImage(ImageSource.resource(R.drawable.monkey));
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        key = getIntent().getIntExtra("key", 0);
        if (key == Constant.LEADERSHIP_KEY) {
            getSupportActionBar().setTitle("Leadership Principles");
            imageView.setImage(ImageSource.resource(R.drawable.leader));
            //imageView.setScaleX(1.5f);
        } else {
            getSupportActionBar().setTitle("Ferring Philosophy");
            imageView.setImage(ImageSource.resource(R.drawable.about));
        }
    }
}
