package com.olenick.avatar.web.containers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.model.overview_values.OverviewValue;
import com.olenick.avatar.model.overview_values.OverviewValues;
import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Report Overview tab.
 */
public class HCAHPSNationalTabIFrame extends ReportGraphsTabIFrame<HCAHPSNationalTabIFrame> {
    private static final Logger log = LoggerFactory.getLogger(HCAHPSNationalTabIFrame.class);

    private static final long TIMEOUT_SECS_GET_ROWS = 240;

    private static final String ELEMENT_ID_GRAPHS_FRAME = "reportvbp";
    private static final String ELEMENT_ID_RESULTS_FRAME = "oldreport";
    private static final String XPATH_ROWS = "//td[normalize-space(text())='HCAHPS Composite']/../../tr[position()>4]";
    private static final String XPATH_RELATIVE_ITEM_NAME = "td[1]";
    private static final String XPATH_RELATIVE_TOP_BOX_PERCENTAGE = "td[2]";
    private static final String XPATH_RELATIVE_COUNT = "td[last()]";
    // private static final String XPATH_TOTAL_VALUE =
    // "//td[normalize-space(text())='Total']/../td[last()-2]";

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

    public OverviewValues getValues() {
        OverviewValues result = new OverviewValues();
        if (this.dataAvailable) {
            for (WebElement row : this.getRows()) {
                String itemName = row
                        .findElement(By.xpath(XPATH_RELATIVE_ITEM_NAME))
                        .getText().trim();
                long count = Long.valueOf(row
                        .findElement(By.xpath(XPATH_RELATIVE_COUNT)).getText()
                        .trim().replace(",", ""));
                float topBoxPercentage = Float.valueOf(row
                        .findElement(
                                By.xpath(XPATH_RELATIVE_TOP_BOX_PERCENTAGE))
                        .getText().trim());
                result.set(itemName, new OverviewValue(count, topBoxPercentage));
            }
        } else {
            result.setDataAvailable(false);
        }
        return result;
    }

    private List<WebElement> getRows() {
        List<WebElement> rows = this.getDriver().findElements(
                By.xpath(XPATH_ROWS), TIMEOUT_SECS_GET_ROWS);
        rows.remove(0);
        return rows;
    }
}
