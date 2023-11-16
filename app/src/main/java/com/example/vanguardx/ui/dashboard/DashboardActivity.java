package com.example.vanguardx.ui.dashboard;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.vanguardx.R;
import com.example.vanguardx.ui.appusage.AppUsageFragment;
import com.example.vanguardx.ui.installedapp.InstalledAppFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

public class DashboardActivity extends AppCompatActivity implements InstalledAppFragment.OnFragmentInteractionListener, AppUsageFragment.OnFragmentInteractionListener {
    private static final String TAG = "DashboardActivity";
    private String deviceId;
    //private AdView mAdView;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        deviceId = sharedPreferences.getString("device_id", "");
        if (deviceId.equals("")) {
            deviceId = createUniqueID();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("device_id", deviceId);
            editor.apply();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_appusagestats,
                R.id.navigation_installedApp)
                .build();

        // Initialize the NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

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
