package Model;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChartEngine extends Application {

        private ExtractReader reader;
        ArrayList<Annee> listYears;
        private String[] tabMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

            reader = new ExtractReader("C:\\Users\\Sean\\Documents\\STAGE - RATP\\Sheet1CUT.xlsx");
            listYears = reader.giveYear();
            this.barChat(stage);
            this.lineChart(stage);

        }

        public void barChat(Stage stage){

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            int OTsMonthPerYear;

            BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

            barChart.setTitle("Proportion OT");

            XYChart.Series series2017 = new XYChart.Series();
            series2017.setName("2017");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2017.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
            }

            XYChart.Series series2018 = new XYChart.Series();
            series2018.setName("2018");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2018.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
            }

            XYChart.Series series2019 = new XYChart.Series();
            series2019.setName("2019");

            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2019.getData().add(new XYChart.Data(tabMois[i], OTsMonthPerYear));
            }

            Scene scene = new Scene(barChart, 1200, 800);
            barChart.setAnimated(false);
            barChart.getData().add(series2017);
            barChart.getData().add(series2018);
            barChart.getData().add(series2019);
            saveAsPng(scene, "C:\\Users\\Sean\\Documents\\STAGE - RATP\\Test Chart JavaFX\\barChart.png");
            stage.setScene(scene);
            stage.show();

        }

        public void lineChart(Stage stage){

            int OTsMonthPerYear;
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();

            LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

            lineChart.setTitle("Proportion OT");

            XYChart.Series series2017 = new XYChart.Series();
            series2017.setName("2017");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2017.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
            }

            XYChart.Series series2018 = new XYChart.Series();
            series2018.setName("2018");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2018.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
            }

            XYChart.Series series2019 = new XYChart.Series();
            series2019.setName("2019");

            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2019.getData().add(new XYChart.Data(i + 1, OTsMonthPerYear));
            }

            Scene scene = new Scene(lineChart, 1200, 800);
            lineChart.setAnimated(false);
            lineChart.getData().add(series2017);
            lineChart.getData().add(series2018);
            lineChart.getData().add(series2019);
            saveAsPng(scene, "C:\\Users\\Sean\\Documents\\STAGE - RATP\\Test Chart JavaFX\\lineChart.png");
            stage.setScene(scene);
            stage.show();
        }

        public void generateBarChartRepartitionLines(Stage stage){

        }

        public void generateBarChartArmoire(Stage stage){
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            int OTsMonthPerYear;

            BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

            barChart.setTitle("Proportion OT");

            XYChart.Series series2017 = new XYChart.Series();
            series2017.setName("2017");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(0).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2017.getData().add(new XYChart.Data("mois : "+i + 1, OTsMonthPerYear));
            }

            XYChart.Series series2018 = new XYChart.Series();
            series2018.setName("2018");
            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(1).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2018.getData().add(new XYChart.Data("mois : "+i + 1, OTsMonthPerYear));
            }

            XYChart.Series series2019 = new XYChart.Series();
            series2019.setName("2019");

            for (int i = 0; i < 12; i++) {
                OTsMonthPerYear = listYears.get(2).getMoisIndex(i).getOverallOTsLignesSpe();
                if(OTsMonthPerYear > 0) series2019.getData().add(new XYChart.Data("mois : "+i + 1, OTsMonthPerYear));
            }

            Scene scene = new Scene(barChart, 1200, 800);
            barChart.setAnimated(false);
            barChart.getData().add(series2017);
            barChart.getData().add(series2018);
            barChart.getData().add(series2019);
            saveAsPng(scene, "C:\\Users\\Sean\\Documents\\STAGE - RATP\\Test Chart JavaFX\\barChart.png");
            stage.setScene(scene);
            stage.show();
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

}
