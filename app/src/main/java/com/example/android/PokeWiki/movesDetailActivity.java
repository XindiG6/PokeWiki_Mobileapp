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

public class movesDetailActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String detail_name = null;
    private MovesDetailViewModel movesDetailViewModel;
    private MovesDetail movesDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Move");
        TextView name = findViewById(R.id.moves_name);
        Intent intent = getIntent();

        if (intent != null) {
            this.detail_name = intent.getStringExtra("MOVES_DETAIL_NAME");
            TextView moveName = findViewById(R.id.moves_name);
            moveName.setText(detail_name);
            //name.setText(detail_name);
        }

        TextView accuracy = findViewById(R.id.moves_accuracy);
        TextView power = findViewById(R.id.moves_power);
        TextView pp = findViewById(R.id.moves_pp);
        TextView priority = findViewById(R.id.moves_priority);
        TextView contest_type = findViewById(R.id.moves_contest_type);
        TextView damage_class = findViewById(R.id.moves_damage_class);
        TextView generation = findViewById(R.id.moves_generation);
        TextView target = findViewById(R.id.moves_target);
        TextView type = findViewById(R.id.moves_type);


        this.movesDetailViewModel = new ViewModelProvider(this)
                .get(com.example.android.PokeWiki.MovesDetailViewModel.class);

        Log.d(TAG, "onCreate: before observe");

        this.movesDetailViewModel.getMovesDetailLiveData().observe(
                this,
                new Observer<MovesDetail>() {
                    @Override
                    public void onChanged(MovesDetail movesDetail ) {
                        if(movesDetail!= null) {
//                            Log.d(TAG, "moves accuracy: "+String.valueOf(movesDetail.getAccuracy()));
//                            Log.d(TAG, "moves accuracy: "+String.valueOf(movesDetail.getPower()));
//                            Log.d(TAG, "moves accuracy: "+String.valueOf(movesDetail.getPp()));
//                            Log.d(TAG, "moves accuracy: "+String.valueOf(movesDetail.getPriority()));
//                            Log.d(TAG, "moves accuracy: "+String.valueOf(movesDetail.getContest_type()));
                            accuracy.setText(String.valueOf(movesDetail.getAccuracy()));
                            power.setText(String.valueOf(movesDetail.getPower()));
                            pp.setText(String.valueOf(movesDetail.getPp()));
                            priority.setText(String.valueOf(movesDetail.getPriority()));
                            contest_type.setText(movesDetail.getContest_type());
                            damage_class.setText(movesDetail.getDamage_class());
                            generation.setText(movesDetail.getGeneration());
                            target.setText(movesDetail.getTarget());
                            type.setText(movesDetail.getType());

                        }
                    }
                }
        );
        movesDetailViewModel.loadDetailResults(detail_name);
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
        String text = "Welcome to PokeWiki, " + "here is moves, "+ "Name: " + detail_name+ " ,thank you";
        String shareText = text;
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareText);
        sendIntent.setType("text/plain");

        Intent chooserIntent = Intent.createChooser(sendIntent, null);
        startActivity(chooserIntent);
    }
}
