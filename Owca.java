import java.awt.Color;

public class Owca extends Zwierze {

    private static final Color kolorOrganizmu = new Color(212, 205, 209);
    private final char znakOrganizmu = 'O';

    public Owca() {
        super();
        this.sila = 4;
        this.inicjatywa = 4;
    }

    public Owca(int x, int y, Swiat swiat) {
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
        return this.znakOrganizmu;
    }

    @Override
    public String getNazwa() {
        return "Owca";
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
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Owca(x, y, swiat);
    }

    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
