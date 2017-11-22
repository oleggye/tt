package by.bsac.timetable.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutForm extends JDialog {

  private static final long serialVersionUID = 1L;

  /**
   * Create the dialog.
   */
  public AboutForm() {
    setResizable(false);
    setModal(true);
    setBounds(100, 100, 432, 180);
    getContentPane().setLayout(null);

    JPanel panel = new JPanel();
    panel.setBounds(23, 20, 140, 104);
    panel
        .add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/logo.png"))));
    getContentPane().add(panel);

    JLabel label = new JLabel(
        "\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0443 \u0440\u0430\u0437\u0440\u0430\u0431\u043E\u0442\u0430\u043B");
    label.setForeground(Color.MAGENTA);
    label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    label.setBounds(248, 23, 142, 31);
    getContentPane().add(label);

    JLabel label_1 = new JLabel(
        "\u0421\u0442\u0443\u0434\u0435\u043D\u0442 \u0433\u0440\u0443\u043F\u043F\u044B \u0421\u041F-442");
    label_1.setForeground(Color.GREEN);
    label_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    label_1.setBounds(239, 70, 162, 14);
    getContentPane().add(label_1);

    JLabel label_2 = new JLabel(
        "\u041C\u0430\u043A\u0441\u0438\u043C\u043E\u0432\u0438\u0447 \u041E\u043B\u0435\u0433 \u0410\u043B\u0435\u043A\u0441\u0430\u043D\u0434\u0440\u043E\u0432\u0438\u0447");
    label_2.setForeground(SystemColor.textHighlight);
    label_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    label_2.setBounds(183, 97, 253, 37);
    getContentPane().add(label_2);
  }
}
