package lk.ijse.project_management_tool.controller.component;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import lk.ijse.project_management_tool.utils.ColourMap;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectCardController implements Initializable {
    public VBox vbxProjectCard;
    public ProgressBar progressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String [] Colours = ColourMap.getRandomColour();
        String newStyle = "-fx-background-color: " + Colours[0] + ";";
        String newStyleLighten = "-fx-accent: " + Colours[1] + ";";

        vbxProjectCard.setStyle(vbxProjectCard.getStyle() + newStyle);
        progressBar.setStyle(progressBar.getStyle() + newStyleLighten);

    }
}
