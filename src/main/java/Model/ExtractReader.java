package Model;

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

/**Lit un fichier .xlsx extrait de la GMAO.
 * Il doit contenir (dans cet ordre) les colonnes [Date Declaration, Date Termine, Famille BM, Client].
 * @author Sean Graux
 * @version 1.0
 */
public class ExtractReader { //Lit un extract AGORA au format .xlsx

    private XSSFWorkbook extract;
    private ArrayList<Annee> yearList = new ArrayList<Annee>();

    public ExtractReader(String parPathToExtract){

        File tempFile = new File(parPathToExtract);

        try {
            System.out.println("--- START ----");
            FileInputStream tempInputStream = new FileInputStream(tempFile);
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
    }

    public void read(){
        Iterator<Row> rowIterator = extract.getSheetAt(0).iterator();
        rowIterator.next(); //remove first line
        Row currentRow;

        Iterator<Cell> cellIterator;
        Cell currentCell;
        int countCell = 0;

        double yearCellContent = 0.0;
        String equipmentCellContent = "";
        String clientCellContent = "";
        String lieuCellContent = "";
        String descOTCellContent = "";

        int testCountRows = 0;//DEBUG

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
                        equipmentCellContent = currentCell.getStringCellValue();
                        break;
                    case 2:
                        clientCellContent  = currentCell.getStringCellValue();
                        break;
                    case 3:
                        descOTCellContent = currentCell.getStringCellValue();
                        break;
                }
                countCell++;
            }
            this.manageCellsContent(yearCellContent, equipmentCellContent, clientCellContent, descOTCellContent);
            countCell = 0;
        }
        //System.out.println(testCountRows);
        Collections.sort(yearList);
    }

    //Gère les données récupérées et les place dans la structure de données
    public void manageCellsContent(Double yearCellContent, String equipmentCellContent, String clientCellContent, String descOTCellContent){

        int[] tabDate = parseDate(yearCellContent);
        int currentYear = tabDate[1];
        int currentMonth = tabDate[0];

        if(yearList.isEmpty()){
            yearList.add(new Annee(currentYear));
            yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
        }
        else{
            if(yearList.size() == 1) {
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
                else{
                    yearList.add(new Annee(currentYear));
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
            }
            else if(yearList.size() == 2){
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
                else if (yearList.get(1).getAnneeInt() == currentYear) {
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
                else{
                    yearList.add(new Annee(currentYear));
                    yearList.get(2).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
            }
            else{
                if (yearList.get(0).getAnneeInt() == currentYear) {
                    yearList.get(0).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
                else if (yearList.get(1).getAnneeInt() == currentYear) {
                    yearList.get(1).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
                else if (yearList.get(2).getAnneeInt() == currentYear) {
                    yearList.get(2).getMoisIndex(currentMonth-1).manageEquipement(equipmentCellContent, parse(clientCellContent), descOTCellContent);
                }
            }
        }
    }

    public String parse(String stringToParse){

        String[] splited = stringToParse.split("-");
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
