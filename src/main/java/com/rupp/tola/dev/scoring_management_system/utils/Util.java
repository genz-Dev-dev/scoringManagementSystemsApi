package com.rupp.tola.dev.scoring_management_system.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    /**
     * generate 6 digit string One Time Password (OTP)
     * @return
     */
    public static String generateOtp() {
        int randomOtp = ThreadLocalRandom.current().nextInt(100000 , 1000000);
        return String.valueOf(randomOtp);
    }

    /**
     * convert fro string like this "2007-02-01" to LocalDate Type
     * @param data
     * @return
     */
    public static LocalDate convertToLocalDate(String data) {
        try {
            return LocalDate.parse(data);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * if value null return empty string
     * @param value
     * @return
     */
    public static String text(String value) {
        return Objects.toString(value, "");
    }

    /**
     * convert to string Util using with cell each row of Excel file
     * @param cell
     * @return
     */
    public static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
