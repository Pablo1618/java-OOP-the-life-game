import java.util.Random;
import java.awt.Color;

public abstract class Roslina extends Organizm {

    public Roslina() {
        super();
    }

    public Roslina(int sila, int x, int y, Swiat swiat) {
        super(swiat, sila, 0, x, y);
    }

    @Override
    public int getOstatniRuch() {
        return 0;
    }

    public abstract char getZnak();

    public abstract Color getKolor();

    public abstract String getNazwa();

    @Override
    public String zapiszInfo() {
        String result = getNazwa() + " " + getSila() + " " + getInicjatywa() + " " + getX() + " " + getY() + " "
                + getWiek();
        return result;
    }

    @Override
    public void akcja() {
        boolean czyRozsiano = false;
        int losowyRuch = new Random().nextInt(4);

        if (losowyRuch == 0 && swiatObiekt.getPoleMapy(x, y - 1) == null) { // góra
            if (y - 1 > 0) {
                Organizm org = urodzenieNowegoOrganizmu(x, y - 1, swiatObiekt);
                swiatObiekt.dodajNowyOrganizm(org);
                czyRozsiano = true;
            }
        } else if (losowyRuch == 1 && swiatObiekt.getPoleMapy(x, y + 1) == null) { // dół
            if (y + 1 < swiatObiekt.getWysokosc() + 1) {
                Organizm org = urodzenieNowegoOrganizmu(x, y + 1, swiatObiekt);
                swiatObiekt.dodajNowyOrganizm(org);
                czyRozsiano = true;
            }
        } else if (losowyRuch == 2 && swiatObiekt.getPoleMapy(x - 1, y) == null) { // lewo
            if (x - 1 > 0) {
                Organizm org = urodzenieNowegoOrganizmu(x - 1, y, swiatObiekt);
                swiatObiekt.dodajNowyOrganizm(org);
                czyRozsiano = true;
            }
        } else if (losowyRuch == 3 && swiatObiekt.getPoleMapy(x + 1, y) == null) { // prawo
            if (x + 1 < swiatObiekt.getSzerokosc() + 1) {
                Organizm org = urodzenieNowegoOrganizmu(x + 1, y, swiatObiekt);
                swiatObiekt.dodajNowyOrganizm(org);
                czyRozsiano = true;
            }
        }

        if (czyRozsiano) {
            swiatObiekt.nowyKomentarz("[ROZPRZESTRZENIENIE] " + getNazwa() + " ===> " + "[" + x + "," + y + "]");
        }
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        atakujacy.komentarzAtak(this);
        if (this.getSila() > atakujacy.getSila()) {
            atakujacy.smierc();
        } else {
            swiatObiekt.setPoleMapy(this.getX(), this.getY(), atakujacy);
            this.smierc();
            atakujacy.ustawOrganizmNaMapie();
        }
    }

    public boolean czyOrganizmToZwierze() {
        return false;
    }

    @Override
    public void komentarzAtak(Organizm atakowany) {
        swiatObiekt.nowyKomentarz("[SMIERTELNA ROSLINA] " + this.getNazwa() + " zabija " + atakowany.getNazwa());
    }

    @Override
    public void setPoprzedniRuch(char ruch) {
        //
    }

    @Override
    public void powrotNaPoprzedniePole() {
        //
    }
}
