package com.edu.pe.controller;

import com.edu.pe.modelo.Empleado;
import com.edu.pe.service.IEmpleadoService;

// Importaciones explícitas para PDF (OpenPDF)
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

// Importaciones explícitas para Excel (Apache POI)
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping("/empleados/exportar/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Empleados.xlsx");

        List<Empleado> lista = empleadoService.ListarTodos();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        // Cabecera
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Nombres", "Apellidos", "DNI", "Fecha Ingreso"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        // Datos
        int rowIdx = 1;
        for (Empleado e : lista) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getIdempleado());
            row.createCell(1).setCellValue(e.getNomempleado());
            row.createCell(2).setCellValue(e.getApelempleado());
            row.createCell(3).setCellValue(e.getDniempleado());
            row.createCell(4).setCellValue(e.getFechaingreso().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/empleados/exportar/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Empleados.pdf");

        List<Empleado> lista = empleadoService.ListarTodos();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Reporte de Empleados - Mi Constructora S.A.C"));
        document.add(new Paragraph(" ")); // Espacio en blanco

        PdfPTable table = new PdfPTable(5); // 5 columnas
        table.setWidthPercentage(100);

        table.addCell("ID");
        table.addCell("Nombres");
        table.addCell("Apellidos");
        table.addCell("DNI");
        table.addCell("Fecha Ingreso");

        for (Empleado e : lista) {
            table.addCell(String.valueOf(e.getIdempleado()));
            table.addCell(e.getNomempleado());
            table.addCell(e.getApelempleado());
            table.addCell(e.getDniempleado());
            table.addCell(e.getFechaingreso().toString());
        }

        document.add(table);
        document.close();
    }
}