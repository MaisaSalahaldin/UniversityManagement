package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelReportGenerator {
    public void generateReport() {
        String query = "SELECT s.StudentsName AS student_name, d.department_name, c.course_name " +
                "FROM Students s " +
                "JOIN Departments d ON s.department_id = d.department_id " +
                "JOIN Enrollments e ON s.student_id = e.student_id " +
                "JOIN Courses c ON e.course_id = c.course_id";

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            //create Excel file
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Students Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Student Name", "Department", "Course Name"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowCount = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(resultSet.getString("student_name"));
                row.createCell(1).setCellValue(resultSet.getString("department_name"));
                row.createCell(2).setCellValue(resultSet.getString("course_name"));
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to Excel file
            try (FileOutputStream outputStream = new FileOutputStream("students_report.xlsx")) {
                workbook.write(outputStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            workbook.close();
            System.out.println("Excel report generated: students_report.xlsx");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
