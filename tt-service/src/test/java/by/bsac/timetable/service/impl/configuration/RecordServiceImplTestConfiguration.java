package by.bsac.timetable.service.impl.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;

public class RecordServiceImplTestConfiguration implements ServiceImplTestConfiguration {

  private static final String FORMAT = "%s%s";
  private static final String PREFIX = "new";

  private RecordServiceImplTestConfiguration() {
  }

  public static RecordServiceImplTestConfiguration getInstance() {
    return SingletonHolder.instance;
  }

  @Override
  public void initField(Object object, Class<?> fieldClass, String fieldName) {
    final Class<?> clazz = object.getClass();
    Field field = ReflectionUtils.findField(clazz, fieldName);
    field.setAccessible(true);
    Object value = getValue(fieldClass);
    ReflectionUtils.setField(field, object, value);
  }

  private Object getValue(Class<?> clazz) {

    final String simpleName = clazz.getSimpleName();

    final String methodName = String.format(FORMAT, PREFIX, simpleName);

    Class<TestEntitiesFactory> factoryClass = TestEntitiesFactory.class;
    Method method = ReflectionUtils.findMethod(factoryClass, methodName);

    return ReflectionUtils.invokeMethod(method, TestEntitiesFactory.getInstance());
  }

  private static class SingletonHolder {

    private static final RecordServiceImplTestConfiguration instance =
        new RecordServiceImplTestConfiguration();
  }
}
