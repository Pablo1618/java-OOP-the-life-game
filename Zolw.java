import java.util.Random;
import java.awt.Color;

public class Zolw extends Zwierze {
    private static final Color kolorOrganizmu = new Color(180, 194, 138);
    private final char znakOrganizmu = 'Z';

    public Zolw() {
        this.sila = 2;
        this.inicjatywa = 1;
    }
    
    public Zolw(int x, int y, Swiat swiat) {
        super(2, 1, x, y, swiat);
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
    public char getZnak() {
        return znakOrganizmu;
    }

    @Override
    public Color getKolor() {
        return kolorOrganizmu;
    }
    
    @Override
    public String getNazwa() {
        return "Zolw";
    }
    
    @Override
    public void akcja() {
        int losowyRuch = new Random().nextInt(4);
        if (losowyRuch < 2) { // 25% szansy na ruch
            super.akcja();
        } else {
            ostatniRuch = 0;
        }
    }
    
    @Override
    public void kolizja(Organizm atakujacy) {
        if (atakujacy.getSila() < 5 && !atakujacy.getNazwa().equals("Zolw")) {
            atakujacy.powrotNaPoprzedniePole();
        } else {
            super.kolizja(atakujacy);
        }    
    }
    
    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Zolw(x, y, swiat);
    }
    
    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}    