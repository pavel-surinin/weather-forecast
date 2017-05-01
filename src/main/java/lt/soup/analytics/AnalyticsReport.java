package lt.soup.analytics;

/**
 * Created by Pavel on 2017-04-30.
 */
public class AnalyticsReport {

    private String country;
    private Float averageError;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getAverageError() {
        return averageError;
    }

    public void setAverageError(Float averageError) {
        this.averageError = averageError;
    }

}
