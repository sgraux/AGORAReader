package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;

public class PDFCreator {

    private final String pathToPNGs = "src/Charts";

    public void generatePDF() throws FileNotFoundException, DocumentException, MalformedURLException, Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pathToPNGs+"/rapport.pdf"));
        document.open();
        float scaler;// = 0;
        Image img;// = Image.getInstance(pathToPNGs+"/armoire.png");

        File chartsDirectory = new File("src/Charts");
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getName().contains(".png")) {
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
}
