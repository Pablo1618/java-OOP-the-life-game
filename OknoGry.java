import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class OknoGry extends JFrame {

    private JPanel[][] kwadraty;

    private void aktualizujPanel(JPanel panelKwadraty, int wiersze, int kolumny, Organizm[][] tablica) {
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                ((OknoGry.KwadratPanel) kwadraty[i][j]).ustawKolorNaPodstawieOrganizmu(tablica[i][j]);
            }
        }
        panelKwadraty.revalidate();
        panelKwadraty.repaint();
    }

    public OknoGry(Swiat swiat, SwiatManager manager, Organizm[][] tablica) {
        setTitle("Wirtualny swiat - Paweł Wawrzyński 193270");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //konsola boczna
        KonsolaBoczna komunikaty = new KonsolaBoczna();

        int wiersze = swiat.getWysokosc() + 2;
        int kolumny = swiat.getSzerokosc() + 2;

        kwadraty = new JPanel[wiersze][kolumny];

        // tworzymy wielki panel na kwadraty (organizmy)
        JPanel panelNaKwadraty = new JPanel(new GridLayout(wiersze, kolumny));
        panelNaKwadraty.setPreferredSize(new Dimension(swiat.getSzerokosc() * 35, swiat.getWysokosc() * 35));
        panelNaKwadraty.setFocusable(true);
        panelNaKwadraty.requestFocusInWindow();
        // dodajemy kwadraty do panelu i dodajemy do nich obsluge klikniecia myszką
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                if (i != 0 && i != wiersze - 1 && j != 0 && j != kolumny - 1) {
                    kwadraty[i][j] = new KwadratPanel(true);
                    kwadraty[i][j].requestFocusInWindow();
                    int wspolrzednaX = j;
                    int wspolrzednaY = i;
                    kwadraty[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (e.getButton() == 1 && swiat.getPoleMapy(wspolrzednaX, wspolrzednaY) == null) { // Button = 1 czyli lewy przycisk myszy

                                new OknoWyboru(swiat, wspolrzednaX, wspolrzednaY);
                            }
                        }
                    });
                } else {
                    kwadraty[i][j] = new KwadratPanel(false);
                }
                ((OknoGry.KwadratPanel) kwadraty[i][j]).ustawKolorNaPodstawieOrganizmu(tablica[i][j]);
                panelNaKwadraty.add(kwadraty[i][j]);
            }
        }

        panelNaKwadraty.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                komunikaty.setText(" ");
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    swiat.wykonajTure('u');
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    swiat.wykonajTure('w');
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    swiat.wykonajTure('a');
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    swiat.wykonajTure('s');
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    swiat.wykonajTure('d');
                }

                aktualizujPanel(panelNaKwadraty, wiersze, kolumny, tablica);
            }
        });

        // przycisk do zapisu gry
        JButton button2 = new JButton("Zapisz grę");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.zapiszStanSwiata();
            }
        });
        button2.setBackground(new Color(77, 145, 255));
        button2.setForeground(Color.WHITE);
        button2.setFont(new Font("Arial", Font.BOLD, 16));

        // przycisk do nastepnej tury
        JButton button = new JButton("Następna tura");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                swiat.wykonajTure('0');
                aktualizujPanel(panelNaKwadraty, wiersze, kolumny, tablica);
            }
        });
        button.setBackground(new Color(255, 183, 3));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelPrzycisk = new JPanel();
        panelPrzycisk.add(button);
        panelPrzycisk.add(button2);


        // tworzymy panel glowny i dodajemy skladowe panele
        JPanel panelGlowny = new JPanel(new BorderLayout());
        panelGlowny.add(panelNaKwadraty, BorderLayout.CENTER);
        panelGlowny.add(panelPrzycisk, BorderLayout.SOUTH);
        panelGlowny.add(komunikaty, BorderLayout.WEST);

        add(panelGlowny);
        // panelGlowny.requestFocusInWindow();

        pack();
        setLocationRelativeTo(null); // aby okienko bylo wycentrowane na ekranie
        setVisible(true);
    }

    private class KonsolaBoczna extends JTextArea{

        public KonsolaBoczna() {
            setEditable(false);
            setPreferredSize(new Dimension(400, 500));        

            JScrollPane scrollPane = new JScrollPane(this);

            // Zmieniamy strumień wyjściowy System.out na PrintStream, który będzie
            // wypisywał do JTextArea
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    append(String.valueOf((char) b));
                    setCaretPosition(getDocument().getLength());
                }
            }));

            // Dodajemy JScrollPane do ramki
            getContentPane().add(scrollPane);
            setFocusable(false);
        }
    }

    private class KwadratPanel extends JPanel {

        public KwadratPanel(boolean obramowanie) {
            setLayout(new BorderLayout()); // ustawienie layoutu panelu na BorderLayout
            if (obramowanie) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.WHITE));
            }
        }

        public void ustawKolorNaPodstawieOrganizmu(Organizm organizm) {

            if (organizm != null) {
                // removeAll() potrzebne bo np. na miejscu organizmu moze byc w kolejnej turze
                // juz inny organizm i trzeba usunac stara litere
                removeAll();
                setBackground(organizm.getKolor());
                JLabel label = new JLabel(Character.toString(organizm.getZnak()), SwingConstants.CENTER);
                add(label, BorderLayout.CENTER);
            } else {
                // removeAll() jest konieczne, aby po przemieszczeniu sie zwierzecia usuwac
                // stare litery z kwadratu
                removeAll();
                setBackground(Color.WHITE);
            }
        }
    }
}