package com.superme.telegram07;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        findViewById(R.id.menu).setOnClickListener(v -> showServicesMenu());
        findViewById(R.id.search).setOnClickListener(v -> toast("Search"));

        // Stars is intentionally routed to the Superme Services gateway, not Telegram networking.
        findViewById(R.id.search).setOnLongClickListener(v -> { load("Stars", ServicesClient::stars); return true; });
    }

    private void showServicesMenu() {
        String[] items = {"⭐ Stars", "💎 Premium", "💼 Business", "🎁 Gifts"};
        new AlertDialog.Builder(this)
                .setTitle("Services")
                .setItems(items, (dialog, which) -> {
                    switch (which) {
                        case 0: load("Stars", ServicesClient::stars); break;
                        case 1: load("Premium", ServicesClient::premium); break;
                        case 2: load("Business", ServicesClient::business); break;
                        case 3: load("Gifts", ServicesClient::gifts); break;
                    }
                }).show();
    }

    private void load(String name, ServicesClient.Callback request) {
        Toast.makeText(this, name + " xizmatiga ulanmoqda…", Toast.LENGTH_SHORT).show();
        request.onResult(false, "");
        // The callback is executed by the ServicesClient worker. The request above is replaced below
        // by a fresh call so all four services share one gateway implementation.
        if (name.equals("Stars")) ServicesClient.stars((ok, body) -> result(name, ok, body));
        else if (name.equals("Premium")) ServicesClient.premium((ok, body) -> result(name, ok, body));
        else if (name.equals("Business")) ServicesClient.business((ok, body) -> result(name, ok, body));
        else ServicesClient.gifts((ok, body) -> result(name, ok, body));
    }

    private void result(String name, boolean ok, String body) {
        runOnUiThread(() -> toast(ok ? name + " ulandi" : name + ": " + body));
    }

    private void toast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
}
