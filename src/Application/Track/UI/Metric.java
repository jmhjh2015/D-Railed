package Application.Track.UI;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by adzun_000 on 1/25/2017.
 */
public class Metric {

    private final SimpleStringProperty metric;
    private final SimpleStringProperty value;

    public Metric(String metric, String value) {
        this.metric = new SimpleStringProperty(metric);
        this.value = new SimpleStringProperty(value);
    }

    public String getMetric() {
        return metric.get();
    }

    public String metricProperty() {
        return metric.get();
    }

    public void setMetric(String metric) {
        this.metric.set(metric);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
