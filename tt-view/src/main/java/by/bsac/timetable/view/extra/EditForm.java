package by.bsac.timetable.view.extra;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.util.CheckGeneralization;
import by.bsac.timetable.util.GetNamesClass;
import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.view.component.MyComboBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditForm extends JDialog {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(EditForm.class.getName());

  private final JPanel contentPanel = new JPanel();
  private JTextField textField;// текстовое поле
  private JTextField textFieldForAbbr;// специально для аббревиатур
  private JTable table;
  private JComboBox comboBox;
  private JLabel lblNewLabel;// для текстовое поле
  private JLabel abrLabel;// специально для аббревиатур
  private JButton editButton;

  private List<Faculty> facultiesCollection;// массив-кол-ция всех факультетов
  private List<Chair> chairsCollection;// массив всех кафедр
  private List<Group> groupsCollection; // массив-кол-ция групп, в зависимости
  // от выбранного факультета
  private List<Subject> subjectsCollection; // массив предметов, в зависимости
  // от выбранной кафедры
  private List<Lecturer> lecturersCollection; // массив преподавателей, в
  // зависимости от выбранной
  // кафедры
  private byte educationLevel = 1;

  private int selItemIndex = -1;
  private int cBoxMode = 0;
  private int tableMode = 0;

  /**
   * Launch the application.
   */
  public static void main() {
    try {
      EditForm dialog = new EditForm(null, 1, 2, (byte) 1);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
    } catch (Exception e) {
      LOGGER.error("Ошибка при загрузке класса EditForm", e);
    }
  }

  public EditForm() {
  }

  /**
   * Create the dialog.
   */
  public EditForm(JFrame parent, int cBoxMode, int tableMode, byte educationLevel) {
    super(parent, true);
    this.cBoxMode = cBoxMode;
    this.tableMode = tableMode;
    this.educationLevel = educationLevel;

    setBounds(100, 100, 590, 380);
    setResizable(false);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowOpened(java.awt.event.WindowEvent evt) {
        try {
          chooseCBByMode();
          chooseTableByMode();
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        }
      }
    });

    lblNewLabel = new JLabel("New label");

    lblNewLabel.setBounds(400, 20, 70, 14);
    contentPanel.add(lblNewLabel);

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setBounds(345, 100, 190, 14);
    contentPanel.add(label);

    this.comboBox = new MyComboBox();

    this.comboBox.setBounds(300, 40, 255, 23);
    contentPanel.add(this.comboBox);

    this.comboBox.addItemListener(evt -> {
      try {
        chooseTableByMode();
        editButton.setVisible(false);
      } catch (CommandException ex) {
        LOGGER.error(ex.getCause().getMessage(), ex);
        JOptionPane.showMessageDialog(getContentPane(), ex);
      }
    });

    textField = new JTextField();

    textField.setBounds(325, 123, 220, 23);
    contentPanel.add(textField);

    textField.setColumns(10);

    abrLabel = new JLabel("Аббревиатура");
    abrLabel.setBounds(395, 200, 190, 14);
    contentPanel.add(abrLabel);
    abrLabel.setVisible(false);

    textFieldForAbbr = new JTextField();
    textFieldForAbbr.setBounds(370, 220, 130, 23);
    contentPanel.add(textFieldForAbbr);

    textFieldForAbbr.setColumns(10);
    textFieldForAbbr.setVisible(false);

    editButton = new JButton("Изменить");
    editButton.setVisible(false);
    editButton.setBounds(335, 169, 100, 23);
    contentPanel.add(editButton);

    editButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        try {
          editButton.setEnabled(false);
          addOrUpdateRecord(ActionMode.UPDATE);
          chooseTableByMode();
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        } finally {
          editButton.setEnabled(true);
        }

      }
    });

    JButton addButton = new JButton("Добавить");

    addButton.setBounds(440, 169, 95, 23);
    contentPanel.add(addButton);

    addButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!textField.getText().equals("")) {
          try {
            addButton.setEnabled(false);
            addOrUpdateRecord(ActionMode.ADD);
            chooseTableByMode();
            editButton.setVisible(false);
          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex);
          } finally {
            addButton.setEnabled(true);
          }
        }
      }
    });

    this.table = new JTable() {
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      // Implement table cell tool tips.
      @Override
      public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        if (realColumnIndex == 0) {
          tip = (String) getValueAt(rowIndex, colIndex);
        }
        return tip;
      }
    };

    this.table
        .setModel(new javax.swing.table.DefaultTableModel(new Object[][]{{}}, new String[]{}) {

          private static final long serialVersionUID = 1L;
          Class[] types = new Class[]{java.lang.String.class};
          boolean[] canEdit = new boolean[]{false};

          @Override
          public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
          }

          @Override
          public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
          }
        });
    this.table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    this.table.setFont(new Font("Verdana", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBounds(25, 40, 259, 245);
    contentPanel.add(scrollPane);

    this.table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        selItemIndex = table.getSelectedRow();
        if (selItemIndex >= 0) {
          initTextFieldBy();
          editButton.setVisible(true);
        }
      }
    });

    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new java.awt.event.ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
      }
    }
  }

  private void initFacultyComboBox() throws CommandException {

    //
    facultiesCollection = CommandFacade.getFacultyList();
    final DefaultComboBoxModel model1 =
        new DefaultComboBoxModel(GetNamesClass.getFacultiesNames(facultiesCollection));
    this.comboBox.setModel(model1);
  }

  private void initChairComboBox() throws CommandException {

    chairsCollection = CommandFacade.getChairList();
    final DefaultComboBoxModel model1 =
        new DefaultComboBoxModel(GetNamesClass.getChairsNames(chairsCollection));
    this.comboBox.setModel(model1);
  }

  private void chooseCBByMode() throws CommandException {
    switch (this.cBoxMode) {
      case 1:
        initFacultyComboBox();
        lblNewLabel.setText("Факультет");
        break;
      case 2:
        initChairComboBox();
        lblNewLabel.setText("Кафедра");
        break;
      default:
        this.dispose();
    }
  }

  private void initTableBySubjects(List<Subject> collection) {

    DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
    Vector title = new Vector();
    title.add("Предмет");
    tModel.setDataVector(arrayListSubjectsToNamesVector(collection), title);
    SupportClass.setHorizontalAlignmentToTable(this.table);
  }

  private void initTableByGroups(List<Group> collection) {

    DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
    Vector title = new Vector();
    title.add("Группа");
    tModel.setDataVector(arrayListGroupsToNamesVector(collection), title);
    SupportClass.setHorizontalAlignmentToTable(this.table);
  }

  private void initTableByLecturers(List<Lecturer> collection) {

    DefaultTableModel tModel = (DefaultTableModel) this.table.getModel();
    Vector title = new Vector();
    title.add("Преподаватель");
    tModel.setDataVector(arrayListLecturersToNamesVector(collection), title);
    SupportClass.setHorizontalAlignmentToTable(this.table);
  }

  private void chooseTableByMode() throws CommandException {

    switch (this.tableMode) {
      case 1:
        getSubjectsCollection();
        break;
      case 2:
        getGroupsCollection();
        break;
      case 3:
        getLecturersCollection();
        break;
      default:
        this.dispose();
    }
    ;
  }

  private void initTextFieldBy() {
    switch (this.tableMode) {
      case 1:
        initTextFieldBySubjectN(subjectsCollection);
        textFieldForAbbr.setVisible(true);
        abrLabel.setVisible(true);
        break;
      case 2:
        initTextFieldByGroupsN(groupsCollection);
        break;
      case 3:
        initTextFieldByLecturersN(lecturersCollection);
        break;
      default:
        this.dispose();
    }
    ;
  }

  private void initTextFieldBySubjectN(List<Subject> collection) {

    String text = collection.get(this.selItemIndex).getNameSubject();
    textField.setText(text);
    String abrText = collection.get(this.selItemIndex).getAbnameSubject();
    textFieldForAbbr.setText(abrText);
  }

  private void initTextFieldByGroupsN(List<Group> collection) {

    String text = collection.get(this.selItemIndex).getNameGroup();
    textField.setText(text);
  }

  private void initTextFieldByLecturersN(List<Lecturer> collection) {

    String text = collection.get(this.selItemIndex).getNameLecturer();
    textField.setText(text);
  }

  private void getSubjectsCollection() throws CommandException {
    // т.к. есть разделение по уровню образования, то изменен запрос
    //
    int CB_index = this.comboBox.getSelectedIndex(); // передаем index
    // выбранного
    // элемента в
    // ComboBox
    // subjectsCollection = (ArrayList)
    // Factory.getInstance().getSubjectsDAO().getSubjectsRecordsByChairId(chairsCollection.get(CB_index));

    Chair chair = chairsCollection.get(CB_index);
    subjectsCollection = CommandFacade.getSubjectListByChairAndEduLevel(chair, this.educationLevel);

    /*
     * использовалась для автоматического генерирования аббревиатур предметов subjectsCollection =
     * SupportClass.addAbrToCollection(subjectsCollection);
     */
    initTableBySubjects(subjectsCollection);
  }

  private void getGroupsCollection() throws CommandException {
    //
    int CB_index = this.comboBox.getSelectedIndex(); // передаем index
    // выбранного
    // элемента в
    // ComboBox
    Faculty faculty = facultiesCollection.get(CB_index);
    groupsCollection = CommandFacade.getGroupListByFacultyAndEduLevel(faculty, this.educationLevel);
  }

  private void getLecturersCollection() throws CommandException {
    //
    int CB_index = this.comboBox.getSelectedIndex(); // передаем index
    // выбранного
    // элемента в
    // ComboBox
    Chair chair = chairsCollection.get(CB_index);
    lecturersCollection = CommandFacade.getLecturerListByChair(chair);
    initTableByLecturers(lecturersCollection);
  }

  private Vector<String> arrayListGroupsToNamesVector(List<Group> collection) {

    Vector vec = new Vector<String>();
    String[] str = new String[1];// 1 - кол-во столбцов

    for (Group e : collection) {
      Vector element = new Vector();
      for (int i = 0; i < 1; i++) {
        str[i] = e.getNameGroup();
        element.add(str[i]);
      }
      vec.add(element);
    }
    return vec;
  }

  private Vector<String> arrayListLecturersToNamesVector(List<Lecturer> collection) {

    Vector vec = new Vector<String>();
    String[] str = new String[1];// 1 - кол-во столбцов

    for (Lecturer e : collection) {
      Vector element = new Vector();
      for (int i = 0; i < 1; i++) {
        str[i] = e.getNameLecturer();
        element.add(str[i]);
      }
      vec.add(element);
    }
    return vec;
  }

  private Vector<String> arrayListSubjectsToNamesVector(List<Subject> collection) {

    Vector vec = new Vector<String>();
    String[] str = new String[1];// 1 - кол-во столбцов

    for (Subject e : collection) {
      Vector element = new Vector();
      for (int i = 0; i < 1; i++) {
        str[i] = e.getNameSubject();
        element.add(str[i]);
      }
      vec.add(element);
    }
    return vec;
  }

  private void addOrUpdateSubjects(ActionMode mode) throws CommandException {
    switch (mode) {
      case ADD:
        Chair chair = chairsCollection.get(comboBox.getSelectedIndex());
        String subjName = textField.getText();
        Subject newSubj;
        if (textFieldForAbbr.getText().equals("")) {// если поле пустое, то
          // генерируем
          // автоматически
          newSubj = new Subject(chair, subjName, this.educationLevel);
        } else {
          String abrName = textFieldForAbbr.getText();
          newSubj = new Subject(chair, subjName, this.educationLevel, abrName);
        }

        // if (SupportClass.checkSubjectsRecBeforeAdd(subjectsCollection,
        // newSubj)) {
        /* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
        CheckGeneralization<Subject> obj = new CheckGeneralization<Subject>();
        if (obj.checkRecordBeforeAdd(subjectsCollection, newSubj)) {
          CommandFacade.addSubject(newSubj);
        }
        break;
      case UPDATE:
        Subject subj = subjectsCollection.get(this.selItemIndex);
        subj.setNameSubject(textField.getText());
        subj.setAbnameSubject(textFieldForAbbr.getText());
        CommandFacade.updateSubject(subj);
        break;
      default:
        this.dispose();
    }

  }

  private void addOrUpdateGroups(ActionMode mode) throws CommandException {

    switch (mode) {
      case ADD:
        Faculty facult = facultiesCollection.get(comboBox.getSelectedIndex());
        String groupName = textField.getText();

        // FIXME: change!!!
        Group newGroup = new Group();
        // facult, groupName, this.educationLevel
        newGroup.setFaculty(facult);
        newGroup.setNameGroup(groupName);
        newGroup.setEduLevel(this.educationLevel);

        // if (SupportClass.checkGroupsRecBeforeAdd((ArrayList<group>)
        // groupsCollection, newGroup)) {
        /* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
        CheckGeneralization<Group> obj = new CheckGeneralization<Group>();
        if (obj.checkRecordBeforeAdd((ArrayList<Group>) groupsCollection, newGroup)) {
          CommandFacade.addGroup(newGroup);
        }
        break;
      case UPDATE:
        Group group = groupsCollection.get(this.selItemIndex);
        group.setNameGroup(textField.getText());
        CommandFacade.updateGroup(group);
        break;
      default:
        this.dispose();
    }

  }

  private void addOrUpdateLecturers(ActionMode mode) throws CommandException {

    switch (mode) {
      case ADD:
        Chair chair = chairsCollection.get(comboBox.getSelectedIndex());
        String lectName = textField.getText();
        Lecturer newLect = new Lecturer(chair, lectName);

        // if (SupportClass.checkLecturersRecBeforeAdd(lecturersCollection,
        // newLect)) {
        /* !!!!!временна мера, чтобы попробывать обобщения!!!!! */
        CheckGeneralization<Lecturer> obj = new CheckGeneralization<Lecturer>();
        if (obj.checkRecordBeforeAdd(lecturersCollection, newLect)) {
          CommandFacade.addLecturer(newLect);
        }
        break;
      case UPDATE:
        Lecturer lect = lecturersCollection.get(this.selItemIndex);
        lect.setNameLecturer(textField.getText());
        CommandFacade.updateLecturer(lect);
        break;
      default:
        this.dispose();
    }

  }

  private void addOrUpdateRecord(ActionMode mode) throws CommandException {

    switch (this.tableMode) {
      case 1:
        try {
          addOrUpdateSubjects(mode);

        } finally {
          textField.setText("");
          textFieldForAbbr.setText("");
        }
        break;
      case 2:
        try {
          addOrUpdateGroups(mode);
        } finally {
          textField.setText("");
          textFieldForAbbr.setText("");
        }
        break;
      case 3:
        try {
          addOrUpdateLecturers(mode);
        } finally {
          textField.setText("");
          textFieldForAbbr.setText("");
        }
        break;
      default:
        dispose();
    }

  }

  enum ActionMode {
    ADD, UPDATE, DELETE;
  }
}
