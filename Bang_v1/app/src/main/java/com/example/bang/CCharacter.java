package com.example.bang;

import android.widget.ImageView;

/**
 * postacie: opis i umiejętności
 */
abstract public class CCharacter {
    /** opis umijetnosci postaci*/
    public String opis;
    /** nazwa postaci*/
    public String name;
    /** wizerunek postaci - zdjęcie ImageView*/
    public ImageView portret;
    /** rola postaci - zdjęcie ImageView*/
    public ImageView roleview;
    /** bazowe życie postaci*/
    public int basic;
    /** punkty życia */
    public int hp;
    /** id charakteru */
    public int char_id;

    /**
     * przypisane paramtrów postaci graczowi
     * @param a wylosowane id postaci
     */
    void character(int a){
        char_id=a;
        switch (a){
            case 1:
                name="PAUL REGRET";
                opis="Nigdy nie tracisz punktu życia przez Gatlinga";
                portret.setImageResource(R.drawable.paul_regret);
                basic=9;
                hp=basic;
                break;
            case 2:
                name="BLACK JACK";
                opis="Możesz przerzucić dynamit (chyba że wylosowałeś na raz 3)";
                portret.setImageResource(R.drawable.jack_black);
                basic=8;
                hp=basic;
                break;
            case 3:
                name="LUCKY DUKE";
                opis="Możesz wykonać jeden dodatkowy przerzut";
                portret.setImageResource(R.drawable.lucky_duke);
                basic=8;
                hp=basic;
                break;
            case 4:
                name="JOURDONNAIS";
                opis="Nigdy nie tracisz więcej niż jednego punktu życia przez Indian";
                portret.setImageResource(R.drawable.jourdonnais);
                basic=7;
                hp=basic;
                break;
            case 5:
                name="CALAMITY JANET";
                opis="Możesz używać między oczy 1 jako 2 i na odwrót";
                portret.setImageResource(R.drawable.calamity_janet);
                basic=8;
                hp=basic;
                break;
            case 6:
                name="JASSE JONES";
                opis="Gdy masz 4 lub mniej punkty życia, otrzymujesz 2 punkty życia za każde piwo" +
                        " które użyjesz na sobie";
                portret.setImageResource(R.drawable.jasse_jones);
                basic=9;
                hp=basic;
                break;
            case 7:
                name="WILLY THE KID";
                opis="Potrzebujesz tylko dwa gatlingi aby uruchomić efekt";
                portret.setImageResource(R.drawable.willy_the_kid);
                basic=8;
                hp=basic;
                break;
            case 8:
                name="SUZY LAFAYETTE";
                opis="Jeśli nie wyrzuciłeś między oczy 1 i 2, zyskujesz 2 pkt życia";
                portret.setImageResource(R.drawable.suzy_lafayette);
                basic=8;
                hp=basic;
                break;
        }
    }
}
