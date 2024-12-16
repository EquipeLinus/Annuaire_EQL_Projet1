package fr.eql.ai116.team.linus.annuaire.view;

import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import fr.eql.ai116.team.linus.annuaire.model.program.BinManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnchorPaneViewStagiaire extends AnchorPane {

    public ObservableList<Stagiaire> getStagiaireListDao() {
        return stagiaireListDao;
    }

    public void setStagiaireListDao(List<Stagiaire> stagiaireListDao) {
        this.stagiaireListDao = FXCollections.observableList(stagiaireListDao);
    }

    private ObservableList<Stagiaire> stagiaireListDao;

    public AnchorPaneViewStagiaire(TableView<Stagiaire> table){

        super();

        try {
            BinManager binManager = new BinManager();
            List<Stagiaire> stagiaireList = binManager.getAll( 0, new ArrayList<Stagiaire>());
            setStagiaireListDao(stagiaireList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.setEditable(false);

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
        table.setItems(stagiaireListDao);

        getChildren().add(table);

        setRightAnchor(table,5.);
        setLeftAnchor(table,5.);
        setTopAnchor(table,5.);
        setBottomAnchor(table,5.);
    }
}
