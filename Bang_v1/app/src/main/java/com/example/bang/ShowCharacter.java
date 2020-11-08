package com.example.bang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * sprawdzanie roli u umiejętności postaci
 */
public class ShowCharacter extends AppCompatActivity{
    /**
     * wyświetla opis i zdjęcie roli oraz postaci
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_character);
            Intent intent=getIntent();
            TextView SCopis=findViewById(R.id.SCcharacter);
            TextView SCname=findViewById(R.id.SCname);
            TextView SCrola=findViewById(R.id.SCrole);
            ImageView SCchara=findViewById(R.id.SCportretview);
            ImageView SCrolaview=findViewById(R.id.SCroleview);


        SCopis.setText(intent.getStringExtra("opis"));
        SCname.setText(intent.getStringExtra("usname"));
        if(intent.getStringExtra("rola").equals("0")){
            SCrola.setText("Jesteś szeryfem. Twój cel: wyeliminuj bandytów i renegatów!");
            SCrolaview.setImageResource(R.drawable.sheriff);
        }
        else if(intent.getStringExtra("rola").equals("1")){
            SCrola.setText("Jesteś zastępcą szeryfa. Twój cel: pomagaj i chroń szeryfa!");
            SCrolaview.setImageResource(R.drawable.sheriff2);
        }
        else if(intent.getStringExtra("rola").equals("2")){
            SCrola.setText("Jesteś renegatem. Twój cel: bądź ostatnią postacią w grze!");
            SCrolaview.setImageResource(R.drawable.rene);
        }
        else{
            SCrola.setText("Jesteś bandytą. Twój cel: wyeliminuj szeryfa!");
            SCrolaview.setImageResource(R.drawable.bandit);
        }

        if(intent.getStringExtra("portret").equals("1")){
            SCchara.setImageResource(R.drawable.paul_regret);
        }
        else if(intent.getStringExtra("portret").equals("2")){
            SCchara.setImageResource(R.drawable.jack_black);
        }
        else if(intent.getStringExtra("portret").equals("3")){
            SCchara.setImageResource(R.drawable.lucky_duke);
        }
        else if(intent.getStringExtra("portret").equals("4")){
            SCchara.setImageResource(R.drawable.jourdonnais);
        }
        else if(intent.getStringExtra("portret").equals("5")){
            SCchara.setImageResource(R.drawable.calamity_janet);
        }
        else if(intent.getStringExtra("portret").equals("6")){
            SCchara.setImageResource(R.drawable.jasse_jones);
        }
        else if(intent.getStringExtra("portret").equals("7")){
            SCchara.setImageResource(R.drawable.willy_the_kid);
        }
        else{
            SCchara.setImageResource(R.drawable.suzy_lafayette);
        }

        findViewById(R.id.Bwroc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
