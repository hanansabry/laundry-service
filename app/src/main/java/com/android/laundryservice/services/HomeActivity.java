package com.android.laundryservice.services;

import android.content.Intent;
import android.os.Bundle;

import com.android.laundryservice.Injection;
import com.android.laundryservice.MainActivity;
import com.android.laundryservice.R;
import com.android.laundryservice.data.services.ServicesRepository;
import com.android.laundryservice.login.LoginActivity;

import android.view.Menu;
import android.view.View;

import com.android.laundryservice.model.Service;
import com.android.laundryservice.services.serviceItems.ServiceItemsFragment;
import com.android.laundryservice.services.services_list.ServicesAdapter;
import com.android.laundryservice.services.services_list.ServicesPresenter;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    private HomeContract.Presenter presenter;
    private ServicesPresenter servicesPresenter;
    private FragmentManager fragmentManager;
    private TextView totalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new HomePresenter(this, Injection.provideAuthenticationUseCaseHandler());
        servicesPresenter = new ServicesPresenter(this, Injection.provideServicesRepository());

        totalCostTextView = findViewById(R.id.total_cost_textview);
        initializeNavigationHeaderViews();
        initializeServicesRecyclerView();
    }

    private void initializeNavigationHeaderViews() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView userNameTextView = hView.findViewById(R.id.username_textview);
        userNameTextView.setText(presenter.getUserEmail());
    }

    private void initializeServicesRecyclerView() {
        RecyclerView serviceRecyclerView = findViewById(R.id.service_recycler_view);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        final ServicesAdapter servicesAdapter = new ServicesAdapter(servicesPresenter);
        serviceRecyclerView.setAdapter(servicesAdapter);
        servicesPresenter.retrieveServices(new ServicesRepository.RetrieveServicesCallback() {
            @Override
            public void onServiceRetrievedSuccessfully(ArrayList<Service> services) {
                servicesAdapter.bindServices(services);
                addServiceItemsFragment(services.get(0));
            }

            @Override
            public void onServiceRetrievedFailed(String errmsg) {
                Toast.makeText(HomeActivity.this, errmsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addServiceItemsFragment(Service service) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentByTag(service.getId());
//        if (currentFragment != null) {
//            ft.show(currentFragment);
//        } else {
            ft.replace(R.id.container, ServiceItemsFragment.newInstance(service), service.getId());
//        }

        ft.commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            presenter.logout();
            goToLoginScreen();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToLoginScreen() {
        Intent homeIntent = new Intent(this, LoginActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    @Override
    public void onServiceClicked(Service service) {
        Toast.makeText(this, service.getName(), Toast.LENGTH_SHORT).show();
        addServiceItemsFragment(service);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    public void setServiceItemsTotalCost(double serviceItemsTotalCost) {
        totalCostTextView.setText(String.format(getString(R.string.total_cost_value), serviceItemsTotalCost));
    }
}
