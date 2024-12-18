package com.firstapp.implicitintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mWebsiteEditText = findViewById(R.id.website_editText);
        mLocationEditText = findViewById(R.id.location_editText);
        mShareEditText = findViewById(R.id.share_editText);
    }

    public void openWebsite(View view) {
        String url = mWebsiteEditText.getText().toString().trim();
        if (!url.isEmpty()) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "No application can handle this intent for the website.");
            }
        } else {
            Log.d("ImplicitIntents", "Website URL is empty.");
        }
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString().trim();
        if (!loc.isEmpty()) {
            Uri addressUri = Uri.parse("geo:0,0?q=" + Uri.encode(loc));
            Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "No application can handle this intent for the location.");
            }
        } else {
            Log.d("ImplicitIntents", "Location input is empty.");
        }
    }

    public void shareText(View view) {
        String txt = mShareEditText.getText().toString().trim();
        if (!txt.isEmpty()) {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle(R.string.share_text_with)
                    .setText(txt)
                    .startChooser();
        } else {
            Log.d("ImplicitIntents", "Text to share is empty.");
        }
    }
}