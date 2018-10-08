package com.unlam.developerstudentclub.silapu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class RecyclerView extends android.support.v7.widget.RecyclerView.Adapter<RecyclerView.RecylerViewAdapterHolder>  {

    private Context context;
    @Getter  @Setter  private ArrayList<PengaduanItem> listItem;

    public RecyclerView(Context context){
        this.context = context;
    }

    @Override
    public RecylerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pengaduan_items, parent, false);
        return new RecylerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(RecylerViewAdapterHolder holder, int position) {
        final PengaduanItem item = getListItem().get(position);


//        Glide.with(context).load(item.getProfilImg()).into(holder.profile_img);
//        holder.tvJudul.setText(data.getTitle());
//        holder.vote_avarage.setText(data.getVote_average());
//
//        Glide.with(context).load(R.drawable.a001_pdf).into(holder.file_preview);



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return  getListItem().size() == 0 ? 0 : getListItem().size();
    }

    class RecylerViewAdapterHolder extends android.support.v7.widget.RecyclerView.ViewHolder{

        @Nullable @BindView(R.id.profile_img)
        ImageView profile_img;
        @Nullable @BindView(R.id.file_preview)
        ImageView file_preview;
        @Nullable @BindView(R.id.tv_sender)
        TextView tv_sender;
        @Nullable @BindView(R.id.tv_filetype)
        TextView tv_filetype;
        @Nullable @BindView(R.id.tv_filename)
        TextView tv_filename;
        @Nullable @BindView(R.id.tv_filedates)
        TextView tv_filedates;
        @Nullable @BindView(R.id.items)
        View view;

        RecylerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
