import java.awt.Color;

public class Wilk extends Zwierze {

    private static final Color kolorOrganizmu = new Color(150, 150, 150);
    private static final char znakOrganizmu = 'W';

    public Wilk() {
        this.sila = 9;
        this.inicjatywa = 5;
    }

    public Wilk(int x, int y, Swiat swiat) {
        super(9, 5, x, y, swiat);
    }

    @Override
    public void akcja() {
        super.akcja();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        super.kolizja(atakujacy);
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
        return "Wilk";
    }

    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Wilk(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
