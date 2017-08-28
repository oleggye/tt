package by.bsac.timetable.listener;

import java.util.Map;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.util.ActionMode;

public class AppListener {

  private ConfigurableApplicationContext applicationContext;

  public void appStarting() {
    applicationContext = new ClassPathXmlApplicationContext("classpath:springContext.xml");

    for (String name : applicationContext.getBeanDefinitionNames())
      System.out.println(name);

    Map<ActionMode, ICommand> commandStore =
        (Map<ActionMode, ICommand>) applicationContext.getBean("commandStore");
    CommandProvider.getInstance().setCommandStore(commandStore);
  }

  public void appClosing() {
    if (applicationContext != null) {
      applicationContext.close();
    }
    // HibernateUtil.closeSession();
  }

  public static void main(String[] args) throws InterruptedException {
    AppListener appListener = new AppListener();
    appListener.appStarting();
    appListener.appClosing();
  }
}
