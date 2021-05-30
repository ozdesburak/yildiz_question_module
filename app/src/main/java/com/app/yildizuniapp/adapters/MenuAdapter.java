package com.app.yildizuniapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yildizuniapp.R;
import com.app.yildizuniapp.models.MenuModels;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuModels> menuModels;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    public MenuAdapter(Context context, List<MenuModels> menuModels) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.menuModels = menuModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.menuitem.setText(menuModels.get(position).getMenuitems());
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView menuitem;

        ViewHolder(View itemView) {
            super(itemView);
            menuitem = itemView.findViewById(R.id.txtmenuitem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            int position = getAdapterPosition();
            //İtemlara tıklanırsa
//            latitude = stations.get(position).getLocation().getLatitude();
//            Utils.longitude = stations.get(position).getLocation().getLongitude();
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

