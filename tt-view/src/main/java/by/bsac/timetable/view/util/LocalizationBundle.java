package by.bsac.timetable.view.util;

import java.util.ResourceBundle;

public class LocalizationBundle {
  private LocalizationBundle() {}

  private static final String FILE_PATH = "locale/locale";

  private static ResourceBundle bundle;

  public static String getMessage(String key) {
    if (bundle == null) {
      synchronized (ResourceBundle.class) {
        if (bundle == null) {
          bundle = ResourceBundle.getBundle(FILE_PATH);
        }
      }
    }
    return bundle.getString(key);
  }

  public static void main(String[] args) {
    String message = 
        getMessage("mainForm.title");
  }

}
