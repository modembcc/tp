package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.project.Task;


/**
 * Rename a task inside a project.
 */
public class EditTaskNameCommand extends RenameCommand {
    public static final String MESSAGE_USAGE = RenameCommand.COMMAND_WORD + "NEW_TASK_NAME /of TARGET_TASK_NAME /in PROJECT_NAME";

    public static final String MESSAGE_SUCCESS = "Task %1$s of project %2$s has been renamed to %3$s";

    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project %1$s not found: "
            + "Please make sure the project exists.";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task %1$s not found: "
            + "Please make sure the task exists in project %2$s";
    private final Name changeTo;
    private final Person targetProject;
    private final Task targetTask;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public EditTaskNameCommand(Name newName, Person currentProject, Task currentTask) {
        requireNonNull(newName);
        requireNonNull(currentProject);
        this.changeTo = newName;
        this.targetProject = currentProject;
        this.targetTask = currentTask;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasPerson(targetProject)) {
            throw new CommandException(String.format(
                    MESSAGE_PROJECT_NOT_FOUND,
                    Messages.format(targetProject)));
        }
        Person targetPerson = model.findPerson(targetProject.getName());
        if (!targetPerson.hasTask(targetTask)) {
            throw new CommandException(String.format(
                    MESSAGE_TASK_NOT_FOUND,
                    Messages.format(targetTask),
                    Messages.format(targetPerson)));
        }
        Task toBeChanged = targetPerson.findTask(targetTask.getName());
        toBeChanged.setName(changeTo);
        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                Messages.format(targetTask),
                Messages.format(targetProject),
                changeTo));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskNameCommand)) {
            return false;
        }

        EditTaskNameCommand otherEditTaskNameCommand = (EditTaskNameCommand) other;
        return changeTo.equals(otherEditTaskNameCommand.changeTo)
                && targetProject.equals(otherEditTaskNameCommand.targetProject)
                && targetTask.equals(otherEditTaskNameCommand.targetTask);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTask", targetTask)
                .add("changeTo", changeTo)
                .add("targetProject", targetProject)
                .toString();
    }
}
