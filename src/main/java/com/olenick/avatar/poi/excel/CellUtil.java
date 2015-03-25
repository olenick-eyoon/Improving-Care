package com.olenick.avatar.poi.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Extends CellUtil
 */
public class CellUtil {
    public static final String ALIGNMENT = org.apache.poi.ss.util.CellUtil.ALIGNMENT;
    public static final String BORDER_BOTTOM = org.apache.poi.ss.util.CellUtil.BORDER_BOTTOM;
    public static final String BORDER_LEFT = org.apache.poi.ss.util.CellUtil.BORDER_LEFT;
    public static final String BORDER_RIGHT = org.apache.poi.ss.util.CellUtil.BORDER_RIGHT;
    public static final String BORDER_TOP = org.apache.poi.ss.util.CellUtil.BORDER_TOP;
    public static final String BOTTOM_BORDER_COLOR = org.apache.poi.ss.util.CellUtil.BOTTOM_BORDER_COLOR;
    public static final String DATA_FORMAT = org.apache.poi.ss.util.CellUtil.DATA_FORMAT;
    public static final String FILL_BACKGROUND_COLOR = org.apache.poi.ss.util.CellUtil.FILL_BACKGROUND_COLOR;
    public static final String FILL_FOREGROUND_COLOR = org.apache.poi.ss.util.CellUtil.FILL_FOREGROUND_COLOR;
    public static final String FILL_PATTERN = org.apache.poi.ss.util.CellUtil.FILL_PATTERN;
    public static final String FONT = org.apache.poi.ss.util.CellUtil.FONT;
    public static final String HIDDEN = org.apache.poi.ss.util.CellUtil.HIDDEN;
    public static final String INDENTION = org.apache.poi.ss.util.CellUtil.INDENTION;
    public static final String LEFT_BORDER_COLOR = org.apache.poi.ss.util.CellUtil.LEFT_BORDER_COLOR;
    public static final String LOCKED = org.apache.poi.ss.util.CellUtil.LOCKED;
    public static final String RIGHT_BORDER_COLOR = org.apache.poi.ss.util.CellUtil.RIGHT_BORDER_COLOR;
    public static final String ROTATION = org.apache.poi.ss.util.CellUtil.ROTATION;
    public static final String TOP_BORDER_COLOR = org.apache.poi.ss.util.CellUtil.TOP_BORDER_COLOR;
    public static final String VERTICAL_ALIGNMENT = org.apache.poi.ss.util.CellUtil.VERTICAL_ALIGNMENT;
    public static final String WRAP_TEXT = org.apache.poi.ss.util.CellUtil.WRAP_TEXT;

    private CellUtil() {}

    /**
     * Get a row from the spreadsheet, and create it if it doesn't exist.
     *
     * @param rowIndex The 0 based row number
     * @param sheet The sheet that the row is part of.
     * @return The row indicated by the rowCounter
     */
    public static Row getRow(int rowIndex, Sheet sheet) {
        return org.apache.poi.ss.util.CellUtil.getRow(rowIndex, sheet);
    }

    /**
     * Get a specific cell from a row. If the cell doesn't exist, then create
     * it.
     *
     * @param row The row that the cell is part of
     * @param columnIndex The column index that the cell is in.
     * @return The cell indicated by the column.
     */
    public static Cell getCell(Row row, int columnIndex) {
        return org.apache.poi.ss.util.CellUtil.getCell(row, columnIndex);
    }

    /**
     * Creates a cell, gives it a value, and applies a style if provided
     *
     * @param row the row to create the cell in
     * @param column the column index to create the cell in
     * @param value The value of the cell
     * @param style If the style is not null, then set
     * @return A new Cell
     */
    public static Cell createCell(Row row, int column, String value,
            CellStyle style) {
        return org.apache.poi.ss.util.CellUtil.createCell(row, column, value,
                style);
    }

    /**
     * Take a cell, and apply a font to it
     *
     * @param cell the cell to set the alignment for
     * @param workbook The workbook that is being worked with.
     * @param font The Font that you want to set...
     */
    public static void setFont(Cell cell, Workbook workbook, Font font) {
        org.apache.poi.ss.util.CellUtil.setFont(cell, workbook, font);
    }

