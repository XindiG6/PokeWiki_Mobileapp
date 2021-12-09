package com.example.android.PokeWiki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Barry extends AppCompatActivity implements BarryAdapter.OnBarryItemClickListener,SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BarryAdapter barryAdapter;
    private RecyclerView barryListRV;
    private BarryViewModel barryViewModel;
    private SharedPreferences sharedPreferences; // create shared preference object
    //private EditText search_target;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barry);

        Log.d(TAG, "i am in berry");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Berry");
        //this.search_target = findViewById(R.id.berry_search_text);

        this.barryListRV = findViewById(R.id.rv_barry_list);
        this.barryListRV.setLayoutManager(new LinearLayoutManager(this));
        this.barryListRV.setHasFixedSize(true);

        this.barryAdapter = new BarryAdapter(this);
        this.barryListRV.setAdapter(this.barryAdapter);

        this.barryViewModel = new ViewModelProvider(this)
                .get(BarryViewModel.class);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this); // prevent memory leak

        this.barryViewModel.getBarryData().observe(
                this,
                new Observer<BarryData>() {
                    @Override
                    public void onChanged(BarryData barryData) {
                        if(barryData != null){
                            Log.d(TAG, "data: "+barryData.getBarryNames().get(0).getName());
                            Log.d(TAG, "Url: "+ barryData.getBarryNames().get(0).getUrl());
                        }
                        barryAdapter.updateForecastData(barryData);
                    }
                }
        );

//                search_target.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        filters(s.toString());
//                    }
//                });
        barryViewModel.loadForecast(60);
    }

    public void filters(String target){
        ArrayList<BarryData> filteredList = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);// unregister here to prevent memory leak
        super.onDestroy();
    }

    @Override
    public void onBarryItemClickListener(BarryNames barryNames) {
        Log.d(TAG, "You click me hhh");
        Log.d(TAG, "click my name " + barryNames.getName());
        Intent intent = new Intent(this, BarryDetailActivity.class);
        intent.putExtra("BERRY_DETAIL_NAME", barryNames.getName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                barryAdapter.getFilter().filter(newText);
                return false;
            }
        });

//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "shared preference changed:" + key + "value: " + sharedPreferences.getString(key, ""));
        String limit_num = sharedPreferences.getString(
                getString(R.string.pref_limit), "20"
        );
        Log.d(TAG, "onSharedPreferenceChanged: limit_num: " + limit_num);
        int LimitNum = 0;
        try {
            LimitNum = Integer.parseInt(limit_num.trim());
        }catch (NumberFormatException nfe){
            System.out.println("Could not parse " + nfe);
        }
        Log.d(TAG, "After change onSharedPreferenceChanged: limit_num: " + LimitNum);
        this.barryViewModel.loadForecast(LimitNum);
 }
}
