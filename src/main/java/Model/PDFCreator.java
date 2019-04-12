package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
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
}
