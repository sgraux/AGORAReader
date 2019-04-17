package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import com.itextpdf.text.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;

import java.util.Date;

/**Genere le PDF qui presente tous les graphes.
 * @author Sean Graux
 * @version 1.0
 */
public class PDFCreator {

    private final String pathToPNGs = "src/Charts";
    private final String pathToPNGsSAE = "src/ChartsSAE";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    //TODO: create tab with top incidents
    public void generatePDF() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGs + "/Extract.pdf"));
        document.open();
        addTitle(document, "");
        float scaler;
        Image img;

        File chartsDirectory = new File(pathToPNGs);
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().contains(".png")) {
                    img = Image.getInstance(pathToPNGs + "/" + child.getName());
                    scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / img.getWidth()) * 100;
                    img.scalePercent(scaler);
                    document.add(img);
                }
            }
        }
        document.close();
    }

    public void generatePDFSAE() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGsSAE + "/ExtractSAE.pdf"));
        document.open();
        addTitle(document, "SAE");
        document.add(this.createTable());
        float scaler;
        Image img;

        File chartsDirectory = new File(pathToPNGsSAE);
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().contains(".png")) {
                    img = Image.getInstance(pathToPNGsSAE + "/" + child.getName());
                    scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / img.getWidth()) * 100;
                    img.scalePercent(scaler);
                    document.add(img);
                }
            }
        }
        document.close();
    }

    private static void addTitle(Document document, String parString)
            throws DocumentException {
        Paragraph title = new Paragraph("Extraction OT "+ parString + " --- " + new Date(), catFont);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(title, 3);
        document.add(title);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static PdfPTable createTable() throws DocumentException {

        // create 6 column table
        PdfPTable table = new PdfPTable(9);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        // set relative columns width
        //table.setWidths(new float[]{0.6f, 1.4f, 0.8f,0.8f,1.8f,2.6f});

        // ----------------Table Header "Title"----------------
        // font

        //-----------------Table Cells Label/Value------------------

        //TODO: add real values
        addHeader(table, "N-2");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                table.addCell(createValueCell("value", j));
            }
        }
        addHeader(table, "N-1");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                table.addCell(createValueCell("value", j));
            }
        }
        addHeader(table, "N");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                table.addCell(createValueCell("value", j));
            }
        }
        // 1st Row
        /*table.addCell(createLabelCell("Label 1"));
        table.addCell(createValueCell("Value 1"));
        table.addCell(createLabelCell("Label 2"));
        table.addCell(createValueCell("Value 2"));
        table.addCell(createLabelCell("Label 3"));
        table.addCell(createValueCell("Value 3"));

        // 2nd Row
        table.addCell(createLabelCell("Label 4"));
        table.addCell(createValueCell("Value 4"));
        table.addCell(createLabelCell("Label 5"));
        table.addCell(createValueCell("Value 5"));
        table.addCell(createLabelCell("Label 6"));
        table.addCell(createValueCell("Value 6"));*/

        return table;
    }

    public static void addHeader(PdfPTable parTable, String parAnnee){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        // create header cell
        PdfPCell cellHeader = new PdfPCell(new Phrase("TOP PANNES EQUIPEMENT ANNEE " + parAnnee,font));
        PdfPCell cellTop = new PdfPCell(new Phrase("Top #",font));
        PdfPCell cellM1 = new PdfPCell(new Phrase("Metro 1",font));
        PdfPCell cellM3 = new PdfPCell(new Phrase("Metro 3",font));
        PdfPCell cellM4 = new PdfPCell(new Phrase("Metro 4",font));
        PdfPCell cellM13 = new PdfPCell(new Phrase("Metro 13",font));

        // set Column span "1 cell = 6 cells width"
        cellHeader.setColspan(9);
        cellTop.setColspan(1);
        cellM1.setColspan(2);
        cellM3.setColspan(2);
        cellM4.setColspan(2);
        cellM13.setColspan(2);

        // set style
        Style.headerCellStyle(cellHeader, 0);
        Style.headerCellStyle(cellTop, 0);
        Style.headerCellStyle(cellM1, 1);
        Style.headerCellStyle(cellM3, 3);
        Style.headerCellStyle(cellM4, 4);
        Style.headerCellStyle(cellM13, 13);

        // add to table
        parTable.addCell(cellHeader);
        parTable.addCell(cellTop);
        parTable.addCell(cellM1);
        parTable.addCell(cellM3);
        parTable.addCell(cellM4);
        parTable.addCell(cellM13);
    }

    // create cells
    private static PdfPCell createLabelCell(String text){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.labelCellStyle(cell);
        return cell;
    }

    // create cells
    private static PdfPCell createValueCell(String text, int parNumber){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.valueCellStyle(cell, parNumber);
        return cell;
    }
}
