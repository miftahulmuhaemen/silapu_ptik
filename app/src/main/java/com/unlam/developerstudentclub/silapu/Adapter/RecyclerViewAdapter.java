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
import com.unlam.developerstudentclub.silapu.MainActivity;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_BMP;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_DOC;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_DOCX;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_JPG;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_PDF;
import static com.unlam.developerstudentclub.silapu.MainActivity.EXT_PNG;

public class RecyclerViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder>
            implements Filterable {

        private Context context;
        private UserPreference userPreference;

        @Getter  private ArrayList<PengaduanItem> listPengaduanItem = new ArrayList<>();
        @Getter  private ArrayList<PerdataItem> listPerdataitem = new ArrayList<>();
        @Getter  private ArrayList<PengaduanItem> filteredPengaduanItem = new ArrayList<>();
        @Getter  private ArrayList<PerdataItem> filteredPerdataItem = new ArrayList<>();

        private int IDENTIFIER;

        public void setFilteredPengaduanItem(ArrayList<PengaduanItem> item){
            filteredPengaduanItem = item;
            listPengaduanItem = item;
        }

        public void setFilteredPerdataItem(ArrayList<PerdataItem> item){
            filteredPerdataItem = item;
            listPerdataitem = item;
        }

        public RecyclerViewAdapter(Context context, int Identifier){
            this.context = context;
            IDENTIFIER = Identifier;
            userPreference = new UserPreference(context);
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
                holder.tv_sender.setText(userPreference.getNama());
                holder.tv_aduan.setText(item.getAduan());
                holder.tv_perihal.setText(item.getPerihal());
//                if(!item.getExtFile().isEmpty()){
//                    switch (item.getExtFile()){
//                        case EXT_DOC :
//                        case EXT_DOCX :
//                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a002_doc));
//                            break;
//                        case EXT_PDF :
//                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a001_pdf));
//                            break;
//                        case EXT_JPG :
//                        case EXT_PNG :
//                        case EXT_BMP :
//                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a003_jpeg));
//                            break;
//                    }
//                    holder.tv_log.setText(item.getLog());
//                    holder.tv_filetype.setText(item.getExtFile());
//                    holder.tv_filename.setText(item.getNamaFile());
//                } else {
//                    holder.item_view.setVisibility(View.GONE);
//                }

                holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        // Open Detail Dialog Fragment.
                        return false;
                    }
                });



            } else {
                final PerdataItem item = getFilteredPerdataItem().get(position);
                holder.tv_sender.setText(userPreference.getNama());
                holder.tv_permintaan.setText(item.getPermintaan());
                holder.tv_tujuan.setText(item.getTujuan());
                holder.tv_cara.setText(item.getCara());
                holder.tv_log.setText(item.getLog());
                holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
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
                            if(row.getAduan().toLowerCase().contains(charString.toLowerCase())
                                    || row.getPerihal().toLowerCase().contains(charString.toLowerCase()))
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
                            if(row.getCara().toLowerCase().contains(charString.toLowerCase())
                                    || row.getTujuan().toLowerCase().contains(charString.toLowerCase())
                                    || row.getPermintaan().toLowerCase().contains(charString.toLowerCase()))
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

            @Nullable @BindView(R.id.tv_tujuan)
            TextView tv_tujuan;
            @Nullable @BindView(R.id.tv_permintaan)
            TextView tv_permintaan;
            @Nullable @BindView(R.id.tv_cara)
            TextView tv_cara;

            @Nullable @BindView(R.id.tv_sender)
            TextView tv_sender;
            @Nullable @BindView(R.id.tv_perihal)
            TextView tv_perihal;
            @Nullable @BindView(R.id.tv_aduan)
            TextView tv_aduan;

            @Nullable @BindView(R.id.file_preview)
            ImageView file_preview;
            @Nullable @BindView(R.id.tv_filetype)
            TextView tv_filetype;
            @Nullable @BindView(R.id.tv_filename)
            TextView tv_filename;

            @Nullable @BindView(R.id.cardview_layout)
            View view;
            @Nullable @BindView(R.id.itemFile_layout)
            View item_view;
            @Nullable @BindView(R.id.tv_log)
            TextView tv_log;

        RecylerViewAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
