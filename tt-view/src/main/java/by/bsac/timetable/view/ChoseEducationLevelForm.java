package by.bsac.timetable.view;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ChoseEducationLevelForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final String FONT_CONSTANT = "Times New Roman";

  private byte educationLevel = 1;

  /**
   * Create the dialog.
   */
  public ChoseEducationLevelForm() {
    setResizable(false);
    setModal(true);
    setForeground(Color.WHITE);
    getContentPane().setForeground(Color.WHITE);

    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(null);

    JLabel label = new JLabel("Выберите уровень образования");
    label.setForeground(Color.RED);
    label.setFont(new Font(FONT_CONSTANT, Font.PLAIN, 16));
    label.setBounds(25, 11, 225, 40);
    panel.add(label);

    JButton highEducationBtn = new JButton("Высшая ступень");
    highEducationBtn.setFont(new Font(FONT_CONSTANT, Font.PLAIN, 16));
    highEducationBtn.setBounds(45, 55, 187, 32);
    highEducationBtn.addActionListener(e -> {
      setEduLevel((byte) 1); // ������ ��
      dispose();
    });

    panel.add(highEducationBtn);

    JButton middleEducationBtn = new JButton("Среднее специальное");
    middleEducationBtn.setFont(new Font(FONT_CONSTANT, Font.PLAIN, 16));
    middleEducationBtn.setBounds(45, 100, 187, 32);
    middleEducationBtn.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setEduLevel((byte) 2); // ������ ���
        dispose();
      }
    });

    panel.add(middleEducationBtn);

    JButton magistrationBtn = new JButton("Магистратура");
    magistrationBtn.setFont(new Font(FONT_CONSTANT, Font.PLAIN, 16));
    magistrationBtn.setBounds(45, 145, 187, 32);
    magistrationBtn.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setEduLevel((byte) 3); // ������ ��
        dispose();
      }
    });
    panel.add(magistrationBtn);

    setBounds(100, 100, 278, 224);
  }

  public byte getEduLevel() {
    return this.educationLevel;
  }

  private void setEduLevel(byte educationLevel) {
    this.educationLevel = educationLevel;
  }
}
