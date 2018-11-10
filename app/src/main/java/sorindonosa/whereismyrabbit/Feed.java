package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Feed extends Activity {

    public static final int NR_SUPER_GRUP = 10;
    private long vegetables, starInt;
    private long vegDateMillis;
    private long currentDate;
    private int diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.feed);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        RelativeLayout rLayout = findViewById(R.id.layoutFeed);
        TextView txtVeg = findViewById(R.id.txtVeg);
        TextView txtConvert = findViewById(R.id.txtConvert);
        TextView txtDate = findViewById(R.id.txtDate);
        Button btnBuy = findViewById(R.id.btnBuyVeg);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnPlay = findViewById(R.id.btnFarmPlay);

        int culoareFundal = Color.parseColor("#3fb0ac");
        int culoareRoz = Color.parseColor("#ffd5e1");

        rLayout.setBackgroundColor(culoareFundal);
        txtVeg.setTextColor(culoareRoz);
        txtConvert.setTextColor(culoareRoz);
        txtDate.setTextColor(culoareRoz);

        currentDate = System.currentTimeMillis();
        starInt = Menu.sharedPref.getLong("stars", 50L);
        // vegetables = Menu.sharedPref.getLong("vegetables", 0L);
        vegDateMillis = Menu.sharedPref.getLong("vegDate", currentDate);
        long superRabbits = Menu.sharedPref.getLong("superRabbits", 0L);
        long superRab = getLevel(superRabbits);

        int grup = (int) superRab / NR_SUPER_GRUP;

        long diffs = vegDateMillis - currentDate;
        diff = (int) (diffs / ((grup + 1) * (1000 * 60 * 60 * 24)));
        txtConvert.setText("1 BASKET = 50 STARS");
        txtVeg.setText(superRabbits + " RABBITS");

        if (diff > 0) {
            if (diff == 1) {
                txtDate.setText("Food reach for " + diff + " day");
            } else {
                txtDate.setText("Food reach for " + diff + " days");
            }
        } else {
            txtDate.setTextColor(Color.parseColor("#990000"));
            txtDate.setText("Your rabbits are dying by hunger!");
        }


        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Feed.this, Play.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });


        btnBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (starInt < 50) {
                    Toast.makeText(Feed.this, "Not enough STARS!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(Feed.this, Buy.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                    return;
                }

                new AlertDialog.Builder(Feed.this).setTitle("").setMessage("BUY FOOD FOR 50 STARS?").setCancelable(true)
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vegetables = vegetables + 1;
                        starInt = starInt - 50;
                        currentDate = System.currentTimeMillis();
                        SharedPreferences.Editor editor = Menu.sharedPref.edit();
                        editor.putLong("vegetables", vegetables);
                        editor.putLong("stars", starInt);
                        if (diff < 0) {
                            vegDateMillis = System.currentTimeMillis();
                            editor.putLong("vegDate", vegDateMillis + 432000000);
                        } else {
                            editor.putLong("vegDate", vegDateMillis + 432000000);
                        }
                        editor.commit();
                        Intent myIntent = new Intent(Feed.this, Feed.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                    }
                }).create().show();

            }
        });

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Feed.this, Menu.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

    }

    public long getLevel(long rabbits) {
        long level = 1;
        for (int i = 0; i < 1000; i++) {
            level = level + (100 * i);
            if (level > rabbits) {
                level = i;
                break;
            }
        }
        if (level - 1 < 0) {
            return 0;
        } else {
            return level - 1;
        }
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, Menu.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);

    }

}
