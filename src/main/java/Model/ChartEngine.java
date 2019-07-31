package Model;

import data.Data;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;

/**Genere tous les graphes et les enregesitre  dans /src/Charts
 * @author Sean Graux
 * @version 1.0
 */
public class ChartEngine extends Application {

    private ExtractReader reader;
    ArrayList<Annee> listYears;
    private Data data = new Data();
    private String mode;
    private String inputPath;
    private String outputPath;
    private JOptionPane optionpane;

    private boolean isDone;

    public ChartEngine(String parMode, String parInputPath, String parOutputPath, JOptionPane parOptionpane){
        mode = parMode;
        inputPath = parInputPath;
        outputPath = parOutputPath;
        optionpane = parOptionpane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //TODO: add correct way to find input extract file

        isDone = false;
        double start = System.currentTimeMillis();
        optionpane.setMessage(optionpane.getMessage()+"\n  - Reading");
        reader = new ExtractReader(inputPath);
        double endRead = System.currentTimeMillis();
        listYears = reader.giveYear();

        //TODO: add graphic interface to display running information
        System.out.print("--- GENERATE CHARTS --- \n");
        optionpane.setMessage(optionpane.getMessage()+"\n - Generate Charts");
        if(mode.equals("SAE")) {
            this.generateAllCharts(new Stage());
        }
        else if(mode.equals("Global")){

        }
        else{
            this.generateChartsSelection(new Stage(), mode);
        }
        optionpane.setMessage(optionpane.getMessage()+"\n - PNGs done");
        System.out.print("--- PNGs DONE --- \n");
        double endPngs = System.currentTimeMillis();

        optionpane.setMessage(optionpane.getMessage()+"\n - Create PDFs");
        PDFCreator creator = new PDFCreator();
        PDFCreator.setOutputPath(outputPath);
        PDFCreator.setListeAnne(listYears);

        //creator.generatePDF();
        creator.generatePDFSAE();
        creator.generatePDFTopLieu();

        System.out.print("--- PDFs DONE --- \n");
        double endPdfs = System.currentTimeMillis();
        optionpane.setMessage(optionpane.getMessage()+"\n - End");
        System.out.println("--- STOP ---");
        Platform.exit();

        double end = System.currentTimeMillis();
        System.out.println("TEMPS LECTURE = " + (endRead - start)/1000 + "sec\n"
                        + "TEMPS PNGS = " + (endPngs - endRead)/1000 + "sec\n"
                        + "TEMPS PDFS = " + (endPdfs - endPngs)/1000 + "sec\n"
                        + "TEMPS EXEC = " + (end - start)/1000 + "sec\n");

        /*for(Annee currentAnnee : listYears){
            System.out.println("--- " +currentAnnee.getAnneeInt() + " ---");
            for(int i = 0; i < 12; i ++){
                String[] temp = currentAnnee.getMoisIndex(i).getMaxLieu();
                System.out.println(data.getTabMois()[i] + ":" + temp[0] + " - " + temp[1]);
            }
        }*/

        /*Hashtable<String, Integer> hash = listYears.get(0).getMoisIndex(0).getHashLieu();
        for(String currentKey : hash.keySet()){
            System.out.println(currentKey + " - " + hash.get(currentKey));
        }*/

        isDone = true;
    }

    public void generateChartsSelection(Stage stage, String parSelectionFamille)throws MalformedURLException{
        this.cleanCharts("src/Charts");
        this.barChat(stage);
        //this.generateBarChartRepartitionEquipementSAE(stage, parSelectionFamille, 1);
        this.generateBarChartEvolutionEquipementSAE(stage, parSelectionFamille, 1);
        for(String currentLine : data.getTabLinesSAE()){
            this.generateBarChartEvolutionEquipementLigne(stage, parSelectionFamille, 1, currentLine, false);
        }
    }

