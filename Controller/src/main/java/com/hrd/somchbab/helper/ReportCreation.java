package com.hrd.somchbab.helper;

import com.hrd.somchbab.repository.model.*;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
import com.hrd.somchbab.service.generation_service.GenerationService;
import com.hrd.somchbab.service.report_service.ReportService;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;

import static com.hrd.somchbab.helper.CalculateField.ABSENT;
import static com.hrd.somchbab.helper.CalculateField.ACTIVITY_SCORE;
import static com.hrd.somchbab.helper.CalculateField.ATTENDANCE_SCORE;
import static com.hrd.somchbab.helper.CalculateField.ATTITUDE_SCORE;
import static com.hrd.somchbab.helper.CalculateField.GO_OUTSIDE;
import static com.hrd.somchbab.helper.CalculateField.LATE;
import static com.hrd.somchbab.helper.CalculateField.MISSED_SCAN;
import static com.hrd.somchbab.helper.CalculateField.PERFECT_ATTENDANCE;
import static com.hrd.somchbab.helper.CalculateField.PERMISSION;
import static com.hrd.somchbab.helper.CalculateField.STATUS;
import static com.hrd.somchbab.helper.CalculateField.TOTAL_ATTENDANCE;
import static com.hrd.somchbab.helper.CalculateField.TOTAL_SOFTSKILL_SCORE;

@Component
public class ReportCreation {
    @Autowired
    ClassroomService classroomService;

    @Autowired
    GenerationService generationService;

    @Autowired
    ReportService reportService;

    @Autowired
    ReportStorageProperties reportStorageProperties;

    // static
    private final String FILE_EXTENSION = ".xlsx";
    private String FILE_DIRECTORY;
    private final String LOGO_NAME = "kshrd-logo.png";
    private final String DEFAULT_FONT = "Helvetica";

    // Colors
    final IndexedColors COLOR_MISSED_SCAN = IndexedColors.GREY_50_PERCENT;
    final IndexedColors COLOR_GO_OUTSIDE = IndexedColors.LIGHT_ORANGE;
    final IndexedColors COLOR_LATE = IndexedColors.YELLOW;
    final IndexedColors COLOR_PERMISSION = IndexedColors.LIGHT_BLUE;
    final IndexedColors COLOR_ABSENT = IndexedColors.RED;

    private Workbook wb;
    private Sheet reportSheet;
    private HashMap<String, Font> fontFamilies;
    private HashMap<String, CellStyle> cellStyles;

    private HashMap<Integer, Row> rows;
    private HashMap<String, Cell> cells;
    
    private TextCellInterface CellText;
    private NumberCellInterface CellNumber;
    private CellBorderInterface CellBorder;
    private CellMergeBorderInterface CellMergeBorder;
    private FontInterface FontCreator;
    private CellStyleInterface CellStyleCreator;
    private CellStyleColorInterface CellStyleWithColorCreator;

    public ReportCreation() {

    }

