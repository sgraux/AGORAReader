package Model;

import jdk.nashorn.internal.parser.Token;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtractReader {

    private XSSFWorkbook extract;
    private ArrayList<Annee> yearList = new ArrayList<Annee>();

    public ExtractReader(String parPathToExtract){

        File tempFile = new File(parPathToExtract);

        try {
            System.out.println("--- START ----");
            FileInputStream tempInputStream = new FileInputStream(tempFile);
            //System.out.println("input stream done");
            System.out.println("--- READING ----");
            extract = new XSSFWorkbook(tempInputStream);
        }
        catch(FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        }
        catch (IOException e){
            System.out.println("io exception");
        }
        this.read();
        //System.out.println(yearList.get(0) + "\n" + yearList.get(1) + "\n" + yearList.get(2) + "\n");
    }

    public void read(){
        Iterator<Row> rowIterator = extract.getSheetAt(0).iterator();
        rowIterator.next(); //remove first line
        Row currentRow;

        Iterator<Cell> cellIterator;
        Cell currentCell;
        int countCell = 0;

        double yearCellContent = 0.0;
        String timeFinishCellContent = "";
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
                        yearCellContent = currentCell.getNumericCellValue();
                        break;
                    case 1:
                        timeFinishCellContent = ""+currentCell.getNumericCellValue();
                        break;
                    case 2:
                        equipmentCellContent = currentCell.getStringCellValue();
                        break;
                    case 3:
                        clientCellContent  = currentCell.getStringCellValue();
                        break;
                }
                countCell++;
            }
            this.manageCellsContent(yearCellContent, timeFinishCellContent, equipmentCellContent, clientCellContent);
            countCell = 0;
        }
        //System.out.println(testCountRows);
    }

    public void manageCellsContent(Double yearCellContent, String timeFinish, String equipmentCellContent, String clientCellContent){

        int[] tabDate = parseDate(yearCellContent);
        int currentYear = tabDate[1];
        int currentMonth = tabDate[0];

        if(yearList.isEmpty()){
            yearList.add(new Annee(currentYear));
            yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
        }
        else{
            if(yearList.size() == 1) {
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
                else{
                    yearList.add(new Annee(currentYear));
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
            }
            else if(yearList.size() == 2){
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
                else if (yearList.get(1).getAnneeInt() == currentYear) {
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
                else{
                    yearList.add(new Annee(currentYear));
                    yearList.get(2).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
            }
            else{
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
                else if (yearList.get(1).getAnneeInt() == currentYear) {
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
                else if (yearList.get(2).getAnneeInt() == currentYear) {
                    yearList.get(2).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parseClient(clientCellContent));
                }
            }
        }
    }

    public String parseClient(String clientToParse){

        String[] splited = clientToParse.split("-");
        if (splited.length > 1) return splited[1];
        else return "NA";

    }

    public int[] parseDate(double dateToParse){
        Date parsedDate= DateUtil.getJavaDate(dateToParse);
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        int[] tab = new int[2];
        tab[0] = Integer.parseInt(formatMonth.format(parsedDate));
        tab[1] = Integer.parseInt(formatYear.format(parsedDate));
        return tab;


    }

    public ArrayList<Annee> giveYear(){
        return yearList;
    }

    public Annee getAnneeIndex(int parIndex){
        return yearList.get(parIndex);
    }

}
