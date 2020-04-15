package com.example.rkbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment selectedFragment = null;
    FragmentManager fragmentManager,currentFragment;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref1 = getSharedPreferences("login", MODE_PRIVATE);
        String m=pref1.getString("no",null);
        new firebaseDB(getApplicationContext()).ReadAllDataOfUser(m);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        fragmentManager=getSupportFragmentManager();
        if (savedInstanceState == null) {
            selectedFragment=new homeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Offer) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new offerFragment()).commit();
        }

        return super.onOptionsItemSelected(item);
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        selectedFragment=new homeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.myAccount) {
            selectedFragment=new MyAccountFragment();
        } else if (id == R.id.myPassbook) {
            selectedFragment=new bankFragment();
        } else if (id == R.id.accountSetting) {
            selectedFragment=new AccountSettingFragment();
        } else if (id == R.id.history) {
            selectedFragment=new historyFragment();
        } else if (id == R.id.Near_Store) {
            selectedFragment=new nearshopFragment();
        } else if (id == R.id.help) {
            Toast.makeText(this,"we will communicate you soon.",Toast.LENGTH_LONG).show();
        }else if (id == R.id.about) {
            Toast.makeText(this,"this code is develop by sana",Toast.LENGTH_LONG).show();
        }else if (id == R.id.logout) {
            startActivity(new Intent( HomeActivity.this,MainActivity.class));
            SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
            editor.clear().commit();
            finish();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new homeFragment();
                            break;
                        case R.id.mf:
                           selectedFragment = new MoneyTransferFragment();
                            break;
                        case R.id.b:
                            selectedFragment = new bankFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

}
