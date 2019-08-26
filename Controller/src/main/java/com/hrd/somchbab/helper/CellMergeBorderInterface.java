package com.hrd.somchbab.helper;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public interface CellMergeBorderInterface {
    void setMergeBorder(Sheet sheet, CellRangeAddress range);
}
