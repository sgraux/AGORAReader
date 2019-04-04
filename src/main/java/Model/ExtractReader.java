package Model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExtractReader {

    private XSSFWorkbook extract;
    private ArrayList<Annee> yearList = new ArrayList<Annee>();

    public ExtractReader(String parPathToExtract){

        File tempFile = new File(parPathToExtract);

        try {
            System.out.println("Start try");
            FileInputStream tempInputStream = new FileInputStream(tempFile);
            System.out.println("input stream done");
            extract = new XSSFWorkbook(tempInputStream);
        }
        catch(FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        }
        catch (IOException e){
            System.out.println("io exception");
        }

        System.out.println("Launch read ...");
        //this.read();
    }

    public void read(){
        Iterator<Row> rowIterator = extract.getSheetAt(0).iterator();
        rowIterator.next(); //remove first line
        Row currentRow;

        Iterator<Cell> cellIterator;
        Cell currentCell;
        int countCell = 0;

        String yearCellContent = "";
        String equipmentCellContent = "";
        String clientCellContent = "";

        while (rowIterator.hasNext()){
            currentRow = rowIterator.next();
            cellIterator = currentRow.cellIterator();
            while (cellIterator.hasNext()){
                currentCell = cellIterator.next();
                switch (countCell){
                    case 11:
                        yearCellContent = currentCell.getStringCellValue();
                        break;
                    case 18:
                        equipmentCellContent = currentCell.getStringCellValue();
                        break;
                    case 21:
                        clientCellContent  = currentCell.getStringCellValue();
                        break;
                }
                this.manageCellsContent(yearCellContent, equipmentCellContent, clientCellContent);
            }
        }
    }

    public void manageCellsContent(String yearCellContent, String equipmentCellContent, String clientCellContent){


    }

    public String[] cutDate(String dateToCut){
        String processedDate[] = new String[2];

        return processedDate;
    }


}
