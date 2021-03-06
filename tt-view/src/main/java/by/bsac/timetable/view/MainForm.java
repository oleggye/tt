package by.bsac.timetable.view;

import static by.bsac.timetable.view.util.LocalizationBundle.getMessage;
import static java.util.Objects.isNull;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.exception.ApplicationException;
import by.bsac.timetable.util.DateLabelFormatter;
import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.view.component.MyComboBox;
import by.bsac.timetable.view.component.table.TablesArray;
import by.bsac.timetable.view.contoller.CellTableMouseClickedEvent;
import by.bsac.timetable.view.contoller.ShowBtnActionEvent;
import by.bsac.timetable.view.extra.ChairEditForm;
import by.bsac.timetable.view.extra.ClassroomEditForm;
import by.bsac.timetable.view.extra.FacultyEditForm;
import by.bsac.timetable.view.extra.FlowEditForm;
import by.bsac.timetable.view.extra.GroupEditForm;
import by.bsac.timetable.view.extra.LecturerEditForm;
import by.bsac.timetable.view.extra.SubjectEditForm;
import by.bsac.timetable.view.util.FormInitializer;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The app's main class
 *
 * @author Maksimovich Oleg
 * @version 2.0
 */

public class MainForm extends JFrame {

  private static final long serialVersionUID = -8883696845828234226L;

  private static final Logger LOGGER = LoggerFactory.getLogger(MainForm.class.getName());

  private final TablesArray tableArray = new TablesArray(7, 4);

  private JButton showRecordsButton;
  private JComboBox<Faculty> facultyComboBox; // combobox � ����������
  // �����������
  private JComboBox<Group> groupComboBox; // combobox � ���������� �����
  private byte educationLevel = 1;// ���������� ��� ������ �����������

  private Boolean isGroupSelected = false;

  /**
   * Launch the application.
   *
   * @see Exception
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      MainForm window = null;
      try {
        window = new MainForm(getMessage("mainForm.title"));
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
      } catch (Exception e) {
        final String errorMessage = e.getCause().getMessage();
        LOGGER.error(errorMessage, e);
        JOptionPane.showMessageDialog(window.getContentPane(), errorMessage);
      }
    });
  }

  /**
   * Create the application.
   */
  public MainForm() throws IOException {
    initialize();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
  }

  public MainForm(String title) throws IOException {
    super(title);
    initialize();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  protected void initialize() throws IOException {
    this.setBounds(100, 100, 770, 533);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new BorderLayout(0, 0));
    this.setIconImage(
        new ImageIcon(getClass().getClassLoader().getResource("images/table.png")).getImage());

    JLabel progressBarLbl = new JLabel(getMessage("mainForm.progressBarLbl"));
    JProgressBar progressBar = new javax.swing.JProgressBar();

    JPanel topPanel = new JPanel();
    this.getContentPane().add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new GridLayout(2, 1, 0, 0));
    topPanel.setPreferredSize(new Dimension(100, 78));
    topPanel.setMaximumSize(new Dimension(100, 78));

    JPanel menuPanel = new JPanel();
    FlowLayout menuPanelLayout = (FlowLayout) menuPanel.getLayout();
    menuPanelLayout.setAlignment(FlowLayout.LEFT);
    topPanel.add(menuPanel);

    JMenuBar menuBar = new JMenuBar();
    menuPanel.add(menuBar);

    JMenu mnFile = new JMenu(getMessage("mainForm.mainMenu"));
    menuBar.add(mnFile);

    JMenuItem mntmNewFile = new JMenuItem(getMessage("mainForm.educationLevel"));
    mntmNewFile.addActionListener(evt -> {
      try {
        showWindow(false, progressBarLbl, progressBar);
      } catch (ApplicationException ex) {
        LOGGER.error(ex.getCause().getMessage(), ex);
        JOptionPane.showMessageDialog(this.getContentPane(), ex.getCause().getMessage());
      }
    });
    mnFile.add(mntmNewFile);

    JMenu mnNewMenu = new JMenu("\u041D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0438");
    menuBar.add(mnNewMenu);

    JMenuItem changeFacultyMenuItem = new JMenuItem(getMessage("mainForm.editFaculty"));
    changeFacultyMenuItem.addActionListener((ActionEvent e) -> {
      FacultyEditForm dialog = new FacultyEditForm();
      dialog.setLocationRelativeTo(this);
      dialog.setVisible(true);
      try {
        FormInitializer.initFacultyComboBox(facultyComboBox);
      } catch (CommandException | ApplicationException ex) {
        LOGGER.error(ex.getCause().getMessage(), ex);
        JOptionPane.showMessageDialog(this.getContentPane(), ex.getCause().getMessage());
      } finally {
        if (groupComboBox.getItemCount() > 0) {
          groupComboBox.setSelectedIndex(0);
        }
        actOrDeactivateButton();
      }
      resetTimetable();
    });
    mnNewMenu.add(changeFacultyMenuItem);

