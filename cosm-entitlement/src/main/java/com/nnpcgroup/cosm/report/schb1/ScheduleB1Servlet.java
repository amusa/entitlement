/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report.schb1;

import com.nnpcgroup.cosm.report.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nnpcgroup.cosm.controller.CostOilController;
import com.nnpcgroup.cosm.controller.MonthGenerator;
import com.nnpcgroup.cosm.controller.PeriodMonth;
import com.nnpcgroup.cosm.ejb.FiscalPeriodService;
import com.nnpcgroup.cosm.ejb.ProductionSharingContractBean;
import com.nnpcgroup.cosm.ejb.cost.ProductionCostServices;
import com.nnpcgroup.cosm.ejb.crude.CrudePriceBean;
import com.nnpcgroup.cosm.ejb.lifting.PscLiftingServices;
import com.nnpcgroup.cosm.ejb.tax.TaxServices;
import com.nnpcgroup.cosm.entity.FiscalPeriod;
import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.crude.CrudePrice;
import com.nnpcgroup.cosm.entity.crude.CrudePricePK;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.ejb.EJB;

/**
 * @author Ayemi
 * @since 02.12.0216
 */
@WebServlet(name = "ScheduleB1Report", urlPatterns = {"/faces/reports/SchB1"})
public class ScheduleB1Servlet extends HttpServlet {

    @Inject
    MonthGenerator monthGen;

    @Inject
    TaxServices taxBean;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    CostOilController costOilController;

    @EJB
    private PscLiftingServices liftingBean;

    @EJB
    private CrudePriceBean priceBean;

    @EJB
    private ProductionSharingContractBean pscBean;

    @EJB
    private ProductionCostServices prodCostBean;

    private java.util.List<LiftingSummary> liftingSummaryList;
    private java.util.List<ProceedAllocation> proceedAllocationList;

    Long pscId;
    Integer periodYear;
    Integer periodMonth;
    ProductionSharingContract psc;
    ByteArrayOutputStream baos;

    private static final Font CAT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font RED_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font MID_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font SUB_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String id = request.getParameter("pscId");
            String year = request.getParameter("periodYear");
            String month = request.getParameter("periodMonth");

            this.pscId = Long.parseLong(id);
            this.periodYear = Integer.parseInt(year);
            this.periodMonth = Integer.parseInt(month);

            this.psc = pscBean.find(this.pscId);

            Document document = new Document(PageSize.A4.rotate());
            baos = new ByteArrayOutputStream();
            //PdfWriter.getInstance(document, baos);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new PdfWatermarkHelper());

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
        PdfPTable liftingSummarytable = new PdfPTable(6);
        addLiftingSummaryTableHeader(liftingSummarytable);
        addLiftingSummaryTableContent(liftingSummarytable);

        document.add(liftingSummarytable);

        PdfPTable allocationTable = new PdfPTable(7);
        addAllocationTableTitle(document);
        addAllocationTableHeader(allocationTable);
        addAllocationTableContent(allocationTable);

