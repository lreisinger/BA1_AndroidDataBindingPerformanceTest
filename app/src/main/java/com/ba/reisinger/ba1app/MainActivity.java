package com.ba.reisinger.ba1app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openView(View view)
    {
        Intent intent;
        if(view.getId() == R.id.button_databinding)
        {
            intent = new Intent(this, DataBindingActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.button_nondatabinding)
        {
            intent = new Intent(this, NonDataBindingActivity.class);
            startActivity(intent);
        }
    }
}
