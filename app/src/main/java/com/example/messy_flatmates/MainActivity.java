package com.example.messy_flatmates;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.messy_flatmates.Fragments.All_tasks_fragment;
import com.example.messy_flatmates.Fragments.Calendar_fragment;
import com.example.messy_flatmates.Fragments.Create_task_fragment;
import com.example.messy_flatmates.Fragments.Flat_home_fragment;
import com.example.messy_flatmates.Fragments.Group_fragment;
import com.example.messy_flatmates.Fragments.Login_Home_page;
import com.example.messy_flatmates.Fragments.My_profile;
import com.example.messy_flatmates.Fragments.Settings_fragment;
import com.example.messy_flatmates.Fragments.dashboard_fragment;
import com.example.messy_flatmates.db.InternalDBHandler;
import com.google.android.material.navigation.NavigationView;

/**
 * @version 1.0
 * the main activity builds the components of the app
 * @todo add code in to hide the nav button    getSupportActionBar().hide();
 * @author Jordan Gardiner
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle bundle = new Bundle();
    public FragmentManager fragment_manager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Extra_Code wrapper = new Extra_Code();
        final InternalDBHandler internalDBHandler = new InternalDBHandler(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    //Possibly use as an alert for new messages on the message board
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        //toggle button
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                wrapper.hideKeyboardFrom(getBaseContext(), toolbar);

                String token = internalDBHandler.getToken();

                final NavigationView navigationView = findViewById(R.id.nav_view);
                //choose what to display depending on user login status
                if(token == null){
                    //user is not logged in
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_calendar).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_create_task).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_flat).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_group).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_my_tasks).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);

                } else {
                    //user logged in
                    navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                    navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_calendar).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_create_task).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_flat).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_group).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_my_tasks).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
                }



            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        InternalDBHandler db = new InternalDBHandler((this.getBaseContext()));

        int id = item.getItemId();


        String token = db.getToken();
        System.out.println("setting up nav token");
        System.out.println(token);

        if(token == null){
            if (id == R.id.nav_login) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new Login_Home_page()).commit();
            } else if (id == R.id.nav_settings) {
                Settings_fragment settings_fragment = new Settings_fragment();
                settings_fragment.setArguments(bundle);
                fragment_manager.beginTransaction().replace(R.id.content_frame,settings_fragment).commit();
            }

        } else {
            if (id == R.id.nav_home) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new dashboard_fragment()).commit();
            } else if (id == R.id.nav_calendar) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new Calendar_fragment()).commit();
            } else if (id == R.id.nav_create_task) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new Create_task_fragment()).commit();
            } else if (id == R.id.nav_my_tasks) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new All_tasks_fragment()).commit();
            } else if (id == R.id.nav_group) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new Group_fragment()).commit();
            } else if (id == R.id.nav_flat) {
                fragment_manager.beginTransaction().replace(R.id.content_frame, new Flat_home_fragment()).commit();
            } else if(id == R.id.nav_profile){
                fragment_manager.beginTransaction().replace(R.id.content_frame, new My_profile()).commit();
            } else if (id == R.id.nav_settings) {
                fragment_manager.beginTransaction().replace(R.id.content_frame,new Settings_fragment()).commit();
            }
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
