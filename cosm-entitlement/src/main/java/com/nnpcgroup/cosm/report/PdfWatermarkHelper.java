/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayemi
 */
public class PdfWatermarkHelper extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        //super.onEndPage(writer, document);
        try {
            Image img = Image.getInstance(getResource("/nnpc-logo-bg.jpg"));
            img.setAbsolutePosition(95, 230);
            float w = img.getScaledWidth();
            float h = img.getScaledHeight();

            img.scaleAbsolute(400.0f, 400.0f);
          

            //img.setTransparency(new int[]{1});
            // This scales the image to the page,
            // use the image's width & height if you don't want to scale.
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();

            // transparency
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.1f);
            gs1.setBlendMode(PdfGState.BM_SCREEN);
            writer.setRgbTransparencyBlending(true);
            writer.getDirectContentUnder().setGState(gs1);
            writer.getDirectContentUnder().addImage(img, false);
        } catch (DocumentException ex) {

        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }
    }

    private static URL getResource(String name) {
        return PdfWatermarkHelper.class.getResource(name);
    }
}
