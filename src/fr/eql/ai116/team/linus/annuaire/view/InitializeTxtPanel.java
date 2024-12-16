package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import fr.eql.ai116.team.linus.annuaire.model.program.StagiairesSorter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        filePathBox.getChildren().addAll(txtFilePath,btnFilePath);
        getChildren().addAll(filePathBox,btnValidate);

        btnFilePath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("C:/"));
                File f = fileChooser.showOpenDialog(((Stage)(getScene().getWindow())).getOwner());

                if (f != null) {
                    txtFilePath.setText(f.getAbsolutePath());

                    StagiairesSorter.path = f.getPath();
                    try {
                        BinManager bManager = new BinManager();
                        bManager.initialize();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ((Stage)getScene().getWindow()).close();
                }
            }
        });
    }
}
