package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Report composite tab.
 */
public class CompositeTabIFrame extends
        ReportGraphsTabIFrame<CompositeTabIFrame> {
    private static final Logger log = LoggerFactory
            .getLogger(CompositeTabIFrame.class);

    private static final String ELEMENT_ID_COMPOSITE_BAR_GRAPH_FRAME = "reportCGbar";
    private static final String ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK = "imageCGpdf";
    private static final String ELEMENT_ID_GRID_FRAME = "reportCGgrid";
    private static final String ELEMENT_ID_ITEM_BAR_GRAPH_FRAME = "reportCGbar1";
    private static final String ELEMENT_ID_LINE_GRAPH_FRAME = "reportCGline";
    private static final String ELEMENT_ID_RESULTS_FRAME = "rptfactor";

    private PatientExperienceIFrame parent;

    public CompositeTabIFrame(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);
        this.parent = parent;
    }

    public CompositeTabIFrame accessGridFrame() {
        return this.accessFrame(ELEMENT_ID_GRID_FRAME);
    }

    public CompositeTabIFrame accessCompositeBarGraphFrame() {
        return this.accessFrame(ELEMENT_ID_COMPOSITE_BAR_GRAPH_FRAME);
    }

    public CompositeTabIFrame accessItemBarGraphFrame() {
        return this.accessFrame(ELEMENT_ID_ITEM_BAR_GRAPH_FRAME);
    }

    public CompositeTabIFrame accessLineGraphFrame() {
        return this.accessFrame(ELEMENT_ID_LINE_GRAPH_FRAME);
    }

    public CompositeTabIFrame accessResultsFrame() {
        this.parent.accessPanelFrame();
        this.switchToFrame(ELEMENT_ID_RESULTS_FRAME);
        return this;
    }

    @Override
    public CompositeTabIFrame waitForElementsToLoad() {
        this.accessCompositeBarGraphFrame().waitForResults();
        this.accessItemBarGraphFrame().waitForResults();
        this.accessLineGraphFrame().waitForResults();
        this.accessGridFrame().waitForResults();
        return this;
    }

    private CompositeTabIFrame accessFrame(String frameId) {
        this.accessResultsFrame();
        this.switchToFrame(frameId);
        return this;
    }

    @Override
    public CompositeTabIFrame exportToPDF() {
        this.accessResultsFrame();
        this.driver.findElement(By.id(ELEMENT_ID_EXPORT_ALL_TO_PDF_LINK))
                .click();
        this.handlePDFNewWindow();
        return this;
    }
}
