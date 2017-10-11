package com.example.ahmed.eyecare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.eyecare.R;
import com.example.ahmed.eyecare.model.Post;

import java.util.List;

/**
 * Created by ahmed on 11/10/17.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.CutomViewHolder> {


    List<Post> posts;
    public NewsFeedAdapter(List<Post> posts) {
        this.posts=posts;
    }

    @Override
    public NewsFeedAdapter.CutomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newfeed, null);
        CutomViewHolder cutomViewHolder = new CutomViewHolder(view);
        return cutomViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsFeedAdapter.CutomViewHolder holder, int position) {

        // TODO : SET DATA
        Post post = posts.get(position);
        holder.tvDay.setText(post.getDayDate());
        holder.tvMonth.setText("");

        holder.tvUserName.setText(post.getUserName());
        holder.tvUserPost.setText(post.getPost());
        holder.ivTime.setText(post.getTime());
        holder.ivComment.setText(post.getCommentsSize());
        holder.ivLike.setText(post.getNumberOfLike());
        if(post.getIsMakeLike()){
            // TODO red color
        }else {
            // TODO null color
        }
        // TODO Image
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class CutomViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvMonth ,tvUserName ,tvUserPost , ivTime , ivComment ,ivLike;
        ImageView ivAvatar;

        public CutomViewHolder(View view) {
            super(view);
            tvDay = view.findViewById(R.id.tv_day);
            tvMonth = view.findViewById(R.id.tv_month);
            tvUserName = view.findViewById(R.id.tv_user_name);
            tvUserPost = view.findViewById(R.id.tv_user_post);
            ivTime = view.findViewById(R.id.tv_time);
            ivComment = view.findViewById(R.id.tv_comment);
            ivLike = view.findViewById(R.id.tv_like);
            ivAvatar = view.findViewById(R.id.iv_avatar);
        }
    }
}
