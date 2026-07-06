package utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static String getCellData(String filePath, int row, int col) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);

            Sheet sheet = workbook.getSheetAt(0);

            String value = sheet.getRow(row).getCell(col).toString();

            workbook.close();
            fis.close();

            return value;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}