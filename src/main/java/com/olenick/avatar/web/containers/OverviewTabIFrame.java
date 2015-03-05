package com.olenick.avatar.web.containers;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.olenick.avatar.web.ExtendedRemoteWebDriver;

/**
 * Report Overview tab.
 */
public class OverviewTabIFrame extends ReportGraphsTabIFrame<OverviewTabIFrame> {
    private static final Logger log = LoggerFactory
            .getLogger(OverviewTabIFrame.class);

    private static final String ELEMENT_ID_GRAPHS_FRAME = "visframe";
    private static final String ELEMENT_ID_RESULTS_FRAME = "ovrvwframe";

    private PatientExperienceIFrame parent;

    public OverviewTabIFrame(@NotNull ExtendedRemoteWebDriver driver,
            PatientExperienceIFrame parent) {
        super(driver);
        this.parent = parent;
    }

    public OverviewTabIFrame accessGraphsFrame() {
        this.accessResultsFrame();
        this.switchToFrame(ELEMENT_ID_GRAPHS_FRAME);
        return this;
    }

    public OverviewTabIFrame accessResultsFrame() {
        this.parent.accessPanelFrame();
        this.switchToFrame(ELEMENT_ID_RESULTS_FRAME);
        return this;
    }

    @Override
    public OverviewTabIFrame waitForElementsToLoad() {
        this.accessGraphsFrame().waitForResults();
        return this;
    }

    @Override
    public OverviewTabIFrame exportToPDF() {
        // This is a bit rubbish...
        throw new UnsupportedOperationException(
                "This operation is non-existent in the Overview tab.");
    }
}
