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

public class BarryDetailActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //public static final String BERRY_DETAIL_NAME = "";
    private String detail_name = null;
    private BarryDetailViewModel barryDetailViewModel;
    private BerryDetail berryDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barry_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // undisplayed the default title
        TextView tile = findViewById(R.id.toolbar_title);
        tile.setText("Berry");

        Intent intent = getIntent();

        if (intent != null) {
            this.detail_name = intent.getStringExtra("BERRY_DETAIL_NAME");
            TextView berryName = findViewById(R.id.barry_name);
            berryName.setText(detail_name);
        }

        TextView firmness_type = findViewById(R.id.barry_firmness);
        TextView type = findViewById(R.id.barry_type);
        TextView berry_power = findViewById(R.id.barry_power_num);
        TextView berry_growth_time = findViewById(R.id.barry_growth_time);
        TextView berry_max_harvest = findViewById(R.id.barry_max_harvest);
        TextView berry_size = findViewById(R.id.barry_size);
        TextView berry_smoothness = findViewById(R.id.barry_smooth);
        TextView berry_soil_dryness = findViewById(R.id.barry_soil_dryness);


        this.barryDetailViewModel = new ViewModelProvider(this)
                .get(com.example.android.PokeWiki.BarryDetailViewModel.class);

        Log.d(TAG, "onCreate: before observe");
        this.barryDetailViewModel.getBerryDetailLiveData().observe(
                this,
                new Observer<BerryDetail>() {
                    @Override
                    public void onChanged(BerryDetail berryDetail ) {
                        if(berryDetail!= null) {
                            Log.d(TAG, "berryDetail: " + berryDetail.getNatural_gift_power());
                            firmness_type.setText(berryDetail.getFirmness_name());
                            type.setText(berryDetail.getNatural_gift_type_name());
                            berry_power.setText(String.valueOf(berryDetail.getNatural_gift_power()));
                            berry_growth_time.setText(String.valueOf(berryDetail.getGrowth_time())+" s");
                            berry_max_harvest.setText(String.valueOf(berryDetail.getMax_harvest()));
                            berry_size.setText(String.valueOf(berryDetail.getSize())+"cm");
                            berry_smoothness.setText(String.valueOf(berryDetail.getSmoothness()));
                            berry_soil_dryness.setText(String.valueOf(berryDetail.getSoil_dryness()));

                        }
                    }
                }
        );
        barryDetailViewModel.loadDetailResults(detail_name);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_berry_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareBerry();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareBerry(){
        String text = "Welcome to PokeWiki, " + "here is berry, "+ "Name: " + detail_name+ " ,thank you";
        String shareText = text;
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareText);
        sendIntent.setType("text/plain");

        Intent chooserIntent = Intent.createChooser(sendIntent, null);
        startActivity(chooserIntent);
    }
}
