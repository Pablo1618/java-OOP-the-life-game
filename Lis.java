import java.awt.Color;

public class Lis extends Zwierze {

    private static final Color kolorOrganizmu = new Color(255, 139, 31);
    private char znakOrganizmu = 'L';
    
    public Lis() {
        this.sila = 3;
        this.inicjatywa = 7;
    }
    
    public Lis(int x, int y, Swiat swiat) {
        super(3, 7, x, y, swiat);
    }
    
    @Override
    public void akcja() {
        super.ruchZwierzecia(1, true);
    }
    
    @Override
    public void kolizja(Organizm atakujacy) {
        super.kolizja(atakujacy);
    }
    
    @Override
    public Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat) {
        x--;
        y--;
        return new Lis(x, y, swiat);
    }
    
    @Override
    public String getNazwa() {
        return "Lis";
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
    public int getOstatniRuch() {
        return super.getOstatniRuch();
    }
    
    @Override
    public void setPoprzedniRuch(char ruch) {
        super.setPoprzedniRuch(ruch);
    }
    
    @Override
    public void smierc() {
        getSwiat().usunOrganizm(this);
    }
}
