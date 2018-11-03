package com.unlam.developerstudentclub.silapu.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

import static com.unlam.developerstudentclub.silapu.Utils.Util.ADMIN_REPLY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_BMP;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_DOC;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_DOCX;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_JPG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_PDF;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_PNG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.URL_File;

public class RecyclerViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder> {
//            implements Filterable {

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

                if(item.getKet().equals(ADMIN_REPLY)){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.START;
                    holder.view.setLayoutParams(params);
                    holder.tv_log.setLayoutParams(params);
                    holder.tv_sender.setText(ADMIN_REPLY);
                } else {
                    holder.tv_sender.setText(userPreference.getNama());
                }

                holder.tv_aduan.setText(item.getAduan());
                holder.tv_perihal.setText(item.getPerihal());
                holder.tv_log.setText(item.getLog());

                if(!item.getNamaFile().isEmpty()){
                    try{
                        String [] bit = item.getNamaFile().split("\\.");
                        item.setExtFile(bit[1]);
                    } catch (Exception e){
                        /*Catching Errors Without Print it in Anywhere*/
                    }

                    switch (item.getExtFile().toLowerCase()){
                        case EXT_DOC :
                        case EXT_DOCX :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a002_doc))
                                    .into(holder.file_preview);
                            break;
                        case EXT_PDF :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a001_pdf))
                                    .into(holder.file_preview);
                            break;
                        case EXT_JPG :
                        case EXT_PNG :
                        case EXT_BMP :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.a003_jpeg))
                                    .into(holder.file_preview);
                            break;
                    }
                    holder.tv_filetype.setText(item.getExtFile());
                    holder.tv_filename.setText(item.getNamaFile());
                    holder.file_preview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = URL_File + item.getNamaFile();
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setTitle("Mengunduh");  //set title for notification in status_bar
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  //flag for if you want to show notification in status or not
                            String nameOfFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url)); //fetching name of file and type from server
                            request.setDestinationInExternalPublicDir("", nameOfFile);
                            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            downloadManager.enqueue(request);

                        }
                    });
                } else {
                    holder.item_view.setVisibility(View.GONE);
                }

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

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//                FilterResults filterResults = new FilterResults();
//
//                if(IDENTIFIER == FRAGMENT_PENGADUAN){
//                    if (charString.isEmpty())
//                        filteredPengaduanItem = listPengaduanItem;
//                    else {
//                        ArrayList<PengaduanItem> filteredList = new ArrayList<>();
//                        for (PengaduanItem row : listPengaduanItem) {
//                            if(row.getAduan().toLowerCase().contains(charString.toLowerCase())
//                                    || row.getPerihal().toLowerCase().contains(charString.toLowerCase()))
//                                filteredList.add(row);
//                        }
//                        filteredPengaduanItem = filteredList;
//                    }
//                    filterResults.values = filteredPengaduanItem;
//                } else {
//                    if (charString.isEmpty())
//                        filteredPerdataItem = listPerdataitem;
//                    else {
//                        ArrayList<PerdataItem> filteredList = new ArrayList<>();
//                        for (PerdataItem row : listPerdataitem) {
//                            if(row.getCara().toLowerCase().contains(charString.toLowerCase())
//                                    || row.getTujuan().toLowerCase().contains(charString.toLowerCase())
//                                    || row.getPermintaan().toLowerCase().contains(charString.toLowerCase()))
//                                filteredList.add(row);
//                        }
//                        filteredPerdataItem = filteredList;
//                    }
//                    filterResults.values = filteredPerdataItem;
//                }
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                if(IDENTIFIER == FRAGMENT_PENGADUAN)
//                    filteredPengaduanItem = (ArrayList<PengaduanItem>) filterResults.values;
//                else
//                    filteredPerdataItem = (ArrayList<PerdataItem>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

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
