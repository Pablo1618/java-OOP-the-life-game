import java.util.Random;
import java.awt.Color;

public class Trawa extends Roslina {
    private static final Color kolorOrganizmu = new Color(150, 255, 156);
    private static final char znakOrganizmu = 'T';
    private static final int szansaRozsiania = 20;

    public Trawa() {
        // this.sila = 0;
        this.setSila(0);
    }

    public Trawa(int x, int y, Swiat swiat) {
        super(0, x, y, swiat);
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
        return "Trawa";
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int los = rand.nextInt(100);
        if (los < szansaRozsiania) {
            super.akcja();
        }
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        super.kolizja(atakujacy);
    }

    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Trawa(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