    //TODO: merge fcts to limitate for loops
    //TODO: add setStyle fct for X and Y
    public void generateAllCharts(Stage stage)throws MalformedURLException{
        this.cleanCharts("src/Charts");
        this.barChat(stage);
        String[] tabEquip;

        for(int i = 0; i < data.getTabEquip().length; i++){

            tabEquip = data.getTabEquip();
           // this.generateBarChartRepartitionEquipement(stage, tabEquip[i], i+2);
            this.generateBarChartRepartitionEquipementSAE(stage, tabEquip[i], i+2);
            //this.generateLineChartEvolutionEquipement(stage, tabEquip[i], i+2);
            //this.generateBarChartEvolutionEquipement(stage, tabEquip[i], i+2);
            //this.generateLineChartEvolutionEquipementSAE(stage, tabEquip[i], i+2);
            this.generateBarChartEvolutionEquipementSAE(stage, tabEquip[i], i+2);

            for(String currentLine : data.getTabLinesSAE()){
                //this.generateLineChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, false);
                this.generateBarChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, false);
                /*if(currentLine.equals("M01"))
                    this.generateLineChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, true);
                else if(currentLine.equals("M03"))
                    this.generateLineChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, true);
                else if(currentLine.equals("M04"))
                    this.generateLineChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, true);
                else if(currentLine.equals("M13"))
                    this.generateLineChartEvolutionEquipementLigne(stage, tabEquip[i], i+2, currentLine, true);*/
            }
        }
    }

    public void barChat(Stage stage) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        int OTsMonthPerYear;

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Proportion OT");

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], OTsMonthPerYear));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], OTsMonthPerYear));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], OTsMonthPerYear));
        }

        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/1 - ProportionOts_GLOBAL.png");
        stage.setScene(scene);


    }

    //TODO: merge RepartitionEquipement et RapartitionEquipementSAE
    public void generateBarChartRepartitionEquipement(Stage stage, String parEquipement, int chartNumber){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, compareMaxAnnee(listYears.get(0).getMaxOTEquip(parEquipement),listYears.get(1).getMaxOTEquip(parEquipement),listYears.get(2).getMaxOTEquip(parEquipement)),5);

        int[] OTsLinesN2 = listYears.get(0).getSumOTsLines(parEquipement);
        int[] OTsLinesN1 = listYears.get(1).getSumOTsLines(parEquipement);
        int[] OTsLinesN = listYears.get(2).getSumOTsLines(parEquipement);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Répartition OT " + parEquipement + " --- TOUTES LIGNES\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN2[i]));
        }
        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN1[i]));
        }
        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN[i]));
        }


        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart_" + parEquipement + "_GLOBAL.png");
        stage.setScene(scene);
    }

    public void generateBarChartRepartitionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        int[] OTsLinesN2 = listYears.get(0).getSumOTsLines(parEquipement);
        int[] OTsLinesN1 = listYears.get(1).getSumOTsLines(parEquipement);
        int[] OTsLinesN = listYears.get(2).getSumOTsLines(parEquipement);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Répartition " + parEquipement + "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                seriesN2.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN2[i]));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                seriesN1.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN1[i]));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                seriesN.getData().add(new XYChart.Data(data.getTabLines()[i], OTsLinesN[i]));
        }


        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);


        saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);


    }

    //TODO: think about merging EvoEquip et EvoEquipSAE by adding list of SAE lines in Data
    public void generateLineChartEvolutionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + " --- TOUTES LIGNES\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));
        lineChart.setAnimated(false);
        lineChart.getData().add(seriesN2);
        lineChart.getData().add(seriesN1);
        lineChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - lineChart_" + parEquipement + "_GLOBAL.png");
        stage.setScene(scene);
    }

    public void generateLineChartEvolutionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + "\nLignes : M01, M03, M04, M13" + "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        lineChart.setAnimated(false);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        lineChart.getData().add(seriesN2);
        lineChart.getData().add(seriesN1);
        lineChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - lineChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);
    }

    public void generateBarChartEvolutionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> lineChart = new BarChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + "\nLignes : M01, M03, M04, M13" + "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        lineChart.setAnimated(false);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        lineChart.getData().add(seriesN2);
        lineChart.getData().add(seriesN1);
        lineChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - BarChart_Evolution_SAE_" + parEquipement + ".png");
        stage.setScene(scene);
    }

    public void generateLineChartEvolutionEquipementLigne(Stage stage, String parEquipement, int chartNumber, String parLigne, boolean estSymphonieSAE) throws MalformedURLException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement +"\nLigne : " +parLigne+ "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        Scene scene = new Scene(lineChart, 1200, 800);

        lineChart.setAnimated(false);
        lineChart.getData().add(seriesN2);//année N-2
        lineChart.getData().add(seriesN1);//année N-1
        lineChart.getData().add(seriesN);//année N en cours

        stage.setScene(scene);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        lineChart.setCreateSymbols(false);

        if(parLigne.equals("M01")){
            //jaune
            lineChart.setStyle("-fx-background-color: #ffff4d;"); /*"-fx-background-color: #ffff1a;"*/
        }
        else if(parLigne.equals("M02")){
            lineChart.setStyle("-fx-background-color: #66b3ff;");
        }
        else if(parLigne.equals("M03")){
            lineChart.setStyle("-fx-background-color: #b3b300;");
        }
        else if(parLigne.equals("M04")){
            //violet
            lineChart.setStyle("-fx-background-color: #d966ff;");
        }
        else if(parLigne.equals("M05")){
            lineChart.setStyle("-fx-background-color: #ff8533;");
        }
        else if(parLigne.equals("M06")){
            lineChart.setStyle("-fx-background-color: #47d147;");
        }
        else if(parLigne.equals("M07")){
            lineChart.setStyle("-fx-background-color: #ffb3b3;");
        }
        else if(parLigne.equals("M08")){
            lineChart.setStyle("-fx-background-color: #eb99ff;");
        }
        else if(parLigne.equals("M09")){
            lineChart.setStyle("-fx-background-color: #cccc00;");
        }
        else if(parLigne.equals("M10")){
            lineChart.setStyle("-fx-background-color: #ffbf00;");
        }
        else if(parLigne.equals("M11")){
            lineChart.setStyle("-fx-background-color: #cc9966;");
        }
        else if(parLigne.equals("M12")){
            lineChart.setStyle("-fx-background-color: #00e600;");
        }
        else if(parLigne.equals("M13")) {
            //bleu
            lineChart.setStyle("-fx-background-color: #99ffff;");
        }

        if(estSymphonieSAE)
            saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - lineChart_SAE_"+parLigne+"_" + parEquipement + ".png");
        else
            saveAsPng(scene, "src/Charts/"+ chartNumber+" - lineChart_"+parLigne+"_" + parEquipement + "_DETAIL.png");
    }

    public void generateBarChartEvolutionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Evolution OT " + parEquipement + " --- TOUTES LIGNES\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        Scene scene = new Scene(barChart, 1200, 800);
        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));
        barChart.setAnimated(false);
        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart_Evolution_" + parEquipement + "_GLOBAL.png");
        stage.setScene(scene);
    }

    public void generateBarChartEvolutionEquipementLigne(Stage stage, String parEquipement, int chartNumber, String parLigne, boolean estSymphonieSAE) throws MalformedURLException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, compareMaxAnnee(listYears.get(0).getMaxOTEquip(parEquipement),listYears.get(1).getMaxOTEquip(parEquipement),listYears.get(2).getMaxOTEquip(parEquipement))+5,5);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Evolution OT " + parEquipement +"\nLigne : " +parLigne+ "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getOTsLigne(parLigne)));
        }

        Scene scene = new Scene(barChart, 1200, 800);

        barChart.setAnimated(false);
        barChart.getData().add(seriesN2);//année N-2
        barChart.getData().add(seriesN1);//année N-1
        barChart.getData().add(seriesN);//année N en cours

        stage.setScene(scene);

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        if(parLigne.equals("M01")){
            //jaune
            barChart.setStyle("-fx-background-color: #ffff4d;"); /*"-fx-background-color: #ffff1a;"*/
        }
        else if(parLigne.equals("M02")){
            barChart.setStyle("-fx-background-color: #66b3ff;");
        }
        else if(parLigne.equals("M03")){
            barChart.setStyle("-fx-background-color: #b3b300;");
        }
        else if(parLigne.equals("M04")){
            //violet
            barChart.setStyle("-fx-background-color: #d966ff;");
        }
        else if(parLigne.equals("M05")){
            barChart.setStyle("-fx-background-color: #ff8533;");
        }
        else if(parLigne.equals("M06")){
            barChart.setStyle("-fx-background-color: #47d147;");
        }
        else if(parLigne.equals("M07")){
            barChart.setStyle("-fx-background-color: #ffb3b3;");
        }
        else if(parLigne.equals("M08")){
            barChart.setStyle("-fx-background-color: #eb99ff;");
        }
        else if(parLigne.equals("M09")){
            barChart.setStyle("-fx-background-color: #cccc00;");
        }
        else if(parLigne.equals("M10")){
            barChart.setStyle("-fx-background-color: #ffbf00;");
        }
        else if(parLigne.equals("M11")){
            barChart.setStyle("-fx-background-color: #cc9966;");
        }
        else if(parLigne.equals("M12")){
            barChart.setStyle("-fx-background-color: #00e600;");
        }
        else if(parLigne.equals("M13")) {
            //bleu
            barChart.setStyle("-fx-background-color: #99ffff;");
        }

        if(estSymphonieSAE)
            saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - lineChart_SAE_"+parLigne+"_" + parEquipement + ".png");
        else
            saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart-Evolution_"+parLigne+"_" + parEquipement + "_DETAIL.png");
    }

    public void saveAsPng(Scene scene, String path) {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanCharts(String pathToDirectory){
        File chartsDirectory = new File(pathToDirectory);
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                child.delete();
            }
        }

    }

    public int compareMaxAnnee(int maxN2, int maxN1, int maxN){
        int max = maxN2;
        if(maxN1 > max){
            max = maxN1;
        }
        if(maxN > max){
            max = maxN;
        }
        return max;
    }

    public boolean getIsDone(){
        return isDone;
    }

}
