package dataModel;

import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class FactuurPrinter {

    public void printPdfFactuur(Factuur factuur) {
        File file = new File("src/main/resources/facturen/factuur nr." + factuur.getFactuurNummer() + " " +
                factuur.getLEERLING().getNAME() + ".pdf");
        file.getParentFile().mkdirs();

        Document document = new Document(); //itext Document

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(generateTitleParagraph());
            document.add(generateBedrijfsGegevensParagraph(factuur));
            document.add(generateKlantGegevensParagraph(factuur));
            document.add(generateTable(factuur));
            document.add(generateFooterParagraph(factuur));
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private Paragraph generateTitleParagraph(){
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 30);
        return new Paragraph("Factuur\n", font);
    }

    private Paragraph generateBedrijfsGegevensParagraph(Factuur factuur) {
        String bedrijfsGegevens = "\n" + factuur.getDocent().getBedrijfsGegevens().toString();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
        Paragraph bedrijfsGegevensParagraph = new Paragraph(bedrijfsGegevens, font);
        bedrijfsGegevensParagraph.setAlignment(Element.ALIGN_RIGHT);
        return bedrijfsGegevensParagraph;
    }

    private Paragraph generateKlantGegevensParagraph(Factuur factuur) {
        String klantGegevens = printKlantGegevensFactuur(factuur);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 14);

        return (new Paragraph(klantGegevens, font));
    }

    private String printKlantGegevensFactuur(Factuur factuur){
        StringBuilder sb = new StringBuilder();
        sb.append("Klant:\n\n");
        sb.append(factuur.getLEERLING().getNAME()).append("\n");
        if (factuur.getLEERLING().getAdres() != null) {
            sb.append(factuur.getLEERLING().getAdres().toString()).append("\n");
        }
        sb.append("\n");
        sb.append("Factuurnummer: ").append(factuur.getFactuurNummer()).append("\n");
        sb.append("Factuurdatum: ").append(factuur.getFactuurDatum()).append("\n\n\n\n");

        return sb.toString();
    }

    private PdfPTable generateTable(Factuur factuur) {
        PdfPTable table = new PdfPTable(new float[]{4,1});


        table.addCell(generateLesTypeCell(factuur));
        table.addCell(generateBedrag1Cell(factuur));
        table.addCell(generateBtwPercentageCell(factuur));
        table.addCell(generateBtwBedragCell(factuur));
        table.addCell(generateTotaalCell());
        table.addCell(generateTotaalBedragCell(factuur));

        return table;
    }

    private PdfPCell generateLesTypeCell(Factuur factuur) {
        PdfPCell lesTypeCell = new PdfPCell();
        String lesType = factuur.getDocent().getInstrument() + ("lessen ") + factuur.getMUZIEK_SCHOOL().getNAME() +
                " " + factuur.getLesBlok();
        lesTypeCell.setPhrase(new Phrase(lesType));
        lesTypeCell.setPadding(5);
        lesTypeCell.setVerticalAlignment(Element.ALIGN_CENTER);

        return lesTypeCell;
    }

    private PdfPCell generateBedrag1Cell(Factuur factuur) {
        double bedrag1;
        if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
            bedrag1 = factuur.getBTW_BEDRAGEN().getBEDRAG_ZONDER_BTW();
        } else {
            bedrag1 = factuur.getLESGELD();
        }
        PdfPCell bedrag1Cell = new PdfPCell();
        bedrag1Cell.setPhrase(new Phrase("€ " + String.format("%.2f",(bedrag1))));
        bedrag1Cell.setPadding(5);
        bedrag1Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        return bedrag1Cell;
    }

    private PdfPCell generateBtwPercentageCell(Factuur factuur) {
        String percentage;

        if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
            percentage = BtwBedragen.getBtwPercentage() + "%";
        } else {
            percentage = "Vrijgesteld";
        }
        PdfPCell btwPercentageCell = new PdfPCell();
        btwPercentageCell.setPhrase(new Phrase("Btw " + percentage));
        btwPercentageCell.setPadding(5);

        return btwPercentageCell;
    }

    private PdfPCell generateBtwBedragCell(Factuur factuur) {
        PdfPCell btwBedragCell = new PdfPCell();
        if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
            double btwBedrag = factuur.getBTW_BEDRAGEN().getBTW_BEDRAG();
            btwBedragCell.setPhrase(new Phrase("€ " + String.format("%.2f",(btwBedrag))));
        } else {
            btwBedragCell.setPhrase(new Phrase("€ 0,00"));
        }
        btwBedragCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        btwBedragCell.setPadding(5);

        return btwBedragCell;
    }

    private PdfPCell generateTotaalCell() {
        PdfPCell totaalCell = new PdfPCell();
        totaalCell.setPhrase(new Phrase("Totaalbedrag:"));
        totaalCell.setPadding(5);

        return totaalCell;
    }

    private PdfPCell generateTotaalBedragCell(Factuur factuur) {
        PdfPCell totaalBedragCell = new PdfPCell();
        totaalBedragCell.setBorderWidth(2);
        totaalBedragCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        totaalBedragCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totaalBedragCell.setPadding(5);
        totaalBedragCell.setPhrase(new Phrase("€ " + String.format("%.2f",(factuur.getLESGELD()))));

        return totaalBedragCell;
    }

    private Paragraph generateFooterParagraph(Factuur factuur) {
        String footer = printFooterFactuur(factuur);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC, BaseColor.DARK_GRAY);

        return new Paragraph(footer, font);
    }


    private String printFooterFactuur(Factuur factuur){
        StringBuilder sb = new StringBuilder();

        sb.append("\n\nU wordt vriendelijk verzocht binnen 3 weken na ontvangst van deze brief " +
                "de betaling over te maken op ").append(factuur.getDocent().getBedrijfsGegevens().getRekeningNummer())
                .append(" t.n.v. ").append(factuur.getDocent().getBedrijfsGegevens().getBedrijfsNaam()).append(" te ")
                .append(factuur.getDocent().getBedrijfsGegevens().getAdres().getPlaatsNaam()).append(" onder vermelding van ");
        sb.append("factuurnummer: ").append(factuur.getFactuurNummer());

        return sb.toString();

    }

}
