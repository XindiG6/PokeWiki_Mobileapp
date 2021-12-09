package com.example.android.PokeWiki;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class PokemonsDetailActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String detail_name = null;
    private PokemonsDetailViewModel pokemonsDetailViewModel;
    private PokemonsDetail pokemonsDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Pokemon");
        TextView name = findViewById(R.id.pokemon_name);
        Intent intent = getIntent();

        if (intent != null) {
            this.detail_name = intent.getStringExtra("POKEMONS_DETAIL_NAME");
           // TextView moveName = findViewById(R.id.moves_name);
//            moveName.setText(detail_name);
            name.setText(detail_name);
        }
        TextView baseExperience = findViewById(R.id.pokemon_species_name);
        TextView height = findViewById(R.id.pokemon_height);
        TextView is_default = findViewById(R.id.pokemon_default);
        TextView order = findViewById(R.id.pokemon_order);
        TextView weight = findViewById(R.id.pokemon_weight);
        TextView species = findViewById(R.id.pokemon_species_name);


        this.pokemonsDetailViewModel = new ViewModelProvider(this)
                .get(com.example.android.PokeWiki.PokemonsDetailViewModel.class);

        pokemonsDetailViewModel.loadDetailResults(detail_name);
        this.pokemonsDetailViewModel.getPokemonsDetailLiveData().observe(
                this,
                new Observer<PokemonsDetail>() {
                    @Override
                    public void onChanged(PokemonsDetail pokemonsDetail) {
                        if(pokemonsDetail != null){
                            baseExperience.setText(String.valueOf(pokemonsDetail.getBase_experience()));
                            height.setText(String.valueOf(pokemonsDetail.getHeight()) + " CM");
                            is_default.setText(String.valueOf(pokemonsDetail.getIs_default()));
                            order.setText(String.valueOf(pokemonsDetail.getOrder()));
                            weight.setText(String.valueOf(pokemonsDetail.getWeight())+" LB");
                            species.setText(pokemonsDetail.getSpecies_name());
                        }
                    }
                }
        );
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_move_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareMove();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareMove(){
        String text = "Welcome to PokeWiki, "+ "here is pokemon, "+ "Name: " + detail_name+ " ,thank you";
        String shareText = text;
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareText);
        sendIntent.setType("text/plain");

        Intent chooserIntent = Intent.createChooser(sendIntent, null);
        startActivity(chooserIntent);
    }
}
