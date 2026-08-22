package com.superme.telegram07;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(com.superme.telegram07.R.layout.activity_main);
        findViewById(R.id.menu).setOnClickListener(v -> Toast.makeText(this, "Menu UI", Toast.LENGTH_SHORT).show());
        findViewById(R.id.search).setOnClickListener(v -> Toast.makeText(this, "Search UI", Toast.LENGTH_SHORT).show());
    }
}
