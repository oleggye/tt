package by.bsac.timetable.view;

import static java.util.Objects.isNull;

import by.bsac.timetable.listener.SpringApplicationContextManager;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomMainForm extends MainForm {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomMainForm.class.getName());

  private SpringApplicationContextManager listener;

  /**
   * Create the application.
   */
  private CustomMainForm() throws IOException {
    super();

  }

  @Override
  protected void initialize() throws IOException {
    super.initialize();
    listener = new SpringApplicationContextManager();
    listener.startApplicationContext();

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        listener.closeApplicationContext();
      }
    });

  }

  /**
   * Launch the application.
   *
   * @see Exception
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      MainForm window = null;
      try {
        window = new CustomMainForm();
      } catch (Exception e) {
        final String errorMessage = e.getCause().getMessage();
        LOGGER.error(errorMessage, e);
        if (!isNull(window)) {
          JOptionPane.showMessageDialog(window.getContentPane(), errorMessage);
        }
      }
    });
  }
}
