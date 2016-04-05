package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import com.olenick.avatar.model.Environment;
import com.olenick.avatar.model.report_values.ReportValuesSearchSpec;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.exceptions.NotImplementedException;
import com.olenick.avatar.model.report_values.ReportValues;
import com.olenick.selenium.drivers.ExtendedRemoteWebDriver;

/**
 * Report side-by-side tab.
 */
public class SideBySideTabIFrame extends
        ReportGraphsTabIFrame<SideBySideTabIFrame> {
    private static final Logger log = LoggerFactory
            .getLogger(SideBySideTabIFrame.class);

    private static final String ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK = "compdf";
    private static final String ELEMENT_ID_GRAPHS_FRAME = "iframe91";
    private static final String ELEMENT_ID_OPTIONS_MENU_LINK = "imgopt";
    private static final String ELEMENT_ID_RESULTS_FRAME = "sdframe";

    private PatientExperienceIFrame parent;

    public SideBySideTabIFrame(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);
        this.parent = parent;
    }

    public SideBySideTabIFrame accessGraphsFrame() {
        this.accessResultsFrame();
        this.switchToFrame(ELEMENT_ID_GRAPHS_FRAME);
        return this;
    }

    public SideBySideTabIFrame accessResultsFrame() {
        this.parent.accessPanelFrame();
        this.switchToFrame(ELEMENT_ID_RESULTS_FRAME);
        return this;
    }

    @Override
    public SideBySideTabIFrame waitForElementsToLoad() {
        this.accessGraphsFrame().waitForResults();
        return this;
    }

    @Override
    public SideBySideTabIFrame exportToPDF() {
        this.accessResultsFrame();
        driver.findElement(By.id(ELEMENT_ID_OPTIONS_MENU_LINK)).click();
        driver.findElement(By.id(ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK)).click();
        this.handlePDFNewWindow();
        return this;
    }

    public ReportValues getValues(ReportValuesSearchSpec searchSpec) {
        throw new UnsupportedOperationException("Not needed yet.");
    }

    @Override
    public ReportValues getValues() {
        throw new NotImplementedException();
    }
}
