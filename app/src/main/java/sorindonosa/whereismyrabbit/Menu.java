package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Menu extends Activity {

    // public static MediaPlayer songFundal;
    public static SharedPreferences sharedPref;
    public TextView txtHighScore;
    public TextView txtStars;
    Calendar cal = Calendar.getInstance();
    private long millis = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button buttonPlay = findViewById(R.id.btnPlay);
        Button buttonSettings = findViewById(R.id.btnSettings);
        Button buttonFarm = findViewById(R.id.btnFarm);
        Button buttonBuy = findViewById(R.id.btnBuyStars);
        RelativeLayout rLayout = findViewById(R.id.layoutMenu);
        txtHighScore = findViewById(R.id.txtHighScore);
        txtStars = findViewById(R.id.txtStars);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/cesar_font.otf");

        int culoareFundal = Color.parseColor("#3fb0ac");
        int culoareRoz = Color.parseColor("#ffd5e1");

        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 20);
        cal.set(Calendar.SECOND, 10);

        Intent intentNot = new Intent(Menu.this, NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Menu.this, 100, intentNot,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        assert alarm != null;
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        long hsLong = sharedPref.getLong("highScore", 0L);
        long srLong = sharedPref.getLong("superRabbits", 0L);
        long starsLong = sharedPref.getLong("stars", 50L);
        // public static TextView txtSuperrabbs;
        long firstDateMillis = sharedPref.getLong("firstDate", 0L);
        if (firstDateMillis == 0L) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("firstDate", millis);
            editor.apply();
        }

        txtHighScore.setTextColor(culoareRoz);
        txtHighScore.setTypeface(typeFace);
        txtHighScore.setText("" + hsLong);
        txtStars.setTextColor(culoareRoz);
        txtStars.setTypeface(typeFace);
        if (starsLong == 1) {
            txtStars.setText(starsLong + ""/* + " STAR" */);
        } else {
            txtStars.setText(starsLong + "" /* + " STARS" */);
        }

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        rLayout.setBackgroundColor(culoareFundal);

        buttonPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Play.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });


        buttonSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Settings.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });


        buttonFarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Feed.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });


        buttonBuy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Buy.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("QUIT GAME?").setCancelable(false).setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Menu.this.finish();
                    }
                }).create().show();
    }


}