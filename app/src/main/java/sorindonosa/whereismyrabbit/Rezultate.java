package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Rezultate extends Activity {

    private Button buttonMenu, buttonAgain;
    private TextView txtRabbs, txtStars;
    private RelativeLayout rLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rezultate);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        rLayout = findViewById(R.id.layoutRezultate);
        buttonMenu = findViewById(R.id.btnMenu);
        buttonAgain = findViewById(R.id.btnAgain);
        txtRabbs = findViewById(R.id.txtRabC);
        txtStars = findViewById(R.id.txtStarC);

        int culoareFundal = Color.parseColor("#3fb0ac");
        int culoareAccent = Color.parseColor("#fae596");

        rLayout.setBackgroundColor(culoareFundal);

        if ((Play.rezultat) == 1) {
            txtRabbs.setText((Play.rezultat + " RABBIT"));
        } else {
            txtRabbs.setText((Play.rezultat + " RABBITS"));
        }

        if ((Play.rezultat / 10) == 1) {
            txtStars.setText((Play.rezultat / 10) + " STAR");
        } else {
            txtStars.setText((Play.rezultat / 10) + " STARS");
        }

        txtRabbs.setTextColor(Color.parseColor("#ffd5e1"));
        txtStars.setTextColor(Color.parseColor("#ffd5e1"));

        buttonMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Rezultate.this, Menu.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });

        buttonAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Rezultate.this, Play.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
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
