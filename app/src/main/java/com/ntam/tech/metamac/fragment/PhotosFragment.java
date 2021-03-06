package com.ntam.tech.metamac.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.adapter.PhotoListAdapter;
import com.ntam.tech.metamac.api.utils.RetrofitRequest;
import com.ntam.tech.metamac.api.utils.RetrofitResponse;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;
import com.ntam.tech.metamac.model.Photo;
import com.ntam.tech.metamac.utils.SharedPref;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment implements OnClickListenerAdapter {


    RecyclerView recycleview;
    ProgressBar progress, progressupload;
    private Toolbar mToolbar;
    private PhotoListAdapter photoListAdapter;
    private TextView btnSelectImage;
    int userId;
    private List<Photo> photoList;
    private TextView tv_no_photo;


    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        onClick();
        if (getActivity() == null)
            return;
        userId = SharedPref.getMyAccount(getActivity()).getUserId();
        loadData();
    }

    private void onClick() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(PhotosFragment.this, "Select your image:");
            }
        });
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getActivity() == null)
            return;
        if (resultCode == getActivity().RESULT_OK) {
            startUpload(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() == null)
                        return;
                    Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    final String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    uploadImage(encoded);
                }
            }).start();
        }
    }

    private void findViewById(View view) {

        mToolbar = view.findViewById(R.id.toolbar);
        recycleview = view.findViewById(R.id.recycleview);
        progress = view.findViewById(R.id.progress);
        progressupload = view.findViewById(R.id.progressupload);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        btnSelectImage = view.findViewById(R.id.btn_select_image);
        tv_no_photo = view.findViewById(R.id.tv_no_photo);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Photos");

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void loadData() {
        RetrofitRequest.getPhotoList(userId, new RetrofitResponse<List<Photo>>() {
            @Override
            public void onSuccess(List<Photo> photos) {
                if (getActivity() == null)
                    return;
                photoList = photos;
                photoListAdapter = new PhotoListAdapter(getActivity(), photos, PhotosFragment.this);
                recycleview.setAdapter(photoListAdapter);
                if (photos.size() == 0) {
                    tv_no_photo.setVisibility(View.VISIBLE);
                    recycleview.setVisibility(View.INVISIBLE);
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String errorMessage) {
                if (getActivity() == null)
                    return;
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void uploadImage(String imagePathEncode) {
        RetrofitRequest.addPhoto(userId, imagePathEncode, new RetrofitResponse<Photo>() {
            @Override
            public void onSuccess(Photo photo) {
                startUpload(false);
                if (photo.getId() == 0)
                    return;
                if (photoListAdapter != null) {
                    photoListAdapter.addPhoto(photo);
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                startUpload(false);
                if (getActivity() == null)
                    return;
                Toast.makeText(getActivity(), R.string.upload_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startUpload(boolean start) {
        if (start) {
            progressupload.setVisibility(View.VISIBLE);
            btnSelectImage.setEnabled(false);
        } else {
            progressupload.setVisibility(View.GONE);
            btnSelectImage.setEnabled(true);
        }
    }

    @Override
    public void onClick(final int position) {
        final Photo photo = photoList.get(position);
        photo.changeLike();
        photoListAdapter.updatePhoto(position, photo);
        RetrofitRequest.addLikeToPhoto(userId, photo.getId(), new RetrofitResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (!aBoolean) {
                    onFailed("");
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                photo.changeLike();
                photoListAdapter.updatePhoto(position, photo);
                if (getActivity() == null)
                    return;
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
