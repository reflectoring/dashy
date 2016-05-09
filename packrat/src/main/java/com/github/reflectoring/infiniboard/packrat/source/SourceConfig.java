package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

/**
 * configuration of a source with widget and plugin id, last modified date and a
 * time interval to be checked for new information
 */
public class SourceConfig {

    @Id
    private String id;

    private String widgetId;

    private String sourceId;

    private Date lastModified;

    private int interval;

    private Map<String, Object> configData;

    public SourceConfig(String widgetId, String sourceId, Date lastModified, int interval, Map<String, Object> configData) {
        this.widgetId = widgetId;
        this.sourceId = sourceId;
        this.lastModified = lastModified;
        this.interval = interval;
        this.configData = configData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Map<String, Object> getConfigData() {
        return configData;
    }

    public void setConfigData(Map<String, Object> configData) {
        this.configData = configData;
    }

}
