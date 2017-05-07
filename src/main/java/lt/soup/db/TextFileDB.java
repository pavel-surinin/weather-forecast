package lt.soup.db;

import lt.soup.DateUtils;
import lt.soup.weather.data.Level;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherGetType;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Pavel on 2017-04-28.
 */
public class TextFileDB implements Database {

    private static final String DELIMITER = "/";
    private static final String PATH_TO_FILE_DB = "src/main/resources/textDB.txt";
    private final Path path = Paths.get(PATH_TO_FILE_DB);
    private final Logger log = Logger.getLogger(TextFileDB.class);
    private AtomicLong idCounter = new AtomicLong();
    BufferedWriter bw = null;
    FileWriter fw = null;


    @Override
    public boolean save(WeatherData line) {
        if (isNewRecord(line)) {
            writeToFile(line);
            log.debug("Saved to database: " + line);
            return true;
        }
        log.debug("Failed to save, record already exists: " + line);
        return false;
    }

    @Override
    public List<WeatherData> findAll() {
        return readAll();
    }

    @Override
    public WeatherData findById(Long id) {
        WeatherData weatherData = readAll().stream().filter(d -> d.getId().equals(id)).findFirst().get();
        return weatherData;
    }

    @Override
    public void deleteAll() {
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            log.warn(path + ": no such file or directory");
        } catch (DirectoryNotEmptyException x) {
            log.warn(path + ": is not empty");
        } catch (IOException x) {
            log.warn(x);
        }
    }

    private void writeToFile(WeatherData line) {
        try {
            String data = (
                    idCounter.addAndGet(1) + DELIMITER +
                            line.getWeatherGetType() + DELIMITER +
                            DateUtils.getDateAsString(line.getDate()) + DELIMITER +
                            line.getCountry() + DELIMITER +
                            line.getCity() + DELIMITER +
                            line.getLevel() + DELIMITER +
                            line.getTemperature() + "\n"
            );
            File file = new File(PATH_TO_FILE_DB);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
        } catch (IOException e) {
            log.warn(e);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                log.warn(ex);
            }
        }
    }

    private List<WeatherData> readAll() {
        File file = new File(PATH_TO_FILE_DB);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.warn("Failed to read file: " + PATH_TO_FILE_DB);
            }
        }

        List<WeatherData> allData = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(PATH_TO_FILE_DB));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] vals = sCurrentLine.split(DELIMITER);
                allData.add(parseToWeatherData(vals));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    private WeatherData parseToWeatherData(String[] values) {
        WeatherData weatherData = new WeatherData();
        weatherData.setId(Long.valueOf(values[0]));
        if (values[1].equals(WeatherGetType.FORECAST.toString())) {
            weatherData.setWeatherGetType(WeatherGetType.FORECAST);
        } else {
            weatherData.setWeatherGetType(WeatherGetType.ACTUAL);
        }
        weatherData.setDate(DateUtils.getDateFromString(values[2]));
        weatherData.setCountry(values[3]);
        weatherData.setCity(values[4]);
        if (values[5].equals(Level.MIN.toString())) {
            weatherData.setLevel(Level.MIN);
        } else {
            weatherData.setLevel(Level.MAX);
        }
        weatherData.setTemperature(Float.valueOf(values[6]));
        return weatherData;
    }

    private boolean isNewRecord(WeatherData line) {
        long foundRecords = readAll().stream()
                .filter(r -> r.getCity().equals(line.getCity()))
                .filter(r -> r.getCountry().equals(line.getCountry()))
                .filter(r -> DateUtils.getDateAsString(r.getDate()).equals(DateUtils.getDateAsString(line.getDate())))
                .filter(r -> r.getLevel().equals(line.getLevel()))
                .filter(r -> r.getWeatherGetType().equals(line.getWeatherGetType()))
                .count();
        return foundRecords == 0;

    }
}
