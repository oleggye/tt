package by.bsac.timetable.listener;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.util.ActionMode;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContextManager {

  private static final Logger LOGGER = LogManager.getLogger(SpringApplicationContextManager.class.getName());

  private ConfigurableApplicationContext applicationContext;

  public void startApplicationContext() {
    LOGGER.info("Starting Spring app.....");
    applicationContext = new ClassPathXmlApplicationContext("classpath:springContext.xml");

    @SuppressWarnings("unchecked")
    Map<ActionMode, ICommand> commandStore =
        (Map<ActionMode, ICommand>) applicationContext.getBean("commandStore");
    CommandProvider.getInstance().setCommandStore(commandStore);
    LOGGER.info("Spring app is started!");
  }

  public void closeApplicationContext() {
    if (applicationContext != null) {
      LOGGER.info("Closing Spring app.....");
      applicationContext.close();
      LOGGER.info("Spring app is closed!");
    }
  }
}
