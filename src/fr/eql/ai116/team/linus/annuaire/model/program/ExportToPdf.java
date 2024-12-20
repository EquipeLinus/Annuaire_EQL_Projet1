package fr.eql.ai116.team.linus.annuaire.model.program;

import com.aspose.pdf.BorderInfo;
import com.aspose.pdf.BorderSide;
import com.aspose.pdf.Cell;
import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.MarginInfo;
import com.aspose.pdf.Page;
import com.aspose.pdf.Position;
import com.aspose.pdf.Row;
import com.aspose.pdf.Table;
import com.aspose.pdf.TextBuilder;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.internal.html.dom.Text;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileNotFoundException;


public class ExportToPdf {

    private static final Logger logger = LogManager.getLogger();

    public static boolean exportAnchorPaneViewStagiaireToPdf(TableView<Stagiaire> tableAnchorPane){

        Document doc = new Document();
        //Ajouter une page
        Page page = doc.getPages().add();

        TextFragment textFragment = new TextFragment("Liste des stagiaires");
        textFragment.setPosition(new Position(40, 0));
        textFragment.getTextState().setFontSize(22);



        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        table.setLeft(40);

        Stagiaire item = null;
        TableColumn col = null;
        String transform = null;

        for (int row_count = 1; row_count < tableAnchorPane.getItems().size()
                ; row_count++) {
            Row row = table.getRows().add();

            item = tableAnchorPane.getItems().get(row_count);

            col = tableAnchorPane.getColumns().get(0);
            Cell cell = row.getCells().add((String) col.getCellObservableValue(item).getValue() );



            col = tableAnchorPane.getColumns().get(1);
            row.getCells().add((String) col.getCellObservableValue(item).getValue() );


            col = tableAnchorPane.getColumns().get(2);
            row.getCells().add((String) col.getCellObservableValue(item).getValue() );

            col = tableAnchorPane.getColumns().get(3);
            transform = col.getCellObservableValue(item).getValue().toString();
            row.getCells().add(transform);

            col = tableAnchorPane.getColumns().get(4);
            transform = col.getCellObservableValue(item).getValue().toString();
            row.getCells().add(transform);
        }

        doc.getPages().get_Item(1).getParagraphs().add(table);

        doc.save( "ExportTable.pdf");
        logger.info("Le pdf a été créé");
        return true;
    }
}
