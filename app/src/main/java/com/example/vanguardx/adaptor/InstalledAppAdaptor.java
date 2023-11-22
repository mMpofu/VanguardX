package com.example.vanguardx.adaptor;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vanguardx.R;
import com.example.vanguardx.databinding.RvAppInstalledViewBinding;
import com.example.vanguardx.model.db.entity.InstalledAppEntity;
import com.example.vanguardx.utilities.AppUtils;

import java.util.ArrayList;

public class InstalledAppAdaptor extends RecyclerView.Adapter<InstalledAppAdaptor.ViewHolder> {
    private ArrayList<InstalledAppEntity> appList = new ArrayList<>();
    private Context context; // Add this field

    public InstalledAppAdaptor(Context context) {
        this.context = context;
    }

    public void setAppList(ArrayList<InstalledAppEntity> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvAppInstalledViewBinding appInstalledViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_app_installed_view, parent, false);

        return new ViewHolder(appInstalledViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final InstalledAppEntity installedAppEntity = appList.get(position);

        if (installedAppEntity != null) {
            holder.rvAppInstalledViewBinding.setInstalledAppProperty(installedAppEntity);

            holder.itemView.setOnClickListener(v -> {
                String packageName = installedAppEntity.getPackageName();
                String activityName = "com.example.vanguardx.SettingsActivity"; // Replace with actual activity name

                if (isAppEnabled(packageName, activityName)) {
                    AppUtils.disableApp(context, packageName, activityName);
                } else {
                    AppUtils.enableApp(context, packageName, activityName);
                }

                // Update the UI
                notifyItemChanged(holder.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvAppInstalledViewBinding rvAppInstalledViewBinding;

        public ViewHolder(@NonNull RvAppInstalledViewBinding rvAppInstalledViewBinding) {
            super(rvAppInstalledViewBinding.getRoot());
            this.rvAppInstalledViewBinding = rvAppInstalledViewBinding;
        }
    }

    private boolean isAppEnabled(String packageName, String activityName) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(packageName, activityName);
        int enabledSetting = packageManager.getComponentEnabledSetting(componentName);

        return enabledSetting != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }
}
