package softwaredesign;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerExecute extends Controller {

    @FXML
    private Label instructionLabel;
    @FXML
    private TextArea annotationArea;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private List<Instruction> newInstructions;
    private int currInstructionIndex;

    public ControllerExecute(File recipePath) {
        super(EXECUTE,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(VIEW));
        title.setText(recipe.getName());
        nextButton.setOnAction(event -> nextInstruction());
        prevButton.setOnAction(event -> prevInstruction());

        newInstructions = recipe.getInstructions();
        //show first instruction right away
        currInstructionIndex = 0;
        displayInstruction(currInstructionIndex);
    }

    private void saveAnnotation() {
        newInstructions.get(currInstructionIndex).setAnnotation(annotationArea.getText());
    }

    private boolean inBounds(int index) {
        return (index < newInstructions.size() && index >= 0);
    }

    private boolean isLast() {
        return currInstructionIndex == newInstructions.size() - 1;
    }

    private void displayInstruction(int index) {
        Instruction instruction = newInstructions.get(index);
        instructionLabel.setText(instruction.getText());
        instructionLabel.setWrapText(true);
        annotationArea.setText(instruction.getAnnotation());
    }

    private void updateInstructions() {
        recipe.updateAnnotations(newInstructions);
        recipe.writeToFile(recipePath);
    }

    private void nextInstruction() {
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex++;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (isLast()) nextButton.setText("Finish");
        }
        else {
            updateInstructions();
            controllerFactory(VIEW);
        }
    }

    private void prevInstruction() {
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex--;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (!isLast()) nextButton.setText("Next");
        }
        else {
            updateInstructions();
            controllerFactory(CHECK);
        }
    }
}
