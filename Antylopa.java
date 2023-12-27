import java.util.Random;
import java.awt.Color;

public class Antylopa extends Zwierze {
    private static final Color kolorOrganizmu = new Color(255, 213, 77);
    private char znakOrganizmu = 'A';

    public Antylopa() {
        this.sila = 4;
        this.inicjatywa = 4;
    }

    public Antylopa(int x, int y, Swiat swiat) {
        super(4, 4, x, y, swiat);
    }

    @Override
    public void setPoprzedniRuch(char ruch) {
        super.setPoprzedniRuch(ruch);
    }

    @Override
    public int getOstatniRuch() {
        return super.getOstatniRuch();
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
        return "Antylopa";
    }

    @Override
    public void akcja() {
        swiatObiekt.setPoleMapy(this.x, this.y, null);
        ruchZwierzecia(2, false);
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        if (!atakujacy.getNazwa().equals("Antylopa")) {
            Random rand = new Random();
            int losowanieCzyUcieczka = rand.nextInt(2);
            if (losowanieCzyUcieczka == 1) {
                if (ucieczkaPrzedWalka(2)) {
                    return;
                }
            }
        }
        super.kolizja(atakujacy);
    }

    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Antylopa(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
