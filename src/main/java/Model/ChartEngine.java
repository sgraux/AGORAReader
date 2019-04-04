package Model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChartEngine extends Application {

    private ExtractReader reader;
    private ArrayList<Annee> listYears;

    public ChartEngine(String pathToExtract){
        reader = new ExtractReader(pathToExtract);
        listYears = reader.giveYear();
    }

    public void start(Stage primaryStage) throws Exception {

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();

            LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

            lineChart.setTitle("Proportion OT");

            XYChart.Series series2017 = new XYChart.Series();
            series2017.setName("2017");
            for (int i = 0; i < 12; i++) {
                series2017.getData().add(new XYChart.Data(i + 1, listYears.get(0).getMoisIndex(i).getOverallOts()));
            }

            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().add(series2017);
            primaryStage.setScene(scene);
            primaryStage.show();
    }
}