    JMenuItem changeFlowMenuItem = new JMenuItem(getMessage("mainForm.editFlow"));
    changeFlowMenuItem.addActionListener((ActionEvent e) -> {
      FlowEditForm edf = new FlowEditForm();
      edf.setLocationRelativeTo(this);
      edf.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeFlowMenuItem);

    JMenuItem changeChairMenuItem = new JMenuItem(getMessage("mainForm.editChair"));
    changeChairMenuItem.addActionListener((ActionEvent e) -> {
      ChairEditForm dialog = new ChairEditForm();
      dialog.setLocationRelativeTo(this);
      dialog.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeChairMenuItem);

    JMenuItem changeGroupMenuItem = new JMenuItem(getMessage("mainForm.editGroup"));
    changeGroupMenuItem.addActionListener((ActionEvent e) -> {
      GroupEditForm edf = new GroupEditForm(educationLevel);
      edf.setLocationRelativeTo(this);
      edf.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeGroupMenuItem);

    JMenuItem changeSubjectMenuItem = new JMenuItem(getMessage("mainForm.editSubject"));
    changeSubjectMenuItem.addActionListener((ActionEvent e) -> {
      /* EditForm edf = new EditForm(this, 2, 1, educationLevel); */
      SubjectEditForm edf = new SubjectEditForm(educationLevel);
      edf.setLocationRelativeTo(this);
      edf.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeSubjectMenuItem);

    JMenuItem changeLecturerMenuItem = new JMenuItem(getMessage("mainForm.editLecturer"));
    changeLecturerMenuItem.addActionListener((ActionEvent e) -> {
      // EditForm edf = new EditForm(this, 2, 3, educationLevel);
      LecturerEditForm edf = new LecturerEditForm();
      edf.setLocationRelativeTo(this);
      edf.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeLecturerMenuItem);

    JMenuItem changeClassroomMenuItem = new JMenuItem(getMessage("mainForm.editClassroom"));
    changeClassroomMenuItem.addActionListener((ActionEvent e) -> {
      ClassroomEditForm edf = new ClassroomEditForm();
      edf.setLocationRelativeTo(this);
      edf.setVisible(true);
      resetTimetable();
    });
    mnNewMenu.add(changeClassroomMenuItem);

    JMenu aboutProgramMenu = new JMenu(getMessage("mainForm.aboutProgram"));
    aboutProgramMenu.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        AboutForm edf = new AboutForm();
        edf.setLocationRelativeTo(evt.getComponent().getParent());
        edf.setVisible(true);
      }
    });
    menuBar.add(aboutProgramMenu);

    JPanel facultySubPanel = new JPanel();
    FlowLayout facultySubPanelLayout = (FlowLayout) facultySubPanel.getLayout();
    facultySubPanelLayout.setAlignment(FlowLayout.LEFT);
    topPanel.add(facultySubPanel);

    facultySubPanel.add(new JLabel(getMessage("mainForm.faculty")));
    facultyComboBox = new MyComboBox<>();
    facultySubPanel.add(facultyComboBox);

    facultyComboBox.addItemListener((ItemEvent ev) -> {
      if (ev.getStateChange() == ItemEvent.SELECTED) {
        tableArray.resetAllTablesInArray();
        try {
          FormInitializer.initGroupComboBox(facultyComboBox, groupComboBox, educationLevel);
        } catch (CommandException | ApplicationException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(this.getContentPane(), ex.getCause().getMessage());
        } finally {
          if (groupComboBox.getItemCount() > 0) {
            groupComboBox.setSelectedIndex(0);
          }
          actOrDeactivateButton();
        }
      }
    });
    facultySubPanel.add(new JLabel(getMessage("mainForm.group")));
    groupComboBox = new MyComboBox<>();
    facultySubPanel.add(groupComboBox);
    groupComboBox.addItemListener((ItemEvent e) -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        tableArray.resetAllTablesInArray();
        isGroupSelected = false;
        showRecordsButton.setEnabled(true);
      }
    });
    facultySubPanel.add(new JLabel(getMessage("mainForm.reference")));
    // ������ ������� ���� ��� ��������� � �������� �������
    UtilDateModel model = new UtilDateModel();
    Calendar calendar = Calendar.getInstance();
    model.setValue(calendar.getTime());
    Properties p = new Properties();
    p.put("text.today", getMessage("mainForm.today"));
    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    datePicker.addActionListener((ActionEvent e) -> {
      //
    });
    facultySubPanel.add(datePicker);

