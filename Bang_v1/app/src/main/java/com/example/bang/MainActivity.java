package com.example.bang;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

/**
 * obsługa głównej części gry
 */
public class MainActivity extends AppCompatActivity {
    /**
     * pokazuje czy przerzucić daną kość: false- przerzuć, true- nie przerzucaj
     */
    public boolean[] chec = {false, false, false, false, false};
    /**
     * wartość pokazuje kość jest dynamitem
     */
    public boolean[] tnt = {false, false, false, false, false};
    /**
     * ilość przerzutów kostkami
     */
    int zm = 0;
    /**
     * id_gracza który teraz ma turę
     */
    int gracz = 0;
    /**
     * ilość graczy
     */
    int iloscgraczy = 4;
    /**
     * zmienna pomocnicza dla postaci Jassie Jones
     */
    int jjhp;
    /**
     * tablica przechowująca wyniki kości
     */
    int[] wyniki = {0, 0, 0, 0, 0};
    /**
     * czekanie na zakończenie rozptrzenia 3 dynamitów
     */
    boolean wait = false;
    /**
     * ng (false) - nie wykonuj zmiana() kiedy przydziel()
     */
    boolean ng = true;
    /**
     * zmienna potrzebna do podwójnego kliknięcia w przycisk powrotu
     */
    boolean doubleclick = false;
    /**
     * zmienna potrzebna do podwójnego kliknięcia w portret postaci
     */
    boolean doubleclick2=false;
    /**
     * tablica graczy
     */
    CPanel[] users = new CPanel[]{new CPanel(), new CPanel(), new CPanel(), new CPanel()};
    /**
     * ImageView kości
     */
    ImageView mimg1, mimg2, mimg3, mimg4, mimg5;
    /**
     * ImageView przycisku
     */
    ImageView but1, but2;

    /**
     * przypusuje pola do graczy
     * @param ar tablica TextView strzał
     * @param hp tablica TextView punktów życia
     * @param intent wartości poprzedniej aktywności
     */
    void przypisz(TextView ar[], TextView hp[], Intent intent) {

        users[0].un = (TextView) findViewById(R.id.P1name);
        users[1].un = (TextView) findViewById(R.id.P2name);
        users[2].un = (TextView) findViewById(R.id.P3name);
        users[3].un = (TextView) findViewById(R.id.P4name);
        users[0].shot = (ImageView) findViewById(R.id.shot1);
        users[1].shot = (ImageView) findViewById(R.id.shot2);
        users[2].shot = (ImageView) findViewById(R.id.shot3);
        users[3].shot = (ImageView) findViewById(R.id.shot4);
        users[0].setUser_name(intent.getStringExtra("player1name"));
        users[1].setUser_name(intent.getStringExtra("player2name"));
        users[2].setUser_name(intent.getStringExtra("player3name"));
        users[3].setUser_name(intent.getStringExtra("player4name"));
        users[0].roleview = findViewById(R.id.rview1);
        users[1].roleview = findViewById(R.id.rview2);
        users[2].roleview = findViewById(R.id.rview3);
        users[3].roleview = findViewById(R.id.rview4);
        users[0].portret = findViewById(R.id.cview1);
        users[1].portret = findViewById(R.id.cview2);
        users[2].portret = findViewById(R.id.cview3);
        users[3].portret = findViewById(R.id.cview4);
        users[0].txt();
        users[1].txt();
        users[2].txt();
        users[3].txt();
        users[1].rotation(90);
        users[2].rotation(180);
        users[3].rotation(270);
        sheriff_role_char();
        users[gracz].color();

        mimg1 = (ImageView) findViewById(R.id.kosc1);
        mimg2 = (ImageView) findViewById(R.id.kosc2);
        mimg3 = (ImageView) findViewById(R.id.kosc3);
        mimg4 = (ImageView) findViewById(R.id.kosc4);
        mimg5 = (ImageView) findViewById(R.id.kosc5);

        but1 = (ImageView) findViewById(R.id.but1);
        but2 = (ImageView) findViewById(R.id.but2);

        hp[0].setText(String.valueOf(users[0].getHp()));
        ar[0].setText(String.valueOf(users[0].getArrow()));
        hp[1].setText(String.valueOf(users[1].getHp()));
        ar[1].setText(String.valueOf(users[1].getArrow()));
        hp[2].setText(String.valueOf(users[2].getHp()));
        ar[2].setText(String.valueOf(users[2].getArrow()));
        hp[3].setText(String.valueOf(users[3].getHp()));
        ar[3].setText(String.valueOf(users[3].getArrow()));

        ar[1].setRotation(90);
        hp[1].setRotation(90);
        ar[2].setRotation(180);
        hp[2].setRotation(180);
        ar[3].setRotation(270);
        hp[3].setRotation(270);

    }

