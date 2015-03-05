package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Report composite tab.
 */
public class DemographicsTabIFrame extends
        ReportGraphsTabIFrame<DemographicsTabIFrame> {
    private static final Logger log = LoggerFactory
            .getLogger(DemographicsTabIFrame.class);

    private static final String ELEMENT_ID_BAR_GRAPH_FRAME = "report1";
    private static final String ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK = "image45";
    private static final String ELEMENT_ID_GRID_FRAME = "report33";
    private static final String ELEMENT_ID_LINE_GRAPH_FRAME = "report2";
    private static final String ELEMENT_ID_RESULTS_FRAME = "report3";

    private PatientExperienceIFrame parent;

    public DemographicsTabIFrame(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);
        this.parent = parent;
    }

    public DemographicsTabIFrame accessBarGraphFrame() {
        return this.accessFrame(ELEMENT_ID_BAR_GRAPH_FRAME);
    }

    public DemographicsTabIFrame accessGridFrame() {
        return this.accessFrame(ELEMENT_ID_GRID_FRAME);
    }

    public DemographicsTabIFrame accessLineGraphFrame() {
        return this.accessFrame(ELEMENT_ID_LINE_GRAPH_FRAME);
    }

    public DemographicsTabIFrame accessResultsFrame() {
        this.parent.accessPanelFrame();
        this.switchToFrame(ELEMENT_ID_RESULTS_FRAME);
        return this;
    }

    @Override
    public DemographicsTabIFrame waitForElementsToLoad() {
        this.accessBarGraphFrame().waitForResults();
        this.accessLineGraphFrame().waitForResults();
        this.accessGridFrame().waitForResults();
        return this;
    }

    private DemographicsTabIFrame accessFrame(String frameId) {
        this.accessResultsFrame();
        this.switchToFrame(frameId);
        return this;
    }

    @Override
    public DemographicsTabIFrame exportToPDF() {
        this.accessResultsFrame();
        this.driver.findElement(By.id(ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK))
                .click();
        this.handlePDFNewWindow();
        return this;
    }
}
