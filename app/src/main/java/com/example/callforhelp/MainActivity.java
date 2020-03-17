package com.example.callforhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);


//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.home: {
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.parent);
//                break;
//            }
            case R.id.emergency: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.emergency);
                break;
            }
            case R.id.power: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.power);
                break;
            }
            case R.id.schedule: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.schedule);
                break;
            }
            case R.id.location: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.location);
                break;
            }
            case R.id.safety: {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.safety);
                break;
            }
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
