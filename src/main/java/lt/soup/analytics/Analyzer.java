package lt.soup.analytics;

import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherMinMax;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-04-30.
 */
public class Analyzer {


    public static List<AnalyticsReport> analyze(List<WeatherData> dataList) {
        return dataList
                .stream()
                .collect(Collectors.groupingBy(WeatherData::getCountry))
                .entrySet()
                .stream()
                .map(map -> Analyzer.getCountryReport(map.getValue()))
                .filter(report -> !(report == null))
                .collect(Collectors.toList());
    }

    private static AnalyticsReport getCountryReport(List<WeatherData> list) {
        List<List<WeatherData>> listByDate = list
                .stream()
                .collect(Collectors.groupingBy(WeatherData::getDate)) //get map<date,list> sorted by date
                .entrySet()
                .stream()
                .filter(map -> map.getValue().size() == 4) // FORECAST.MIN, FORECAST.MAX, ACT.MIN, ACT.MAX
                .map(map -> map.getValue())
                .collect(Collectors.toList());

        if (listByDate.isEmpty()) {
            return null;
        } else {
            double max = listByDate
                    .stream()
                    .map(bd -> getDeltasByDay(bd))
                    .mapToDouble(hm -> hm.get(WeatherMinMax.MAX))
                    .average()
                    .getAsDouble();
            double min = listByDate
                    .stream()
                    .map(bd -> getDeltasByDay(bd))
                    .mapToDouble(hm -> hm.get(WeatherMinMax.MIN))
                    .average()
                    .getAsDouble();
            float average = new Double((min + max) / 2).floatValue();
            String country = list.get(0).getCountry();
            AnalyticsReport report = new AnalyticsReport();
            report.setCountry(country);
            report.setAverageError(average);
            return report;
        }
    }

    private static Map<WeatherMinMax, Float> getDeltasByDay(List<WeatherData> bd) {
        Map<WeatherMinMax, Float> deltas = new HashMap<>();
        Map<WeatherMinMax, List<WeatherData>> byMinMax = bd.
                stream().
                collect(Collectors.groupingBy(WeatherData::getWeatherMinMax));
        float deltaMin = byMinMax.get(WeatherMinMax.MIN).get(0).getTemperature() - byMinMax.get(WeatherMinMax.MIN).get(1).getTemperature();
        float deltaMax = byMinMax.get(WeatherMinMax.MAX).get(0).getTemperature() - byMinMax.get(WeatherMinMax.MAX).get(1).getTemperature();
        deltas.put(WeatherMinMax.MIN, Math.abs(deltaMin));
        deltas.put(WeatherMinMax.MAX, Math.abs(deltaMax));
        return deltas;
    }
}
