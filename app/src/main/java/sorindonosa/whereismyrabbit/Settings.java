package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity {

    //	private Switch swAudio, swVibrate;
    public static AudioManager audioManager;
    public static boolean audioEffects;
    private Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        RelativeLayout rLayout = findViewById(R.id.layoutSettings);
        Button buttonExit = findViewById(R.id.btnExitSett);
        ToggleButton tglAudio = findViewById(R.id.tbAudio);
        ToggleButton tglVibrate = findViewById(R.id.tbVibrate);

        int culoareFundal = Color.parseColor("#3fb0ac");
        int culoareRoz = Color.parseColor("#ffd5e1");

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        tglAudio.setText(null);
        tglAudio.setTextOn(null);
        tglAudio.setTextOff(null);

        tglVibrate.setText(null);
        tglVibrate.setTextOn(null);
        tglVibrate.setTextOff(null);

        rLayout.setBackgroundColor(culoareFundal);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tglAudio.setTextColor(culoareRoz);
        tglVibrate.setTextColor(culoareRoz);

        buttonExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Settings.this, Menu.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);

            }
        });

        if (audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0) {
            tglAudio.setChecked(true);
        } else {
            tglAudio.setChecked(false);
        }

        tglAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 10, 0);
                    Toast.makeText(Settings.this, "Audio ON", Toast.LENGTH_SHORT).show();
                    audioEffects = true;

                } else {
                    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
                    Toast.makeText(Settings.this, "Audio OFF", Toast.LENGTH_SHORT).show();
                    audioEffects = false;
                }

            }

        });

        if (audioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER) == AudioManager.VIBRATE_SETTING_ON) {
            tglVibrate.setChecked(true);
        } else {
            tglVibrate.setChecked(false);
        }

        tglVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vib.vibrate(50);
                    audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
                    Toast.makeText(Settings.this, "Vibrate ON", Toast.LENGTH_SHORT).show();
                } else {
                    audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
                    audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
                            AudioManager.VIBRATE_SETTING_OFF);
                    Toast.makeText(Settings.this, "Vibrate OFF", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, Menu.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }
}
