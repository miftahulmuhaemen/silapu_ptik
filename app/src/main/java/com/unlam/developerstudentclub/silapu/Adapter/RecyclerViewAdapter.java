package com.unlam.developerstudentclub.silapu.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Fragment.DialogDetail;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.Gravity.START;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ADMIN_REPLY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ADMIN_REPLY__;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_DETIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PARCABLE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PENGADUAN_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PERDATA_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_BMP;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_DOC;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_DOCX;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_JPG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_PDF;
import static com.unlam.developerstudentclub.silapu.Utils.Util.EXT_PNG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PERMISSION_DOWNLOAD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.URL_File;

public class RecyclerViewAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecyclerViewAdapter.RecylerViewAdapterHolder> {

        private Context context;
        private UserPreference userPreference;
        private Activity activity;

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
            activity = (Activity) context;
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
        public void onBindViewHolder(final RecylerViewAdapterHolder holder, int position) {

            if(IDENTIFIER == FRAGMENT_PENGADUAN) {
                final PengaduanItem item = getFilteredPengaduanItem().get(position);

                if(item.getKet().equals(ADMIN_REPLY)){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = START;
                    holder.view.setLayoutParams(params);
                    params.topMargin = 20;
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

                    /*https://www.flaticon.com/packs/file-formats-icons*/
                    switch (item.getExtFile().toLowerCase()){
                        case EXT_DOC :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.doc_file_format_symbol))
                                    .into(holder.file_preview);
                            break;
                        case EXT_DOCX :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.docx_file_format))
                                    .into(holder.file_preview);
                            break;
                        case EXT_PDF :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.pdf_file_format_symbol))
                                    .into(holder.file_preview);
                            break;
                        case EXT_JPG :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.jpg_image_file_format))
                                    .into(holder.file_preview);
                            break;
                        case EXT_PNG :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.png_file_extension_interface_symbol))
                                    .into(holder.file_preview);
                            break;
                        case EXT_BMP :
                            Glide.with(context).load(context.getResources().getDrawable(R.drawable.bmp_file_format_symbol))
                                    .into(holder.file_preview);
                            break;
                    }
                    holder.tv_filetype.setText(item.getExtFile());
                    holder.tv_filename.setText(item.getNamaFile());
                    holder.file_preview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Unduh ?")
                                    .setPositiveButton("UNDUH", new DialogInterface.OnClickListener() {

                                        @AfterPermissionGranted(RESULT_CODE_PERMISSION_DOWNLOAD)
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                            if (EasyPermissions.hasPermissions(context, perms)) {
                                                String url = URL_File + item.getNamaFile();
                                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                                request.setTitle("Mengunduh");  //set title for notification in status_bar
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);  //flag for if you want to show notification in status or not
                                                String nameOfFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url)); //fetching name of file and type from server
                                                request.setDestinationInExternalPublicDir("", nameOfFile);
                                                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                                downloadManager.enqueue(request);
                                            } else {
                                                // Do not have permissions, request them now
                                                EasyPermissions.requestPermissions(activity, context.getString(R.string.app_name), RESULT_CODE_PERMISSION_DOWNLOAD, perms);
                                            }
                                        }
                                    })
                                    .setNegativeButton("TIDAK", null);
                                    AlertDialog alertDialog  =  builder.create();
                                    alertDialog.show();
                        }
                    });
                } else {
                    holder.item_view.setVisibility(View.GONE);
                }

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogDetail dialogMainActivity = new DialogDetail();
                        Bundle bundle = new Bundle();

                        bundle.putString(DIALOG_DETIL,DIALOG_PENGADUAN_DETAIL);
                        bundle.putParcelable(DIALOG_PARCABLE, item);
                        dialogMainActivity.setArguments(bundle);

                        dialogMainActivity.show(activity.getFragmentManager(),DialogDetail.class.getSimpleName());
                    }
                });

            } else {
                final PerdataItem item = getFilteredPerdataItem().get(position);

                holder.tv_permintaan.setText(item.getPermintaan());
                holder.tv_tujuan.setText(item.getTujuan());

                if(item.getKet().equals(ADMIN_REPLY__)){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = START;
                    holder.view.setLayoutParams(params);
                    params.topMargin = 20;
                    holder.tv_log.setLayoutParams(params);
                    holder.tv_sender.setText(ADMIN_REPLY);
                    holder.tv_cara.setVisibility(View.GONE);
                } else {
                    holder.tv_cara.setText(item.getCara());
                    holder.tv_sender.setText(userPreference.getNama());
                }

                holder.tv_log.setText(item.getLog());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogDetail dialogMainActivity = new DialogDetail();
                        Bundle bundle = new Bundle();

                        bundle.putString(DIALOG_DETIL,DIALOG_PERDATA_DETAIL);
                        bundle.putParcelable(DIALOG_PARCABLE, item);
                        dialogMainActivity.setArguments(bundle);

                        dialogMainActivity.show(activity.getFragmentManager(),DialogDetail.class.getSimpleName());
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
