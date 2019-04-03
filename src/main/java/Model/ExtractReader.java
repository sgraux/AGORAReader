package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public abstract class ExtractReader {

    private XSSFWorkbook extract;

    public ExtractReader(String parPathToExtract){

        File tempFile = new File(parPathToExtract);

        try {
            FileInputStream tempInputStream = new FileInputStream(tempFile);
            extract = new XSSFWorkbook(tempInputStream);
        }
        catch(FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        }
        catch (IOException e){
            System.out.println("io exception");
        }

        this.read();
    }

    public void read(){
        Iterator<Row> rowIterator = extract.getSheetAt(0).iterator();
        Row currentRow;
        Iterator<Cell> cellIterator;

        while (rowIterator.hasNext()){
            currentRow = rowIterator.next();
            cellIterator = currentRow.cellIterator();
            while (cellIterator.hasNext()){
                
            }
        }
    }


}
