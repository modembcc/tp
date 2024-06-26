package seedu.address.ui;

// import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
// import seedu.address.commons.core.LogsCenter;
import seedu.address.model.project.Project;

/**
 * Panel containing the list of projects.
 */
public class CurrentProjectPanel extends UiPart<Region> {
    private static final String FXML = "CurrentProjectPanel.fxml";
    // private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Project> currentProjectView;

    /**
     * Creates a {@code ProjectListPanel} with the given {@code ObservableList}.
     */
    public CurrentProjectPanel(ObservableList<Project> currentProject) {
        super(FXML);
        currentProjectView.setItems(currentProject);
        currentProjectView.setCellFactory(listView -> new ProjectListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Project} using a {@code ProjectCard}.
     */
    class ProjectListViewCell extends ListCell<Project> {
        @Override
        protected void updateItem(Project project, boolean empty) {
            super.updateItem(project, empty);

            if (empty || project == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskListPanel(project).getRoot());
            }
        }
    }

}
