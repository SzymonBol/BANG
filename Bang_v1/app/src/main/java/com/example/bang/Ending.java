package com.example.bang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * obsługa ekranu końcowego
 */
public class Ending extends AppCompatActivity {

    /**
     *wyświetla zwycięzcę rozgrywki
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        Intent intent=getIntent();
        TextView tx=findViewById(R.id.endtx);
        ImageView im=findViewById(R.id.endim);

        if(intent.getStringExtra("winner").equals("Szeryf")){
            tx.setText("Szeryf wygrywa!");
            im.setImageResource(R.drawable.sheriff);
        }
        else if(intent.getStringExtra("winner").equals("Renegat")){
            tx.setText("Renegat wygrywa!");
            im.setImageResource(R.drawable.rene);
        }

        findViewById(R.id.Bagain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Ending.this, UserName.class);
                startActivity(intent1);
                finish();
            }
        });

        findViewById(R.id.Bexit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