    /**
     * zakańcza turę gracz i zaczyna następnego
     */
    void end() {
        win();
        if (wyniki[0] == 6 && wyniki[1] == 6 && wyniki[2] == 6 && wyniki[3] == 6 && wyniki[4] == 6) {
            if (!wait) {

                mimg2.setImageResource(R.drawable.quest);
                mimg3.setImageResource(R.drawable.quest);
                mimg4.setImageResource(R.drawable.quest);
                mimg5.setImageResource(R.drawable.quest);
                mimg1.setImageResource(R.drawable.quest);

                mimg1.setBackgroundResource(R.drawable.unborder);
                mimg2.setBackgroundResource(R.drawable.unborder);
                mimg3.setBackgroundResource(R.drawable.unborder);
                mimg4.setBackgroundResource(R.drawable.unborder);
                mimg5.setBackgroundResource(R.drawable.unborder);


                for (int i = 0; i < 5; i++) {
                    chec[i] = false;
                    tnt[i] = false;
                }

                users[gracz].uncolor();
                do {
                    if (zm != 0) {
                        if (gracz < 3) gracz++;
                        else gracz = 0;
                    }
                } while (users[gracz].dead);
                users[gracz].color();
                zm = 0;
                jjhp = 0;
                ng = true;
            }
        }
    }

    /**
     * zakreśla których kości nie przerzucać
     * @param x id kości
     * @param v obraz kości
     */
    void zmiana(int x, View v) {
        if (!tnt[x] && zm != 0 && ng || users[gracz].name.equals("BLACK JACK") && zm != 0 && ng) {
            if (chec[x]) {
                chec[x] = false;
                v.setBackgroundResource(R.drawable.unborder);
            } else {
                chec[x] = true;
                v.setBackgroundResource(R.drawable.border);
            }
        }
    }

