package com.unlam.developerstudentclub.silapu.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class RecyclerViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder>
            implements Filterable {

    private Context context;
    @Getter  private ArrayList<PengaduanItem> listPengaduanItem = new ArrayList<>();
    @Getter  private ArrayList<PerdataItem> listPerdataitem = new ArrayList<>();
    @Getter  private ArrayList<PengaduanItem> filteredPengaduanItem = new ArrayList<>();
    @Getter  private ArrayList<PerdataItem> filteredPerdataItem = new ArrayList<>();


        public void setFilteredPengaduanItem(ArrayList<PengaduanItem> item){
            filteredPengaduanItem = item;
            listPengaduanItem = item;
        }

        public void setFilteredPerdataItem(ArrayList<PerdataItem> item){
            filteredPerdataItem = item;
            listPerdataitem = item;
        }

        public static String FRAGMENT_PENGADUAN = "pengaduan";
        public static String FRAGMENT_PERDATA = "perdata";
        private String IDENTIFIER;

        public RecyclerViewAdapter(Context context, String Identifier){
            this.context = context;
            IDENTIFIER = Identifier;
        }

        @Override
        public RecylerViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if(IDENTIFIER == FRAGMENT_PENGADUAN)
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pengaduan_items, parent, false);
            else
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.perdata_items, parent, false);

            return new RecylerViewAdapterHolder(view);
        }

        @Override
        public void onBindViewHolder(RecylerViewAdapterHolder holder, int position) {

            if(IDENTIFIER == FRAGMENT_PENGADUAN) {
                final PengaduanItem item = getFilteredPengaduanItem().get(position);
                Glide.with(context).load(item.getProfilImg()).into(holder.profile_img);
                holder.tv_sender.setText(item.getSender());
                holder.tv_classification.setText(item.getClassification());
                holder.tv_content.setText(item.getContent());
                holder.tv_filedates.setText(item.getFiledates());
                holder.tv_filetype.setText(item.getFiletype());
                holder.tv_filename.setText(item.getFilename());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            } else {
                final PerdataItem item = getFilteredPerdataItem().get(position);
                Glide.with(context).load(item.getProfilImg()).into(holder.profile_img);
                holder.tv_sender.setText(item.getSender());
                holder.tv_classification.setText(item.getClassification());
                holder.tv_content.setText(item.getContent());
                holder.tv_purpose.setText(item.getPurpose());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if(IDENTIFIER == FRAGMENT_PENGADUAN)
                return  getFilteredPengaduanItem().size() == 0 ? 0 : getFilteredPengaduanItem().size();
            else
                return getFilteredPerdataItem().size() == 0 ? 0 : getFilteredPerdataItem().size();
        }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                FilterResults filterResults = new FilterResults();

                if(IDENTIFIER == FRAGMENT_PENGADUAN){
                    if (charString.isEmpty())
                        filteredPengaduanItem = listPengaduanItem;
                    else {
                        ArrayList<PengaduanItem> filteredList = new ArrayList<>();
                        for (PengaduanItem row : listPengaduanItem) {
                            if(row.getSender().toLowerCase().contains(charString.toLowerCase())
                                    || row.getContent().toLowerCase().contains(charString.toLowerCase())
                                    || row.getClassification().toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(row);
                        }
                        filteredPengaduanItem = filteredList;
                    }
                    filterResults.values = filteredPengaduanItem;
                } else {
                    if (charString.isEmpty())
                        filteredPerdataItem = listPerdataitem;
                    else {
                        ArrayList<PerdataItem> filteredList = new ArrayList<>();
                        for (PerdataItem row : listPerdataitem) {
                            if(row.getSender().toLowerCase().contains(charString.toLowerCase())
                                    || row.getContent().toLowerCase().contains(charString.toLowerCase())
                                    || row.getClassification().toLowerCase().contains(charString.toLowerCase())
                                    || row.getPurpose().toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(row);
                        }
                        filteredPerdataItem = filteredList;
                    }
                    filterResults.values = filteredPerdataItem;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(IDENTIFIER == FRAGMENT_PENGADUAN)
                    filteredPengaduanItem = (ArrayList<PengaduanItem>) filterResults.values;
                else
                    filteredPerdataItem = (ArrayList<PerdataItem>) filterResults.values;

                notifyDataSetChanged();
            }
        };
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
            @Nullable @BindView(R.id.tv_classification)
            TextView tv_classification;
            @Nullable @BindView(R.id.tv_purpose)
            TextView tv_purpose;
            @Nullable @BindView(R.id.tv_content)
            TextView tv_content;
            @Nullable @BindView(R.id.items)
            View view;

        RecylerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