    public void initializeComponents() {
        wb = new XSSFWorkbook();
        reportSheet = wb.createSheet();
        fontFamilies = new HashMap<>();
        cellStyles = new HashMap<>();
        rows = new HashMap<>();

        initializeInterfaceMethods();

        // Font Setup
        fontFamilies.put("titleFont", FontCreator.createFont(wb, DEFAULT_FONT, 20, true, IndexedColors.BLACK));
        fontFamilies.put("subTitleFont", FontCreator.createFont(wb, DEFAULT_FONT, 16, true, IndexedColors.BLACK));
        fontFamilies.put("tableHeadFont", FontCreator.createFont(wb, DEFAULT_FONT, 12, true, IndexedColors.BLACK));
        fontFamilies.put("totalMemberFont", FontCreator.createFont(wb, DEFAULT_FONT, 12, true, IndexedColors.WHITE));
        fontFamilies.put("tableBodyFont", FontCreator.createFont(wb, DEFAULT_FONT, 10, false, IndexedColors.BLACK));

        // Style Title
        cellStyles.put("titleStyle", CellStyleCreator.createCellStyle(wb, fontFamilies.get("titleFont"), HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("subTitleStyle", CellStyleCreator.createCellStyle(wb, fontFamilies.get("subTitleFont"), HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("tableHeadStyle", CellStyleCreator.createCellStyle(wb, fontFamilies.get("tableHeadFont"), HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("tableBodyStyle", CellStyleCreator.createCellStyle(wb, fontFamilies.get("tableBodyFont"), HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("missedScanStyle", CellStyleWithColorCreator.createCellStyle(wb, fontFamilies.get("totalMemberFont"), COLOR_MISSED_SCAN, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("goOutsideStyle", CellStyleWithColorCreator.createCellStyle(wb, fontFamilies.get("totalMemberFont"), COLOR_GO_OUTSIDE, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("lateStyle", CellStyleWithColorCreator.createCellStyle(wb, fontFamilies.get("tableHeadFont"), COLOR_LATE, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("permissionStyle", CellStyleWithColorCreator.createCellStyle(wb, fontFamilies.get("totalMemberFont"), COLOR_PERMISSION, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));
        cellStyles.put("absentStyle", CellStyleWithColorCreator.createCellStyle(wb, fontFamilies.get("totalMemberFont"), COLOR_ABSENT, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER));

        // Rows
        rows.put(2, reportSheet.createRow(1));  // Korea Software HRD Center
        rows.put(3, reportSheet.createRow(2));  // List of Monthly Attendance for [MONTH-NAME]
        rows.put(4, reportSheet.createRow(3));  // [GENERATION-NAME]
        rows.put(5, reportSheet.createRow(4));  // Time : 8:00 AM - 5:00 PMo
        rows.put(6, reportSheet.createRow(5));  // 'Classroom Name' + Class

        rows.put(8, reportSheet.createRow(7));
        rows.put(9, reportSheet.createRow(8));
        rows.put(10, reportSheet.createRow(9));
        rows.put(11, reportSheet.createRow(10));
        rows.put(12, reportSheet.createRow(11));

        // Rows Height
        rows.get(8).setHeightInPoints(23);
        rows.get(9).setHeightInPoints(23);
        rows.get(10).setHeightInPoints(23);
        rows.get(11).setHeightInPoints(23);
    }

    private boolean putImage(int firstRow, int lastRow, int firstCol, int lastCol, String imageLocation) {
        reportSheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        try (InputStream is = new FileInputStream(imageLocation)) {
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = reportSheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(firstCol);
            anchor.setRow1(firstRow);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String generateReport(int classroomId, String date) {
        initializeComponents();

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        Classroom classroom = classroomService.findByID(classroomId);
        String classroomName = classroom.getClassName().getName();
        // String DEFAULT_DOCUMENT_LOCATION = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();

        // Todo Section
        String DEFAULT_DOCUMENT_LOCATION = reportStorageProperties.getUploadDir();
        String FILE_NAME = "In-" + date + "-" + classroomName + "-" + dateTimeFormat.format(now) + FILE_EXTENSION;
        FILE_DIRECTORY = DEFAULT_DOCUMENT_LOCATION + FILE_NAME;

        // Picture
        putImage(1,5,12,16, "Controller/src/main/resources/static/image/" + LOGO_NAME);

        // Korea Software HRD Center
        int mergeTitleWidth = 18;
        String schoolTitle = "Korea Software HRD Center";
        Cell cellSchoolTitle = CellText.createCell(rows.get(2), 17, cellStyles.get("titleStyle"), schoolTitle);
        reportSheet.addMergedRegion(new CellRangeAddress(cellSchoolTitle.getRowIndex(), cellSchoolTitle.getRowIndex(), cellSchoolTitle.getColumnIndex(), cellSchoolTitle.getColumnIndex() + mergeTitleWidth));

        // List of Monthly Attendance for [MONTH-NAME]
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        String monthName = Month.of(month).name();
        String monthCapitalize = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
        String listMonthTitle = "List of Monthly Attendance for " + monthCapitalize;

        Cell cellMonthTitle = CellText.createCell(rows.get(3), 17, cellStyles.get("subTitleStyle"), listMonthTitle);
        reportSheet.addMergedRegion(new CellRangeAddress(cellMonthTitle.getRowIndex(), cellMonthTitle.getRowIndex(), cellMonthTitle.getColumnIndex(), cellMonthTitle.getColumnIndex() + mergeTitleWidth));

        // [GENERATION-NAME]
        Generation currentGeneration = generationService.findByClassroomId(classroomId);
        String generationName = currentGeneration.getName();
        String duration = "";
        String generationTitle = generationName + " " + duration;

        Cell cellGenerationTitle = CellText.createCell(rows.get(4), 17, cellStyles.get("subTitleStyle"), generationTitle);
        reportSheet.addMergedRegion(new CellRangeAddress(cellGenerationTitle.getRowIndex(), cellGenerationTitle.getRowIndex(), cellGenerationTitle.getColumnIndex(), cellGenerationTitle.getColumnIndex() + mergeTitleWidth));

        // Time : 8:00 AM - 5:00 PM
        String studyTimeTitle = "Time : 8:00 AM - 5:00 PM";
        Cell cellTimeTitle = CellText.createCell(rows.get(5), 17, cellStyles.get("subTitleStyle"), studyTimeTitle);
        reportSheet.addMergedRegion(new CellRangeAddress(cellTimeTitle.getRowIndex(), cellTimeTitle.getRowIndex(), cellTimeTitle.getColumnIndex(), cellTimeTitle.getColumnIndex() + mergeTitleWidth));

        // 'Classroom Name' + Class
        String classNameTitle = classroomName.equals("BTB") ? "Battambang Class" :
                classroomName.equals("PP") ? "Phnom Penh Class" :
                        classroomName.equals("SR") ? "Siemreap Class" :
                                classroomName.equals("KP") ? "Kompot Class" :
                                        classroomName.equals("KPS") ? "Kompong Som Class" : "";

        Cell cellClassNameTitle = CellText.createCell(rows.get(6), 17, cellStyles.get("subTitleStyle"), classNameTitle);
        reportSheet.addMergedRegion(new CellRangeAddress(cellClassNameTitle.getRowIndex(), cellClassNameTitle.getRowIndex(), cellClassNameTitle.getColumnIndex(), cellClassNameTitle.getColumnIndex() + mergeTitleWidth));

        // Table header
        // NO
        Cell cellNo = CellText.createCell(rows.get(8), 0, cellStyles.get("tableHeadStyle"), "NO");
        CellRangeAddress rangeNo = new CellRangeAddress(cellNo.getRowIndex(), rows.get(11).getRowNum(), cellNo.getColumnIndex(), cellNo.getColumnIndex());
        reportSheet.addMergedRegion(rangeNo);

        // EnrollId(Hidden)
        Cell cellEnrollId = CellText.createCell(rows.get(8), 1, cellStyles.get("tableHeadStyle"), "EnrollId");
        CellRangeAddress rangeEnrollId = new CellRangeAddress(cellEnrollId.getRowIndex(), rows.get(11).getRowNum(), cellEnrollId.getColumnIndex(), cellEnrollId.getColumnIndex());
        reportSheet.setColumnHidden(cellEnrollId.getColumnIndex(), true);
        reportSheet.addMergedRegion(rangeEnrollId);

        // Name
        Cell cellName = CellText.createCell(rows.get(8), 2, cellStyles.get("tableHeadStyle"), "Name");
        CellRangeAddress rangeName = new CellRangeAddress(cellName.getRowIndex(), rows.get(11).getRowNum(), cellName.getColumnIndex(), cellName.getColumnIndex());
        reportSheet.setColumnWidth(cellName.getColumnIndex(), PixelUtil.pixel2WidthUnits(200));
        reportSheet.addMergedRegion(rangeName);

        // Gender
        Cell cellGender = CellText.createCell(rows.get(8), 3, cellStyles.get("tableHeadStyle"), "Gender");
        CellRangeAddress rangeGender = new CellRangeAddress(cellGender.getRowIndex(), rows.get(11).getRowNum(), cellGender.getColumnIndex(), cellGender.getColumnIndex());
        reportSheet.setColumnWidth(cellGender.getColumnIndex(), PixelUtil.pixel2WidthUnits(80));
        reportSheet.addMergedRegion(rangeGender);

        // Week Section
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int monthLength = yearMonthObject.lengthOfMonth();
        int monthWeek = (int) Math.floor(monthLength / 7);
        int monthSpareDay = monthLength % 7;
        int beginIndex = cellGender.getColumnIndex() + 1;

        if (monthSpareDay > 0) monthWeek++;

        int mIndex = beginIndex;
        int aIndex = mIndex + 1;
        int startDayIndex = beginIndex;

        // Dayname, Daynumber, Shift M, A
        for (int i = 0; i < monthLength; i++) {
            int dayNumber = i + 1;
            String dayName = yearMonthObject.atDay(i + 1).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            if (!(dayName.equals("Sat") || dayName.equals("Sun"))) {
                int endDayIndex = startDayIndex + 1;

                Cell cellDayName = CellText.createCell(rows.get(9), startDayIndex, cellStyles.get("tableHeadStyle"), dayName);
                CellRangeAddress rangeDayName = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(9).getRowNum(), startDayIndex, endDayIndex);
                reportSheet.addMergedRegion(rangeDayName);
                CellMergeBorder.setMergeBorder(reportSheet, rangeDayName);

                Cell cellDayNumber = CellNumber.createCell(rows.get(10), startDayIndex, cellStyles.get("tableHeadStyle"), dayNumber);
                CellRangeAddress rangeDayNumber = new CellRangeAddress(rows.get(10).getRowNum(), rows.get(10).getRowNum(), startDayIndex, endDayIndex);
                reportSheet.addMergedRegion(rangeDayNumber);
                CellMergeBorder.setMergeBorder(reportSheet, rangeDayNumber);

                Cell cellShiftM = CellText.createCell(rows.get(11), mIndex, cellStyles.get("tableHeadStyle"), "M");
                reportSheet.setColumnWidth(cellShiftM.getColumnIndex(), PixelUtil.pixel2WidthUnits(30));
                CellBorder.setBorder(cellShiftM.getCellStyle());

                Cell cellShiftA = CellText.createCell(rows.get(11), aIndex, cellStyles.get("tableHeadStyle"), "A");
                reportSheet.setColumnWidth(cellShiftA.getColumnIndex(), PixelUtil.pixel2WidthUnits(30));
                CellBorder.setBorder(cellShiftA.getCellStyle());

                mIndex = aIndex + 1;
                aIndex = mIndex + 1;
                startDayIndex = startDayIndex + 2;
            }
        }

        // Week [NUMBER]
        Cell cellWeek;
        int weekIndex = beginIndex;

        int i = 0;
        int j = 0;
        int dayShiftLength = 0;

        while (i < monthWeek) {
            int lengthColSpan = 0;
            int dayPerWeek = 0;

            while (j < monthLength) {
                String dayName = yearMonthObject.atDay(j + 1).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

                if (!(dayName.equals("Sat") || dayName.equals("Sun"))) {
                    lengthColSpan += 2;
                    dayPerWeek++;
                    dayShiftLength += 2;
                }
                j++;

                if (dayPerWeek >= 5) break;
            }

            if (lengthColSpan > 0) {
                CellRangeAddress rangeWeek;
                int nextWeekIndex = weekIndex + lengthColSpan - 1;
                cellWeek = CellText.createCell(rows.get(8), weekIndex, cellStyles.get("tableHeadStyle"), "Week " + (i + 1));
                rangeWeek = new CellRangeAddress(cellWeek.getRowIndex(), cellWeek.getRowIndex(), weekIndex, nextWeekIndex);
                reportSheet.addMergedRegion(rangeWeek);
                CellMergeBorder.setMergeBorder(reportSheet, rangeWeek);

                weekIndex = nextWeekIndex + 1;
            }

            i++;
        }

        // Total
        int afterWeekIndex = rows.get(8).getLastCellNum();
        int startTotalIndex = afterWeekIndex;
        int endTotalIndex = startTotalIndex + 4;

        Cell cellTotalHead = CellText.createCell(rows.get(8), startTotalIndex, cellStyles.get("tableHeadStyle"), "Total");
        CellRangeAddress rangeTotalHead = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(8).getRowNum(), startTotalIndex, endTotalIndex);
        reportSheet.addMergedRegion(rangeTotalHead);

        // Missed Scan
        int afterDayIndex = rows.get(9).getLastCellNum();
        Cell cellMissedScan = CellText.createCell(rows.get(9), afterDayIndex, cellStyles.get("missedScanStyle"), "Missed Scan");
        CellRangeAddress rangeMissedScan = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(11).getRowNum(), afterDayIndex, afterDayIndex);
        cellMissedScan.getCellStyle().setWrapText(true);

        reportSheet.setColumnWidth(cellMissedScan.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeMissedScan);

        // Go Outside
        int afterMissedScanIndex = cellMissedScan.getColumnIndex() + 1;
        Cell cellGoOutside = CellText.createCell(rows.get(9), afterMissedScanIndex, cellStyles.get("goOutsideStyle"), "Go Outside");
        CellRangeAddress rangeGoOutside = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(11).getRowNum(), afterMissedScanIndex, afterMissedScanIndex);
        cellGoOutside.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellGoOutside.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeGoOutside);

        // Late
        int afterGoOutIndex = cellGoOutside.getColumnIndex() + 1;
        Cell cellLate = CellText.createCell(rows.get(9), afterGoOutIndex, cellStyles.get("lateStyle"), "Late");
        CellRangeAddress rangeLate = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(11).getRowNum(), afterGoOutIndex, afterGoOutIndex);
        cellLate.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellLate.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeLate);

        // Permission
        int afterLateIndex = cellLate.getColumnIndex() + 1;
        Cell cellPermission = CellText.createCell(rows.get(9), afterLateIndex, cellStyles.get("permissionStyle"), "Permission");
        CellRangeAddress rangePermission = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(11).getRowNum(), afterLateIndex, afterLateIndex);
        cellPermission.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellPermission.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangePermission);

        // Absent
        int afterPermissionIndex = cellPermission.getColumnIndex() + 1;
        Cell cellAbsent = CellText.createCell(rows.get(9), afterPermissionIndex, cellStyles.get("absentStyle"), "Absent");
        CellRangeAddress rangeAbsent = new CellRangeAddress(rows.get(9).getRowNum(), rows.get(11).getRowNum(), afterPermissionIndex, afterPermissionIndex);
        cellAbsent.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellAbsent.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeAbsent);

        // Total Attendance
        int afterTotalHeadIndex = cellTotalHead.getColumnIndex() + 5;
        Cell cellTotalAttendance = CellText.createCell(rows.get(8), afterTotalHeadIndex, cellStyles.get("tableHeadStyle"), "Total Attendance");
        CellRangeAddress rangeTotalAttendance = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterTotalHeadIndex, afterTotalHeadIndex);
        cellTotalAttendance.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellTotalAttendance.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeTotalAttendance);

        // Attendance Score
        int afterTotalAttendanceIndex = cellTotalAttendance.getColumnIndex() + 1;
        Cell cellAttendanceScore = CellText.createCell(rows.get(8), afterTotalAttendanceIndex, cellStyles.get("tableHeadStyle"), "Attendance Score");
        CellRangeAddress rangeAttendanceScore = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterTotalAttendanceIndex, afterTotalAttendanceIndex);
        cellAttendanceScore.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellAttendanceScore.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeAttendanceScore);

        // Activity Score
        int afterAttendanceScoreIndex = cellAttendanceScore.getColumnIndex() + 1;
        Cell cellActivityScore = CellText.createCell(rows.get(8), afterAttendanceScoreIndex, cellStyles.get("tableHeadStyle"), "Activity Score");
        CellRangeAddress rangeActivityScore = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterAttendanceScoreIndex, afterAttendanceScoreIndex);
        cellActivityScore.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellActivityScore.getColumnIndex(), PixelUtil.pixel2WidthUnits(80));
        reportSheet.addMergedRegion(rangeActivityScore);

        // Attitude Score
        int afterActivityScoreIndex = cellActivityScore.getColumnIndex() + 1;
        Cell cellAttitudeScore = CellText.createCell(rows.get(8), afterActivityScoreIndex, cellStyles.get("tableHeadStyle"), "Attitude Score");
        CellRangeAddress rangeAttitudeScore = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterActivityScoreIndex, afterActivityScoreIndex);
        cellAttitudeScore.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellAttitudeScore.getColumnIndex(), PixelUtil.pixel2WidthUnits(80));
        reportSheet.addMergedRegion(rangeAttitudeScore);

        // Total Softskill Score
        int afterAttitudeScoreIndex = cellAttitudeScore.getColumnIndex() + 1;
        Cell cellTotalSoftskillScore = CellText.createCell(rows.get(8), afterAttitudeScoreIndex, cellStyles.get("tableHeadStyle"), "Total Softskill Score");
        CellRangeAddress rangeTotalSoftskillScore = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterAttitudeScoreIndex, afterAttitudeScoreIndex);
        cellTotalSoftskillScore.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellTotalSoftskillScore.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeTotalSoftskillScore);

        // Perfect Attendance
        int afterTotalSoftskillIndex = cellTotalSoftskillScore.getColumnIndex() + 1;
        Cell cellPerfectAttendance = CellText.createCell(rows.get(8), afterTotalSoftskillIndex, cellStyles.get("tableHeadStyle"), "Perfect Attendance");
        CellRangeAddress rangePerfectAttendance = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterTotalSoftskillIndex, afterTotalSoftskillIndex);
        cellPerfectAttendance.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellPerfectAttendance.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangePerfectAttendance);

        // Status
        int afterPerfectAttendanceIndex = cellPerfectAttendance.getColumnIndex() + 1;
        Cell cellStatus = CellText.createCell(rows.get(8), afterPerfectAttendanceIndex, cellStyles.get("tableHeadStyle"), "Status");
        CellRangeAddress rangeStatus = new CellRangeAddress(rows.get(8).getRowNum(), rows.get(11).getRowNum(), afterPerfectAttendanceIndex, afterPerfectAttendanceIndex);
        cellStatus.getCellStyle().setWrapText(true);
        reportSheet.setColumnWidth(cellStatus.getColumnIndex(), PixelUtil.pixel2WidthUnits(100));
        reportSheet.addMergedRegion(rangeStatus);

        // Calculate Cells
        Cell cellMissedScanData = null;
        Cell cellGoOutsideData = null;
        Cell cellLateData = null;
        Cell cellPermissionData = null;
        Cell cellAbsentData = null;
        Cell cellTotalAttendanceScoreData = null;
        Cell cellAttendanceScoreData = null;
        Cell cellActivityData = null;
        Cell cellAttitudeData = null;
        Cell cellTotalSoftskillScoreData = null;
        Cell cellPerfectAttendanceData = null;
        Cell cellStatusData = null;

        // Set Border
        CellMergeBorder.setMergeBorder(reportSheet, rangeNo);
        CellMergeBorder.setMergeBorder(reportSheet, rangeEnrollId);
        CellMergeBorder.setMergeBorder(reportSheet, rangeName);
        CellMergeBorder.setMergeBorder(reportSheet, rangeGender);
        CellMergeBorder.setMergeBorder(reportSheet, rangeTotalHead);
        CellMergeBorder.setMergeBorder(reportSheet, rangeMissedScan);
        CellMergeBorder.setMergeBorder(reportSheet, rangeGoOutside);
        CellMergeBorder.setMergeBorder(reportSheet, rangeLate);
        CellMergeBorder.setMergeBorder(reportSheet, rangePermission);
        CellMergeBorder.setMergeBorder(reportSheet, rangeAbsent);
        CellMergeBorder.setMergeBorder(reportSheet, rangeTotalAttendance);
        CellMergeBorder.setMergeBorder(reportSheet, rangeAttendanceScore);
        CellMergeBorder.setMergeBorder(reportSheet, rangeActivityScore);
        CellMergeBorder.setMergeBorder(reportSheet, rangeAttitudeScore);
        CellMergeBorder.setMergeBorder(reportSheet, rangeTotalSoftskillScore);
        CellMergeBorder.setMergeBorder(reportSheet, rangePerfectAttendance);
        CellMergeBorder.setMergeBorder(reportSheet, rangeStatus);

        // Generate Cell
        int studentDetailLength = 4;
        int calculateLength = 12;
        List<Classenroll> studentList = reportService.findAllEnrollByClassroomId(classroomId);
        int studentListLength = studentList.size();

        for (int a = 0; a < studentListLength; a++) {
            int colCounter = 0;
            Row row = reportSheet.createRow(rows.get(12).getRowNum() + a);

            for (int b = 0; b < studentDetailLength; b++) {
                Cell cellStudentDetail = row.createCell(colCounter, CellType.STRING);
                cellStudentDetail.setCellStyle(cellStyles.get("tableBodyStyle"));
                CellBorder.setBorder(cellStudentDetail.getCellStyle());
                colCounter++;
            }

            int firstDayIndex = colCounter;
            for (int c = 0; c < dayShiftLength; c++) {
                Cell cellDayShift = row.createCell(colCounter, CellType.STRING);
                cellDayShift.setCellStyle(cellStyles.get("tableBodyStyle"));
                CellBorder.setBorder(cellDayShift.getCellStyle());
                colCounter++;
            }

            int lastDayIndex = colCounter - 1;
            for (int d = 0; d < calculateLength; d++) {
                String formula = null;
                Cell cellCalculate = row.createCell(colCounter, CellType.FORMULA);
                cellCalculate.setCellStyle(cellStyles.get("tableBodyStyle"));
                CellBorder.setBorder(cellCalculate.getCellStyle());

                CellAddress cellStartAddress = row.getCell(firstDayIndex).getAddress();
                CellAddress cellEndAddress = row.getCell(lastDayIndex).getAddress();

                switch (d) {
                    case MISSED_SCAN:
                        cellMissedScanData = cellCalculate;
                        formula = "COUNTIF(" + cellStartAddress + ":" + cellEndAddress + ",\"M\")";
                        break;

                    case GO_OUTSIDE:
                        cellGoOutsideData = cellCalculate;
                        formula = "COUNTIF(" + cellStartAddress + ":" + cellEndAddress + ",\"G\")";
                        break;

                    case LATE:
                        cellLateData = cellCalculate;
                        formula = "COUNTIF(" + cellStartAddress + ":" + cellEndAddress + ",\"L\")";
                        break;

                    case PERMISSION:
                        cellPermissionData = cellCalculate;
                        formula = "COUNTIF(" + cellStartAddress + ":" + cellEndAddress + ",\"P\")";
                        break;

                    case ABSENT:
                        cellAbsentData = cellCalculate;
                        formula = "COUNTIF(" + cellStartAddress + ":" + cellEndAddress + ",\"A\")";
                        break;

                    case TOTAL_ATTENDANCE:
                        cellTotalAttendanceScoreData = cellCalculate;
                        String totalMissedScan = cellMissedScanData.getAddress() + "*0.1";
                        String totalGoOutside = cellGoOutsideData.getAddress() + "*0.25";
                        String totalLate = cellLateData.getAddress() + "*0.25";
                        String totalPermission = cellLateData.getAddress() + "*0.5";
                        String totalAbsent = cellAbsentData.getAddress() + "*1";

                        formula = totalMissedScan + "+" + totalGoOutside + "+" + totalLate + "+" + totalPermission + "+" + totalAbsent;
                        break;

                    case ATTENDANCE_SCORE:
                        cellAttendanceScoreData = cellCalculate;
                        formula = "4 - " + cellTotalAttendanceScoreData.getAddress();
                        break;

                    case ACTIVITY_SCORE:
                        cellActivityData = cellCalculate;
                        cellActivityData.setCellValue(3);
                        break;

                    case ATTITUDE_SCORE:
                        cellAttitudeData = cellCalculate;
                        cellAttitudeData.setCellValue(3);
                        break;

                    case TOTAL_SOFTSKILL_SCORE:
                        cellTotalSoftskillScoreData = cellCalculate;
                        formula = "SUM(" + cellAttendanceScoreData.getAddress() + ":" + cellAttitudeData.getAddress() + ")";
                        break;

                    case PERFECT_ATTENDANCE:
                        cellPerfectAttendanceData = cellCalculate;
                        formula = "IF(" + cellTotalSoftskillScoreData.getAddress() + ">=10, \"PA\", \"-\")";
                        break;

                    case STATUS:
                        cellStatusData = cellCalculate;
                        formula = "IF(OR(" + cellAttendanceScoreData.getAddress() + "<2.5," + cellTotalSoftskillScoreData.getAddress() + "<5),\"Warning\",\"-\")";
                        break;
                }

                cellCalculate.setCellFormula(formula);
                colCounter++;
            }

            // Conditional Rules
            CellRangeAddress[] rangeDays = {
                    new CellRangeAddress(row.getRowNum(), row.getRowNum(), firstDayIndex, lastDayIndex)
            };

            ConditionalFormattingRule[] rules = {
                    reportSheet.getSheetConditionalFormatting().createConditionalFormattingRule(
                            ComparisonOperator.EQUAL,
                            "\"M\"",
                            null
                    ),

                    reportSheet.getSheetConditionalFormatting().createConditionalFormattingRule(
                            ComparisonOperator.EQUAL,
                            "\"G\"",
                            null
                    ),

                    reportSheet.getSheetConditionalFormatting().createConditionalFormattingRule(
                            ComparisonOperator.EQUAL,
                            "\"L\"",
                            null
                    ),

                    reportSheet.getSheetConditionalFormatting().createConditionalFormattingRule(
                            ComparisonOperator.EQUAL,
                            "\"P\"",
                            null
                    ),

                    reportSheet.getSheetConditionalFormatting().createConditionalFormattingRule(
                            ComparisonOperator.EQUAL,
                            "\"A\"",
                            null
                    )
            };

            // Create pattern with red background
            PatternFormatting[] patternFormatting = {
                    rules[0].createPatternFormatting(),
                    rules[1].createPatternFormatting(),
                    rules[2].createPatternFormatting(),
                    rules[3].createPatternFormatting(),
                    rules[4].createPatternFormatting(),
            };

            patternFormatting[0].setFillBackgroundColor(COLOR_MISSED_SCAN.getIndex());
            patternFormatting[1].setFillBackgroundColor(COLOR_GO_OUTSIDE.getIndex());
            patternFormatting[2].setFillBackgroundColor(COLOR_LATE.getIndex());
            patternFormatting[3].setFillBackgroundColor(COLOR_PERMISSION.getIndex());
            patternFormatting[4].setFillBackgroundColor(COLOR_ABSENT.getIndex());


            reportSheet.getSheetConditionalFormatting().addConditionalFormatting(rangeDays, rules[0]);
            reportSheet.getSheetConditionalFormatting().addConditionalFormatting(rangeDays, rules[1]);
            reportSheet.getSheetConditionalFormatting().addConditionalFormatting(rangeDays, rules[2]);
            reportSheet.getSheetConditionalFormatting().addConditionalFormatting(rangeDays, rules[3]);
            reportSheet.getSheetConditionalFormatting().addConditionalFormatting(rangeDays, rules[4]);
        }

        // Student Detail
        for (int n = 0; n < studentListLength; n++) {
            int enrollId = studentList.get(n).getId();
            String fullName = studentList.get(n).getUser().getFullName();
            String gender = studentList.get(n).getUser().getGender().toUpperCase(Locale.ENGLISH);

            Row row = reportSheet.getRow(rows.get(12).getRowNum() + n);
            row.setHeightInPoints(23);

            Cell cellNoByRow = CellNumber.createCell(row, cellNo.getColumnIndex(), cellStyles.get("tableBodyStyle"), n + 1);

            Cell cellEnrollIdByRow = CellNumber.createCell(row, cellEnrollId.getColumnIndex(), cellStyles.get("tableBodyStyle"), enrollId);

            Cell cellNameByRow = CellText.createCell(row, cellName.getColumnIndex(), cellStyles.get("tableBodyStyle"), fullName);
            cellNameByRow.getCellStyle().setWrapText(true);

            Cell cellGenderByRow = CellText.createCell(row, cellGender.getColumnIndex(), cellStyles.get("tableBodyStyle"), gender);
        }

        // Attendance List
        List<Attendance> attendanceList = reportService.findAll(classroomId, date);
        for (int n = 0; n < studentListLength; n++) {
            Row row = reportSheet.getRow(rows.get(12).getRowNum() + n);
            int enrollId = (int) row.getCell(cellEnrollId.getColumnIndex()).getNumericCellValue();

            for (Attendance attendanceData : attendanceList) {
                int rowEnrollId = attendanceData.getClassenroll().getId();
                if (rowEnrollId == enrollId) {
                    String leaveStatus = attendanceData.getLeaveStatus();

                    int startIndex = 0;
                    int dayFrom = Integer.parseInt(attendanceData.getDateFrom().split("-")[2]);
                    int r10StartIndex = rows.get(10).getFirstCellNum();
                    int r10EndIndex = rows.get(10).getLastCellNum();
                    for (int m = r10StartIndex; m < r10EndIndex; m++) {
                        if (m % 2 != 0)
                            continue;

                        int day = (int) rows.get(10).getCell(m, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        if (day == dayFrom) {
                            startIndex = rows.get(10).getCell(m).getColumnIndex();
                            break;
                        }
                    }

                    if (leaveStatus.equals("g") || leaveStatus.equals("l") || leaveStatus.equals("le")) {
                        String badge;
                        int timeShift = Integer.parseInt(attendanceData.getArriveTime().split(":")[0]) < 13 ? 0 : 1;
                        int cellPos = startIndex + timeShift;

                        if (leaveStatus.equals("g"))
                            badge = "G";
                        else if (leaveStatus.equals("l"))
                            badge = "L";
                        else
                            badge = "LE";

                        Cell cellLeaveStatus = row.getCell(cellPos, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        cellLeaveStatus.setCellValue(badge);
                        cellLeaveStatus.setCellStyle(cellStyles.get("tableBodyStyle"));
                    } else if (leaveStatus.equals("p")) {
                        int startShift = attendanceData.getAmPm().toUpperCase().equals("AM") ? 0 : 1;
                        int permissionCount = attendanceData.getPermissionCount();
                        int startCol = startIndex + startShift;
                        int endCol = startCol + permissionCount;

                        String badge = "P";

                        for (int p = startCol; p < endCol; p++) {
                            Cell cellLeaveStatus = row.getCell(p, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            cellLeaveStatus.setCellValue(badge);
                            cellLeaveStatus.setCellStyle(cellStyles.get("tableBodyStyle"));
                        }
                    }
                }
            }
        }

        try (OutputStream fileOut = new FileOutputStream(FILE_DIRECTORY)) {
            wb.write(fileOut);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return FILE_NAME;
    }

    private void initializeInterfaceMethods() {
        // TextCellInterface
        CellText = (row, colIndex, cellStyle, cellValue) -> {
            Cell cell = row.createCell(colIndex, CellType.STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cellValue);
            return cell;
        };

        CellNumber = (row, colIndex, cellStyle, cellValue) -> {
            Cell cell = row.createCell(colIndex, CellType.NUMERIC);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cellValue);
            return cell;
        };

        // CellBorderInterface
        CellBorder = (cellStyle) -> {
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        };

        CellMergeBorder = (sheet, range) -> {
            RegionUtil.setBorderBottom(BorderStyle.THIN, range, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, range, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, range, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
        };

        FontCreator = (wb, DEFAULT_FONT, fontSize, isBold, textColor) -> {
            Font font = wb.createFont();
            font.setFontName(DEFAULT_FONT);
            font.setFontHeightInPoints((short) fontSize);
            font.setColor(textColor.getIndex());
            font.setBold(isBold);
            return font;
        };

        CellStyleCreator = (wb, font, horizontalAlignment, verticalAlignment) -> {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(horizontalAlignment);
            cellStyle.setVerticalAlignment(verticalAlignment);
            return cellStyle;
        };

        CellStyleWithColorCreator = (wb, font, foregroundColor, horizontalAlignment, verticalAlignment) -> {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(horizontalAlignment);
            cellStyle.setVerticalAlignment(verticalAlignment);
            cellStyle.setFillForegroundColor(foregroundColor.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            return cellStyle;
        };
    }
}
