package com.service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] createPdf(String text) throws IOException {
        try (
                PDDocument document = new PDDocument();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDFont font = PDType0Font.load(
                    document,
                    getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")
            );


            try (PDPageContentStream contentStream =
                         new PDPageContentStream(document, page)) {

                contentStream.beginText();

                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(50, 750);

                for (String line : text.split("\\R")) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -16);
                }

                contentStream.endText();
            }

            document.save(outputStream);

            return outputStream.toByteArray();
        }
    }
}
