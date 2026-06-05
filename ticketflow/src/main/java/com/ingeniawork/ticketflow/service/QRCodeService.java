package com.ingeniawork.ticketflow.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.ServletOutputStream;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class QRCodeService {


    public byte[] generarPDF(String contenidoQR) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // 📝 Título
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Código QR de Acceso", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);
            document.add(new Paragraph(" ")); // espacio

            // 📷 Generar QR
            byte[] qrBytes = generarQR(contenidoQR);
            Image qrImage = Image.getInstance(qrBytes);

            qrImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrImage);

            document.add(new Paragraph(" "));

            // 🧾 Texto adicional (futuro escalable)
            document.add(new Paragraph("Este código es único y personal."));
            document.add(new Paragraph("No compartir."));

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }


    public byte[] generarQR(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);
        var image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }



}
