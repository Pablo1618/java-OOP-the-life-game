import java.util.Random;
import java.awt.Color;

public class Czlowiek extends Zwierze {
    private static final Color kolorOrganizmu = new Color(18, 200, 255);
    private final char znakOrganizmu = 'C';
    private boolean umiejetnoscJestAktywowana = false;
    private int turyUmiejetnosc = 0;

    private final int GORA = 1;
    private final int PRAWO = 2;
    private final int DOL = 3;
    private final int LEWO = 4;

    public Czlowiek(int x, int y, Swiat swiat) {
        super(5, 44, x, y, swiat);
        this.turyUmiejetnosc = 5;
        this.umiejetnoscJestAktywowana = false;
    }

    @Override
    public void setPoprzedniRuch(char ruch) {
        if (ruch == 'w') {
            ostatniRuch = GORA;
        } else if (ruch == 'd') {
            ostatniRuch = PRAWO;
        } else if (ruch == 's') {
            ostatniRuch = DOL;
        } else if (ruch == 'a') {
            ostatniRuch = LEWO;
        } else if (ruch == '0'){
            ostatniRuch = '0';
        }
    }

    public void setCzyUmiejetnoscAktywowana(boolean umiejetnoscAktywowana) {
        this.umiejetnoscJestAktywowana = umiejetnoscAktywowana;
    }

    public void setIleTurMineloUmiejetnosc(int tury) {
        this.turyUmiejetnosc = tury;
    }

    @Override
    public int getOstatniRuch() {
        return super.getOstatniRuch();
    }

    public void uzyjUmiejetnosci(boolean rekurencja) {
        if (!rekurencja) {
            swiatObiekt.setPoleMapy(x, y, null);
        }
        int staryKoordynatX = x;
        int staryKoordynatY = y;
        int losowyRuch = new Random().nextInt(4);

        if (losowyRuch == 0) { // gora
            if (y - 1 > 0) {
                ostatniRuch = GORA;
                y--;
            } else {
                ostatniRuch = DOL;
                y++;
            }
        } else if (losowyRuch == 1) { // dol
            if (y + 1 < swiatObiekt.getWysokosc() + 1) {
                ostatniRuch = DOL;
                y++;
            } else {
                ostatniRuch = GORA;
                y--;
            }
        } else if (losowyRuch == 2) { // lewo
            if (x - 1 > 0) {
                ostatniRuch = LEWO;
                x--;
            } else {
                ostatniRuch = PRAWO;
                x++;
            }
        } else if (losowyRuch == 3) { // prawo
            if (x + 1 < swiatObiekt.getSzerokosc() + 1) {
                ostatniRuch = PRAWO;
                x++;
            } else {
                ostatniRuch = LEWO;
                x--;
            }
        }

        if ((swiatObiekt.getPoleMapy(x + 1, y) != null && swiatObiekt.getPoleMapy(x + 1, y).getZnak() == 'B')
                || (swiatObiekt.getPoleMapy(x - 1, y) != null && swiatObiekt.getPoleMapy(x - 1, y).getZnak() == 'B')
                || (swiatObiekt.getPoleMapy(x, y + 1) != null && swiatObiekt.getPoleMapy(x, y + 1).getZnak() == 'B')
                || (swiatObiekt.getPoleMapy(x, y - 1) != null && swiatObiekt.getPoleMapy(x, y - 1).getZnak() == 'B')) {
            uzyjUmiejetnosci(true);
            return;
        }
        if (swiatObiekt.getPoleMapy(x, y) != null && swiatObiekt.getPoleMapy(x, y).getZnak() == 'J') {
            uzyjUmiejetnosci(true);
            return;
        }
        if (swiatObiekt.getPoleMapy(x, y) != null) {
            kolizja(swiatObiekt.getPoleMapy(x, y));
        }

        swiatObiekt.setPoleMapy(x, y, this);

        if (ostatniRuch != 0) {
            swiatObiekt.nowyKomentarz("[NIESMIERTELNOSC] " + this.getNazwa() + " [" + Integer.toString(staryKoordynatX)
                    + "," + Integer.toString(staryKoordynatY) + "] ----> [" + Integer.toString(x) + ","
                    + Integer.toString(y) + "]");
        }
    }

    public boolean umiejetnoscWlaczona() {
        return umiejetnoscJestAktywowana;
    }

    public int ileTurMineloUmiejetnosc() {
        return turyUmiejetnosc;
    }

    @Override
    public Color getKolor() {
        return kolorOrganizmu;
    }

    @Override
    public char getZnak() {
        return znakOrganizmu;
    }

    @Override
    public String getNazwa() {
        return "Czlowiek";
    }

    @Override
    public String zapiszInfo() { // do zapisu stanu gry
        
        int czyUmiejetnoscJestWlaczona = umiejetnoscJestAktywowana ? 1 : 0;

        String result = this.getNazwa() + " " + Integer.toString(sila) + " " + Integer.toString(inicjatywa) + " "
                + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(wiek) + " "
                + czyUmiejetnoscJestWlaczona + " " + Integer.toString(turyUmiejetnosc);
        return result;
    }

    @Override
    public void akcja() {
        String komentarz;
        int staryKoordynatX = x;
        int staryKoordynatY = y;

        swiatObiekt.setPoleMapy(x, y, null);
        if (ostatniRuch == GORA && y - 1 > 0) {
            y -= 1;
        } else if (ostatniRuch == DOL && y != swiatObiekt.getWysokosc()) {
            y += 1;
        } else if (ostatniRuch == LEWO && x - 1 > 0) {
            x -= 1;
        } else if (ostatniRuch == PRAWO && x != swiatObiekt.getSzerokosc()) {
            x += 1;
        } else {
            //nic nie robimy - czlowiek sie nie ruszyl
        }

        komentarz = "[RUCH CZLOWIEKA] [" + Integer.toString(staryKoordynatX) + "," + Integer.toString(staryKoordynatY) + "] ----> [" + Integer.toString(x) + ", " + Integer.toString(y) + "]";
        swiatObiekt.nowyKomentarz(komentarz);
    }

    public void kolizja(Organizm atakujacy) {
        super.kolizja(atakujacy);
    }

    public void aktywujUmiejetnosc() {
        umiejetnoscJestAktywowana = true;
        turyUmiejetnosc = 0;
        System.out.println("[UMIEJETNOSC] Niesmiertelnosc na 5 tur zostala aktywowana!");
    }

    public void dezaktywujUmiejetnosc() {
        umiejetnoscJestAktywowana = false;
        turyUmiejetnosc = 0;
    }

    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Czlowiek(x, y, swiat);
    }

    public void smierc() {
        if (umiejetnoscWlaczona()) {
            uzyjUmiejetnosci(false);
        } else {
            swiatObiekt.setCzyCzlowiekZyje(false);
            getSwiat().usunOrganizm(this);
        }
    }

}