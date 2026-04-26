package com.kh.rupp_dev.boukryuniversity.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    private static final List<String> IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png");

    /**
     * update image from multipart file request
     * @param file
     * @param dir
     * @return
     */
    public static String uploadImage(MultipartFile file , String dir) {
        try {
            if (Objects.isNull(file)) {
                throw new IllegalArgumentException("File is null");
            }

            String extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();

            if(!IMAGE_EXTENSIONS.contains(extension)) {
                throw new IllegalArgumentException("File is not a valid file name");
            }

            Path uploadPath = Paths.get(dir);

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            String uniqueName = System.currentTimeMillis() + "_" + fileName;
            Path filePath = uploadPath.resolve(uniqueName)
                    .toAbsolutePath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return uniqueName;
        }catch (IOException e){
            throw new IllegalArgumentException("Could not upload file: " + e.getMessage());
        }
    }

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