        document.add(allocationTable);
    }

    private void addLiftingSummaryTableHeader(PdfPTable table) throws DocumentException {

        table.setWidths(new float[]{2.5f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f});
        table.setWidthPercentage(100);

        PdfPCell c1 = new PdfPCell(new Phrase("LIFTING", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(2);
        c1.setFixedHeight(30);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("RP/OSP", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("PROCEED", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("PROCEED RECEIVED BY", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("DATE", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setFixedHeight(30);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("VOLUME", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CORPORATION", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CONTRACTOR", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(2);
    }

    private void addLiftingSummaryTableContent(PdfPTable table) {
        PdfPCell cell;

        processLiftingSummary();

        if (!liftingSummaryList.isEmpty()) {
            for (LiftingSummary ls : liftingSummaryList) {

                cell = new PdfPCell(new Phrase(ls.getLiftingDate().toUpperCase(), SMALL_BOLD));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.format("%,.2f", ls.getLiftingVolume())));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.format("%.2f", ls.getOsPrice())));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.format("%,.2f", ls.getProceed())));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.format("%,.2f", ls.getCorporationProceed())));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.format("%,.2f", ls.getContractorProceed())));
                table.addCell(cell);

            }
        }
    }

    private void addAllocationTableTitle(Document document) throws DocumentException {
        Paragraph allocationPreface = new Paragraph();
        addEmptyLine(allocationPreface, 2);
        allocationPreface.add(new Paragraph(String.format("SECTION B: Allocation of Proceeds - Expressed in US Dollars"), SMALL_BOLD));
        addEmptyLine(allocationPreface, 1);

        document.add(allocationPreface);
    }

    private void addAllocationTableHeader(PdfPTable table) throws DocumentException {
        table.setWidths(new float[]{4.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 4.0f});
        table.setWidthPercentage(100);

        PdfPCell c1 = new PdfPCell(new Phrase("CATEGORY (US$)", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        c1.setFixedHeight(30);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("PRIOR MONTH\nCARRY OVER", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("MONTHY\nCURRENT CHARGE", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("RECIEVABLE\nTHIS MONTH", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("ALLOCATION OF PROCEEDS", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CARRY OVER TO\nNEXT MONTH", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRowspan(2);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CORPORATION", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setFixedHeight(30);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CONTRACTOR", SMALL_BOLD));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(2);
    }

    private void addAllocationTableContent(PdfPTable table) {
        PdfPCell cell;

        processAllocation();

        if (!proceedAllocationList.isEmpty()) {
            for (ProceedAllocation pa : proceedAllocationList) {
                cell = new PdfPCell(new Phrase(pa.getCategoryTitle(), SMALL_BOLD));
                table.addCell(cell);

                if (pa.getMonthlyChargeBfw() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getMonthlyChargeBfw())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }

                table.addCell(cell);

                if (pa.getMonthlyCharge() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getMonthlyCharge())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }

                table.addCell(cell);

                if (pa.getRecoverable() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getRecoverable())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }
                table.addCell(cell);

                if (pa.getCorporationProceed() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getCorporationProceed())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }

                table.addCell(cell);

                if (pa.getContractorProceed() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getContractorProceed())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }

                table.addCell(cell);

                if (pa.getMonthlyChargeCfw() != null) {
                    cell = new PdfPCell(new Phrase(String.format("%,.2f", pa.getMonthlyChargeCfw())));
                } else {
                    cell = new PdfPCell(new Phrase(""));
                }

                table.addCell(cell);

            }
        }

    }

    private void addMetaData(Document document) {
        document.addTitle("SCHEDULE B1");
        document.addSubject("Monthly Accounting Analysis");
        document.addKeywords("PSC, Schedule B1, Report");
        document.addAuthor("COSM");
        document.addCreator("COSM");
        document.addCreationDate();
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph topPreface = new Paragraph();

        topPreface.add(new Paragraph(this.psc.getTitle(), SMALL_BOLD));
        topPreface.add(new Paragraph(String.format("CRUDE TYPE: %s", psc.getCrudeType().getCrudeType().toUpperCase()), SMALL_BOLD));

        addEmptyLine(topPreface, 1);
        document.add(topPreface);

        Paragraph midPreface = new Paragraph();
        midPreface.add(new Paragraph("SCHEDULE B1", SUB_FONT));

        addEmptyLine(midPreface, 1);

        PeriodMonth pm = monthGen.find(periodMonth);
        midPreface.add(new Paragraph("MONTHLY ACCOUNTING ANALYSIS", MID_BOLD));
        midPreface.add(new Paragraph(String.format("%s %d", pm.getMonthStr().toUpperCase(), periodYear), MID_BOLD));
        midPreface.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(midPreface, 2);
        document.add(midPreface);

        Paragraph liftingPreface = new Paragraph();
        liftingPreface.add(new Paragraph(String.format("SECTION A: Lifting Summary"), SMALL_BOLD));

        addEmptyLine(liftingPreface, 1);
        document.add(liftingPreface);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void processLiftingSummary() {
        if (this.psc != null && this.periodYear != null && this.periodMonth != null) {
            java.util.List<PscLifting> pscLiftings = liftingBean.find(this.psc, this.periodYear, this.periodMonth);
            liftingSummaryList = new ArrayList<>();

            double price;
            double proceed = 0;

            for (PscLifting lifting : pscLiftings) {
                CrudePricePK pricePK = new CrudePricePK();
                pricePK.setCrudeTypeCode(psc.getCrudeType().getCode());
                pricePK.setPriceDate(lifting.getLiftingDate());
                CrudePrice crudePrice = priceBean.find(pricePK);

                if (crudePrice != null) {
                    price = crudePrice.getOsPrice();
                } else {
                    price = 0;
                }

                proceed += (lifting.getTotalLifting() * price);

                LiftingSummary liftSumm = new LiftingSummary();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM");
                String liftingDate = dateFormat.format(lifting.getLiftingDate());
                double corpProceed = lifting.getOwnLifting() * price;
                double contProceed = lifting.getPartnerLifting() * price;

                liftSumm.setLiftingDate(liftingDate);
                liftSumm.setLiftingVolume(lifting.getTotalLifting());
                liftSumm.setOsPrice(price);
                liftSumm.setProceed(proceed);
                liftSumm.setCorporationProceed(corpProceed);
                liftSumm.setContractorProceed(contProceed);

                liftingSummaryList.add(liftSumm);

            }

        }

    }

    private void processAllocation() {
        proceedAllocationList = new ArrayList<>();

        ProceedAllocation royPa = new ProceedAllocation();
        Allocation royAlloc = processRoyalty(this.psc, this.periodYear, this.periodMonth);
        royPa.setCategoryTitle("ROYALTY OIL");
        royPa.setMonthlyChargeBfw(royAlloc.getChargeBfw());
        royPa.setMonthlyCharge(royAlloc.getMonthlyCharge());
        royPa.setRecoverable(royAlloc.getRecoverable());
        royPa.setCorporationProceed(royAlloc.getReceived());
        royPa.setMonthlyChargeCfw(royAlloc.getChargeCfw());

        proceedAllocationList.add(royPa);

        ProceedAllocation coPa = new ProceedAllocation();
        Allocation costOilAlloc = processCostOil(this.psc, this.periodYear, this.periodMonth);
        coPa.setCategoryTitle("COST OIL");
        coPa.setMonthlyChargeBfw(costOilAlloc.getChargeBfw());
        coPa.setMonthlyCharge(costOilAlloc.getMonthlyCharge());
        coPa.setRecoverable(costOilAlloc.getRecoverable());
        coPa.setContractorProceed(costOilAlloc.getReceived());
        coPa.setMonthlyChargeCfw(costOilAlloc.getChargeCfw());

        proceedAllocationList.add(coPa);

        ProceedAllocation toPa = new ProceedAllocation();
        Allocation taxOilAlloc = processTaxOil(this.psc, this.periodYear, this.periodMonth);
        toPa.setCategoryTitle("TAX OIL");
        toPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
        toPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
        toPa.setRecoverable(taxOilAlloc.getRecoverable());
        toPa.setCorporationProceed(taxOilAlloc.getReceived());
        toPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(toPa);

        ProceedAllocation corpPoPa = new ProceedAllocation();
        // Allocation profOilAlloc = processProfitOil(this.psc, this.periodYear, this.periodMonth);
        corpPoPa.setCategoryTitle("CORP PROFIT OIL");
//        corpPoPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
//        corpPoPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
//        corpPoPa.setRecoverable(taxOilAlloc.getRecoverable());
//        corpPoPa.setContractorProceed(taxOilAlloc.getReceived());
//        corpPoPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(corpPoPa);

        ProceedAllocation contPoPa = new ProceedAllocation();
        // Allocation profOilAlloc = processProfitOil(this.psc, this.periodYear, this.periodMonth);
        contPoPa.setCategoryTitle("CONT PROFIT OIL");
//        contPoPa.setMonthlyChargeBfw(taxOilAlloc.getChargeBfw());
//        contPoPa.setMonthlyCharge(taxOilAlloc.getMonthlyCharge());
//        contPoPa.setRecoverable(taxOilAlloc.getRecoverable());
//        contPoPa.setContractorProceed(taxOilAlloc.getReceived());
//        contPoPa.setMonthlyChargeCfw(taxOilAlloc.getChargeCfw());

        proceedAllocationList.add(contPoPa);

    }

    private RoyaltyAllocation processRoyalty(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (!prodCostBean.fiscalPeriodExists(lpsc, year, month)) {
            return new RoyaltyAllocation();
        }

        RoyaltyAllocation allocation = new RoyaltyAllocation();

        double royalty = taxBean.computeRoyalty(lpsc, year, month);
        double corpLiftProceed = getCorporationProceed(lpsc, year, month);

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);

        Allocation prevAlloc = processRoyalty(lpsc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(royalty);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        return allocation;
    }

    private CostOilAllocation processCostOil(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (!prodCostBean.fiscalPeriodExists(lpsc, year, month)) {
            return new CostOilAllocation();
        }

        CostOilAllocation allocation = new CostOilAllocation();

        double costOil = costOilController.computeCostOilCum(lpsc, year, month);
        double contractorLiftProceed = getContractorProceed(lpsc, year, month);
        double proceed = getProceed(lpsc, year, month);

        costOil = Math.max(0, Math.min(proceed * lpsc.getCostRecoveryLimit(), costOil));
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = processCostOil(lpsc, prevFp.getYear(), prevFp.getMonth());
//        double costOilBfw = costOilController.computeCostOilBfw(lpsc, prevFp.getYear(), prevFp.getMonth());

        allocation.setMonthlyCharge(costOil);
        allocation.setLiftingProceed(contractorLiftProceed);
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        return allocation;
    }

    private TaxOilAllocation processTaxOil(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (!prodCostBean.fiscalPeriodExists(lpsc, year, month)) {
            return new TaxOilAllocation();
        }

        TaxOilAllocation allocation = new TaxOilAllocation();

        double taxOil = taxBean.computeTaxOil(lpsc, year, month);
        double corpLiftProceed = getCorporationProceed(lpsc, year, month);
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(year, month);
        Allocation prevAlloc = processTaxOil(lpsc, prevFp.getYear(), prevFp.getMonth());
        Allocation royAlloc = processRoyalty(this.psc, this.periodYear, this.periodMonth);
        allocation.setMonthlyCharge(taxOil);
        allocation.setLiftingProceed(corpLiftProceed);
        allocation.setRoyalty(royAlloc.getReceived());
        allocation.setChargeBfw(prevAlloc.getChargeCfw());

        return allocation;
    }

    private double getCorporationProceed(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (lpsc != null && year != null && month != null) {
            java.util.List<PscLifting> pscLiftings = liftingBean.find(lpsc, year, month);

            double price;
            double corpProceed = 0;

            for (PscLifting lifting : pscLiftings) {
                CrudePricePK pricePK = new CrudePricePK();
                pricePK.setCrudeTypeCode(psc.getCrudeType().getCode());
                pricePK.setPriceDate(lifting.getLiftingDate());
                CrudePrice crudePrice = priceBean.find(pricePK);

                if (crudePrice != null) {
                    price = crudePrice.getOsPrice();
                } else {
                    price = 0;
                }

                corpProceed += lifting.getOwnLifting() * price;
            }
            return corpProceed;
        }
        return 0;
    }

    private double getContractorProceed(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (lpsc != null && year != null && month != null) {
            java.util.List<PscLifting> pscLiftings = liftingBean.find(lpsc, year, month);

            double price;
            double contProceed = 0;

            for (PscLifting lifting : pscLiftings) {
                CrudePricePK pricePK = new CrudePricePK();
                pricePK.setCrudeTypeCode(psc.getCrudeType().getCode());
                pricePK.setPriceDate(lifting.getLiftingDate());
                CrudePrice crudePrice = priceBean.find(pricePK);

                if (crudePrice != null) {
                    price = crudePrice.getOsPrice();
                } else {
                    price = 0;
                }

                contProceed += lifting.getPartnerLifting() * price;
            }
            return contProceed;
        }
        return 0;
    }

    private double getProceed(ProductionSharingContract lpsc, Integer year, Integer month) {
        if (lpsc != null && year != null && month != null) {
            java.util.List<PscLifting> pscLiftings = liftingBean.find(lpsc, year, month);

            double price;
            double proceed = 0;

            for (PscLifting lifting : pscLiftings) {
                CrudePricePK pricePK = new CrudePricePK();
                pricePK.setCrudeTypeCode(psc.getCrudeType().getCode());
                pricePK.setPriceDate(lifting.getLiftingDate());
                CrudePrice crudePrice = priceBean.find(pricePK);

                if (crudePrice != null) {
                    price = crudePrice.getOsPrice();
                } else {
                    price = 0;
                }

                proceed += lifting.getTotalLifting() * price;
            }
            return proceed;
        }
        return 0;
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
        try {
            processRequest(request, response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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
        try {
            processRequest(request, response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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