    /**
     * Take a cell, and align it.
     *
     * @param cell the cell to set the alignment for
     * @param workbook The workbook that is being worked with.
     * @param align the column alignment to use.
     * @see CellStyle for alignment options
     */
    public static void setAlignment(Cell cell, Workbook workbook, short align) {
        org.apache.poi.ss.util.CellUtil.setAlignment(cell, workbook, align);
    }

    /**
     * Create a cell, and give it a value.
     *
     * @param row the row to create the cell in
     * @param column the column index to create the cell in
     * @param value The value of the cell
     * @return A new Cell.
     */
    public static Cell createCell(Row row, int column, String value) {
        return org.apache.poi.ss.util.CellUtil.createCell(row, column, value);
    }

    /**
     * This method attempt to find an already existing CellStyle that matches
     * what you want the style to be. If it does not find the style, then it
     * creates a new one. If it does create a new one, then it applies the
     * propertyName and propertyValue to the style. This is necessary because
     * Excel has an upper limit on the number of Styles that it supports.
     *
     * @param workbook The workbook that is being worked with.
     * @param propertyName The name of the property that is to be changed.
     * @param propertyValue The value of the property that is to be changed.
     * @param cell The cell that needs it's style changes
     */
    public static void setCellStyleProperty(Cell cell, Workbook workbook,
            String propertyName, Object propertyValue) {
        org.apache.poi.ss.util.CellUtil.setCellStyleProperty(cell, workbook,
                propertyName, propertyValue);
    }

    /**
     * This method attempt to find an already existing CellStyle that matches
     * what you want the style to be. If it does not find the style, then it
     * creates a new one. If it does create a new one, then it applies the
     * propertyName and propertyValue to the style. This is necessary because
     * Excel has an upper limit on the number of Styles that it supports.
     *
     * @param workbook The workbook that is being worked with.
     * @param properties The map of properties to be changed.
     * @param cell The cell that needs it's style changes
     */
    public static void setCellStyleProperty(Cell cell, Workbook workbook,
            Map<String, Object> properties) {
        CellStyle originalStyle = cell.getCellStyle();
        CellStyle newStyle = null;
        Map<String, Object> values = getFormatProperties(originalStyle);
        values.putAll(properties);

        // index seems like what index the cellstyle is in the list of styles
        // for a workbook.
        // not good to compare on!
        short numberCellStyles = workbook.getNumCellStyles();

        for (short i = 0; i < numberCellStyles; i++) {
            CellStyle wbStyle = workbook.getCellStyleAt(i);
            Map<String, Object> wbStyleMap = getFormatProperties(wbStyle);

            if (wbStyleMap.equals(values)) {
                newStyle = wbStyle;
                break;
            }
        }

        if (newStyle == null) {
            newStyle = workbook.createCellStyle();
            setFormatProperties(newStyle, workbook, values);
        }

        cell.setCellStyle(newStyle);
    }

    /**
     * Returns a map containing the format properties of the given cell style.
     *
     * @param style cell style
     * @return map of format properties (String -> Object)
     * @see #setFormatProperties(org.apache.poi.ss.usermodel.CellStyle,
     *      org.apache.poi.ss.usermodel.Workbook, java.util.Map)
     */
    private static Map<String, Object> getFormatProperties(CellStyle style) {
        Map<String, Object> properties = new HashMap<>();
        putShort(properties, ALIGNMENT, style.getAlignment());
        putShort(properties, BORDER_BOTTOM, style.getBorderBottom());
        putShort(properties, BORDER_LEFT, style.getBorderLeft());
        putShort(properties, BORDER_RIGHT, style.getBorderRight());
        putShort(properties, BORDER_TOP, style.getBorderTop());
        putShort(properties, BOTTOM_BORDER_COLOR, style.getBottomBorderColor());
        putShort(properties, DATA_FORMAT, style.getDataFormat());
        putShort(properties, FILL_BACKGROUND_COLOR,
                style.getFillBackgroundColor());
        putShort(properties, FILL_FOREGROUND_COLOR,
                style.getFillForegroundColor());
        putShort(properties, FILL_PATTERN, style.getFillPattern());
        putShort(properties, FONT, style.getFontIndex());
        putBoolean(properties, HIDDEN, style.getHidden());
        putShort(properties, INDENTION, style.getIndention());
        putShort(properties, LEFT_BORDER_COLOR, style.getLeftBorderColor());
        putBoolean(properties, LOCKED, style.getLocked());
        putShort(properties, RIGHT_BORDER_COLOR, style.getRightBorderColor());
        putShort(properties, ROTATION, style.getRotation());
        putShort(properties, TOP_BORDER_COLOR, style.getTopBorderColor());
        putShort(properties, VERTICAL_ALIGNMENT, style.getVerticalAlignment());
        putBoolean(properties, WRAP_TEXT, style.getWrapText());
        return properties;
    }

