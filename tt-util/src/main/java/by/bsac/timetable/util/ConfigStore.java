package by.bsac.timetable.util;

import java.util.HashMap;
import java.util.Map;

public final class ConfigStore {
  private static final ConfigStore INSTANCE = new ConfigStore();

  private Map<String, Object> map;

  private ConfigStore() {
    map = new HashMap<>();
  }

  public static ConfigStore getInstance() {
    return INSTANCE;
  }

  public Object getValue(String key) {
    return map.get(key);
  }

  public void putValue(String key, Object value) {
    map.put(key, value);
  }
}