    /**
     * rozpatrywanie piw
     * @param i która kość jest rozpatrywana
     * @param kosci ImageView kości
     * @param hp TextView punktów życia graczy
     */
    void beers(final int i, final ImageView[] kosci, final TextView hp[]) {
        win();
        for (int l = 0; l < 5; l++) {
            if (wyniki[l] == 5) {
                kosci[l].setBackgroundResource(R.drawable.border_przydziel);
                break;
            }
        }
        if (wyniki[i] == 5) {
            for (int j = 0; j < 4; j++) {
                final int x = j;
                if (!users[j].dead)
                    users[j].shot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (users[gracz].name.equals("JASSE JONES") && jjhp <= 4 && x == gracz)
                                users[gracz].addhp(2);
                            else
                                users[x].addhp(1);
                            hp[x].setText(String.valueOf(users[x].getHp()));
                            kosci[i].setBackgroundResource(R.drawable.unborder);
                            kosci[i].setImageResource(R.drawable.quest);
                            wyniki[i] = 6;
                            if (wyniki[0] != 5 && wyniki[1] != 5 && wyniki[2] != 5 && wyniki[3] != 5 && wyniki[4] != 5) {
                                for (int s = 0; s < 4; s++) {
                                    users[s].shot.setOnClickListener(null);
                                }
                                gunsling(kosci, hp);
                                return;
                            }
                            beers(i + 1, kosci, hp);
                        }
                    });
            }
        } else {
            for (int s = 0; s < 4; s++) {
                users[s].shot.setOnClickListener(null);
            }
            if (wyniki[0] != 5 && wyniki[1] != 5 && wyniki[2] != 5 && wyniki[3] != 5 && wyniki[4] != 5) {
                gunsling(kosci, hp);
                return;
            }
            beers(i + 1, kosci, hp);

        }

    }

    /**
     * rozpatrywanie dynamitów
     * @param t ilość dynamitów
     * @param hp TextView punktów życia graczy
     */
    void tnts(int t, final TextView hp[]) {
        final ImageView[] kosci = {(ImageView) findViewById(R.id.kosc1), (ImageView) findViewById(R.id.kosc2),
                (ImageView) findViewById(R.id.kosc3), (ImageView) findViewById(R.id.kosc4), (ImageView) findViewById(R.id.kosc5)};
        for (int i = 0; i <= 4; i++) {
            if (wyniki[i] == 2) {
                t = t + 1;
            }
        }
        if (t >= 3) {
            for (int i = 0; i <= 4; i++) {
                if (wyniki[i] == 2) {
                    kosci[i].setBackgroundResource(R.drawable.border_przydziel);
                }
            }
            wait = true;
            for (int i = 0; i < 5; i++) {
                chec[i] = true;
                wyniki[i] = 6;
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    users[gracz].delhp(1);
                    hp[gracz].setText(String.valueOf(users[gracz].getHp()));
                    wait = false;
                    end();
                }
            }, 1000);


        }
        ;
    }

    /**
     * rozpatrywanie strzał
     * @param hp TextView punktów życia graczy
     * @param arr TextView strzał graczy
     */
    void arrows(final TextView hp[], final TextView arr[]) {
        for (int i = 0; i < iloscgraczy; i++) {
            if (!users[i].name.equals("JOURDONNAIS"))
                users[i].delhp(users[i].arrow);
            else
                users[i].delhp(1);
            users[i].setArrow(0);

            hp[i].setText(String.valueOf(users[i].getHp()));
            arr[i].setText(String.valueOf(users[i].getArrow()));
        }
        if(users[gracz].dead){
            for (int i = 0; i < 5; i++) {
                chec[i] = true;
                wyniki[i] = 6;
            }
            end();
        }
    }

    /**
     * rozpatrywanie miedzy oczy 1 i 2
     * @param kosci ImageView kości
     * @param hp TextView punktów życia graczy
     */
    void viewfinders(final ImageView[] kosci, final TextView hp[]) {
        win();
        for (int l = 0; l < 5; l++) {
            if (wyniki[l] == 0 || wyniki[l] == 1) {
                kosci[l].setBackgroundResource(R.drawable.border_przydziel);
            }
        }

        int szukaj_kolejnego = gracz;
        do {
            if (szukaj_kolejnego == iloscgraczy - 1)
                szukaj_kolejnego = -1;
            szukaj_kolejnego++;
        } while (users[szukaj_kolejnego].dead);

        int szukaj_poprzedniego = gracz;
        do {
            if (szukaj_poprzedniego == 0)
                szukaj_poprzedniego = iloscgraczy;
            szukaj_poprzedniego--;
        } while (users[szukaj_poprzedniego].dead);

        int szukaj_kolejnego2 = szukaj_kolejnego;
        do {
            if (szukaj_kolejnego2 == iloscgraczy - 1)
                szukaj_kolejnego2 = -1;
            szukaj_kolejnego2++;
        } while (users[szukaj_kolejnego2].dead);

        int szukaj_poprzedniego2 = szukaj_poprzedniego;
        do {
            if (szukaj_poprzedniego2 == 0)
                szukaj_poprzedniego2 = iloscgraczy;
            szukaj_poprzedniego2--;
        } while (users[szukaj_poprzedniego2].dead);

        if (szukaj_kolejnego2 == gracz) {
            szukaj_kolejnego2 = szukaj_kolejnego;
            szukaj_poprzedniego2 = szukaj_poprzedniego;
        }

        final int[] KP = {szukaj_kolejnego, szukaj_poprzedniego, szukaj_kolejnego2, szukaj_poprzedniego2}; //kolejny / poprzedni

        final int i;
        int k;
        for (k = 0; k < iloscgraczy; k++) {
            if (wyniki[k] == 0) break;
        }
        i = k;
        final int j;
        int u;
        for (u = 0; u < iloscgraczy; u++) {
            if (wyniki[u] == 1) break;
        }
        j = u;
        if (wyniki[i] == 0 || wyniki[j] == 1) {
            if (wyniki[i] == 0 || (users[gracz].name == "CALAMITY JANET" && wyniki[j] == 1)) {
                users[KP[0]].shot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users[KP[0]].delhp(1);
                        hp[KP[0]].setText(String.valueOf(users[KP[0]].getHp()));
                        if (wyniki[i] == 0) {
                            kosci[i].setBackgroundResource(R.drawable.unborder);
                            kosci[i].setImageResource(R.drawable.quest);
                            wyniki[i] = 6;
                        } else {
                            kosci[j].setBackgroundResource(R.drawable.unborder);
                            kosci[j].setImageResource(R.drawable.quest);
                            wyniki[j] = 6;
                        }
                        for (int s = 0; s < 4; s++)
                            users[s].shot.setOnClickListener(null);
                        viewfinders(kosci, hp);
                    }
                });
                users[KP[1]].shot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users[KP[1]].delhp(1);
                        hp[KP[1]].setText(String.valueOf(users[KP[1]].getHp()));
                        if (wyniki[i] == 0) {
                            kosci[i].setBackgroundResource(R.drawable.unborder);
                            kosci[i].setImageResource(R.drawable.quest);
                            wyniki[i] = 6;
                        } else {
                            kosci[j].setBackgroundResource(R.drawable.unborder);
                            kosci[j].setImageResource(R.drawable.quest);
                            wyniki[j] = 6;
                        }
                        for (int s = 0; s < 4; s++)
                            users[s].shot.setOnClickListener(null);
                        viewfinders(kosci, hp);
                    }
                });
            }


            if (wyniki[u] == 1 || (users[gracz].name == "CALAMITY JANET" && wyniki[i] == 0)) {
                users[KP[2]].shot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users[KP[2]].delhp(1);
                        hp[KP[2]].setText(String.valueOf(users[KP[2]].getHp()));
                        if (wyniki[j] == 1) {
                            kosci[j].setBackgroundResource(R.drawable.unborder);
                            kosci[j].setImageResource(R.drawable.quest);
                            wyniki[j] = 6;
                        } else {
                            kosci[i].setBackgroundResource(R.drawable.unborder);
                            kosci[i].setImageResource(R.drawable.quest);
                            wyniki[i] = 6;
                        }
                        for (int s = 0; s < 4; s++)
                            users[s].shot.setOnClickListener(null);

                        viewfinders(kosci, hp);
                    }
                });
                users[KP[3]].shot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users[KP[3]].delhp(1);
                        hp[KP[3]].setText(String.valueOf(users[KP[3]].getHp()));
                        if (wyniki[j] == 1) {
                            kosci[j].setBackgroundResource(R.drawable.unborder);
                            kosci[j].setImageResource(R.drawable.quest);
                            wyniki[j] = 6;
                        } else {
                            kosci[i].setBackgroundResource(R.drawable.unborder);
                            kosci[i].setImageResource(R.drawable.quest);
                            wyniki[i] = 6;
                        }
                        for (int s = 0; s < 4; s++)
                            users[s].shot.setOnClickListener(null);
                        viewfinders(kosci, hp);
                    }
                });
            }
        } else {
            beers(0, kosci, hp);
            return;
        }
    }

    /**
     * rozpatrywanie gatlinga
     * @param kosci ImageView kości
     * @param hp TextView punktów życia graczy
     */
    void gunsling(final ImageView[] kosci, final TextView[] hp) {
        final TextView[] ar = {findViewById(R.id.p1arrowtx), findViewById(R.id.p2arrowtx),
                findViewById(R.id.p3arrowtx), findViewById(R.id.p4arrowtx)};
        int guns = 0;
        for (int i = 0; i < 5; i++) {
            if (wyniki[i] == 3) {
                guns++;
            }
        }
        if (guns >= 3 || (users[gracz].name == "WILLY THE KID" && guns == 2)) {
            for (int i = 0; i < iloscgraczy; i++) {
                if (gracz != i && users[i].name != "PAUL REGRET")
                    users[i].delhp(1);
                if (wyniki[i] == 3) {
                    kosci[i].setImageResource(R.drawable.quest);
                    wyniki[i] = 6;
                }
            }
            users[gracz].setArrow(0);
        }
        for (int i = 0; i < 4; i++) {
            hp[i].setText(String.valueOf(users[i].getHp()));
            ar[i].setText(String.valueOf(users[i].getArrow()));
        }
        arr_tnt();
    }

    /**
     * zamina na wartości końcowe dynamitu i strzał przy rozpatrywaniu
     */
    void arr_tnt() {
        for (int i = 0; i < 5; i++) {
            if (wyniki[i] != 6) {
                wyniki[i] = 6;
            }
        }
        end();
    }

    /**
     * zaczęcie etapu przydzielania kości
     */
    void przydziel() {
        final ImageView[] kosci = {(ImageView) findViewById(R.id.kosc1), (ImageView) findViewById(R.id.kosc2),
                (ImageView) findViewById(R.id.kosc3), (ImageView) findViewById(R.id.kosc4), (ImageView) findViewById(R.id.kosc5)};
        final TextView[] hp = {findViewById(R.id.p1hptx), findViewById(R.id.p2hptx),
                findViewById(R.id.p3hptx), findViewById(R.id.p4hptx)};
        if (users[gracz].name.equals("JASSE JONES")) jjhp = users[gracz].hp;
        zm = 3;
        ng = false;
        boolean suzy = true;
        for (int i = 0; i < 5; i++) {
            kosci[i].setBackgroundResource(R.drawable.unborder);
            if (wyniki[i] == 0 || wyniki[i] == 1) suzy = false;
        }
        if (users[gracz].name.equals("SUZY LAFAYETTE") && suzy) {
            users[gracz].addhp(2);
        }
        viewfinders(kosci, hp);


    }

    /**
     * losowanie postaci i roli
     */
    void sheriff_role_char() {
        int[] id_char = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] role = {0, 2, 3, 3, 1, 3, 1, 2}; //0-szeryf, 1-zastępca, 2-renegat, 3-bandyta
        final TextView[] hp = {findViewById(R.id.p1hptx), findViewById(R.id.p2hptx),
                findViewById(R.id.p3hptx), findViewById(R.id.p4hptx)};
        //postacie
        Random rand = new Random();
        int a;
        for (int i = 0; i < iloscgraczy; i++) {
            do {
                a = rand.nextInt(8);
            } while (id_char[a] == -1);
            users[i].character(a + 1);
            id_char[a] = -1;
        }
        //role
        for (int i = 0; i < iloscgraczy; i++) {
            int x;
            do {
                x = rand.nextInt(iloscgraczy);
            } while (role[x] == -1);
            users[i].role = role[x];
            role[x] = -1;

            if (users[i].role == 0) {
                users[i].roleview.setImageResource(R.drawable.sheriff);
                users[i].basic = users[i].basic + 2;
                users[i].addhp(2);
                gracz = i;
            }
            hp[i].setText(String.valueOf(users[i].getHp()));
        }

        setClick();
    }

    /**
     * przypisanie funkcji onClick portretom
     */
    void setClick(){
        final Intent intent=new Intent(MainActivity.this, ShowCharacter.class);

        for(int i=0;i<iloscgraczy;i++){
            final int z=i;
            users[i].portret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (doubleclick2) {
                        String t1,t2;
                        t1=Integer.toString(users[z].role);
                        t2=Integer.toString(users[z].char_id);
                        intent.putExtra("rola",t1);
                        intent.putExtra("usname", users[z].user_name);
                        intent.putExtra("opis", users[z].opis);
                        intent.putExtra("portret", t2);
                        startActivity(intent);

                    }
                    else
                    Toast.makeText(MainActivity.this, "Wciśnij ponownie aby zobaczyć", Toast.LENGTH_LONG).show();
                    doubleclick2 = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleclick2 = false;
                        }
                    }, 2000);

                }
            });
        }
    }

    /**
     * sprawdza czy ktoś wykonał swój cel
     */
    void win(){
        boolean rene=false,szer=false,band=false;
        String winner="";
        //renegat win
        for(int i=0;i<iloscgraczy;i++){
            if(users[i].dead || users[i].role == 2) {
                rene=true;
                winner="Renegat";
            }
            else{
                rene=false;
                break;
            }
        }
        //szeryf win do poprawy
        for(int i=0;i<iloscgraczy;i++){
            if(((users[i].role==2  && users[i].dead) || (users[i].role==3  && users[i].dead) || users[i].role==0) && !rene ){
                szer=true;
                winner="Szeryf";
            }
            else{
                szer=false;
                break;
            }
        }
        //bandyci win
        for(int i=0;i<iloscgraczy;i++){
            if(users[i].role==0 && users[i].dead && !rene) {
                band=true;
                winner="Bandyci";
                break;
            }
        }
        if(rene || szer || band){
            Intent intent=new Intent(MainActivity.this, Ending.class);
            intent.putExtra("winner", winner);
            startActivity(intent);
            finish();
        }

    }

    /**
     * wyjście z aplikacji po podwójnym naciśnięciu powrotu
     */
    @Override
    public void onBackPressed() {
        if (doubleclick) finish();
        else
        Toast.makeText(MainActivity.this, "Wciśnij ponownie aby wyjść", Toast.LENGTH_LONG).show();
        doubleclick = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleclick = false;
            }
        }, 2000);
    }

    /**
     *przypisanie funkcji przyciskom oraz wstępna kofiguracja
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        final TextView[] ar = {findViewById(R.id.p1arrowtx), findViewById(R.id.p2arrowtx),
                findViewById(R.id.p3arrowtx), findViewById(R.id.p4arrowtx)};
        final TextView[] hp = {findViewById(R.id.p1hptx), findViewById(R.id.p2hptx),
                findViewById(R.id.p3hptx), findViewById(R.id.p4hptx)};


        przypisz(ar, hp, intent);


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int arrows = 0;
                for (int i = 0; i < iloscgraczy; i++) {
                    arrows = arrows + users[i].getArrow();
                }
                Random rand = new Random();
                int a = rand.nextInt(6);
                int b = rand.nextInt(6);
                int c = rand.nextInt(6);
                int d = rand.nextInt(6);
                int e = rand.nextInt(6);
                int t = 0;
                if (zm <= 2 || (users[gracz].name.equals("LUCKY DUKE") && zm == 3)) {
                    if (!chec[0] && !tnt[0] || users[gracz].name.equals("BLACK JACK") && !chec[0]) {
                        switch (a) {
                            case 0:
                                mimg1.setImageResource(R.drawable.viewfinder1);
                                break;
                            case 1:
                                mimg1.setImageResource(R.drawable.viewfinder2);
                                break;
                            case 2:
                                mimg1.setImageResource(R.drawable.tnt);
                                tnt[0] = true;
                                break;
                            case 3:
                                mimg1.setImageResource(R.drawable.gunslinger);
                                break;
                            case 4:
                                mimg1.setImageResource(R.drawable.arrow);
                                if (arrows != 9) {
                                    users[gracz].addarrow(1);
                                    arrows++;
                                }
                                break;
                            case 5:
                                mimg1.setImageResource(R.drawable.beer);
                                break;
                        }
                        wyniki[0] = a;
                    }
                    if (!chec[1] && !tnt[1] || users[gracz].name.equals("BLACK JACK") && !chec[1]) {
                        switch (b) {
                            case 0:
                                mimg2.setImageResource(R.drawable.viewfinder1);
                                break;
                            case 1:
                                mimg2.setImageResource(R.drawable.viewfinder2);
                                break;
                            case 2:
                                mimg2.setImageResource(R.drawable.tnt);
                                tnt[1] = true;
                                break;
                            case 3:
                                mimg2.setImageResource(R.drawable.gunslinger);
                                break;
                            case 4:
                                mimg2.setImageResource(R.drawable.arrow);
                                if (arrows != 9) {
                                    users[gracz].addarrow(1);
                                    arrows++;
                                }
                                break;
                            case 5:
                                mimg2.setImageResource(R.drawable.beer);
                                break;
                        }
                        wyniki[1] = b;
                    }
                    if (!chec[2] && !tnt[2] || users[gracz].name.equals("BLACK JACK") && !chec[2]) {
                        switch (c) {
                            case 0:
                                mimg3.setImageResource(R.drawable.viewfinder1);
                                break;
                            case 1:
                                mimg3.setImageResource(R.drawable.viewfinder2);
                                break;
                            case 2:
                                mimg3.setImageResource(R.drawable.tnt);
                                tnt[2] = true;
                                break;
                            case 3:
                                mimg3.setImageResource(R.drawable.gunslinger);
                                break;
                            case 4:
                                mimg3.setImageResource(R.drawable.arrow);
                                if (arrows != 9) {
                                    users[gracz].addarrow(1);
                                    arrows++;
                                }
                                break;
                            case 5:
                                mimg3.setImageResource(R.drawable.beer);
                                break;
                        }
                        wyniki[2] = c;
                    }
                    if (!chec[3] && !tnt[3] || users[gracz].name.equals("BLACK JACK") && !chec[3]) {
                        switch (d) {
                            case 0:
                                mimg4.setImageResource(R.drawable.viewfinder1);
                                break;
                            case 1:
                                mimg4.setImageResource(R.drawable.viewfinder2);
                                break;
                            case 2:
                                mimg4.setImageResource(R.drawable.tnt);
                                tnt[3] = true;
                                break;
                            case 3:
                                mimg4.setImageResource(R.drawable.gunslinger);
                                break;
                            case 4:
                                mimg4.setImageResource(R.drawable.arrow);
                                if (arrows != 9) {
                                    users[gracz].addarrow(1);
                                    arrows++;
                                }
                                break;
                            case 5:
                                mimg4.setImageResource(R.drawable.beer);
                                break;
                        }
                        wyniki[3] = d;
                    }
                    if (!chec[4] && !tnt[4] || users[gracz].name.equals("BLACK JACK") && !chec[4]) {
                        switch (e) {
                            case 0:
                                mimg5.setImageResource(R.drawable.viewfinder1);
                                break;
                            case 1:
                                mimg5.setImageResource(R.drawable.viewfinder2);
                                break;
                            case 2:
                                mimg5.setImageResource(R.drawable.tnt);
                                tnt[4] = true;
                                break;
                            case 3:
                                mimg5.setImageResource(R.drawable.gunslinger);
                                break;
                            case 4:
                                mimg5.setImageResource(R.drawable.arrow);
                                if (arrows != 9) {
                                    users[gracz].addarrow(1);
                                    arrows++;
                                }
                                break;
                            case 5:
                                mimg5.setImageResource(R.drawable.beer);
                                break;
                        }
                        wyniki[4] = e;
                    }
                    zm++;
                }
                if (arrows == 9) {
                    arrows(hp, ar);
                    arrows = 0;
                }
                tnts(t, hp);

                ar[gracz].setText(String.valueOf(users[gracz].getArrow()));
                hp[gracz].setText(String.valueOf(users[gracz].getHp()));
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zm != 0)
                    przydziel();

            }
        });

        mimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmiana(0, v);
            }
        });
        mimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmiana(1, v);
            }
        });
        mimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmiana(2, v);
            }
        });
        mimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmiana(3, v);
            }
        });
        mimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmiana(4, v);
            }
        });
    }
}