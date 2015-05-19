package com.olenick.avatar.web.containers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.olenick.avatar.model.report_values.HCAHPSNationalValue;
import com.olenick.avatar.model.report_values.ReportValues;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;

/**
 * Report Overview tab.
 */
public class HCAHPSNationalTabIFrame extends
        ReportGraphsTabIFrame<HCAHPSNationalTabIFrame> {
    private static final long TIMEOUT_SECS_GET_ROWS = 240;

    private static final String ELEMENT_ID_GRAPHS_FRAME = "oldreport";
    private static final String ELEMENT_ID_RESULTS_FRAME = "reportvbp";
    // private static final String FACILITY_SELECT = "FACT";
    // private static final String FACILITY_SELECTION_VALUE = "ALL";
    private static final String XPATH_ROWS = "//td/b/font[normalize-space(text())='HCAHPS Composite']/../../../../tr[position()>4]";
    private static final String XPATH_RELATIVE_ITEM_NAME = "td[1]";
    private static final String XPATH_RELATIVE_ADJUSTED_SCORE = "td[2]";

    private PatientExperienceIFrame parent;

    public HCAHPSNationalTabIFrame(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);
        this.parent = parent;
    }

    public HCAHPSNationalTabIFrame accessGraphsFrame() {
        this.accessResultsFrame();
        this.switchToFrame(ELEMENT_ID_GRAPHS_FRAME);
        return this;
    }

    public HCAHPSNationalTabIFrame accessResultsFrame() {
        this.parent.accessPanelFrame();
        this.switchToFrame(ELEMENT_ID_RESULTS_FRAME);
        return this;
    }

    @Override
    public HCAHPSNationalTabIFrame waitForElementsToLoad() {
        this.accessGraphsFrame().waitForResults();
        return this;
    }

    @Override
    public HCAHPSNationalTabIFrame exportToPDF() {
        throw new RuntimeException("Not implemented yet");
    }

    public ReportValues getValues() {
        ReportValues result = new ReportValues();
        if (this.dataAvailable) {
            for (WebElement row : this.getRows()) {
                String itemName = row
                        .findElement(By.xpath(XPATH_RELATIVE_ITEM_NAME))
                        .getText().trim();
                float adjustedScore = Float.valueOf(row
                        .findElement(By.xpath(XPATH_RELATIVE_ADJUSTED_SCORE))
                        .getText().trim());
                result.set(itemName, new HCAHPSNationalValue(adjustedScore));
            }
        } else {
            result.setDataAvailable(false);
        }
        return result;
    }

    private List<WebElement> getRows() {
        List<WebElement> rows = this.getDriver().findElements(
                By.xpath(XPATH_ROWS), TIMEOUT_SECS_GET_ROWS);
        return rows;
    }
}
