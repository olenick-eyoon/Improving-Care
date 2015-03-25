package com.olenick.avatar.poi.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * RegionUtil extended to a sort of Builder pattern.
 * <p>
 * TODO: Apply changes, so only one style gets created.
 * </p>
 */
public class RegionFormatter {
    private CellRangeAddress region;
    private Sheet sheet;
    private Workbook workbook;

    public RegionFormatter(CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        this.region = region;
        this.sheet = sheet;
        this.workbook = workbook;
    }

    public RegionFormatter mergeRegion() {
        this.sheet.addMergedRegion(this.region);
        return this;
    }

    public RegionFormatter setAlignment(short alignment) {
        RegionUtil.setAlignment(alignment, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setVerticalAlignment(short verticalAlignment) {
        RegionUtil.setVerticalAlignment(verticalAlignment, this.region,
                this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setBorder(short border) {
        RegionUtil.setBorder(border, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setBorder(Short borderTop, Short borderRight,
            Short borderBottom, Short borderLeft) {
        RegionUtil.setBorder(borderTop, borderRight, borderBottom, borderLeft,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setBorderTop(short border) {
        RegionUtil.setBorderTop(border, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setBorderRight(short border) {
        RegionUtil.setBorderRight(border, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setBorderBottom(short border) {
        RegionUtil.setBorderBottom(border, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setBorderLeft(short border) {
        RegionUtil
                .setBorderLeft(border, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setInternalBorders(short border) {
        RegionUtil.setInternalBorders(border, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setInternalBorders(Short horizontalBorders,
            Short verticalBorders) {
        RegionUtil.setInternalBorders(horizontalBorders, verticalBorders,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setInternalHorizontalBorders(short border) {
        RegionUtil.setInternalHorizontalBorders(border, this.region,
                this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setInternalVerticalBorders(short border) {
        RegionUtil.setInternalVerticalBorders(border, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setBorderColor(short color) {
        RegionUtil
                .setBorderColor(color, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setBorderColor(Short colorTop, Short colorRight,
            Short colorBottom, Short colorLeft) {
        RegionUtil.setBorderColor(colorTop, colorRight, colorBottom, colorLeft,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setTopBorderColor(short color) {
        RegionUtil.setTopBorderColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setRightBorderColor(short color) {
        RegionUtil.setRightBorderColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setBottomBorderColor(short color) {
        RegionUtil.setBottomBorderColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setLeftBorderColor(short color) {
        RegionUtil.setLeftBorderColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setInternalBordersColor(short color) {
        RegionUtil.setInternalBordersColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setInternalBordersColor(
            Short horizontalBordersColor, Short verticalBordersColor) {
        RegionUtil.setInternalBordersColor(horizontalBordersColor,
                verticalBordersColor, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setInternalHorizontalBordersColor(short color) {
        RegionUtil.setInternalHorizontalBordersColor(color, this.region,
                this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setInternalVerticalBordersColor(short color) {
        RegionUtil.setInternalVerticalBordersColor(color, this.region,
                this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setFillBackgroundColor(short color) {
        RegionUtil.setFillBackgroundColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setFillForegroundColor(short color) {
        RegionUtil.setFillForegroundColor(color, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setFillPattern(short pattern) {
        RegionUtil.setFillPattern(pattern, this.region, this.sheet,
                this.workbook);
        return this;
    }

    public RegionFormatter setFont(Font font) {
        RegionUtil.setFont(font, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setWrapText(boolean wrapText) {
        RegionUtil
                .setWrapText(wrapText, this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionTopStyleProperty(String propertyName,
            Object propertyValue) {
        RegionUtil.setRegionTopStyleProperty(propertyName, propertyValue,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionRightStyleProperty(String propertyName,
            Object propertyValue) {
        RegionUtil.setRegionRightStyleProperty(propertyName, propertyValue,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionBottomStyleProperty(String propertyName,
            Object propertyValue) {
        RegionUtil.setRegionBottomStyleProperty(propertyName, propertyValue,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionLeftStyleProperty(String propertyName,
            Object propertyValue) {
        RegionUtil.setRegionLeftStyleProperty(propertyName, propertyValue,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionStyleProperty(String propertyName,
            Object propertyValue) {
        RegionUtil.setRegionStyleProperty(propertyName, propertyValue,
                this.region, this.sheet, this.workbook);
        return this;
    }

    public RegionFormatter setRegionStyleProperties(
            Map<String, Object> properties) {
        RegionUtil.setRegionStyleProperties(properties, this.region,
                this.sheet, this.workbook);
        return this;
    }
}
