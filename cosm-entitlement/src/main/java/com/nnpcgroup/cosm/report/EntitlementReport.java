/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nnpcgroup.cosm.controller.MonthGenerator;
import com.nnpcgroup.cosm.controller.PeriodMonth;
import com.nnpcgroup.cosm.ejb.forecast.jv.JvForecastServices;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecast;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author Ayemi
 */
@WebServlet(name = "EntitlementReport", urlPatterns = {"/faces/reports/EntitlementReport"})
public class EntitlementReport extends HttpServlet {

    @EJB
    private JvForecastServices forecastBean;
    @Inject
    MonthGenerator monthGen;

    private java.util.List<JvForecastDetail> productions;

    Integer periodYear;
    Integer periodMonth;
    ByteArrayOutputStream baos;

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String year = request.getParameter("periodYear");
            String month = request.getParameter("periodMonth");

            periodYear = Integer.parseInt(year);
            periodMonth = Integer.parseInt(month);

            Document document = new Document();
            baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            
            document.close();
            
            setResponseHeaders(response);

            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
    }

    private void addContent(Document document) throws DocumentException {
        loadProductions();
        PdfPTable table = new PdfPTable(5);
        PdfPCell c1 = new PdfPCell(new Phrase("JV COMPANY"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CRUDE TYPE"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("NNPC (BBLS)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("COMPANY (BBLS)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("REMARKS"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        addTableContent(table);

        document.add(table);
    }

    private void addTableContent(PdfPTable table) {
        if (productions != null) {
            for (JvForecastDetail forecast : productions) {
                table.addCell(forecast.getContract().getFiscalArrangement().getOperator().getName());
                table.addCell(forecast.getContract().getCrudeType().getCode());
                table.addCell(String.valueOf(forecast.getLifting()));
                table.addCell(String.valueOf(forecast.getPartnerLifting()));
                table.addCell("");
            }
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("JV Entitlement Advice");
        document.addSubject("JV Entitlement Advice");
        document.addKeywords("Entitlement, Joint Venture");
        document.addAuthor("COSM");
        document.addCreator("COSM");
        document.addCreationDate();
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("ENTITLEMENT ADVICE", catFont));
        addEmptyLine(preface, 1);
        PeriodMonth pm = monthGen.find(periodMonth);
        preface.add(new Paragraph(String.format("NNPC %s %d JV & AF ENTITLEMENT", pm.getMonthStr(), periodYear), smallBold));
        addEmptyLine(preface, 3);
        document.add(preface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void loadProductions() {
        if (periodYear != null && periodMonth != null) {
            productions = forecastBean.findByYearAndMonth(periodYear, periodMonth);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
