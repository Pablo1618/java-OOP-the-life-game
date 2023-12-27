//import java.util.ArrayList;

public class Swiat {

    private int szerokosc;
    private int wysokosc;
    private int liczbaOrganizmowDoroslych;
    private boolean czyCzlowiekZyje;
    private Czlowiek czlowiek;
    private String[] komentarze;
    private int liczbaKomentarzy;
    private Organizm[][] mapa;
    private Organizm[] organizmy;
    private int liczbaOrganizmow;
    private int tura;

    public Swiat() {
        this(100, 100);
    }

    public Swiat(int szerokosc, int wysokosc) {
        
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.liczbaOrganizmowDoroslych = 0;
        this.czyCzlowiekZyje = false;
        this.czlowiek = null;
        this.komentarze = new String[500 * szerokosc * wysokosc];
        this.liczbaKomentarzy = 0;

        this.organizmy = new Organizm[szerokosc * wysokosc];

        this.liczbaOrganizmow = 0;
        this.tura = 0;
        this.mapa = new Organizm[wysokosc + 2][szerokosc + 2];


        for (int y = 0; y < wysokosc + 2; y++) {
            for (int x = 0; x < szerokosc + 2; x++) {
                mapa[y][x] = null;
            }
        }
    }

    public void setTura(int tura) {
        this.tura = tura;
    }

    public int getTura(int tura){
        return this.tura;
    }

    public void setPoleMapy(int x, int y, Organizm organizm) {
        mapa[y][x] = organizm;
    }

    public void setRozmiarPlanszy(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
    }

    public void setCzyCzlowiekZyje(boolean opcja) {
         this.czyCzlowiekZyje = opcja;
    }