    /**
     * Sets the format properties of the given style based on the given map.
     *
     * @param style cell style
     * @param workbook parent workbook
     * @param properties map of format properties (String -> Object)
     * @see #getFormatProperties(CellStyle)
     */
    private static void setFormatProperties(CellStyle style, Workbook workbook,
            Map<String, Object> properties) {
        style.setAlignment(getShort(properties, ALIGNMENT));
        style.setBorderBottom(getShort(properties, BORDER_BOTTOM));
        style.setBorderLeft(getShort(properties, BORDER_LEFT));
        style.setBorderRight(getShort(properties, BORDER_RIGHT));
        style.setBorderTop(getShort(properties, BORDER_TOP));
        style.setBottomBorderColor(getShort(properties, BOTTOM_BORDER_COLOR));
        style.setDataFormat(getShort(properties, DATA_FORMAT));
        style.setFillBackgroundColor(getShort(properties, FILL_BACKGROUND_COLOR));
        style.setFillForegroundColor(getShort(properties, FILL_FOREGROUND_COLOR));
        style.setFillPattern(getShort(properties, FILL_PATTERN));
        style.setFont(workbook.getFontAt(getShort(properties, FONT)));
        style.setHidden(getBoolean(properties, HIDDEN));
        style.setIndention(getShort(properties, INDENTION));
        style.setLeftBorderColor(getShort(properties, LEFT_BORDER_COLOR));
        style.setLocked(getBoolean(properties, LOCKED));
        style.setRightBorderColor(getShort(properties, RIGHT_BORDER_COLOR));
        style.setRotation(getShort(properties, ROTATION));
        style.setTopBorderColor(getShort(properties, TOP_BORDER_COLOR));
        style.setVerticalAlignment(getShort(properties, VERTICAL_ALIGNMENT));
        style.setWrapText(getBoolean(properties, WRAP_TEXT));
    }

    /**
     * Utility method that returns the named short value form the given map.
     * 
     * @return zero if the property does not exist, or is not a {@link Short}.
     * @param properties map of named properties (String -> Object)
     * @param name property name
     * @return property value, or zero
     */
    private static short getShort(Map<String, Object> properties, String name) {
        Object value = properties.get(name);
        if (value instanceof Short) {
            return (Short) value;
        }
        return 0;
    }

    /**
     * Utility method that returns the named boolean value form the given map.
     * 
     * @return false if the property does not exist, or is not a {@link Boolean}
     *         .
     * @param properties map of properties (String -> Object)
     * @param name property name
     * @return property value, or false
     */
    private static boolean getBoolean(Map<String, Object> properties,
            String name) {
        Object value = properties.get(name);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    /**
     * Utility method that puts the named short value to the given map.
     *
     * @param properties map of properties (String -> Object)
     * @param name property name
     * @param value property value
     */
    private static void putShort(Map<String, Object> properties, String name,
            short value) {
        properties.put(name, value);
    }

    /**
     * Utility method that puts the named boolean value to the given map.
     *
     * @param properties map of properties (String -> Object)
     * @param name property name
     * @param value property value
     */
    private static void putBoolean(Map<String, Object> properties, String name,
            boolean value) {
        properties.put(name, value);
    }

    /**
     * Looks for text in the cell that should be unicode, like &alpha; and
     * provides the unicode version of it.
     *
     * @param cell The cell to check for unicode values
     * @return translated to unicode
     */
    public static Cell translateUnicodeValues(Cell cell) {
        return org.apache.poi.ss.util.CellUtil.translateUnicodeValues(cell);
    }
}
