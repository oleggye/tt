package by.bsac.timetable.spring.profiling;

import by.bsac.timetable.spring.logging.Logging;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Created by hello on 26.11.17.
 */
@Component
@Conditional(value = DebugCondition.class)
public class BeanPrinter {

  @Logging
  private Logger logger;

  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  private void printBeanDefinitionNames(){
    logger.debug("\nManaged bean list:");
    for (String name : applicationContext.getBeanDefinitionNames()) {
      logger.debug(name);
    }
  }
}
