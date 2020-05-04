package com.example.test_bottomnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.test_bottomnav.Fragment.AccountFragment;
import com.example.test_bottomnav.Fragment.HistoryFragment;
import com.example.test_bottomnav.Fragment.HomeFragment;
import com.example.test_bottomnav.Fragment.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNav = findViewById(R.id.btm_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.action_home:
//                          mFrameContainer.setVisibility(View.VISIBLE);
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_history:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.action_notif:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.action_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();

                    return true;
                }
            };

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}
