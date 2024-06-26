package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetProjectStatusCommand;
import seedu.address.logic.commands.SetTaskStatusCommand;
import seedu.address.model.person.Name;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;


class SetStatusCommandParserTest {
    private SetStatusCommandParser parser = new SetStatusCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Project project = new Project(new Name("project"));
        Task task = new Task("task");

        assertParseSuccess(parser, "complete /of project",
                new SetProjectStatusCommand("complete", project));

        assertParseSuccess(parser, "complete /of task /in project",
                new SetTaskStatusCommand("complete", task, project));

    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessageTask = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetTaskStatusCommand.MESSAGE_USAGE);

        // missing project name
        assertParseFailure(parser, "complete /of ",
                "Names should be alphanumerical and not empty.");

        assertParseFailure(parser, "complete /of task /in ",
                expectedMessageTask);

        // missing task name
        assertParseFailure(parser, "complete /of /in proj",
                "Names should be alphanumerical and not empty.");
    }

}
