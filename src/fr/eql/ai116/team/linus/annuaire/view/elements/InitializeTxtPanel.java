package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.Application;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.model.program.Delay;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

// Pop up initialisation fichier
public class InitializeTxtPanel extends VBox {
    private Label textFile = new Label("Entrez un chemin d'accès");
    private TextField txtFilePath = new TextField("");
    private Button btnFilePath = new Button("Sélectionner fichier");
    private Button btnValidate = new Button("Valider");
    private Label lblInfo = new Label("");

    public InitializeTxtPanel() {

        GridPane filePathBox = new GridPane();
        txtFilePath.setPromptText("Chemin d'accès au fichier...");

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
                Delay.delay(500, () -> {
                    try {
                        BinManager bManager = new BinManager();
                        bManager.initialize();
                        lblInfo.setText("Fini");
                        Application.getInstance().getSearchPanel().search();

                        Delay.delay(500, () -> ((Stage) getScene().getWindow()).close());
                    } catch (IOException | NumberFormatException e) {
                        lblInfo.setTextFill(Color.RED);
                        lblInfo.setText("Fichier invalide !");
                    }
                });
            }

        });
    }



    public static void openWindow() {
        InitializeTxtPanel init = new InitializeTxtPanel();
        Scene secondScene = new Scene(init, 400, 120);
        init.requestFocus();
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        newWindow.setTitle("Choisissez le fichier stagiaire.txt pour l'importer");
        newWindow.setScene(secondScene);
        Application.getInstance().setCurrentPopup(newWindow);

    }
}
