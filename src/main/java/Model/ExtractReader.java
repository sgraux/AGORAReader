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
        this.read();
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

        int testCountRows = 0;

        while (rowIterator.hasNext()){
            testCountRows++;
            currentRow = rowIterator.next();
            cellIterator = currentRow.cellIterator();
            while (cellIterator.hasNext()){
                currentCell = cellIterator.next();
                switch (countCell){
                    case 0:
                        yearCellContent = ""+currentCell.getNumericCellValue();
                        break;
                    case 2:
                        equipmentCellContent = currentCell.getStringCellValue();
                        break;
                    case 3:
                        clientCellContent  = currentCell.getStringCellValue();
                        break;
                }
                countCell++;
                this.manageCellsContent(yearCellContent, equipmentCellContent, clientCellContent);
            }
        }
        System.out.println(testCountRows);
    }

    public void manageCellsContent(String yearCellContent, String equipmentCellContent, String clientCellContent){


    }

    public String[] cutDate(String dateToCut){
        String processedDate[] = new String[2];

        return processedDate;
    }


}
