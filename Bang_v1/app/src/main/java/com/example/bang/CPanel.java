package com.example.bang;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * panel gracza
 */
public class CPanel extends CCharacter {
    /** przycisk do przydzelania piw / celowników */
    public ImageView shot;
    /** ilość strzał */
    public int arrow=0;
    /** id roli */
    public int role;
    /** nazwa gracza */
    public String user_name;
    /** nazwa gracza - pole TextView */
    public TextView un;
    /** śmierć gracza: false - żywy, true- wyeliminowany */
    public boolean dead=false;

    /** pusty konstruktor CPanel */
    public CPanel() {
    }

    /**
     * dodanie strzał graczowi
     * @param numb ilość dodawanych strzał
     */
    void addarrow(int numb){
        arrow=arrow+numb;
    }

    /**
     * dodawanie punktów życia graczowi
     * @param numb ilość dodawanych punktów życia
     */
    void addhp(int numb){
        for(int i=0;i<numb;i++){
            if(hp<basic)
                hp++;
        }
    }

    /**
     * usuwanie punktów życia graczowi
     * @param numb ilość usuwanych punktów życia
     */
    void delhp(int numb){
        hp=hp-numb;
        if(hp<=0) smierc();
    }

    /**
     * działania po spadnięciu punktów życia gracza do 0 lub niżej
     */
    private void smierc() {
        dead=true;
        arrow=0;
        switch (role){
            case 1:
                roleview.setImageResource(R.drawable.sheriff2);
                break;
            case 2:
                roleview.setImageResource(R.drawable.rene);
                break;
            case 3:
                roleview.setImageResource(R.drawable.bandit);
                break;
        }
    }

    /**
     *zwracanie wartości punktów życia
     */
    public int getHp() {
        return hp;
    }

    /**
     *zwracanie ilości strzał gracza
     */
    public int getArrow() {
        return arrow;
    }

    /**
     * nadawanie nowej ilości strzał
     * @param arrow nowa ilość
     */
    public void setArrow(int arrow) {
        this.arrow = arrow;
    }

    /**
     * nadawanie nazwy graczowi
     * @param user_name nazwa gracza
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * dodwanie nazwy gracza do TextView
     */
    void txt(){
        un.setText(user_name);
    }

    /**
     * obracanie poszczególnych elementów
     * @param a kąt obrotu
     */
    void rotation(float a){
        un.setRotation(a);
        roleview.setRotation(a);
        portret.setRotation(a);
    }

    /**
     * zmiana koloru nazwy gracza na zielony
     */
    void color(){
        un.setTextColor(Color.GREEN);
    }

    /**
     * zmiana koloru nazwy gracza na czarny
     */
    void uncolor(){
        un.setTextColor(Color.BLACK);
    }

}
