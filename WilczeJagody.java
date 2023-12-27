import java.util.Random;
import java.awt.Color;

public class WilczeJagody extends Roslina {
    private static final Color kolorOrganizmu = new Color(156, 0, 16);
    private final char znakOrganizmu = 'J';
    private static final int szansaRozsiania = 10;

    public WilczeJagody() {
        this.sila = 99;
    }

    public WilczeJagody(int x, int y, Swiat swiat) {
        super(99, x, y, swiat);
    }

    @Override
    public char getZnak() {
        return znakOrganizmu;
    }

    @Override
    public Color getKolor() {
        return kolorOrganizmu;
    }

    @Override
    public String getNazwa() {
        return "WilczeJagody";
    }

    @Override
    public void akcja() {
        int los = new Random().nextInt(100);
        if (los < szansaRozsiania) {
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
        return new WilczeJagody(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
