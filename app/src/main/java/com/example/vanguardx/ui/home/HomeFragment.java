package com.example.vanguardx.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.vanguardx.MyAdminReceiver;
import com.example.vanguardx.R;
import com.example.vanguardx.databinding.FragmentHomeBinding;
import com.example.vanguardx.model.db.entity.AppUsageEntity;
import com.dewan.usagestatshelper.UsageStatsHelper;

import java.text.DecimalFormat;

/** @noinspection ALL*/
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private static final int USAGE_STATS_PERMISSION = 12345;
    private static final int REQUEST_CODE = 1;

    private View view;
    private FragmentHomeBinding fragmentHomeBinding;
    private HomeViewModel homeViewModel;
    private AppUsageEntity appUsageEntity = new AppUsageEntity();
    private static DecimalFormat decimalFormat;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize decimal format
        decimalFormat = new DecimalFormat("#.##");

        // Inflate the layout using DataBinding
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = fragmentHomeBinding.getRoot();

        // Initialize ViewModel
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        // Check and request permissions
        checkUsageStatsPermission();
        checkDeviceAdmin();

        return view;
    }

    // Observe and update UI based on usage statistics
    private void getUsageStats() {
        // ... (existing code for observing usage stats)
    }

    // Check and request usage stats permission
    private void checkUsageStatsPermission() {
        boolean isPermissionGranted = UsageStatsHelper.getAppUsageStatsPermission(getActivity());
        if (!isPermissionGranted) {
            askForUsageStatsPermission();
        } else {
            getUsageStats();
        }
    }

    // Request usage stats permission
    private void askForUsageStatsPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Permission");
        builder.setMessage("Please Provide Usage Stats App Permission");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, USAGE_STATS_PERMISSION);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                // Retry checking usage stats permission
                checkUsageStatsPermission();
            }
        });
        builder.show();
    }

    // Check and request device admin permission
    private void checkDeviceAdmin() {
        DevicePolicyManager dpm = (DevicePolicyManager) requireActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName deviceAdmin = new ComponentName(requireContext(), MyAdminReceiver.class);

        if (!dpm.isAdminActive(deviceAdmin)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    // Handle the result of various permissions requests
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // Handle the result of the device administration activation
            if (resultCode == Activity.RESULT_OK) {
                // Device administration activated successfully
                Toast.makeText(getActivity(), "Device admin activated", Toast.LENGTH_SHORT).show();
                // You can perform additional actions if needed
            } else {
                // Device administration activation failed or was canceled
                Toast.makeText(getActivity(), "Device admin activation failed", Toast.LENGTH_SHORT).show();
                // Handle accordingly
            }
        } else if (requestCode == USAGE_STATS_PERMISSION) {
            // Check the result of the usage stats permission request
            if (UsageStatsHelper.getAppUsageStatsPermission(getActivity())) {
                // Permission granted, fetch usage stats
                getUsageStats();
            } else {
                Toast.makeText(getActivity(), "Usage stats permission denied", Toast.LENGTH_SHORT).show();
                // Retry checking usage stats permission
                checkUsageStatsPermission();
            }
        }
    }
}
