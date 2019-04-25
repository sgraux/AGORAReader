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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**Genere tous les graphes et les enregesitre  dans /src/Charts
 * @author Sean Graux
 * @version 1.0
 */
public class ChartEngine extends Application {

    private ExtractReader reader;
    ArrayList<Annee> listYears;

    private Data data = new Data();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //TODO: add correct way to find input extract file
        reader = new ExtractReader("C:\\Users\\Sean\\Documents\\STAGE - RATP\\Sheet1CUT.xlsx");
        listYears = reader.giveYear();

        //TODO: add graphic interface to display running information
        //Scene scene = new Scene(new Pane(new Text("... READING ...")),800, 800);
        //stage.setScene(scene);
        //stage.show();
        this.generateAllCharts(new Stage());
        System.out.print("--- PNGs DONE --- \n");

        PDFCreator creator = new PDFCreator();
        creator.setListeAnne(listYears);

        creator.generatePDF();
        creator.generatePDFSAE();
        System.out.print("--- PDFs DONE --- \n");
        System.out.print("--- STOP ---");
        Platform.exit();
    }

    public void generateAllCharts(Stage stage)throws MalformedURLException{
        this.cleanCharts("src/Charts");
        this.cleanCharts("src/ChartsSAE");
        this.barChat(stage);

        for(int i = 0; i < 6; i++){
            this.generateBarChartRepartitionEquipement(stage, data.getTabEquip()[i], i+2);
            this.generateBarChartRepartitionEquipementSAE(stage, data.getTabEquip()[i], i+2);
            this.generateLineChartEvolutionEquipement(stage, data.getTabEquip()[i], i+2);
            this.generateLineChartEvolutionEquipementSAE(stage, data.getTabEquip()[i], i+2);

            this.generateLineChartEvolutionEquipementLigneSAE(stage, data.getTabEquip()[i], i+2, 1);
            this.generateLineChartEvolutionEquipementLigneSAE(stage, data.getTabEquip()[i], i+2, 3);
            this.generateLineChartEvolutionEquipementLigneSAE(stage, data.getTabEquip()[i], i+2, 4);
            this.generateLineChartEvolutionEquipementLigneSAE(stage, data.getTabEquip()[i], i+2, 13);

        }
    }

    public void barChat(Stage stage) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
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
        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/1 - ProportionOts.png");
        stage.setScene(scene);
    }

    public void generateBarChartRepartitionEquipement(Stage stage, String parEquipement, int chartNumber){
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
        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart_" + parEquipement + ".png");
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
        barChart.getData().add(seriesN2);
        barChart.getData().add(seriesN1);
        barChart.getData().add(seriesN);


        saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - barChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);

    }

    public void generateLineChartEvolutionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + "\nCodes : " + data.getCodesEquipement(parEquipement));

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
        lineChart.setAnimated(false);
        lineChart.getData().add(seriesN2);
        lineChart.getData().add(seriesN1);
        lineChart.getData().add(seriesN);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - lineChart_" + parEquipement + ".png");
        stage.setScene(scene);
    }

    public void generateLineChartEvolutionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + " --- GLOBALE" + "\nCodes : " + data.getCodesEquipement(parEquipement));

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
        lineChart.getData().add(seriesN2);
        lineChart.getData().add(seriesN1);
        lineChart.getData().add(seriesN);
        saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - lineChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);
    }

    public void generateLineChartEvolutionEquipementLigneSAE(Stage stage, String parEquipement, int chartNumber, int parLigne) throws MalformedURLException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement +" --- Métro " +parLigne+ "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series seriesN2 = new XYChart.Series();
        seriesN2.setName(listYears.get(0).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN2.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
        }

        XYChart.Series seriesN1 = new XYChart.Series();
        seriesN1.setName(listYears.get(1).getAnneeInt()+"");
        for (int i = 0; i < 12; i++) {
            seriesN1.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
        }

        XYChart.Series seriesN = new XYChart.Series();
        seriesN.setName(listYears.get(2).getAnneeInt()+"");

        for (int i = 0; i < 12; i++) {
            seriesN.getData().add(new XYChart.Data(data.getTabMois()[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
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

        if(parLigne == 1){
            //jaune
            lineChart.setStyle("-fx-background-color: #ffff4d;"); /*"-fx-background-color: #ffff1a;"*/
            //lineChart.setStyle("-fx-background-position: top left;");
            //lineChart.setStyle("-fx-background-size: 800 800;");
            //lineChart.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("data/metro1.jpg").toString())), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else if(parLigne == 3){
            lineChart.setStyle("-fx-background-color: #b3b300;");
        }
        else if(parLigne == 4){
            //violet
            lineChart.setStyle("-fx-background-color: #d966ff;");
        }
        else if(parLigne == 13) {
            //bleu
            lineChart.setStyle("-fx-background-color: #99ffff;");
        }


        saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - lineChart_SAE_M"+parLigne+"_" + parEquipement + ".png");
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

}
