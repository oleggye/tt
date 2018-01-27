package by.bsac.timetable.view.util;

import java.util.ResourceBundle;

public class LocalizationBundle {

  private LocalizationBundle() {
  }

  public static String getMessage(String key) {
    return SingletonHolder.bundle.getString(key);
  }

  private static class SingletonHolder {

    private SingletonHolder() {
    }

    private static final String FILE_PATH = "locale/locale";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(FILE_PATH);
  }
}
