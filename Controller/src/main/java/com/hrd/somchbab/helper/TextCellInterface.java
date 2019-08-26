package com.hrd.somchbab.helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface TextCellInterface {
    Cell createCell(Row row, int colIndex, CellStyle cellStyle, String cellValue);
}

