package com.example.bang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bang.R;

/**
 * wpisywanie nazw gracza
 */
public class UserName extends AppCompatActivity {

    /**
     * pole do  wpisywania nazwy gracza
     */
    EditText p1,p2,p3,p4;
    /**
     * zmienna potrzebna do podwójnego kliknięcia w przycisk powrotu
     */
    boolean doubleclick2=false;

    /**
     * napisana metoda przycisku cofania
     */
    @Override
    public void onBackPressed() {
        if(doubleclick2) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
        Toast.makeText(UserName.this, "Wciśnij ponownie aby wyjść",Toast.LENGTH_LONG).show();
        doubleclick2=true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleclick2=false;
            }
        }, 2000);
    }

    /**
     * odpowiedni layout oraz funkcja przycisku
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        p3=findViewById(R.id.p3);
        p4=findViewById(R.id.p4);


        Button b1= (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserName.this, MainActivity.class);
                intent.putExtra("player1name", p1.getText().toString());
                intent.putExtra("player2name", p2.getText().toString());
                intent.putExtra("player3name", p3.getText().toString());
                intent.putExtra("player4name", p4.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }
}
