package lt.soup.weather.data;

import lt.soup.DateUtils;
import lt.soup.Forecast;

import java.util.ArrayList;

/**
 * Created by Pavel on 2017-04-26.
 */
public class WeatherDataFactory {

    private static final int DAYS3 = 2;
    private static final int DAYS7 = 6;
    private Forecast[] forecasts;
    private ArrayList<WeatherData> dataList = new ArrayList();

    public WeatherDataFactory(Forecast... fcs){
        this.forecasts = fcs;
    }

    public ArrayList<WeatherData> getDataList() {
        for (Forecast forecast : forecasts) {
            dataList.add(get3dMaxData(forecast));
            dataList.add(get3dMinData(forecast));
            dataList.add(get7dMaxData(forecast));
            dataList.add(get7dMinData(forecast));
        }
        return dataList;
    }

    private WeatherData get7dMinData(Forecast forecast) {
        WeatherData data = new WeatherData();
        data.setCountry(forecast.getCountry());
        data.setCity(forecast.getCity());
        data.setDate(DateUtils.getDateAdd(DAYS7));
        data.setTemperature(forecast.getDay7Min());
        data.setWeatherMinMax(WeatherMinMax.MIN);
        data.setWeatherGetType(WeatherGetType.FORECAST);
        return data;
    }

    private WeatherData get7dMaxData(Forecast forecast) {
        WeatherData data = new WeatherData();
        data.setCountry(forecast.getCountry());
        data.setCity(forecast.getCity());
        data.setDate(DateUtils.getDateAdd(DAYS7));
        data.setTemperature(forecast.getDay7Max());
        data.setWeatherMinMax(WeatherMinMax.MAX);
        data.setWeatherGetType(WeatherGetType.FORECAST);
        return data;
    }

    private WeatherData get3dMinData(Forecast forecast) {
        WeatherData data = new WeatherData();
        data.setCountry(forecast.getCountry());
        data.setCity(forecast.getCity());
        data.setDate(DateUtils.getDateAdd(DAYS3));
        data.setTemperature(forecast.getDay3Min());
        data.setWeatherMinMax(WeatherMinMax.MIN);
        data.setWeatherGetType(WeatherGetType.FORECAST);
        return data;
    }

    private WeatherData get3dMaxData(Forecast forecast) {
        WeatherData data = new WeatherData();
        data.setCountry(forecast.getCountry());
        data.setCity(forecast.getCity());
        data.setDate(DateUtils.getDateAdd(DAYS3));
        data.setTemperature(forecast.getDay3Max());
        data.setWeatherMinMax(WeatherMinMax.MAX);
        data.setWeatherGetType(WeatherGetType.FORECAST);
        return data;
    }
}