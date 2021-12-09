package com.example.android.PokeWiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.android.PokeWiki.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title

        this.drawerLayout = findViewById(R.id.drawer_layout);
        music = MediaPlayer.create(this, R.raw.sound);

        // Define ActionBar object
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);

        Button pokemonsButton = findViewById(R.id.main_pokemon);
        pokemonsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pokemons.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }
        });

        Button MovesButton = findViewById(R.id.main_moves);
        MovesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Moves.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }
        });

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Button BarryButton = findViewById(R.id.main_barry);
        BarryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Barry.class);
                startActivity(intent);
                //MainActivity.this.finish();
            }
        });

    }

    public void MusicPlay(View v){
        music.start();
    }

    public void MusicPause(View v){
        music.pause();
    }

    public void MusicStop(View v){
        music.stop();
        music = MediaPlayer.create(this, R.raw.sound);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.drawerLayout.closeDrawers();
        switch(item.getItemId()){
            case R.id.pokemon_page:
                Intent pokemonIntent=new Intent(this, Pokemons.class);
                startActivity(pokemonIntent);
                return true;
            case R.id.moves_page:
                Intent movesIntent=new Intent(this, Moves.class);
                startActivity(movesIntent);
                return true;
            case R.id.berry_page:
                Intent berryIntent=new Intent(this, Barry.class);
                startActivity(berryIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}