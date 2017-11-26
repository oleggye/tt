package by.bsac.timetable.view;

import by.bsac.timetable.listener.SpringApplicationContextManager;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by hello on 26.11.17.
 */
public class CustomMainForm extends MainForm {

  private static final Logger LOGGER = LogManager.getLogger(CustomMainForm.class.getName());

  private SpringApplicationContextManager listener;

  /**
   * Create the application.
   */
  public CustomMainForm() throws IOException {
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
        JOptionPane.showMessageDialog(window.getContentPane(), errorMessage);
      }
    });
  }
}
