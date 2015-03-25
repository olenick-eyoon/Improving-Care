package com.olenick.avatar.poi.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * RegionUtil, based on org.apache.poi.ss.util.RegionUtil, but broader.
 */
public class RegionUtil {
    public static void setAlignment(short alignment, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.ALIGNMENT, alignment, region, sheet,
                workbook);
    }

    public static void setVerticalAlignment(short verticalAlignment,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.VERTICAL_ALIGNMENT, verticalAlignment,
                region, sheet, workbook);
    }

    public static void setBorder(short border, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setBorder(border, border, border, border, region, sheet, workbook);
    }

    public static void setBorder(Short borderTop, Short borderRight,
            Short borderBottom, Short borderLeft, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        if (borderTop != null) {
            setBorderTop(borderTop, region, sheet, workbook);
        }
        if (borderRight != null) {
            setBorderRight(borderRight, region, sheet, workbook);
        }
        if (borderBottom != null) {
            setBorderBottom(borderBottom, region, sheet, workbook);
        }
        if (borderLeft != null) {
            setBorderLeft(borderLeft, region, sheet, workbook);
        }
    }

    public static void setBorderTop(short border, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionTopStyleProperty(CellUtil.BORDER_TOP, border, region, sheet,
                workbook);
    }

    public static void setBorderRight(short border, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionRightStyleProperty(CellUtil.BORDER_RIGHT, border, region,
                sheet, workbook);
    }

    public static void setBorderBottom(short border, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionBottomStyleProperty(CellUtil.BORDER_BOTTOM, border, region,
                sheet, workbook);
    }

    public static void setBorderLeft(short border, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionLeftStyleProperty(CellUtil.BORDER_LEFT, border, region, sheet,
                workbook);
    }

    public static void setInternalBorders(short border,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setInternalBorders(border, border, region, sheet, workbook);
    }

    public static void setInternalBorders(Short horizontalBorders,
            Short verticalBorders, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        if (horizontalBorders != null) {
            setInternalHorizontalBorders(horizontalBorders, region, sheet,
                    workbook);
        }
        if (verticalBorders != null) {
            setInternalVerticalBorders(verticalBorders, region, sheet, workbook);
        }
    }

    public static void setInternalHorizontalBorders(short border,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperty(CellUtil.BORDER_BOTTOM, border, colLeft, colRight,
                rowTop, rowBottom - 1, sheet, workbook);
    }

    public static void setInternalVerticalBorders(short border,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperty(CellUtil.BORDER_RIGHT, border, colLeft, colRight - 1,
                rowTop, rowBottom, sheet, workbook);
    }

    public static void setBorderColor(short color, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setBorderColor(color, color, color, color, region, sheet, workbook);
    }

    public static void setBorderColor(Short colorTop, Short colorRight,
            Short colorBottom, Short colorLeft, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        if (colorTop != null) {
            setTopBorderColor(colorTop, region, sheet, workbook);
        }
        if (colorRight != null) {
            setRightBorderColor(colorRight, region, sheet, workbook);
        }
        if (colorBottom != null) {
            setBottomBorderColor(colorBottom, region, sheet, workbook);
        }
        if (colorLeft != null) {
            setLeftBorderColor(colorLeft, region, sheet, workbook);
        }
    }

    public static void setTopBorderColor(short color, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionTopStyleProperty(CellUtil.TOP_BORDER_COLOR, color, region,
                sheet, workbook);
    }

    public static void setRightBorderColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setRegionRightStyleProperty(CellUtil.RIGHT_BORDER_COLOR, color, region,
                sheet, workbook);
    }

    public static void setBottomBorderColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setRegionBottomStyleProperty(CellUtil.BOTTOM_BORDER_COLOR, color,
                region, sheet, workbook);
    }

    public static void setLeftBorderColor(short color, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionLeftStyleProperty(CellUtil.LEFT_BORDER_COLOR, color, region,
                sheet, workbook);
    }

    public static void setInternalBordersColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setInternalBordersColor(color, color, region, sheet, workbook);
    }

    public static void setInternalBordersColor(Short horizontalBordersColor,
            Short verticalBordersColor, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        if (horizontalBordersColor != null) {
            setInternalHorizontalBordersColor(horizontalBordersColor, region,
                    sheet, workbook);
        }
        if (verticalBordersColor != null) {
            setInternalVerticalBordersColor(verticalBordersColor, region,
                    sheet, workbook);
        }
    }

    public static void setInternalHorizontalBordersColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperty(CellUtil.BOTTOM_BORDER_COLOR, color, colLeft,
                colRight, rowTop, rowBottom - 1, sheet, workbook);
    }

    public static void setInternalVerticalBordersColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperty(CellUtil.RIGHT_BORDER_COLOR, color, colLeft,
                colRight - 1, rowTop, rowBottom, sheet, workbook);
    }

    public static void setFillBackgroundColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.FILL_BACKGROUND_COLOR, color, region,
                sheet, workbook);
    }

    public static void setFillForegroundColor(short color,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.FILL_FOREGROUND_COLOR, color, region,
                sheet, workbook);
    }

    public static void setFillPattern(short pattern, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.FILL_PATTERN, pattern, region, sheet,
                workbook);
    }

    public static void setFont(Font font, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        setRegionStyleProperty(CellUtil.FONT, font, region, sheet, workbook);
    }

    public static void setWrapText(boolean wrapText, CellRangeAddress region,
            Sheet sheet, Workbook workbook) {
        setRegionStyleProperty(CellUtil.WRAP_TEXT, wrapText, region, sheet,
                workbook);
    }

    public static void setRegionTopStyleProperty(String propertyName,
            Object propertyValue, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        setStyleProperty(propertyName, propertyValue, region.getFirstColumn(),
                region.getLastColumn(), region.getFirstRow(),
                region.getFirstRow(), sheet, workbook);
    }

    public static void setRegionRightStyleProperty(String propertyName,
            Object propertyValue, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        setStyleProperty(propertyName, propertyValue, region.getLastColumn(),
                region.getLastColumn(), region.getFirstRow(),
                region.getLastRow(), sheet, workbook);
    }

    public static void setRegionBottomStyleProperty(String propertyName,
            Object propertyValue, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        setStyleProperty(propertyName, propertyValue, region.getFirstColumn(),
                region.getLastColumn(), region.getLastRow(),
                region.getLastRow(), sheet, workbook);
    }

    public static void setRegionLeftStyleProperty(String propertyName,
            Object propertyValue, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        setStyleProperty(propertyName, propertyValue, region.getFirstColumn(),
                region.getFirstColumn(), region.getFirstRow(),
                region.getLastRow(), sheet, workbook);
    }

    public static void setRegionStyleProperty(String propertyName,
            Object propertyValue, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperty(propertyName, propertyValue, colLeft, colRight,
                rowTop, rowBottom, sheet, workbook);
    }

    public static void setRegionStyleProperties(Map<String, Object> properties,
            CellRangeAddress region, Sheet sheet, Workbook workbook) {
        int colLeft = region.getFirstColumn();
        int colRight = region.getLastColumn();
        int rowTop = region.getFirstRow();
        int rowBottom = region.getLastRow();
        setStyleProperties(properties, colLeft, colRight, rowTop, rowBottom,
                sheet, workbook);
    }

    private static void setStyleProperty(String propertyName,
            Object propertyValue, int colLeft, int colRight, int rowTop,
            int rowBottom, Sheet sheet, Workbook workbook) {
        for (int rowNumber = rowTop; rowNumber <= rowBottom; ++rowNumber) {
            Row row = CellUtil.getRow(rowNumber, sheet);
            for (int column = colLeft; column <= colRight; ++column) {
                Cell cell = CellUtil.getCell(row, column);
                CellUtil.setCellStyleProperty(cell, workbook, propertyName,
                        propertyValue);
            }
        }
    }

    private static void setStyleProperties(Map<String, Object> properties,
            int colLeft, int colRight, int rowTop, int rowBottom, Sheet sheet,
            Workbook workbook) {
        for (int rowNumber = rowTop; rowNumber <= rowBottom; ++rowNumber) {
            Row row = CellUtil.getRow(rowNumber, sheet);
            for (int column = colLeft; column <= colRight; ++column) {
                Cell cell = CellUtil.getCell(row, column);
                CellUtil.setCellStyleProperty(cell, workbook, properties);
            }
        }
    }
}
