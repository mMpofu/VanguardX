package com.example.vanguardx.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vanguardx.R;
import com.example.vanguardx.databinding.RvAppInstalledViewBinding;
import com.example.vanguardx.model.db.entity.InstalledAppEntity;
import java.util.ArrayList;

public class InstalledAppAdaptor extends RecyclerView.Adapter<InstalledAppAdaptor.ViewHolder>{
    private ArrayList<InstalledAppEntity> appList = new ArrayList<>();

    public void setAppList(ArrayList<InstalledAppEntity> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvAppInstalledViewBinding appInstalledViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_app_installed_view,parent,false);

        return new ViewHolder(appInstalledViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InstalledAppEntity installedAppEntity = appList.get(position);
        if (installedAppEntity != null) {
            holder.rvAppInstalledViewBinding.setInstalledAppProperty(installedAppEntity);
        }
    }


    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RvAppInstalledViewBinding rvAppInstalledViewBinding;

        public ViewHolder(@NonNull RvAppInstalledViewBinding rvAppInstalledViewBinding) {
            super(rvAppInstalledViewBinding.getRoot());

            this.rvAppInstalledViewBinding = rvAppInstalledViewBinding;
        }
    }
}