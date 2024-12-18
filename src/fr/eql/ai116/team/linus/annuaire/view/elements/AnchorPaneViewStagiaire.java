package fr.eql.ai116.team.linus.annuaire.view.elements;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnchorPaneViewStagiaire extends AnchorPane {

    private static final Logger logger = LogManager.getLogger();
    private final TableView<Stagiaire> table;

    public AnchorPaneViewStagiaire(TableView<Stagiaire> table){
        super();
        this.table = table;

        TableColumn<Stagiaire, String> lastNameCol = new TableColumn<>("Nom");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("lastName"));

        TableColumn<Stagiaire, String> firstNameCol = new TableColumn<>("Prénom");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("firstName"));

        TableColumn<Stagiaire, String> promotionCol = new TableColumn<>("Promotion");
        promotionCol .setMinWidth(200);
        promotionCol .setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promotion"));

        TableColumn<Stagiaire, String> yearCol = new TableColumn<>("Année");
        yearCol.setMinWidth(200);
        yearCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("year"));

        TableColumn<Stagiaire, String> departmentCol = new TableColumn<>("Département");
        departmentCol.setMinWidth(200);
        departmentCol.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("department"));

        table.getColumns().addAll(lastNameCol,firstNameCol,promotionCol,yearCol,departmentCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        try {
            BinManager binManager = new BinManager();
            setTable(binManager.getAll( 0, new ArrayList<Stagiaire>()));
        } catch (FileNotFoundException e) {
            logger.info("Fichier non trouvé");
        } catch (IOException e) {
            logger.info("PErsonne dans le fichier");
        }

        getChildren().add(table);

        setRightAnchor(table,5.);
        setLeftAnchor(table,5.);
        setTopAnchor(table,5.);
        setBottomAnchor(table,5.);
    }

    public void setTable(List<Stagiaire> list) {
        table.setItems(FXCollections.observableList(list));
        table.refresh();
    }


}