    public void nowyKomentarz(String komentarz) {
        komentarze[liczbaKomentarzy] = komentarz;
        liczbaKomentarzy++;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public int getLiczbaOrganizmow() {
        return liczbaOrganizmow;
    }

    public int getTura() {
        return tura;
    }

    public Organizm getPoleMapy(int x, int y) {
        if (x > 0 && x < (szerokosc + 2) && y > 0 && y < (wysokosc + 2)) {
            return mapa[y][x];
        } else {
            return null;
        }
    }

    public Organizm getOrganizm(int i) {
        return organizmy[i];
    }

    public Organizm[][] getMapa() {
        return mapa;
    }

    public Czlowiek getCzlowiek() {
        return czlowiek;
    }

    public void dodajNowyOrganizm(Organizm nowyOrganizm) {
        if (mapa[nowyOrganizm.getY()][nowyOrganizm.getX()] == null && liczbaOrganizmow < szerokosc * wysokosc) {
            int i = 0;
            Organizm doPrzesuniecia;
            organizmy[liczbaOrganizmow] = nowyOrganizm;
            liczbaOrganizmow++;

            // szukamy pierwszego organizmu o mniejszej inicjatywie
            while (i < liczbaOrganizmow && organizmy[i].getInicjatywa() >= nowyOrganizm.getInicjatywa()) {
                i++;
            }

            // przesuwamy elementy w tablicy tak aby nowyOrganizm byl w odpowiednim miejscu
            int g = liczbaOrganizmow - 2;
            while (g >= i) {
                doPrzesuniecia = organizmy[g];
                organizmy[g] = organizmy[g + 1];
                organizmy[g + 1] = doPrzesuniecia;
                g--;
            }

            nowyOrganizm.ustawOrganizmNaMapie();
            if (nowyOrganizm.getNazwa().equals("Czlowiek")) {
                czyCzlowiekZyje = true;
                czlowiek = (Czlowiek) nowyOrganizm;
            }

            this.nowyKomentarz("[NOWY ORGANIZM] " + nowyOrganizm.getNazwa() + " ["
                    + Integer.toString(nowyOrganizm.getX()) + "," + Integer.toString(nowyOrganizm.getY()) + "]");
        } else if (liczbaOrganizmow >= szerokosc * wysokosc) {
            this.nowyKomentarz("Osiagnieto limit liczby organizmow.");
        }
    }

    public void usunOrganizm(Organizm organizmUsuwany) {
        if (mapa[organizmUsuwany.getY()][organizmUsuwany.getX()] == organizmUsuwany) {
            mapa[organizmUsuwany.getY()][organizmUsuwany.getX()] = null;
        }

        Organizm doPrzesuniecia;
        for (int i = 0; i < liczbaOrganizmow; i++) {
            if (organizmy[i] == organizmUsuwany) {
                organizmy[i] = null;
                int j = i;
                // przesuwamy organizmy w tablicy
                while (j < liczbaOrganizmow - 1) {
                    doPrzesuniecia = organizmy[j];
                    organizmy[j] = organizmy[j + 1];
                    organizmy[j + 1] = doPrzesuniecia;
                    j++;
                }
                i = liczbaOrganizmow;
            }
        }
        liczbaOrganizmow--;
        liczbaOrganizmowDoroslych--;

        this.nowyKomentarz("[SMIERC] " + organizmUsuwany.getNazwa() + "[" + Integer.toString(organizmUsuwany.getX()) + "," + Integer.toString(organizmUsuwany.getY()) + "]");
        organizmUsuwany = null;
    }

    public void wykonajTure(char znak) {
        boolean graczRuszylCzlowiekiem = false;
        liczbaOrganizmowDoroslych = liczbaOrganizmow;
        if (czyCzlowiekZyje) {
            if (!czlowiek.umiejetnoscWlaczona() && czlowiek.ileTurMineloUmiejetnosc() >= 5) {
                System.out.println("Umiejetnosc czlowieka dostepna - wcisnij 'U' aby aktywowac.");
            } else if (czlowiek.umiejetnoscWlaczona() && czlowiek.ileTurMineloUmiejetnosc() >= 5) {
                System.out.println("Umiejetnosc czlowieka zostala dezaktywowana (minelo 5 tur).");
                czlowiek.dezaktywujUmiejetnosc();
            }
            System.out.println("Oczekiwanie na ruch postaci... (strzalki)");
        }

        if (czyCzlowiekZyje) {

            if(znak == '0'){
                graczRuszylCzlowiekiem = true;
                czlowiek.setPoprzedniRuch('0');
            }
            else if (znak == 'u') {
                if (!czlowiek.umiejetnoscWlaczona() && czlowiek.ileTurMineloUmiejetnosc() >= 5) {
                    czlowiek.aktywujUmiejetnosc();
                    czlowiek.setPoprzedniRuch('0');
                    graczRuszylCzlowiekiem = true;
                }
            }
            else if (znak == 'w' || znak == 'a' || znak == 's' || znak == 'd') {
                    graczRuszylCzlowiekiem = true;
                    czlowiek.setPoprzedniRuch(znak);
                }
            }

        if (graczRuszylCzlowiekiem || !czyCzlowiekZyje) {
            symulacjaAkcjiOrganizmow();
        }

        wypiszKomentarze();
    }

    public void symulacjaAkcjiOrganizmow() {
        Organizm biezacyOrganizm;
        Organizm atakowanyOrganizm;
        for (int i = 0; i < liczbaOrganizmowDoroslych; i++) {
            biezacyOrganizm = organizmy[i];
            biezacyOrganizm.akcja();
            atakowanyOrganizm = mapa[biezacyOrganizm.getY()][biezacyOrganizm.getX()];
            if (atakowanyOrganizm == null || biezacyOrganizm.getOstatniRuch() == 0) {
                biezacyOrganizm.ustawOrganizmNaMapie();
            } else {
                atakowanyOrganizm.kolizja(biezacyOrganizm);
            }
        }

        if (czyCzlowiekZyje) {
            czlowiek.setIleTurMineloUmiejetnosc(czlowiek.ileTurMineloUmiejetnosc() + 1);
            if (czlowiek.umiejetnoscWlaczona() && czlowiek.ileTurMineloUmiejetnosc() < 5) {
                nowyKomentarz("[UMIEJETNOSC] Niesmiertelnosc gracza jest aktywna! Pozostalo: " + (5 - czlowiek.ileTurMineloUmiejetnosc()) + " tur.");
            }
        }

        // zwiekszanie wieku kazdego organizmu
        for (int i = 0; i < liczbaOrganizmowDoroslych; i++) {
            biezacyOrganizm = organizmy[i];
            biezacyOrganizm.setWiek(biezacyOrganizm.getWiek() + 1);
        }

        tura++;
    }

    public void wypiszKomentarze() {

        for (int i = liczbaKomentarzy; i > 0; i--) {
            if(i == wysokosc){
                break;
            }
            System.out.println(komentarze[i]);
        }
        liczbaKomentarzy = 0;

    }
}
