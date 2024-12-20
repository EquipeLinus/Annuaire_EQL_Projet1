package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

// Pop up initialisation fichier
public class InitializeTxtPanel extends VBox {
    private Label textFile = new Label("Entrez un chemin d'accès");
    private TextField txtFilePath = new TextField("chemin d'accès au fichier...");
    private Button btnFilePath = new Button("Select file");
    private Button btnValidate = new Button("Valider");
    private Label lblInfo = new Label("");

    public InitializeTxtPanel() {

        GridPane filePathBox = new GridPane();
        filePathBox.addRow(3,textFile);
        filePathBox.addRow(4,txtFilePath,btnFilePath);
        filePathBox.addRow(6,btnValidate, lblInfo);

        getChildren().addAll(filePathBox);
        filePathBox.setAlignment(Pos.CENTER);
        filePathBox.setVgap(5);
        filePathBox.setHgap(10);


        btnFilePath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("."));
                File f = fileChooser.showOpenDialog(((Stage) (getScene().getWindow())).getOwner());

                if (f != null) {
                    txtFilePath.setText(f.getAbsolutePath());
                    StagiairesSorter.path = f.getPath();
                }
            }
        });

        btnValidate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lblInfo.setTextFill(Color.GREEN);
                lblInfo.setText("Veuillez patienter");
                delay(500, () -> {
                    try {
                        BinManager bManager = new BinManager();
                        bManager.initialize();
                        lblInfo.setText("Fini");
                        Application.getInstance().getSearchPanel().search();

                        delay(500, () -> ((Stage) getScene().getWindow()).close());
                    } catch (IOException | NumberFormatException e) {
                        lblInfo.setTextFill(Color.RED);
                        lblInfo.setText("Fichier invalide !");
                    }
                });
                System.out.println("test");
            }

        });
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public static void openWindow() {
        InitializeTxtPanel init = new InitializeTxtPanel();
        Scene secondScene = new Scene(init, 300, 120);
        Stage newWindow = new Stage();
        newWindow.setTitle("Please select initialization file");
        newWindow.setScene(secondScene);
        Application.getInstance().setCurrentPopup(newWindow);

    }
}
