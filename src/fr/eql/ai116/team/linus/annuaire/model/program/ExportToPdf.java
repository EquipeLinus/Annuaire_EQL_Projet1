package fr.eql.ai116.team.linus.annuaire.model.program;
/*
import com.aspose.pdf.BorderInfo;
import com.aspose.pdf.BorderSide;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.Row;
import com.aspose.pdf.Table;
import fr.eql.ai116.team.linus.annuaire.model.entity.Stagiaire;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.awt.Color;
import java.io.IOException;


 */
public class ExportToPdf {
/*
    public static void exportAnchorPaneViewStagiaireToPdf(TableView<Stagiaire> tableAnchorPane) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            int pageWidth = (int)page.getTrimBox().getWidth(); //get width of the page
            int pageHeight = (int)page.getTrimBox().getHeight(); //get height of the page

            PDPageContentStream contentStream = new PDPageContentStream(document,page);
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1);

            int initX = 50;
            int initY = pageHeight-50;
            int cellHeight = 30;
            int cellWidth = 110;

            int colCount = 5;
            int rowCount = 100;


            for(int i = 1; i<=rowCount;i++){
                for(int j = 1; j<=colCount;j++){
                    if(j == 2){
                        contentStream.addRect(initX,initY,cellWidth,-cellHeight);

                        contentStream.beginText();
                        contentStream.newLineAtOffset(initX+30,initY-cellHeight+10);
                        contentStream.setFont(PDType1Font.TIMES_ROMAN,10);
                        contentStream.showText("Dinuka");
                        contentStream.endText();

                        initX+=cellWidth;

                    }else{
                        contentStream.addRect(initX,initY,cellWidth,-cellHeight);

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.TIMES_ROMAN,10);
                        contentStream.newLineAtOffset(initX+30,initY-cellHeight+10);

                        Stagiaire item = tableAnchorPane.getItems().get(i);
                        TableColumn col = tableAnchorPane.getColumns().get(0);
                        contentStream.showText((String) col.getCellObservableValue(item).getValue());

                        contentStream.endText();

                        initX+=cellWidth;
                    }
                }
                initX = 50;
                initY -=cellHeight;
            }
            contentStream.stroke();
            contentStream.close();


            document.save("table.pdf");
            document.close();
            System.out.println("table pdf created");
        } catch(IOException e) {

            e.printStackTrace();
        }

    }

    /*
    public static void exportAnchorPaneViewStagiaireToPdf(TableView<Stagiaire> tableAnchorPane) {
        Document doc = new Document();

        //Ajouter une page
        Page page = doc.getPages().add();

        Table table = new Table();


        table.setBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));

        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));

        for (int row_count = 1; row_count < 10; row_count++) {

            Row row = table.getRows().add();

            row.getCells().add("Column (" + row_count + ", 1)");
            row.getCells().add("Column (" + row_count + ", 2)");
            row.getCells().add("Column (" + row_count + ", 3)");
        }

        doc.getPages().get_Item(1).getParagraphs().add(table);

        doc.save( "document_with_table.pdf");
    }







     */

}
