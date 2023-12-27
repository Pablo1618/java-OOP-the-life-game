import javax.swing.*;
import java.awt.*;

public class OknoPoczatkowe extends JPanel {

    private JTextField wysokoscPole, szerokoscPole;
    private final JButton utworzButton = new JButton("Utwórz nową grę");
    private final JButton zaladujButton = new JButton("Załaduj grę z zapisu");

    public OknoPoczatkowe() {
        setLayout(new BorderLayout());

        // glowny napis menu
        JLabel naglowek = new JLabel("Symulator wirtualnego świata", JLabel.CENTER);
        naglowek.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        naglowek.setFont(new Font("Arial", Font.BOLD, 20));
        add(naglowek, BorderLayout.NORTH);

        // pola tekstowe do wpisania szerokosci i wysokosci
        JPanel panelFormularza = new JPanel();
        panelFormularza.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelFormularza.add(new JLabel("Wysokość:", JLabel.RIGHT));
        wysokoscPole = new JTextField(10);
        panelFormularza.add(wysokoscPole);

        panelFormularza.add(new JLabel("Szerokość:", JLabel.RIGHT));
        szerokoscPole = new JTextField(10);
        panelFormularza.add(szerokoscPole);

        add(panelFormularza, BorderLayout.CENTER);

        // przycisk utworz gre
        utworzButton.addActionListener(e -> utworzSwiat());
        utworzButton.setBackground(new Color(255, 183, 3));
        utworzButton.setForeground(Color.WHITE);
        utworzButton.setFont(new Font("Arial", Font.BOLD, 16));
        utworzButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // przycisk zaladuj gre
        zaladujButton.addActionListener(e -> zaladujSwiat());
        zaladujButton.setBackground(new Color(163, 200, 255));
        zaladujButton.setForeground(Color.WHITE);
        zaladujButton.setFont(new Font("Arial", Font.BOLD, 16));
        zaladujButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelPrzyciskow = new JPanel(new GridLayout());
        panelPrzyciskow.add(utworzButton);
        panelPrzyciskow.add(zaladujButton);
        add(panelPrzyciskow, BorderLayout.SOUTH);
    }

    private void utworzSwiat() {
        int wysokosc = Integer.parseInt(wysokoscPole.getText());
        int szerokosc = Integer.parseInt(szerokoscPole.getText());

        Swiat swiat = new Swiat(szerokosc, wysokosc);
        SwiatManager manager = new SwiatManager(swiat);

        int liczbaZwierzatJednegoGatunku = (szerokosc * wysokosc) / 30; // na poczatku 30% mapy bedzie zapelnione organizmami
        manager.dodajOrganizmyDoNowegoSwiata(liczbaZwierzatJednegoGatunku);

        swiat.wypiszKomentarze();
        new OknoGry(swiat, manager, swiat.getMapa());
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    private void zaladujSwiat() {
        Swiat swiat = new Swiat();
        SwiatManager manager = new SwiatManager(swiat);
        manager.wczytajStanSwiata();
        new OknoGry(swiat, manager, swiat.getMapa());
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wirtualny Świat - Paweł Wawrzyński 193270");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new OknoPoczatkowe());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
