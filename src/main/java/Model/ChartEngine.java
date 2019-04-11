package Model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**Genere tous les graphes et les enregesitre  dans /src/Charts
 * @author Sean Graux
 * @version 1.0
 */
public class ChartEngine extends Application {

    private ExtractReader reader;
    ArrayList<Annee> listYears;
    private String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    private String[] tabLines = {"M01", "M02", "M03", "M04", "M05", "M06", "M07", "M08", "M09", "M10", "M11", "M12", "M13", "RER A", "RER B"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        reader = new ExtractReader("C:\\Users\\Sean\\Documents\\STAGE - RATP\\Sheet1CUT.xlsx");
        listYears = reader.giveYear();
        this.generateAllCharts(stage);
        System.out.print("--- PNGs DONE --- \n");
        PDFCreator creator = new PDFCreator();
        creator.generatePDF();
        System.out.print("--- PDF DONE --- \n");
        System.out.print("--- STOP ---");
        Platform.exit();
    }

    public void generateAllCharts(Stage stage){
        this.cleanCharts();
        this.barChat(stage);
        this.generateBarChartRepartition(stage);
        this.generateLineChartEvolution(stage);
    }

    public void barChat(Stage stage) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        int OTsMonthPerYear;

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Proportion OT");

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2017.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2018.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");

        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2019.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
        }

        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);
        barChart.getData().add(series2017);
        barChart.getData().add(series2018);
        barChart.getData().add(series2019);
        saveAsPng(scene, "src/Charts/1 - ProportionOts.png");
        stage.setScene(scene);
        //stage.show();

    }

    public void lineChart(Stage stage) {

        int OTsMonthPerYear;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Proportion OT");

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2017.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2018.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");

        for (int i = 0; i < 12; i++) {
            OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
            if (OTsMonthPerYear > 0) series2019.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        lineChart.setAnimated(false);
        lineChart.getData().add(series2017);
        lineChart.getData().add(series2018);
        lineChart.getData().add(series2019);
        saveAsPng(scene, "C:\\Users\\Sean\\Documents\\STAGE - RATP\\Test Chart JavaFX\\lineChart.png");
        stage.setScene(scene);
        //stage.show();
    }

    public void generateBarChartRepartition(Stage stage) {

        this.generateBarChartRepartitionEquipement(stage, "armoire", 2);
        this.generateBarChartRepartitionEquipement(stage, "centrale", 3);
        this.generateBarChartRepartitionEquipement(stage, "telesono", 4);
        this.generateBarChartRepartitionEquipement(stage, "video", 5);
        this.generateBarChartRepartitionEquipement(stage, "sono", 6);
        this.generateBarChartRepartitionEquipement(stage, "interphones", 7);
    }

    public void generateBarChartRepartitionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        int[] OTsLines2017 = listYears.get(0).getSumOTsLines(parEquipement);
        int[] OTsLines2018 = listYears.get(1).getSumOTsLines(parEquipement);
        int[] OTsLines2019 = listYears.get(2).getSumOTsLines(parEquipement);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Répartition " + parEquipement);

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 15; i++) {
            series2017.getData().add(new XYChart.Data(tabLines[i], OTsLines2017[i]));
        }
        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 15; i++) {
            series2018.getData().add(new XYChart.Data(tabLines[i], OTsLines2018[i]));
        }
        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");
        for (int i = 0; i < 15; i++) {
            series2019.getData().add(new XYChart.Data(tabLines[i], OTsLines2019[i]));
        }


        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);
        barChart.getData().add(series2017);
        barChart.getData().add(series2018);
        barChart.getData().add(series2019);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - barChart_" + parEquipement + ".png");
        stage.setScene(scene);
        //stage.show();
    }

    public void generateLineChartEvolution(Stage stage){
        generateLineChartEvolutionEquipement(stage, "armoire", 2);
        generateLineChartEvolutionEquipement(stage, "centrale", 3);
        generateLineChartEvolutionEquipement(stage, "telesono", 4);
        generateLineChartEvolutionEquipement(stage, "video", 5);
        generateLineChartEvolutionEquipement(stage, "sono", 6);
        generateLineChartEvolutionEquipement(stage, "interphones", 7);
    }

    public void generateLineChartEvolutionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Proportion OT " + parEquipement);

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 12; i++) {
            series2017.getData().add(new XYChart.Data(tabMois[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 12; i++) {
            series2018.getData().add(new XYChart.Data(tabMois[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");

        for (int i = 0; i < 12; i++) {
            series2019.getData().add(new XYChart.Data(tabMois[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSpe()));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        lineChart.setAnimated(false);
        lineChart.getData().add(series2017);
        lineChart.getData().add(series2018);
        lineChart.getData().add(series2019);
        saveAsPng(scene, "src/Charts/"+ chartNumber+" - lineChart_" + parEquipement + ".png");
        stage.setScene(scene);
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

    public void cleanCharts(){
        File chartsDirectory = new File("src/Charts");
        File[] directoryListing = chartsDirectory.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                child.delete();
            }
        }

    }

}
