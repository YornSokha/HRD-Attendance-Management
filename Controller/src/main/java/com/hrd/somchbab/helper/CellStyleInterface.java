package com.hrd.somchbab.helper;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public interface CellStyleInterface {
    CellStyle createCellStyle(Workbook wb, Font font, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment);
}
