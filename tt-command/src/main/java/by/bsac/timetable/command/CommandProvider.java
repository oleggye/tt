package by.bsac.timetable.command;

import java.util.Map;

import by.bsac.timetable.util.ActionMode;

public class CommandProvider {

  private static final CommandProvider INSTANCE = new CommandProvider();
  private Map<ActionMode, ICommand> commandStore /* = new HashMap<>() */;

  public static CommandProvider getInstance() {
    return INSTANCE;
  }

  private CommandProvider() {}

  public ICommand getCommand(ActionMode action) {
    ICommand command = commandStore.get(action);

    if (command == null) {
      throw new IllegalArgumentException("Wrong ActionMode:" + action);
    }
    return command;
  }

  public void setCommandStore(Map<ActionMode, ICommand> commandStore) {
    this.commandStore = commandStore;
  }
}
