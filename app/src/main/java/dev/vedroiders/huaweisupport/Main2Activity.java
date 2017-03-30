package dev.vedroiders.huaweisupport;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import dev.vedroiders.huaweisupport.fragments.AccountFragment;
import dev.vedroiders.huaweisupport.fragments.NewsFragment;
import dev.vedroiders.huaweisupport.fragments.SettingsFragment;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    long backTime = 0;
    Fragment currentFragment;
    private ArrayList<NewsItem> newslist;
    private NewsListView newsListView;
    private Consumer consumer;
    private NavigationView navigationView;
    private NewsFragment newsFragment;
    private AccountFragment accountFragment;
    private SettingsFragment settingsFragment;
    private Toolbar toolbar;

    private void setNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.model))
                .setText(String.format("Model: %s", Build.MODEL));

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name))
                .setText(consumer.getName());

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.email))
                .setText(consumer.getEmail());
    }

    Consumer getConsumer() {
        return consumer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.huawei);

//        android.support.v7.app.ActionBar menu = getSupportActionBar();
//        menu.setDisplayShowHomeEnabled(true);
//        menu.setLogo(R.drawable.);
//        menu.setDisplayUseLogoEnabled(true);

        try {
            consumer = (Consumer) getIntent().getSerializableExtra("consumer");
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка загрузки профиля! :(", Toast.LENGTH_SHORT).show();
            finish();
        }

        findViewById(R.id.fab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MailSender(Main2Activity.this, consumer.getEmail()).show();
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setNavigationView();

        initFragments();
//        newsListView = (NewsListView) findViewById(R.id.news_listview);

    }

    private void openNewsFragment() {
        FragmentTransaction tr = getFragmentManager().beginTransaction();

        tr.add(R.layout.content_main2, newsFragment);
        tr.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        openNewsFragment();
    }

    void initFragments() {
        newsFragment = new NewsFragment(this);
        accountFragment = new AccountFragment(this);
        settingsFragment = new SettingsFragment(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                if (backTime == 0) {
                    backTime = System.currentTimeMillis();
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
                else {
//                if()
                }
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation webView item clicks here.
        int id = item.getItemId();

        android.app.FragmentManager manager = getFragmentManager();

        android.app.FragmentTransaction transaction =
                manager.beginTransaction();
        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }

        switch (id) {

            case R.id.menu_news: {
                if (manager.findFragmentById(newsFragment.getId()) == null) {
                    transaction.add(R.id.content_main2, newsFragment);
                    currentFragment = newsFragment;

                }

//                getActionBar().setTitle("News");

                break;
            }

            case R.id.menu_account: {
                if (manager.findFragmentById(accountFragment.getId()) == null) {

                    transaction.add(R.id.content_main2, accountFragment);
                    currentFragment = accountFragment;
                }

//                getActionBar().setTitle("Account");

                break;
            }
            case R.id.menu_settings: {
                if (manager.findFragmentById(settingsFragment.getId()) == null) {
                    transaction.add(R.id.content_main2, settingsFragment);
                    currentFragment = settingsFragment;
                }
//                getActionBar().setTitle("Settings");

                break;
            }

            case R.id.menu_report: {
                new MailSender(Main2Activity.this, consumer.getEmail()).show();
                break;
            }

            case R.id.menu_logout: {
                consumer = null;
                DataLoader.removeProfile();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
        }

        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
