package sorindonosa.whereismyrabbit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONObject;

import java.util.ArrayList;

public class Buy extends Activity {

    public final String stars100 = "100stars";
    public final String stars200 = "200stars";
    public final String stars500 = "500stars";
    public TextView txtStars;
    IInAppBillingService mservice;
    ServiceConnection connection;
    private long stele = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.buy);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button button100 = findViewById(R.id.btn100Stars);
        Button button200 = findViewById(R.id.btn200Stars);
        Button button500 = findViewById(R.id.btn500Stars);
        Button buttonExit = findViewById(R.id.btnExitBuy);
        txtStars = findViewById(R.id.txtStarsBuy);
        RelativeLayout rLayout = findViewById(R.id.layoutBuy);
        long starsLong = Menu.sharedPref.getLong("stars", 50L);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/cesar_font.otf");
        int culoareFundal = Color.parseColor("#3fb0ac");
        int culoareRoz = Color.parseColor("#ffd5e1");
        rLayout.setBackgroundColor(culoareFundal);
        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mservice = null;

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mservice = IInAppBillingService.Stub.asInterface(service);
            }
        };

        txtStars.setTextColor(culoareRoz);
        txtStars.setTypeface(typeFace);
        if (starsLong == 1) {
            txtStars.setText(starsLong + ""/* + " STAR" */);
        } else {
            txtStars.setText(starsLong + "" /* + " STARS" */);
        }

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);

        button100.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cumpara(stars100, 100);
            }
        });

        button200.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cumpara(stars200, 200);
            }
        });

        button500.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cumpara(stars500, 500);
            }
        });

        buttonExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Buy.this, Menu.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
    }

    public void cumpara(String inappid, long stele) {

        ArrayList<String> skuList = new ArrayList<>();
        skuList.add(inappid);
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
        Bundle skuDetails;
        try {
            skuDetails = mservice.getSkuDetails(3, getPackageName(), "inapp", querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {

                ArrayList<String> responseList = skuDetails.getStringArrayList("DETAILS_LIST");

                assert responseList != null;
                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    if (sku.equals(inappid)) {
                        Bundle buyIntentBundle = mservice.getBuyIntent(3, getPackageName(), sku, "inapp",
                                "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                        if (buyIntentBundle.getInt("RESPONSE_CODE") == 0) {
                            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                            assert pendingIntent != null;
                            startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(),
                                    0, 0, 0);
                            this.stele = stele;
                        } else if (buyIntentBundle.getInt("RESPONSE_CODE") == 7) {
                            Bundle ownedItems = mservice.getPurchases(3, getPackageName(), "inapp", null);
                            ArrayList<String> purchaseDataList = ownedItems
                                    .getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                            assert purchaseDataList != null;
                            for (String purchaseData : purchaseDataList) {
                                JSONObject o = new JSONObject(purchaseData);
                                String purchaseToken = o.optString("token", o.optString("purchaseToken"));
                                // Consume purchaseToken, handling any errors
                                mservice.consumePurchase(3, getPackageName(), purchaseToken);
                                cumpara(inappid, stele);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {

            if (resultCode == RESULT_OK) {
                try {
                    long starsLong = Menu.sharedPref.getLong("stars", 0L);
                    SharedPreferences.Editor editor = Menu.sharedPref.edit();
                    editor.putLong("stars", starsLong + stele);
                    editor.apply();
                    Intent myIntent = new Intent(Buy.this, Menu.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);

                } catch (Exception e) {
                    System.out.println("Failed!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, Menu.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
    }

}
