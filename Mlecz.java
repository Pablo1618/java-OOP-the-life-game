import java.util.Random;
import java.awt.Color;

public class Mlecz extends Roslina {
    private static final Color kolorOrganizmu = new Color(240, 255, 23);
    private static final char znakOrganizmu = 'M';
    private static final int szansaRozsiania = 10;

    public Mlecz() {
        this.sila = 0;
    }

    public Mlecz(int x, int y, Swiat swiat) {
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
        return "Mlecz";
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; i++) {
            int losoweRozprzestrzenienie = new Random().nextInt(100);
            if (losoweRozprzestrzenienie < szansaRozsiania) {
                super.akcja();
            }
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
        return new Mlecz(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}