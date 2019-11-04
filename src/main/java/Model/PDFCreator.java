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
public class PDFCreator { //Gère la création des PDFs de résultat

    private final String pathToPNGs = "src/Charts";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static ArrayList<Annee> listeAnnee;
    private static Data data = new Data();
    private static String outputPath;



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
        PdfWriter.getInstance(document, new FileOutputStream(outputPath + "/Stats.pdf"));
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

    //Génère le pdf contenant le top lieu des incidents
    /*public void generatePDFTopLieu() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGs + "/TopLieu.pdf"));
        document.open();
        addTitle(document, "");

        document.add(this.createTableTopLieu());
        document.close();
    }*/

    //Ajoute un titre au pdf
    private static void addTitle(Document document, String parString)
            throws DocumentException {
        Paragraph title = new Paragraph("Extraction OT "+ parString + " --- " + new Date(), catFont);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(title, 3);
        document.add(title);
    }

    //ajoute une ligne vide au pdf
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    //Créer un tableau
    public static PdfPTable createTable() throws DocumentException {

        PdfPTable table = new PdfPTable(9);

        table.setWidthPercentage(100);

        addTabTopPanneEquip(listeAnnee.get(2), table);
        addTabTopPanneEquip(listeAnnee.get(1), table);
        addTabTopPanneEquip(listeAnnee.get(0), table);

        return table;
    }

    //ajoute l'entête du tableau
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

    //Ajoute le top des pannes d'équipement par année et par ligne
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

    //créer le tableau du top lieu des incidents
    /*public static PdfPTable createTableTopLieu() throws DocumentException{
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        addHeaderTopLieuxGen(table);
        addTopLieuxAnnee(listeAnnee.get(2), table);
        addTopLieuxAnnee(listeAnnee.get(1), table);
        addTopLieuxAnnee(listeAnnee.get(0), table);

        return table;
    }

    //Ajoute l'entête du tableau top lieu
    private static void addHeaderTopLieuxGen(PdfPTable table){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);

        PdfPCell cellTitle = new PdfPCell(new Phrase("TOP LIEUX",font));
        PdfPCell cellAnnee = new PdfPCell(new Phrase("Année",font));
        PdfPCell cellMois = new PdfPCell(new Phrase("Mois",font));
        PdfPCell cellTop = new PdfPCell(new Phrase("Top #",font));
        PdfPCell cellM1 = new PdfPCell(new Phrase("General",font));
        /*PdfPCell cellM3 = new PdfPCell(new Phrase("Metro 3",font));
        PdfPCell cellM4 = new PdfPCell(new Phrase("Metro 4",font));
        PdfPCell cellM13 = new PdfPCell(new Phrase("Metro 13",font));

        cellTitle.setColspan(5);
        cellAnnee.setColspan(1);
        cellMois.setColspan(1);
        cellTop.setColspan(1);
        cellM1.setColspan(2);
        /*cellM3.setColspan(2);
        cellM4.setColspan(2);
        cellM13.setColspan(2);

        Style.headerCellStyle(cellTitle, 0);
        Style.headerCellStyle(cellAnnee, 0);
        Style.headerCellStyle(cellMois, 0);
        Style.headerCellStyle(cellTop, 0);
        Style.headerCellStyle(cellM1, 1);
        /*Style.headerCellStyle(cellM3, 3);
        Style.headerCellStyle(cellM4, 4);
        Style.headerCellStyle(cellM13, 13);

        table.addCell(cellTitle);
        table.addCell(cellAnnee);
        table.addCell(cellMois);
        table.addCell(cellTop);
        table.addCell(cellM1);
        /*table.addCell(cellM3);
        table.addCell(cellM4);
        table.addCell(cellM13);
    }

    //ajoute le top lieu dans son tableau
    /*private static void addTopLieuxAnnee(Annee parAnnee, PdfPTable parTable){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        PdfPCell cellN = new PdfPCell(new Phrase("Année " + parAnnee.getAnneeInt(),font));
        cellN.setRowspan(36);
        cellN.setVerticalAlignment(Element.ALIGN_CENTER);
        Style.headerCellStyle(cellN, 0);
        parTable.addCell(cellN);

        String[][][] tabTop = parAnnee.getMaxLieuAnnee();

        for(int month = 0; month < 12; month++){
            parTable.addCell(createMonthCell(data.getTabMois()[month]));
            for(int top = 0; top < 3; top++){
                parTable.addCell(new PdfPCell(new Phrase("#" + (top+1),font)));
                parTable.addCell(new PdfPCell(new Phrase(tabTop[month][top][0],font)));
                parTable.addCell(new PdfPCell(new Phrase(tabTop[month][top][1],font)));
            }
        }
    }*/

    //Création des cellules
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

    private static PdfPCell createMonthCell(String parMois){
        Font font = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
        PdfPCell cellMois = new PdfPCell(new Phrase(parMois,font));
        cellMois.setRowspan(3);
        cellMois.setVerticalAlignment(Element.ALIGN_CENTER);
        Style.headerCellStyle(cellMois, 0);

        return cellMois;
    }

    public static void setListeAnne(ArrayList<Annee> parListe){
        listeAnnee = parListe;
    }

    public static void setOutputPath(String parOutputPath){
        outputPath = parOutputPath;
    }
}
