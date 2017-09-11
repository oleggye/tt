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

    JLabel label = new JLabel(
        "Выберите уровень образования");
    label.setForeground(Color.RED);
    label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    label.setBounds(21, 11, 225, 40);
    panel.add(label);

    JButton btnNewButton = new JButton(
        "Высшее");
    btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    btnNewButton.setBounds(31, 62, 187, 32);
    btnNewButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setEduLevel((byte) 1); // ������ ��
        dispose();
      }
    });

    panel.add(btnNewButton);

    JButton btnNewButton_1 = new JButton(
        "Среднее специальное");
    btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    btnNewButton_1.setBounds(31, 118, 187, 32);
    btnNewButton_1.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setEduLevel((byte) 2); // ������ ���
        dispose();
      }
    });

    panel.add(btnNewButton_1);
    setBounds(100, 100, 259, 199);
  }

  public byte getEduLevel() {
    return this.educationLevel;
  }

  private void setEduLevel(byte edu_level) {
    this.educationLevel = edu_level;
  }
}
