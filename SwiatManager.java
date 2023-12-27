import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SwiatManager {
    private Swiat swiat;

    public SwiatManager() {};

    public SwiatManager(Swiat swiat) {
        this.swiat = swiat;
    }

    public void losowanieWspolrzednych(boolean[][] zajetePola, int szerokosc, int wysokosc, int[] losowyX,
            int[] losowyY) {
        Random rand = new Random();
        do {
            losowyX[0] = rand.nextInt(szerokosc);
            losowyY[0] = rand.nextInt(wysokosc);
        } while (zajetePola[losowyY[0]][losowyX[0]]); // losujemy az znajdziemy pole puste

        zajetePola[losowyY[0]][losowyX[0]] = true;
    }

    public void dodajOrganizmyDoNowegoSwiata(int ileZwierzatJednegoGatunku) {
        int[] losowyX = new int[1];
        int[] losowyY = new int[1];
        int szerokosc = swiat.getSzerokosc();
        int wysokosc = swiat.getWysokosc();

        // tworzymy dwuwymiarowa tablice na zapisanie ktore pola zostaly juz zajete
        boolean[][] zajetePola = new boolean[wysokosc][szerokosc];

        // spawnujemy zwierzeta i rosliny
        for (int i = 0; i < ileZwierzatJednegoGatunku; i++) {
            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm trawa = new Trawa(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(trawa);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm mlecz = new Mlecz(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(mlecz);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm guarana = new Guarana(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(guarana);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm wilczeJagody = new WilczeJagody(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(wilczeJagody);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm barszczSosnowskiego = new BarszczSosnowskiego(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(barszczSosnowskiego);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm owca = new Owca(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(owca);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm antylopa = new Antylopa(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(antylopa);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm zolw = new Zolw(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(zolw);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm wilk = new Wilk(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(wilk);

            losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
            Organizm lis = new Lis(losowyX[0], losowyY[0], swiat);
            swiat.dodajNowyOrganizm(lis);
        }

        // spawnujemy czlowieka
        losowanieWspolrzednych(zajetePola, szerokosc, wysokosc, losowyX, losowyY);
        Organizm gracz = new Czlowiek(losowyX[0], losowyY[0], swiat);
        swiat.dodajNowyOrganizm(gracz);
    }

    public void zapiszStanSwiata() {
        try {
            FileWriter zapisStanuSwiata = new FileWriter("stan_wirtualnego_swiata.txt");

            zapisStanuSwiata.write(swiat.getTura() + " " + swiat.getSzerokosc() + " " + swiat.getWysokosc() + " " + swiat.getLiczbaOrganizmow() + "\n");
            Organizm organizm = null;
            for (int g = 0; g < swiat.getLiczbaOrganizmow(); g++) {
                organizm = swiat.getOrganizm(g);
                zapisStanuSwiata.write(organizm.zapiszInfo() + "\n");
            }
            zapisStanuSwiata.close();
            System.out.println("Pomyslnie zapisano stan wirtualnego swiata do pliku 'stan_wirtualnego_swiata.txt'.");
        } catch (IOException e) {
            System.out.println("Blad podczas zapisywania stanu wirtualnego swiata do pliku 'stan_wirtualnego_swiata.txt");
        }
    }

    public boolean wczytajStanSwiata() {
        String nazwa;
        File plik = new File("stan_wirtualnego_swiata.txt");
        try {
            Scanner odczytPliku = new Scanner(plik);
            if (odczytPliku.hasNext()) {
                int szerokosc, wysokosc, liczbaOrganizmow, ktoraTura;
                ktoraTura = odczytPliku.nextInt();
                szerokosc = odczytPliku.nextInt();
                wysokosc = odczytPliku.nextInt();
                liczbaOrganizmow = odczytPliku.nextInt();
                swiat.setRozmiarPlanszy(szerokosc, wysokosc);
                swiat.setTura(ktoraTura);
                int sila, inicjatywa, x, y, wiek, umiejetnoscAktywowana, turyZUmiejetnoscia;
                boolean umiejetnoscZeWczytywania = false;
                for (int i = 0; i < liczbaOrganizmow; i++) {
                    nazwa = odczytPliku.next();

                    sila = odczytPliku.nextInt();
                    inicjatywa = odczytPliku.nextInt();
                    x = odczytPliku.nextInt();
                    y = odczytPliku.nextInt();
                    wiek = odczytPliku.nextInt();
                    if (nazwa.equals("Czlowiek")) {
                        umiejetnoscAktywowana = odczytPliku.nextInt();
                        turyZUmiejetnoscia = odczytPliku.nextInt();
                        Organizm wczytywanyOrganizm = new Czlowiek(x - 1, y - 1, swiat);
                        wczytywanyOrganizm.setInicjatywa(inicjatywa);
                        wczytywanyOrganizm.setWiek(wiek);
                        wczytywanyOrganizm.setSila(sila);
                        if (umiejetnoscAktywowana == 0) {
                            umiejetnoscZeWczytywania = false;
                        } else if (umiejetnoscAktywowana == 1) {
                            umiejetnoscZeWczytywania = true;
                        }
                        swiat.dodajNowyOrganizm(wczytywanyOrganizm);
                        swiat.getCzlowiek().setIleTurMineloUmiejetnosc(turyZUmiejetnoscia);
                        swiat.getCzlowiek().setCzyUmiejetnoscAktywowana(umiejetnoscZeWczytywania);
                    } else {
                        wczytywanieDanychOrganizmu(nazwa, sila, inicjatywa, x - 1, y - 1, wiek);
                    }
                }
                odczytPliku.close();
            } else {
                odczytPliku.close();
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    public boolean wczytywanieDanychOrganizmu(String nazwa, int sila, int ini, int x, int y, int wiek) {
        Organizm wczytywanyOrganizm = null;
        if (nazwa.equals("Lis")) {
            wczytywanyOrganizm = new Lis(x, y, swiat);
        } else if (nazwa.equals("Zolw")) {
            wczytywanyOrganizm = new Zolw(x, y, swiat);
        } else if (nazwa.equals("Wilk")) {
            wczytywanyOrganizm = new Wilk(x, y, swiat);
        } else if (nazwa.equals("Owca")) {
            wczytywanyOrganizm = new Owca(x, y, swiat);
        } else if (nazwa.equals("Antylopa")) {
            wczytywanyOrganizm = new Antylopa(x, y, swiat);
        } else if (nazwa.equals("Trawa")) {
            wczytywanyOrganizm = new Trawa(x, y, swiat);
        } else if (nazwa.equals("WilczeJagody")) {
            wczytywanyOrganizm = new WilczeJagody(x, y, swiat);
        } else if (nazwa.equals("Mlecz")) {
            wczytywanyOrganizm = new Mlecz(x, y, swiat);
        } else if (nazwa.equals("Guarana")) {
            wczytywanyOrganizm = new Guarana(x, y, swiat);
        } else if (nazwa.equals("BarszczSosnowskiego")) {
            wczytywanyOrganizm = new BarszczSosnowskiego(x, y, swiat);
        }
        wczytywanyOrganizm.setInicjatywa(ini);
        wczytywanyOrganizm.setWiek(wiek);
        wczytywanyOrganizm.setSila(sila);
        swiat.dodajNowyOrganizm(wczytywanyOrganizm);
        return true;
    }
}
