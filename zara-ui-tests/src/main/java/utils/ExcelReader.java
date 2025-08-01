package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

public class ExcelReader {

    private static Workbook workbook;

    public ExcelReader(String fileNameInResources) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(fileNameInResources));
            this.workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        return getCellData("Sheet1", rowNum, colNum);
    }

    public static String getCellData(String sheetName, int rowNum, int colNum) {
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return null;

            Row row = sheet.getRow(rowNum);
            if (row == null) return null;

            Cell cell = row.getCell(colNum);
            if (cell == null) return null;

            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                default -> "";
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (workbook != null) workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCellDataFromResources(String fileNameInResources, int rowNum, int colNum) {
    try {
        InputStream inputStream = ExcelReader.class.getClassLoader().getResourceAsStream(fileNameInResources);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        return sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
