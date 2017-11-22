package by.bsac.timetable.listener;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.util.ActionMode;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppListener {

  private static final Logger LOGGER = LogManager.getLogger(AppListener.class.getName());

  private ConfigurableApplicationContext applicationContext;

  public void appStarting() {
    applicationContext = new ClassPathXmlApplicationContext("classpath:springContext.xml");

    LOGGER.info("\nManaged bean list:");
    for (String name : applicationContext.getBeanDefinitionNames())
      LOGGER.info(name);

    @SuppressWarnings("unchecked")
    Map<ActionMode, ICommand> commandStore =
        (Map<ActionMode, ICommand>) applicationContext.getBean("commandStore");
    CommandProvider.getInstance().setCommandStore(commandStore);
  }

  public void appClosing() {
    if (applicationContext != null) {
      applicationContext.close();
    }
  }

  /*
   * public static void main(String[] args) throws InterruptedException { AppListener appListener =
   * new AppListener(); appListener.appStarting(); appListener.appClosing(); }
   */
}
