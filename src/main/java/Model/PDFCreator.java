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

import java.util.ArrayList;
import java.util.Date;

/**Genere le PDF qui presente tous les graphes.
 * @author Sean Graux
 * @version 1.0
 */
public class PDFCreator {

    private final String pathToPNGs = "src/Charts";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static ArrayList<Annee> listeAnnee;
    private static Data data = new Data();


    public void generatePDF() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGs + "/ExtractTotal.pdf"));
        document.open();
        addTitle(document, "");

        Document documentGlobal = new Document();
        PdfWriter.getInstance(documentGlobal, new FileOutputStream(pathToPNGs + "/ExtractRésumé.pdf"));
        documentGlobal.open();
        addTitle(documentGlobal, "");

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
                    if(child.getName().contains("GLOBAL")) documentGlobal.add(img);
                }
            }
        }
        document.close();
        documentGlobal.close();
    }

    //genère pdf avec png pour symphonie SAE
    public void generatePDFSAE() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGs + "/ExtractSAE.pdf"));
        document.open();
        addTitle(document, "SAE");
        document.add(this.createTable());
        float scaler;
        Image img;

        File chartsDirectory = new File(pathToPNGs);
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().contains(".png")) {
                    if(child.getName().contains("M01") || child.getName().contains("M03") || child.getName().contains("M04") || child.getName().contains("M13") || child.getName().contains("SAE")) {
                        img = Image.getInstance(pathToPNGs + "/" + child.getName());
                        scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                - document.rightMargin() - 0) / img.getWidth()) * 100;
                        img.scalePercent(scaler);
                        document.add(img);
                    }
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

        PdfPTable table = new PdfPTable(9);

        table.setWidthPercentage(100);

        addTabTopPanneEquip(listeAnnee.get(2), table);
        addTabTopPanneEquip(listeAnnee.get(1), table);
        addTabTopPanneEquip(listeAnnee.get(0), table);

        return table;
    }

    public static void addHeader(PdfPTable parTable, String parAnnee){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);

        PdfPCell cellHeader = new PdfPCell(new Phrase("TOP PANNES EQUIPEMENT ANNEE " + parAnnee,font));
        PdfPCell cellTop = new PdfPCell(new Phrase("Top #",font));
        PdfPCell cellM1 = new PdfPCell(new Phrase("Metro 1",font));
        PdfPCell cellM3 = new PdfPCell(new Phrase("Metro 3",font));
        PdfPCell cellM4 = new PdfPCell(new Phrase("Metro 4",font));
        PdfPCell cellM13 = new PdfPCell(new Phrase("Metro 13",font));

        cellHeader.setColspan(9);
        cellTop.setColspan(1);
        cellM1.setColspan(2);
        cellM3.setColspan(2);
        cellM4.setColspan(2);
        cellM13.setColspan(2);

        Style.headerCellStyle(cellHeader, 0);
        Style.headerCellStyle(cellTop, 0);
        Style.headerCellStyle(cellM1, 1);
        Style.headerCellStyle(cellM3, 3);
        Style.headerCellStyle(cellM4, 4);
        Style.headerCellStyle(cellM13, 13);

        parTable.addCell(cellHeader);
        parTable.addCell(cellTop);
        parTable.addCell(cellM1);
        parTable.addCell(cellM3);
        parTable.addCell(cellM4);
        parTable.addCell(cellM13);
    }

    public static void addTabTopPanneEquip(Annee parAnnee, PdfPTable parTable){
        String[][] tabPannes = new String[data.getTabLinesSAE().length][5];//{parAnnee.getTopPanneEquipLigne(1),parAnnee.getTopPanneEquipLigne(3), parAnnee.getTopPanneEquipLigne(4), parAnnee.getTopPanneEquipLigne(13)};
        for(int i = 0; i < data.getTabLinesSAE().length; i++){
            tabPannes[i] = parAnnee.getTopPanneEquipLigne(data.getTabLinesSAE()[i]);
        }
        String[] temp;

        addHeader(parTable, ""+parAnnee.getAnneeInt());

        for(int i = 0; i < 5; i++){
            parTable.addCell(createValueCell("#"+ (i+1),0));
            for(int j = 0; j < 4; j++){
                temp = tabPannes[j][i].split("-");
                parTable.addCell(createValueCell(temp[0], j+1));
                parTable.addCell(createValueCell(temp[1], j+1));
            }
        }
    }

    private static PdfPCell createLabelCell(String text){

        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.labelCellStyle(cell);
        return cell;
    }

    private static PdfPCell createValueCell(String text, int parNumber){
        // font
        Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text,font));

        // set style
        Style.valueCellStyle(cell, parNumber);
        return cell;
    }

    public static void setListeAnne(ArrayList<Annee> parListe){
        listeAnnee = parListe;
    }
}
