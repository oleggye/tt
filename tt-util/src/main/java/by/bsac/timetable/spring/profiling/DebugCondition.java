package by.bsac.timetable.spring.profiling;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by hello on 26.11.17.
 */
public class DebugCondition implements Condition {

  private static final String DEBUG_MODE_VALUE = "debug";

  @Override
  public boolean matches(ConditionContext conditionContext,
      AnnotatedTypeMetadata annotatedTypeMetadata) {
    Environment environment = conditionContext.getEnvironment();
    String mode = environment.getProperty("mode");
    return mode != null && mode.equalsIgnoreCase(DEBUG_MODE_VALUE);
  }
}
