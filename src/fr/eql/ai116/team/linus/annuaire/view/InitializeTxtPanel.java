package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InitializeTxtPanel extends VBox {

    private TextField txtFilePath = new TextField("Enter file path...");
    private Button btnFilePath = new Button("Select file");
    private Button btnValidate = new Button("Valider");

    public InitializeTxtPanel() {
        setAlignment(Pos.CENTER);
        setSpacing(10.);

        HBox filePathBox = new HBox(5.);
        filePathBox.getChildren().addAll(txtFilePath, btnFilePath);

        Label lblInfo = new Label("");
        getChildren().addAll(filePathBox, btnValidate, lblInfo);

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
}
