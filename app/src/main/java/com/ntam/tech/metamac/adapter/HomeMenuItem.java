package com.ntam.tech.metamac.adapter;

/**
 * Created by bassiouny on 10/04/18.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntam.tech.metamac.R;
import com.ntam.tech.metamac.interfaces.OnClickListenerAdapter;

import java.util.ArrayList;

public class HomeMenuItem extends RecyclerView.Adapter<HomeMenuItem.GridViewHolder> {

    //private Context mContext;
    private ArrayList<String> gridViewString;
    private ArrayList<Integer> gridViewImageId;
    private LayoutInflater mLayoutInflater;
    private OnClickListenerAdapter clickInterface;

    public HomeMenuItem(Fragment fragment, ArrayList<String> gridViewString, ArrayList<Integer> gridViewImageId) {
        clickInterface = (OnClickListenerAdapter) fragment;
        this.gridViewString = gridViewString;
        this.gridViewImageId = gridViewImageId;
        mLayoutInflater = (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_home_menu, parent, false);
        return new GridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.tvMenu.setText(gridViewString.get(position));
        holder.ivMenu.setImageResource(gridViewImageId.get(position));
    }

    @Override
    public int getItemCount() {
        return gridViewImageId.size();
    }

    protected class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenu;
        ImageView ivMenu;

        public GridViewHolder(View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tv_menu);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onClick(getAdapterPosition());
                }
            });
        }

    }
    public void updateMessageCountText(int total){
        int size = gridViewString.size();
        for(int i=0;i<size;i++){
            if(gridViewString.get(i).contains("Messages")){
                gridViewString.set(i,"Messages ("+total+")");
                notifyItemChanged(i);
            }
        }
    }


}
