
package com.example.android.PokeWiki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Moves extends AppCompatActivity implements MovesAdapter.OnMovesItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovesAdapter movesAdapter;
    private RecyclerView MovesListRV;
    private MovesViewModel movesViewModel;
    private EditText search_target;
    private SharedPreferences sharedPreferences; // create shared preference object
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Moves");

        this.MovesListRV = findViewById(R.id.rv_moves_list);
        this.MovesListRV.setLayoutManager(new LinearLayoutManager(this));
        this.MovesListRV.setHasFixedSize(true);

        this.movesAdapter = new MovesAdapter(this);
        this.MovesListRV.setAdapter(this.movesAdapter);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this); // prevent memory leak

        this.movesViewModel = new ViewModelProvider(this)
                .get(MovesViewModel.class);

        movesViewModel.loadForecast(843);
        this.movesViewModel.getMovesData().observe(
                this,
                new Observer<MovesData>() {
                    @Override
                    public void onChanged(MovesData movesData) {
                        if(movesData != null){
                            Log.d(TAG, "data: "+movesData.getMovesNames().get(0).getName());
                            Log.d(TAG, "Url: "+ movesData.getMovesNames().get(0).getUrl());
                        }
                        movesAdapter.updateForecastData(movesData);
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);// unregister here to prevent memory leak
        super.onDestroy();
    }

    @Override
    public void onMovesItemClickListener(MovesNames movesNames) {
        Log.d(TAG, "You click me hhh");
        Log.d(TAG, "click my name " + movesNames.getName());
        Intent intent = new Intent(this, movesDetailActivity.class);
        intent.putExtra("MOVES_DETAIL_NAME", movesNames.getName());
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
                movesAdapter.getFilter().filter(newText);
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
        this.movesViewModel.loadForecast(LimitNum);
    }
}