    /* 27/10/16 was changed */
    showRecordsButton = new JButton(getMessage("mainForm.showBtn"));
    facultySubPanel.add(showRecordsButton);
    facultySubPanel.getRootPane().setDefaultButton(showRecordsButton);
    showRecordsButton
        .setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/toolbar_find.png")));
    showRecordsButton
        .addActionListener(new ShowBtnActionEvent(this, tableArray, datePicker, groupComboBox));

    JPanel centralPanel = new JPanel();
    centralPanel.setLayout(new GridLayout(7, 4, 10, 10));

    JPanel rightPanel = new JPanel();
    this.getContentPane().add(rightPanel, BorderLayout.EAST);

    JPanel bottomPanel = new JPanel();
    this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

    // ��������� progressbar � progresslabel �� ������
    bottomPanel.add(progressBarLbl);
    bottomPanel.add(progressBar);

    final JScrollPane scrollPane = new JScrollPane(centralPanel);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    scrollPane.setColumnHeaderView(SupportClass.getJLabelWithPicture(this));

    MouseListener listener =
        new CellTableMouseClickedEvent(this, tableArray, groupComboBox, datePicker);

    // ������ ��������������� �������� ��� ������ � ��������� ������� ������
    tableArray.initArray(centralPanel, listener);
    // ********************************************************************************

    FormInitializer.initLeftPanel(scrollPane);
    this.addWindowListener(new MainFormWindowListener(this, progressBarLbl, progressBar));
  }

  private boolean getEduLevel(boolean isJustStarted) {
    ChoseEducationLevelForm edf = new ChoseEducationLevelForm();
    edf.setLocationRelativeTo(this);
    edf.setVisible(true);
    byte newEduLvl = edf.getEduLevel();
    if (newEduLvl != this.educationLevel) {
      this.educationLevel = newEduLvl;
      return true;
    } else {
      return isJustStarted;
    }
  }

  private void showWindow(boolean isJustStarted, JLabel progressBarLbl, JProgressBar progressBar) {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
      // TODO add your handling code here:
      if (getEduLevel(isJustStarted))// ������� �����������
      {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        FormInitializer.initMainForm(facultyComboBox, groupComboBox, progressBarLbl, progressBar,
            educationLevel);
        actOrDeactivateButton();
      }
    } catch (CommandException ex) {
      LOGGER.error(ex.getCause().getMessage(), ex);
      JOptionPane.showMessageDialog(this.getContentPane(),
          ex.getCause().getCause().getMessage());
      JOptionPane.showMessageDialog(this.getContentPane(),
          getMessage("mainForm.error.sendToAdmin"));
      JOptionPane.showMessageDialog(this.getContentPane(),
          getMessage("mainForm.error.appWillBeClosed"));
      this.dispose();

    } finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  private void actOrDeactivateButton() { // ��� ������
    if (this.groupComboBox.getItemCount() < 1) {
      this.showRecordsButton.setEnabled(false);
    } else {
      this.showRecordsButton.setEnabled(true);
    }
  }

  /**
   * Resets selected element in {@link #facultyComboBox}. Clears a data in all tables and resets the
   * flag
   */
  private void resetTimetable() {
    facultyComboBox
        .setSelectedIndex(facultyComboBox.getItemCount() - 1 - facultyComboBox.getSelectedIndex());
    tableArray.resetAllTablesInArray();
    isGroupSelected = false;
  }

  class MainFormWindowListener extends WindowAdapter {

    private JFrame frame;
    private JLabel progressBarLbl;
    private JProgressBar progressBar;

    MainFormWindowListener(JFrame frame, JLabel progressBarLbl, JProgressBar progressBar) {
      this.progressBarLbl = progressBarLbl;
      this.progressBar = progressBar;
    }

    @Override
    public void windowOpened(java.awt.event.WindowEvent evt) {
      Window window = evt.getWindow();
      try {
        showWindow(true, progressBarLbl, progressBar);

      } catch (ApplicationException ex) {
        final Throwable cause = ex.getCause();
        final String errorMessage = isNull(cause) ? "" : cause.getMessage();

        LOGGER.error(errorMessage, ex);
        JOptionPane.showMessageDialog(getContentPane(), errorMessage);

        progressBarLbl.setText(getMessage("mainForm.error"));
        progressBar.setValue(progressBar.getMaximum());
      } finally {
        window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
    }
  }

  public Boolean getIsGroupSelected() {
    return isGroupSelected;
  }

  public void setIsGroupSelected(Boolean isGroupSelected) {
    this.isGroupSelected = isGroupSelected;
  }

  public JFrame getthis() {
    return this;
  }
}
