package com.hrd.somchbab.helper;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public interface FontInterface {
    Font createFont(Workbook wb, String fontFamily, int fontSize, boolean isBold, IndexedColors textColor);
}
