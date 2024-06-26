package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.generateRandomDigitString;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.project.Project;

/**
 * Adds a deadline to a project.
 */
public class SetDeadlineProjectCommand extends SetDeadlineCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " DEADLINE /to PROJECT_NAME";

    public static final String MESSAGE_SUCCESS = "The project %1$s has been set with the following deadline %2$s.";

    public static final String MESSAGE_PROJECT_NOT_FOUND = "Project %1$s not found: "
            + "Please make sure the project exists.";


    public static final String MESSAGE_WRONG_FORMAT_DEADLINE = "The deadline %1s has been entered in the wrong format. "
            + "An example of the correct format is Mar 15 2024.";

    private final String deadline;
    private final Project project;

    private final String datePattern = "\\b(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\b \\d{1,2} \\d{4}\\b";

    /**
     * Creates an SetDeadlineProjectCommand to add the specified {@code Project}
     */
    public SetDeadlineProjectCommand(String deadline, Project project) {
        requireNonNull(project);
        this.deadline = deadline;
        this.project = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!deadline.matches(datePattern)) {
            throw new CommandException(String.format(MESSAGE_WRONG_FORMAT_DEADLINE, deadline));
        }

        if (!model.hasProject(project)) {
            throw new CommandException(String.format(
                    MESSAGE_PROJECT_NOT_FOUND,
                    Messages.format(project)));
        }

        Project deadlineProject = model.findProject(project.getName());
        Project dupProject = deadlineProject.createEditedProject(
                new Name(generateRandomDigitString(20).toString())); // duplicate project with dummy name
        model.setProject(deadlineProject, dupProject);
        Project realProject = dupProject.createEditedProject(project.getName());
        realProject.setDeadline(deadline);
        model.setProject(dupProject, realProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(deadlineProject), deadline));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetDeadlineProjectCommand)) {
            return false;
        }

        SetDeadlineProjectCommand otherSetDeadlineProjectCommand = (SetDeadlineProjectCommand) other;
        return project.equals(otherSetDeadlineProjectCommand.project)
                && deadline.equals(otherSetDeadlineProjectCommand.deadline);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("setDeadline", deadline)
                .toString();
    }

}
