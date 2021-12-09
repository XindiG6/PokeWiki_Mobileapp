package com.example.android.PokeWiki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class Pokemons extends AppCompatActivity implements PokemonsAdapter.OnPokemonsItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private PokemonsAdapter pokemonsAdapter;
    private RecyclerView PokemonsListRV;
    private PokemonsViewModel pokemonsViewModel;
    private EditText search_target;
    private SharedPreferences sharedPreferences; // create shared preference object

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);
        //Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Pokemons");

        this.PokemonsListRV = findViewById(R.id.rv_pokemons_list);
        this.PokemonsListRV.setLayoutManager(new LinearLayoutManager(this));
        this.PokemonsListRV.setHasFixedSize(true);

        this.pokemonsAdapter = new PokemonsAdapter(this);
        this.PokemonsListRV.setAdapter(this.pokemonsAdapter);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this); // prevent memory leak

        this.pokemonsViewModel = new ViewModelProvider(this)
                .get(PokemonsViewModel.class);

        pokemonsViewModel.loadForecast(843);
        this.pokemonsViewModel.getPokemonsData().observe(
                this,
                new Observer<PokemonsData>() {
                    @Override
                    public void onChanged(PokemonsData pokemonsData) {
                        if(pokemonsData != null){
                            Log.d(TAG, "data: "+pokemonsData.getPokemonsName().get(0).getName());
                            Log.d(TAG, "Url: "+ pokemonsData.getPokemonsName().get(0).getUrl());
                        }
                        pokemonsAdapter.updateForecastData(pokemonsData);
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
                pokemonsAdapter.getFilter().filter(newText);
                return false;
            }
        });

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
    public void onPokemonsItemClickListener(PokemonsName pokemonsName) {
        Log.d(TAG, "You click me hhh");
        Log.d(TAG, "click my name " + pokemonsName.getName());
        Intent intent = new Intent(this, PokemonsDetailActivity.class);
        intent.putExtra("POKEMONS_DETAIL_NAME", pokemonsName.getName());
        startActivity(intent);
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
        this.pokemonsViewModel.loadForecast(LimitNum);

    }

//    @Override
//    public boolean onNavigateUp() {
//        //moveTaskToBack(true);
//        finish();
//        return true;
//    }

}
