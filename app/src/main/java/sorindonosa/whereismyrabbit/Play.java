package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class Play extends Activity {

    public static int rezultat;
    private Button buttonRabbit, buttonArici, buttonBee, buttonSnake, buttonTarantula, buttonLobster;
    private TextView txtScor, txtCeas;
    private CountDownTimer cT;
    private long millis;
    private MediaPlayer songWrongPress;
    private InterstitialAd interAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.play);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        rezultat = 0;
        buttonRabbit = findViewById(R.id.my_button);
        buttonArici = findViewById(R.id.btnArici);
        buttonBee = findViewById(R.id.btnBee);
        buttonSnake = findViewById(R.id.btnSnake);
        buttonTarantula = findViewById(R.id.btnTarantula);
        buttonLobster = findViewById(R.id.btnLobster);

        txtScor = findViewById(R.id.txtScor);
        RelativeLayout rLayout = findViewById(R.id.layoutPlay);
        txtCeas = findViewById(R.id.txtCeas);
        AdView adView = this.findViewById(R.id.adView);

        // songRightPress = MediaPlayer.create(Play.this, R.raw.away);
        songWrongPress = MediaPlayer.create(Play.this, R.raw.right_press);

        interAd = new InterstitialAd(this);
        interAd.setAdUnitId("ca-app-pub-1675138020340294/3569836365");

        interAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                txtCeas.setText("0:0");
                Intent myIntent = new Intent(Play.this, Rezultate.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                highScore(myIntent);
                startActivity(myIntent);
            }
        });
        requestNewInterstitial();

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        interAd.loadAd(adRequest);
        Drawable drawableFundal = getResources().getDrawable(R.drawable.green);
        rLayout.setBackground(drawableFundal);

        txtScor.setTextColor(Color.WHITE);
        txtCeas.setTextColor(Color.WHITE);
        timer(5000);
        randomButton(buttonRabbit);

        buttonRabbit.setBackgroundColor(Color.TRANSPARENT);
        buttonRabbit.bringToFront();
        buttonRabbit.setText("");
        buttonRabbit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rezultat++;
                txtScor.setText(String.valueOf(rezultat));
                // playSound(songRightPress);
                randomButton(buttonArici);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                timer(450);
                verificare();

            }
        });

        buttonArici.setBackgroundColor(Color.TRANSPARENT);
        buttonArici.setText("");
        buttonArici.setVisibility(View.GONE);
        buttonArici.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScor.setText(String.valueOf(rezultat));
                randomButton(buttonArici);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                vibrare();
                playSound(songWrongPress);
                timer(-500);
                verificare();
            }
        });

        buttonBee.setBackgroundColor(Color.TRANSPARENT);
        buttonBee.setText("");
        buttonBee.setVisibility(View.GONE);
        buttonBee.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScor.setText(String.valueOf(rezultat));
                randomButton(buttonBee);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                vibrare();
                playSound(songWrongPress);
                timer(-500);
                verificare();

            }
        });

        buttonSnake.setBackgroundColor(Color.TRANSPARENT);
        buttonSnake.setText("");
        buttonSnake.setVisibility(View.GONE);
        buttonSnake.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScor.setText(String.valueOf(rezultat));
                randomButton(buttonBee);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                vibrare();
                playSound(songWrongPress);
                timer(-500);
                verificare();

            }
        });

        buttonTarantula.setBackgroundColor(Color.TRANSPARENT);
        buttonTarantula.setText("");
        buttonTarantula.setVisibility(View.GONE);
        buttonTarantula.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScor.setText(String.valueOf(rezultat));
                randomButton(buttonBee);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                vibrare();
                playSound(songWrongPress);
                timer(-500);
                verificare();

            }
        });


        buttonLobster.setBackgroundColor(Color.TRANSPARENT);
        buttonLobster.setText("");
        buttonLobster.setVisibility(View.GONE);
        buttonLobster.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScor.setText(String.valueOf(rezultat));
                randomButton(buttonBee);
                randomButton(buttonBee);
                randomButton(buttonSnake);
                randomButton(buttonRabbit);
                randomButton(buttonTarantula);
                randomButton(buttonLobster);
                vibrare();
                playSound(songWrongPress);
                timer(-500);
                verificare();

            }
        });

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interAd.loadAd(adRequest);
    }

    public void verificare() {
        if (rezultat >= 5) {
            buttonArici.setVisibility(View.VISIBLE);
        }
        if (rezultat >= 10) {
            buttonBee.setVisibility(View.VISIBLE);
        }
        if (rezultat >= 20) {
            buttonSnake.setVisibility(View.VISIBLE);
        }
        if (rezultat >= 30) {
            buttonTarantula.setVisibility(View.VISIBLE);
        }
        if (rezultat >= 45) {
            buttonLobster.setVisibility(View.VISIBLE);
        }
    }

    public void vibrare() {

        if (Settings.audioManager == null || (Settings.audioManager
                .getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER) == AudioManager.VIBRATE_SETTING_ON)) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
        }
    }

    public void playSound(MediaPlayer mp) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > 17) {
            if (Settings.audioManager == null
                    || Settings.audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0) {
                mp.start();
            }
        }
    }

    public void randomButton(Button btn) {
        LayoutParams absParams = (LayoutParams) btn.getLayoutParams();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        absParams.leftMargin = btn.getWidth() + new Random().nextInt(displaymetrics.widthPixels - 2 * btn.getWidth());
        System.out.println("left " + absParams.leftMargin);
        absParams.topMargin = btn.getHeight() + new Random().nextInt(displaymetrics.heightPixels - 3 * btn.getHeight());
        System.out.println("top " + absParams.topMargin);
        btn.setLayoutParams(absParams);
    }

    public void timer(long time) {
        if (cT != null) {
            cT.cancel();
            addTime(time);
        } else {
            addTime(time);
        }
    }

    public void addTime(long time) {

        if (cT != null) {
            cT.cancel();
        }

        cT = new CountDownTimer(millis + time, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCeas.setText(millisUntilFinished / 1000 + ":" + millisUntilFinished % 1000);
                millis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                if (cT != null) {
                    cT.cancel();
                }

                if (interAd.isLoaded()) {
                    interAd.show();
                } else {
                    txtCeas.setText("0:0");
                    Intent myIntent = new Intent(Play.this, Rezultate.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    highScore(myIntent);
                    startActivity(myIntent);
                }

            }
        };
        cT.start();
    }

    public void highScore(Intent myIntent) {
        long hsLong;
        long starsLong;
        long superRabbits;

        hsLong = Menu.sharedPref.getLong("highScore", 0L);
        starsLong = Menu.sharedPref.getLong("stars", 50L);
        superRabbits = Menu.sharedPref.getLong("superRabbits", 0L);

        SharedPreferences.Editor editor = Menu.sharedPref.edit();

        if (rezultat > hsLong) {
            editor.putLong("highScore", rezultat);
            editor.apply();
        }

        editor.putLong("stars", starsLong + rezultat / 10);
        editor.putLong("superRabbits", superRabbits + rezultat);
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, Menu.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        if (cT != null) {
            cT.cancel();
        }
    }

}
