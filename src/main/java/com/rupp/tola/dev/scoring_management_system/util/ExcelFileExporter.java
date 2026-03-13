package com.rupp.tola.dev.scoring_management_system.util;

import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rupp.tola.dev.scoring_management_system.entity.Students;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

public class ExcelFileExporter {

      public static void exportStudentsToExcelFile(HttpServletResponse response, List<Students> studentsList)throws IOException {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createCellStyle();

      }

      public static void setBorder(CellStyle style) {
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);

      }

      public static CellStyle createHeaderStyle(Workbook workbook) {  
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();     
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            setBorder(style);
            return style;
      }

      public static CellStyle createBodyStyle(Workbook workbook) {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
            setBorder(style);
            return style;
      }
}
