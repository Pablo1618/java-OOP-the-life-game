import java.awt.Color;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int x;
    protected int y;
    protected Swiat swiatObiekt;
    protected int wiek;

    public abstract Organizm urodzenieNowegoOrganizmu(int x, int y, Swiat swiat);

    public abstract void setPoprzedniRuch(char ruch);

    public abstract void smierc();

    public abstract int getOstatniRuch();

    public abstract char getZnak();

    public abstract Color getKolor();

    public abstract String getNazwa();

    public abstract String zapiszInfo();

    public abstract void akcja();

    public abstract void kolizja(Organizm atakujacy);

    public abstract void powrotNaPoprzedniePole();

    public abstract boolean czyOrganizmToZwierze();

    public abstract void komentarzAtak(Organizm atakowany);

    public Organizm() {
        this.sila = 0;
        this.inicjatywa = 0;
        this.x = 0;
        this.y = 0;
        this.swiatObiekt = null;
        this.wiek = 0;
    }

    public Organizm(Swiat swiat, int sila, int inicjatywa, int x, int y) {
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.x = x + 1;
        this.y = y + 1;
        this.swiatObiekt = swiat;
        this.wiek = 0;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setInicjatywa(int ini) {
        this.inicjatywa = ini;
    }

    public void setPolozenie(int x, int y) {
        this.x = x + 1;
        this.y = y + 1;
    }

    public void setSwiat(Swiat swiat) {
        swiatObiekt = swiat;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSila() {
        return this.sila;
    }

    public int getInicjatywa() {
        return this.inicjatywa;
    }

    public int getWiek() {
        return this.wiek;
    }

    public Swiat getSwiat() {
        return swiatObiekt;
    }

    public void ustawOrganizmNaMapie() {
        swiatObiekt.setPoleMapy(this.x, this.y, this);
    }
}
