package dev.vedroiders.huaweisupport;

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

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<NewsItem> newslist;
    private NewsListView newsListView;
    private Consumer consumer;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                new MailSender(Main2Activity.this, consumer).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setNavigationView();

        newsListView = (NewsListView) findViewById(R.id.news_listview);

    }

    private void setNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.model))
                .setText(String.format("Model: %s", consumer.getModel()));

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name))
                .setText(consumer.getName());

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.email))
                .setText(consumer.getEmail());
    }

    Consumer getConsumer() {
        return consumer;
    }

    private void setNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                newslist = DataLoader.loadNews();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsListView.setNews(newslist);
                    }
                });

            }
        }).start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        setNews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation webView item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
