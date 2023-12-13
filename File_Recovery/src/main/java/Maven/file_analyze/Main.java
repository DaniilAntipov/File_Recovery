package Maven.file_analyze;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {

    private String selectedFilePath;

    @Override
    public void start(Stage primaryStage) throws Exception {
        selectFile(primaryStage);
    }

    protected void selectFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            FileAnalyzer fileAnalyzer = new FileAnalyzer();
            fileAnalyzer.restoreFileExtension(selectedFilePath);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}