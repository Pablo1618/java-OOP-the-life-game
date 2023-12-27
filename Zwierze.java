
public abstract class Zwierze extends Organizm {

    final int GORA = 1;
    final int PRAWO = 2;
    final int DOL = 3;
    final int LEWO = 4;

    protected int ostatniRuch;

    public abstract void smierc();

    public abstract String getNazwa();

    public Zwierze() {
        super();
        this.ostatniRuch = 0;
    }

    public Zwierze(int sila, int inicjatywa, int x, int y, Swiat swiat) {
        super(swiat, sila, inicjatywa, x, y);
        this.ostatniRuch = 0;
    }

    public void akcja() {
        ruchZwierzecia(1, false);
    }

    public String zapiszInfo() {
        String result = getNazwa() + " " + Integer.toString(sila) + " " + Integer.toString(inicjatywa) + " " + Integer.toString(x) + " " + Integer.toString(y) + " " + Integer.toString(wiek);
        return result;
    }

    public void kolizja(Organizm atakujacy) {
        atakujacy.komentarzAtak(this);

        if (getNazwa().equals(atakujacy.getNazwa()) && atakujacy.getWiek() > 1) {
            rozmnazanie(atakujacy, swiatObiekt);
        } else if (getSila() <= atakujacy.getSila()) { // atakujacy wygrywa
            swiatObiekt.setPoleMapy(getX(), getY(), atakujacy);
            smierc();
            atakujacy.ustawOrganizmNaMapie();
        } else if (getSila() > atakujacy.getSila()) { // zaatakowany wygrywa
            atakujacy.smierc();
        }
    }

    public void setPoprzedniRuch(char ruch) {
        ostatniRuch = ruch;
    }

    public int getOstatniRuch() {
        return ostatniRuch;
    }

    public void powrotNaPoprzedniePole() {
        int X = getX() - 1; // -1 bo zamieniamy na indeksowanie od 0
        int Y = getY() - 1;
        if (getOstatniRuch() == GORA) {
            setPolozenie(X, Y + 1);
        } else if (getOstatniRuch() == PRAWO) {
            setPolozenie(X - 1, Y);
        } else if (getOstatniRuch() == DOL) {
            setPolozenie(X, Y - 1);
        } else if (getOstatniRuch() == LEWO) {
            setPolozenie(X + 1, Y);
        }
        ustawOrganizmNaMapie();
    }

    public boolean czyOrganizmToZwierze() {
        return true;
    }

    public boolean ucieczkaPrzedWalka(int odlegloscRuchu) { // np. dla antylopy
        swiatObiekt.setPoleMapy(x, y, null);
        int staryKoordynatX = x;
        int staryKoordynatY = y;
        if (y - odlegloscRuchu > 0 && swiatObiekt.getPoleMapy(x, y - odlegloscRuchu) == null) { // ucieczka do gory
            ostatniRuch = GORA;
            y--;
        } else if (y + odlegloscRuchu < swiatObiekt.getWysokosc() + 1
                && swiatObiekt.getPoleMapy(x, y + odlegloscRuchu) == null) { // ucieczka do dolu
            ostatniRuch = DOL;
            y++;
        } else if (x - odlegloscRuchu > 0 && swiatObiekt.getPoleMapy(x - odlegloscRuchu, y) == null) { // ucieczka w lewo
            ostatniRuch = LEWO;
            x--;
        } else if (x + odlegloscRuchu < swiatObiekt.getSzerokosc() + 1
                && swiatObiekt.getPoleMapy(x + odlegloscRuchu, y) == null) { // ucieczka w prawo
            ostatniRuch = PRAWO;
            x++;
        } else {
            ostatniRuch = 0;
        }

        if (ostatniRuch != 0) {
            swiatObiekt.nowyKomentarz("[UCIECZKA PRZED WALKA] " + this.getNazwa() + " [" + Integer.toString(staryKoordynatX) + "," + Integer.toString(staryKoordynatY) + "] ----> [" + Integer.toString(x) + "," + Integer.toString(y) + "]");
            return true;
        }
        return false;
    }

