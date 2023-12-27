import java.util.Random;
import java.awt.Color;

public class Guarana extends Roslina {
    private static final Color kolorOrganizmu = new Color(34, 255, 0);
    private final char znakOrganizmu = 'G';
    private static final int szansaRozsiania = 10;

    public Guarana() {
        this.sila = 0;
    }

    public Guarana(int x, int y, Swiat swiat) {
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
        return "Guarana";
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
        atakujacy.setSila(atakujacy.getSila() + 3);
        swiatObiekt.nowyKomentarz("[GUARANA] Dodano 3 punkty sily dla organizmu ====> " + atakujacy.getNazwa() + " ["
                + atakujacy.getX() + "," + atakujacy.getY() + "]");
        swiatObiekt.setPoleMapy(this.getX(), this.getY(), atakujacy);
        this.smierc();
        atakujacy.ustawOrganizmNaMapie();
    }

    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Guarana(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }

}