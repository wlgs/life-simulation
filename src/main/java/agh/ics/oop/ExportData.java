package agh.ics.oop;

import javafx.scene.chart.XYChart;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExportData {

    public void exportDataFromChartSeries(ArrayList<XYChart.Series<Number, Number>> chartSeriesArr, String filename)
            throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/chart_data/" + filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("era,animalsAmount,plantsAmount,avgEnergy,avgChildren,avgLifeSpan\r\n");

        int dataLength = chartSeriesArr.get(0).getData().size();

        for (int i = 0; i < dataLength; i++) {
            String toPrint = i + 1 + ",";
            for (XYChart.Series<Number, Number> series : chartSeriesArr) {
                toPrint += series.getData().get(i).toString().split(",")[1];
                toPrint += ",";
            }
            toPrint = toPrint.substring(0, toPrint.length() - 1);
            printWriter.print(toPrint + "\r\n");


        }

        printWriter.close();
    }

}
