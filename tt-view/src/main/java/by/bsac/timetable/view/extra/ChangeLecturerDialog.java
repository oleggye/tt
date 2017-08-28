package by.bsac.timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.view.util.FormInitializer;
import components.MyComboBox;

public class ChangeLecturerDialog extends JDialog {

  private static final Logger LOGGER = LogManager.getLogger(ChangeLecturerDialog.class.getName());

  private static final long serialVersionUID = 1L;

  private final JPanel contentPanel = new JPanel();

  public ChangeLecturerDialog(Chair chair, Lecturer oldLecturer) {
    setModal(true);
    setResizable(false);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JComboBox<Lecturer> lecturerComboBox = new MyComboBox<>();

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        try {
          FormInitializer.initLecturerComboBox(lecturerComboBox, chair);
          lecturerComboBox.removeItem(oldLecturer);

          if (lecturerComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(getContentPane(),
                "На данной кафедре нет других преподавателей!", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            dispose();
          }

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage(), "Ошибка",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    setResizable(false);
    setBounds(100, 100, 222, 135);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);

    lecturerComboBox.setBounds(10, 25, 185, 34);
    contentPanel.add(lecturerComboBox);

    JLabel label = new JLabel("Преподаватели");
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    label.setBounds(10, 0, 156, 24);
    contentPanel.add(label);
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");

        okButton.addActionListener(ItemEvent -> {
          Lecturer newLecturer = (Lecturer) lecturerComboBox.getSelectedItem();
          LOGGER.debug("newLecturer:" + newLecturer);

          Request request = new Request();
          try {
            changeLecturer(oldLecturer, newLecturer, request);
            CommandFacade.deleteLecturer(oldLecturer);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            /*
             * FIXME: а если не поменяли (ошибка) то как второй форме узнать об этом (т.е. значит ей
             * нельзя удалять или я удаляю здесь
             */
            dispose();
          }
        });

        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
    }
  }

  private void changeLecturer(Lecturer oldLecturer, Lecturer newLecturer, Request request)
      throws CommandException {
    ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Change_Lecturer);
    request.putParam("oldLecturer", oldLecturer);
    request.putParam("newLecturer", newLecturer);
    command.execute(request);
  }
}
