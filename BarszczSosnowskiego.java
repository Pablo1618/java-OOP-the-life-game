import java.util.Random;
import java.awt.Color;

public class BarszczSosnowskiego extends Roslina {
    private static final Color kolorOrganizmu = new Color(255, 0, 47);
    private final char znakOrganizmu = 'B';
    private static final int szansaRozsiania = 5;

    public BarszczSosnowskiego() {
        super();
        this.sila = 10;
    }

    public BarszczSosnowskiego(int x, int y, Swiat swiat) {
        super(10, x, y, swiat);
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
        return "BarszczSosnowskiego";
    }

    @Override
    public void akcja() {
        Organizm[] organizm = new Organizm[8];
        int[][] pozycja = { { 0,-1 }, { 1,0 }, { 0,1 }, { -1,0 }, { 1,1}, {-1,-1}, {-1,1}, {1,-1} };
        for (int i = 0; i < 8; i++) {
            organizm[i] = swiatObiekt.getPoleMapy(x + pozycja[i][0], y + pozycja[i][1]);
            if (organizm[i] != null && organizm[i].czyOrganizmToZwierze()) {
                this.komentarzAtak(organizm[i]);
                organizm[i].smierc();
            }
        }

        int losoweRozprzestrzenienie = new Random().nextInt(100);
        if (losoweRozprzestrzenienie < szansaRozsiania) {
            super.akcja();
        }
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        atakujacy.komentarzAtak(this);
        atakujacy.smierc();
    }

    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new BarszczSosnowskiego(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
