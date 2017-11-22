package by.bsac.timetable.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

public class ConfigForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private final JPanel contentPanel = new JPanel();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    try {
      ConfigForm dialog = new ConfigForm();
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the dialog.
   */
  public ConfigForm() {

    setBounds(100, 100, 649, 391);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);

    JSplitPane splitPane = new JSplitPane();
    splitPane.setBounds(10, 11, 613, 297);

    contentPanel.add(splitPane);
    {
      JTree tree = new JTree();
      tree.setVisibleRowCount(7);
      tree.setShowsRootHandles(true);
      tree.setRootVisible(false);
      tree.setFont(new Font("Tahoma", Font.PLAIN, 16));
      tree.setBackground(Color.WHITE);
      splitPane.setLeftComponent(tree);
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
  }
}
