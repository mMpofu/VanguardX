package com.example.vanguardx.utilities;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

public class AppUtils {
    public static void disableApp(Context context, String packageName, String activityName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(packageName, activityName);

            // Check if the component is enabled before disabling
            if (packageManager.getComponentEnabledSetting(componentName)
                    != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {

                // Disable the component
                packageManager.setComponentEnabledSetting(
                        componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it)
        }
    }


    public static void enableApp(Context context, String packageName, String activityName) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(packageName, activityName);

        packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }
}
