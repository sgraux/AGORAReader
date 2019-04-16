package Model;

import com.sun.security.ntlm.Client;
import data.Data;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
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
    //TODO: clean tabMois et tabLines
    private String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    private String[] tabLines = {"M01", "M02", "M03", "M04", "M05", "M06", "M07", "M08", "M09", "M10", "M11", "M12", "M13", "RER A", "RER B"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        reader = new ExtractReader("C:\\Users\\Sean\\Documents\\STAGE - RATP\\Sheet1CUT.xlsx");
        listYears = reader.giveYear();

        String[] temp = listYears.get(0).getTopPanneEquipLigne(13);

        Scene scene = new Scene(new Pane(new Text("... READING ...")),800, 800);
        stage.setScene(scene);
        stage.show();
        this.generateAllCharts(new Stage());
        System.out.print("--- PNGs DONE --- \n");
        PDFCreator creator = new PDFCreator();
        creator.generatePDF();
        creator.generatePDFSAE();
        System.out.print("--- PDFs DONE --- \n");
        System.out.print("--- STOP ---");
        Platform.exit();
    }

    //TODO: changer 2017, 2019 et 2018 par N, N-1 et N-2
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

    public void generateBarChartRepartitionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        int[] OTsLines2017 = listYears.get(0).getSumOTsLines(parEquipement);
        int[] OTsLines2018 = listYears.get(1).getSumOTsLines(parEquipement);
        int[] OTsLines2019 = listYears.get(2).getSumOTsLines(parEquipement);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Répartition " + parEquipement + "\nCodes : " + data.getCodesEquipement(parEquipement));

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

    public void generateBarChartRepartitionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        int[] OTsLines2017 = listYears.get(0).getSumOTsLines(parEquipement);
        int[] OTsLines2018 = listYears.get(1).getSumOTsLines(parEquipement);
        int[] OTsLines2019 = listYears.get(2).getSumOTsLines(parEquipement);

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.setTitle("Répartition " + parEquipement + "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                series2017.getData().add(new XYChart.Data(tabLines[i], OTsLines2017[i]));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                series2018.getData().add(new XYChart.Data(tabLines[i], OTsLines2018[i]));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");
        for (int i = 0; i < 15; i++) {
            if(i == 0 || i == 2 || i ==3 || i == 12)
                series2019.getData().add(new XYChart.Data(tabLines[i], OTsLines2019[i]));
        }


        Scene scene = new Scene(barChart, 1200, 800);
        barChart.setAnimated(false);
        barChart.getData().add(series2017);
        barChart.getData().add(series2018);
        barChart.getData().add(series2019);

        /*for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue;");
        }
        for(Node n:barChart.lookupAll(".default-color0.chart-legend")) {
            n.setStyle("-fx-bar-fill: blue;");
        }
        //second bar color
        for(Node n:barChart.lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: red;");
        }*/


        saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - barChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);
        //stage.show();

    }

    public void generateLineChartEvolutionEquipement(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + "\nCodes : " + data.getCodesEquipement(parEquipement));

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

    public void generateLineChartEvolutionEquipementSAE(Stage stage, String parEquipement, int chartNumber){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement + " --- GLOBALE" + "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 12; i++) {
            series2017.getData().add(new XYChart.Data(tabMois[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 12; i++) {
            series2018.getData().add(new XYChart.Data(tabMois[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");

        for (int i = 0; i < 12; i++) {
            series2019.getData().add(new XYChart.Data(tabMois[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getNbOTLignesSAE()));
        }

        Scene scene = new Scene(lineChart, 1200, 800);
        lineChart.setAnimated(false);
        lineChart.getData().add(series2017);
        lineChart.getData().add(series2018);
        lineChart.getData().add(series2019);
        saveAsPng(scene, "src/ChartsSAE/"+ chartNumber+" - lineChart_SAE_" + parEquipement + ".png");
        stage.setScene(scene);
    }

    public void generateLineChartEvolutionEquipementLigneSAE(Stage stage, String parEquipement, int chartNumber, int parLigne) throws MalformedURLException {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Evolution OT " + parEquipement +" --- Métro " +parLigne+ "\nCodes : " + data.getCodesEquipement(parEquipement));

        XYChart.Series series2017 = new XYChart.Series();
        series2017.setName("2017");
        for (int i = 0; i < 12; i++) {
            series2017.getData().add(new XYChart.Data(tabMois[i], listYears.get(0).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
        }

        XYChart.Series series2018 = new XYChart.Series();
        series2018.setName("2018");
        for (int i = 0; i < 12; i++) {
            series2018.getData().add(new XYChart.Data(tabMois[i], listYears.get(1).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
        }

        XYChart.Series series2019 = new XYChart.Series();
        series2019.setName("2019");

        for (int i = 0; i < 12; i++) {
            series2019.getData().add(new XYChart.Data(tabMois[i], listYears.get(2).getMoisIndex(i).getEquipement(parEquipement).getOTLigneMetro(parLigne)));
        }

        Scene scene = new Scene(lineChart, 1200, 800);

        lineChart.setAnimated(false);
        lineChart.getData().add(series2017);
        lineChart.getData().add(series2018);
        lineChart.getData().add(series2019);

        stage.setScene(scene);

        //xAxis.setStyle("-fx-font-size: 30px;");
        /*for(Node n:lineChart.lookupAll(".axis")) {
            n.setStyle("-fx-font-size: 30px;");
        }*/

        xAxis.tickLabelFontProperty().set(Font.font(20));
        xAxis.setTickLabelRotation(-45);
        yAxis.tickLabelFontProperty().set(Font.font(20));

        lineChart.setCreateSymbols(false);
        //Node node = lineChart.lookup(".default-color0.chart-line-symbol") ;
        //node.setStyle("-fx-background-color: blue");

        /*Node node = lineChart.lookup(".default-color0.chart-series-line");
        node.setStyle("-fx-stroke: blue;");

        node = lineChart.lookup(".default-color1.chart-series-line");
        node.setStyle("-fx-stroke: red;");

        node = lineChart.lookup(".default-color2.chart-series-line");
        node.setStyle("-fx-stroke: green;");*/

        if(parLigne == 1){
            //jaune //TODO: ajuster couleurs et ajouter image si possible
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

        /*for(Node n:lineChart.lookupAll(".chart-plot-background")) {
            n.setStyle("-fx-background-color: transparent;");
        }*/
        //lineChart.setStyle(".chart-plot-background { -fx-background-image: url(data/metro1.jpg); }");

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
