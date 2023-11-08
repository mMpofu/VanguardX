package com.example.vanguardx.ui.dashboard;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.vanguardx.R;
import com.example.vanguardx.ui.appusage.AppUsageFragment;
import com.example.vanguardx.ui.installedapp.InstalledAppFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import java.util.UUID;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class DashboardActivity extends AppCompatActivity implements InstalledAppFragment.OnFragmentInteractionListener, AppUsageFragment.OnFragmentInteractionListener {
    private static final String TAG = "DashboardActivity";
    private String deviceId;
    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        deviceId = sharedPreferences.getString("device_id","");
        if (deviceId.equals("")){
            deviceId = createUniqueID();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("device_id",deviceId);
            editor.apply();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_appusagestats,
                R.id.navigation_installedApp)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e(TAG, "onFragmentInteraction: " + uri );
    }

    public String createUniqueID() {

        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

    }
}