    public void ruchZwierzecia(int odlegloscRuchu, boolean czyDobryWech) {
        if (wiek > 0) {
            int staryKoordynatX = getX();
            int staryKoordynatY = getY();
            int losowyRuch = (int) (Math.random() * 4);
            String komentarz;
            if (losowyRuch == 0) { // gora
                if (y - odlegloscRuchu > 0) {
                    ostatniRuch = GORA;
                    y = y - odlegloscRuchu;
                } else {
                    ostatniRuch = DOL;
                    y = y + odlegloscRuchu;
                }
            } else if (losowyRuch == 1) { // dol
                if (y + odlegloscRuchu < swiatObiekt.getWysokosc() + 1) {
                    ostatniRuch = DOL;
                    y = y + odlegloscRuchu;
                } else {
                    ostatniRuch = GORA;
                    y = y - odlegloscRuchu;
                }
            } else if (losowyRuch == 2) { // lewo
                if (x - odlegloscRuchu > 0) {
                    ostatniRuch = LEWO;
                    x = x - odlegloscRuchu;
                } else {
                    ostatniRuch = PRAWO;
                    x = x + odlegloscRuchu;
                }
            } else if (losowyRuch == 3) { // prawo
                if (x + odlegloscRuchu < swiatObiekt.getSzerokosc() + 1) {
                    ostatniRuch = PRAWO;
                    x = x + odlegloscRuchu;
                } else {
                    ostatniRuch = LEWO;
                    x = x - odlegloscRuchu;
                }
            }

            if (czyDobryWech) {
                if (swiatObiekt.getPoleMapy(x, y) != null && swiatObiekt.getPoleMapy(x, y).getSila() > getSila()) {
                    x = staryKoordynatX;
                    y = staryKoordynatY;
                    ostatniRuch = 0;
                }
            }

            if (ostatniRuch != 0) {
                swiatObiekt.setPoleMapy(staryKoordynatX, staryKoordynatY, null);
                komentarz = "[PRZEMIESZCZENIE] " + getNazwa() + " [" + staryKoordynatX + "," + staryKoordynatY + "]" + " ----> " + "[" + x + "," + y + "]";
                swiatObiekt.nowyKomentarz(komentarz);
            }
        }
    }

    public void rozmnazanie(Organizm atakujacy, Swiat swiat) {
        int pozycjaRodzicaX = atakujacy.getX();
        int pozycjaRodzicaY = atakujacy.getY();

        atakujacy.powrotNaPoprzedniePole();

        if (swiat.getPoleMapy(pozycjaRodzicaX, pozycjaRodzicaY - 1) == null && pozycjaRodzicaY - 1 > 0) { // pole nad
            Organizm nowyOrganizm = this.urodzenieNowegoOrganizmu(pozycjaRodzicaX, pozycjaRodzicaY - 1, swiat);
            swiat.dodajNowyOrganizm(nowyOrganizm);
            return;
        } else if (swiat.getPoleMapy(pozycjaRodzicaX, pozycjaRodzicaY + 1) == null
                && pozycjaRodzicaY + 1 < swiatObiekt.getWysokosc() + 1) { // pole pod
            Organizm nowyOrganizm = this.urodzenieNowegoOrganizmu(pozycjaRodzicaX, pozycjaRodzicaY + 1, swiat);
            swiat.dodajNowyOrganizm(nowyOrganizm);
            return;
        } else if (swiat.getPoleMapy(pozycjaRodzicaX - 1, pozycjaRodzicaY) == null && pozycjaRodzicaX - 1 > 0) { //pole na lewo
            Organizm nowyOrganizm = this.urodzenieNowegoOrganizmu(pozycjaRodzicaX - 1, pozycjaRodzicaY, swiat);
            swiat.dodajNowyOrganizm(nowyOrganizm);
            return;
        } else if (swiat.getPoleMapy(pozycjaRodzicaX + 1, pozycjaRodzicaY) == null
                && pozycjaRodzicaX + 1 < swiatObiekt.getSzerokosc() + 1) { // pole na prawo
            Organizm nowyOrganizm = this.urodzenieNowegoOrganizmu(pozycjaRodzicaX + 1, pozycjaRodzicaY, swiat);
            swiat.dodajNowyOrganizm(nowyOrganizm);
        }
    }

    public void komentarzAtak(Organizm atakowany) {
        String komentarz = "[ATAK] " + this.getNazwa() + " zaatakowal " + atakowany.getNazwa();
        swiatObiekt.nowyKomentarz(komentarz);
    }

}