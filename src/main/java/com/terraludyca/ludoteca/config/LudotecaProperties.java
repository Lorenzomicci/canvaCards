package com.terraludyca.ludoteca.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the ludoteca workflow.
 */
@ConfigurationProperties(prefix = "ludoteca")
public class LudotecaProperties {

    private String sheetId;
    private String sheetRange;
    private int targetYear;
    private String reportOutputDir;

    /**
     * Returns the Google Sheet identifier that stores the members list.
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Sets the Google Sheet identifier that stores the members list.
     */
    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    /**
     * Returns the A1 range that contains the sheet data.
     */
    public String getSheetRange() {
        return sheetRange;
    }

    /**
     * Sets the A1 range that contains the sheet data.
     */
    public void setSheetRange(String sheetRange) {
        this.sheetRange = sheetRange;
    }

    /**
     * Returns the target year used for the automatic notification.
     */
    public int getTargetYear() {
        return targetYear;
    }

    /**
     * Sets the target year used for the automatic notification.
     */
    public void setTargetYear(int targetYear) {
        this.targetYear = targetYear;
    }

    /**
     * Returns the directory where rendered PDF reports are stored.
     */
    public String getReportOutputDir() {
        return reportOutputDir;
    }

    /**
     * Sets the directory where rendered PDF reports are stored.
     */
    public void setReportOutputDir(String reportOutputDir) {
        this.reportOutputDir = reportOutputDir;
    }
}
