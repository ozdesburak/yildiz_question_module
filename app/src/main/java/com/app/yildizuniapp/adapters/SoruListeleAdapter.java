package com.app.yildizuniapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.models.ListelemeModels;
import com.app.yildizuniapp.utils.Utils;

import java.util.List;

public class SoruListeleAdapter extends RecyclerView.Adapter<SoruListeleAdapter.ViewHolder> {

    private List<ListelemeModels> listelemeModels;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    public SoruListeleAdapter(Context context, List<ListelemeModels> listelemeModels) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listelemeModels = listelemeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_sorulistele, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSoru.setText(listelemeModels.get(position).getSoru());
        holder.txtCevap1.setText(listelemeModels.get(position).getBirinci());
        holder.txtCevap2.setText(listelemeModels.get(position).getIkinci());
        holder.txtCevap3.setText(listelemeModels.get(position).getUcuncu());
        holder.txtCevap4.setText(listelemeModels.get(position).getDorduncu());

    }

    @Override
    public int getItemCount() {
        return listelemeModels.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtSoru,txtCevap1,txtCevap2,txtCevap3,txtCevap4,txtSil,txtGuncelle;

        ViewHolder(View itemView) {
            super(itemView);
            txtSoru = itemView.findViewById(R.id.txtSoru);
            txtCevap1 = itemView.findViewById(R.id.txtCevap1);
            txtCevap2 = itemView.findViewById(R.id.txtCevap2);
            txtCevap3 = itemView.findViewById(R.id.txtCevap3);
            txtCevap4 = itemView.findViewById(R.id.txtCevap4);
            txtSil = itemView.findViewById(R.id.txtSil);
            txtSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(txtSil.getContext());
                    dialog.setTitle("Dikkat")
                            .setMessage("Silmek İstediğinizden Emin Misiniz?")
                            .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    Log.e("KONUM", String.valueOf(getAdapterPosition()));
                                    Utils.sinavsorulari.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                }
                            });
                    dialog.show();
                }
            });
            txtGuncelle = itemView.findViewById(R.id.txtGuncelle);
            txtGuncelle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //GÜNCELLEME SAYFASI AÇILACAK
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            int position = getAdapterPosition();
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

