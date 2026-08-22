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
    }

    private void showServicesMenu() {
        String[] items = {"⭐ Stars", "💎 Premium", "💼 Business", "🎁 Gifts"};
        new AlertDialog.Builder(this)
                .setTitle("Services")
                .setItems(items, (dialog, which) -> {
                    if (which == 0) loadStars();
                    else if (which == 1) loadPremium();
                    else if (which == 2) loadBusiness();
                    else loadGifts();
                }).show();
    }

    private boolean ready() {
        if (BuildConfig.SERVICES_BASE_URL.contains("YOUR-SERVICES-SERVER")) {
            toast("Services server URLini app/build.gradle ichida kiriting");
            return false;
        }
        return true;
    }

    private void loadStars() {
        if (!ready()) return;
        toast("Stars xizmatiga ulanmoqda…");
        ServicesClient.stars((ok, body) -> result("Stars", ok, body));
    }

    private void loadPremium() {
        if (!ready()) return;
        toast("Premium xizmatiga ulanmoqda…");
        ServicesClient.premium((ok, body) -> result("Premium", ok, body));
    }

    private void loadBusiness() {
        if (!ready()) return;
        toast("Business xizmatiga ulanmoqda…");
        ServicesClient.business((ok, body) -> result("Business", ok, body));
    }

    private void loadGifts() {
        if (!ready()) return;
        toast("Gifts xizmatiga ulanmoqda…");
        ServicesClient.gifts((ok, body) -> result("Gifts", ok, body));
    }

    private void result(String name, boolean ok, String body) {
        runOnUiThread(() -> toast(ok ? name + " ulandi" : name + ": " + body));
    }

    private void toast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
}
