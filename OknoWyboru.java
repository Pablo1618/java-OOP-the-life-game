import javax.swing.*;
import java.awt.*;
import java.awt.Font;

public class OknoWyboru extends JPanel {

    public OknoWyboru(Swiat swiat, int x, int y) {
        setLayout(new BorderLayout());

        // glowny duzy napis
        JLabel naglowek = new JLabel("Stwórz nowy organizm", JLabel.CENTER);
        naglowek.setFont(new Font("Arial", Font.BOLD, 20));
        add(naglowek, BorderLayout.NORTH);

        // glowny panel
        JPanel panelFormularza = new JPanel(new GridLayout());
        panelFormularza.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(panelFormularza, BorderLayout.CENTER);

        // przycisk na dole
        JButton utworzButton = new JButton("Dodaj nowy organizm");
        add(utworzButton, BorderLayout.SOUTH);

        // rozwijana lista z wszystkimi dostepnymi organizmami
        String[] opcje = { "Antylopa", "Żółw", "Wilk", "Lis", "Guarana", "Wilcze Jagody", "Barszcz Sosnowskiego", "Mlecz", "Trawa" };
        JComboBox<String> listaRozwijana = new JComboBox<>(opcje);
        panelFormularza.add(listaRozwijana);

        utworzButton.addActionListener(e -> {

            String wybranaOpcja = (String) listaRozwijana.getSelectedItem();
            Organizm organizm = null;

            if (wybranaOpcja.equals("Antylopa")) {
                organizm = new Antylopa(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Żółw")) {
                organizm = new Zolw(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Wilk")) {
                organizm = new Wilk(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Lis")) {
                organizm = new Lis(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Guarana")) {
                organizm = new Guarana(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Wilcze Jagody")) {
                organizm = new WilczeJagody(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Barszcz Sosnowskiego")) {
                organizm = new BarszczSosnowskiego(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Mlecz")) {
                organizm = new Mlecz(x - 1, y - 1, swiat);
            } else if (wybranaOpcja.equals("Trawa")) {
                organizm = new Trawa(x - 1, y - 1, swiat);
            }

            if (organizm != null && swiat.getPoleMapy(x, y) == null) {
                swiat.dodajNowyOrganizm(organizm);
            }
            

            SwingUtilities.getWindowAncestor(this).dispose();

        });

        JFrame frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
