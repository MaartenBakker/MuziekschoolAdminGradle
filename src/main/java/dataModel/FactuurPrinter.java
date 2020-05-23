package dataModel;

import java.io.*;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.*;


public class FactuurPrinter {

    public void printPdfFactuur(Factuur factuur) {
        File file = new File("src/main/resources/facturen/factuur nr." + factuur.getFactuurNummer() + " " +
                factuur.getLEERLING().getNAME() + ".pdf");
        file.getParentFile().mkdirs();

        Document document = new Document(); //itext Document

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 30);
            Paragraph title = new Paragraph("Factuur\n\n\n\n\n", font);
            document.add(title);

            String klantGegevens = printKlantGegevensFactuur(factuur);
            font = FontFactory.getFont(FontFactory.HELVETICA, 14);
            Paragraph klantGegevensParagraph = new Paragraph(klantGegevens, font);
            document.add(klantGegevensParagraph);

            PdfPTable table = new PdfPTable(2);

            PdfPCell lesTypeCell = new PdfPCell();


            String lesType = factuur.getDocent().getInstrument() + ("lessen ") + factuur.getMUZIEK_SCHOOL().getNAME();
            lesTypeCell.setPhrase(new Phrase(lesType));
            table.addCell(lesTypeCell);


            double bedrag1;
            String percentage;
            if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
                bedrag1 = factuur.getBTW_BEDRAGEN().getBEDRAG_ZONDER_BTW();
                percentage = BtwBedragen.getBtwPercentage() + "%";
            } else {
                bedrag1 = factuur.getLESGELD();
                percentage = "Vrijgesteld";
            }

            PdfPCell bedrag1Cell = new PdfPCell();
            bedrag1Cell.setPhrase(new Phrase("€ " + String.format("%.2f",(bedrag1))));
            bedrag1Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(bedrag1Cell);

            PdfPCell btwPercentageCell = new PdfPCell();
            btwPercentageCell.setPhrase(new Phrase("Btw " + percentage));
            table.addCell(btwPercentageCell);

            PdfPCell btwBedragCell = new PdfPCell();
            if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
                double btwBedrag = factuur.getBTW_BEDRAGEN().getBTW_BEDRAG();
                btwBedragCell.setPhrase(new Phrase("€ " + String.format("%.2f",(btwBedrag))));
            } else {
                btwBedragCell.setPhrase(new Phrase("€ 0,00"));
            }
            btwBedragCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(btwBedragCell);

            PdfPCell totaalCell = new PdfPCell();
            totaalCell.setPhrase(new Phrase("Totaalbedrag:"));
            table.addCell(totaalCell);

            PdfPCell totaalBedragCell = new PdfPCell();
            totaalBedragCell.setBorderWidth(2);
            totaalBedragCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            totaalBedragCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totaalBedragCell.setPhrase(new Phrase("€ " + String.format("%.2f",(factuur.getLESGELD()))));
            table.addCell(totaalBedragCell);

            document.add(table);


            String footer = printFooterFactuur(factuur);
            font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC, BaseColor.DARK_GRAY);
            Paragraph footerParagraph = new Paragraph(footer, font);
            document.add(footerParagraph);

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public void printDocFactuur(Factuur factuur) {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("Factuur\n");
//        titleRun.setColor("101010");
        titleRun.setBold(true);
        titleRun.setFontFamily("Verdana");
        titleRun.setFontSize(30);

        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        String string1 = printKlantGegevensFactuur(factuur);
        XWPFRun para1Run = para1.createRun();
        para1Run.setText(string1);
        para1Run.setFontFamily("Verdana");
        para1Run.setFontSize(14);

        double bedrag1;
        String percentage;
        if(factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()){
            bedrag1 = factuur.getBTW_BEDRAGEN().getBEDRAG_ZONDER_BTW();
            percentage = BtwBedragen.getBtwPercentage() + "%";
        } else {
            bedrag1 = factuur.getLESGELD();
            percentage = "Vrijgesteld";
        }

        XWPFTable table = document.createTable(4,2);
        table.getRow(0).getCell(0).setText(factuur.getDocent().getInstrument() + "lessen "
                + factuur.getMUZIEK_SCHOOL().getNAME() + ", " + factuur.getSeizoen() + " " + factuur.getLesBlok());
        table.getRow(0).getCell(1).setText("€ " + bedrag1);
        table.getRow(1).getCell(0).setText("(aantal lessen: " + factuur.getLEERLING().getAantalLessen() + ")");
        table.getRow(2).getCell(0).setText("BTW " + percentage);



//        XWPFParagraph para2 = document.createParagraph();
//        para2.setAlignment(ParagraphAlignment.RIGHT);
//        String string2 = printBtwGegevensFactuur(factuur);
//        XWPFRun para2Run = para2.createRun();
//        para2Run.setText(string2);
//        para2Run.setItalic(true);
//
//        XWPFParagraph para3 = document.createParagraph();
//        para3.setAlignment(ParagraphAlignment.LEFT);
//        String string3 = printTotaalbedragFactuur(factuur);
//        XWPFRun para3Run = para3.createRun();
//        para3Run.setText(string3);

        File file = new File("src/main/resources/facturen/" + factuur.getFactuurNummer()+".doc");
        file.getParentFile().mkdirs();

        try(FileOutputStream out = new FileOutputStream(file)) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void printFactuur(Factuur factuur) {

        String klantGegevensFactuur = printKlantGegevensFactuur(factuur);
        String btwGegevensFactuur = printBtwGegevensFactuur(factuur);
        String totaalBedragFactuur = printTotaalbedragFactuur(factuur);
        String footer = printFooterFactuur(factuur);

        File file = new File("src/main/resources/facturen/" + factuur.getFactuurNummer()+".txt");
        file.getParentFile().mkdirs();

        try (BufferedWriter factuurFileWriter = new BufferedWriter(new FileWriter
                (file))){
            factuurFileWriter.write(klantGegevensFactuur);
            factuurFileWriter.write(btwGegevensFactuur);
            factuurFileWriter.write(totaalBedragFactuur);
            factuurFileWriter.write(footer);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        printStippellijn();
    }

    private String printKlantGegevensFactuur(Factuur factuur){
        StringBuilder sb = new StringBuilder();
        sb.append("Klant:\n\n");
        sb.append(factuur.getLEERLING().getNAME()).append("\n");
        sb.append(factuur.getLEERLING().getAdres()).append("\n\n\n\n");
        sb.append("Factuurnummer: ").append(factuur.getFactuurNummer()).append("\n");
        sb.append("Factuurdatum: ").append(factuur.getFactuurDatum()).append("\n\n");
        sb.append("Factuurbedrag:").append("\n\n\n");

        return sb.toString();

//        System.out.println("Klant:");
//        System.out.println();
//        System.out.println(factuur.getLEERLING().getNAME());
//        System.out.println(factuur.getLEERLING().getAdres());
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("Factuurnummer: " + factuur.getFactuurNummer());
//        System.out.println("Factuurdatum: " + factuur.getFactuurDatum());
//        System.out.println();
//        System.out.println("Factuurbedrag:");
//        System.out.println();
    }

    private String printBtwGegevensFactuur(Factuur factuur) {

        if (factuur.getLEERLING().getLesVorm().IS_BOVEN_DE_21()) {
           return printBtwBovenDe21(factuur);
        } else {
            return printBtwOnderDe21(factuur);
        }
    }

    private String printBtwBovenDe21(Factuur factuur) {
        double lesgeldZonderBtw = factuur.getBTW_BEDRAGEN().getBEDRAG_ZONDER_BTW();
        double btwBedrag = factuur.getBTW_BEDRAGEN().getBTW_BEDRAG();

        StringBuilder sb = new StringBuilder();

        sb.append(factuur.getDocent().getInstrument()).append("lessen ").append(factuur.getMUZIEK_SCHOOL()
                .getNAME());
        sb.append(", ").append(factuur.getSeizoen()).append(" ").append(factuur.getLesBlok()).append(":\t\t\t\t")
                .append("€ ").append(String.format("%.2f", lesgeldZonderBtw)).append("\n");
        sb.append("(aantal lessen: ").append(factuur.getLEERLING().getAantalLessen()).append( ")\n\n");
        sb.append("BTW ").append(String.format("%.2f", BtwBedragen.getBtwPercentage())).append("%:\t\t\t\t\t\t\t\t\t\t\t\t")
                .append("€ ").append(String.format("%.2f", btwBedrag)).append("\n");

        return sb.toString();


//        System.out.println(factuur.getDocent().getInstrument() + "lessen " + factuur.getMUZIEK_SCHOOL().getNAME()
//                + ", " + factuur.getSeizoen() + " " + factuur.getLesBlok() + ":\t\t\t\t"
//                + "€ " + String.format("%.2f", lesgeldZonderBtw));
//        System.out.println("(aantal lessen: " + factuur.getLEERLING().getAantalLessen() + ")");
//        System.out.println();
//        System.out.println("BTW " + String.format("%.2f", BtwBedragen.getBtwPercentage()) + "%:\t\t\t\t\t\t\t\t\t\t\t"
//                + "€ " + String.format("%.2f", btwBedrag));
    }

    private String printBtwOnderDe21(Factuur factuur) {
        StringBuilder sb = new StringBuilder();

        sb.append(factuur.getDocent().getInstrument()).append("lessen ").append(factuur.getMUZIEK_SCHOOL().getNAME())
                .append(", ").append(factuur.getSeizoen()).append(" ").append(factuur.getLesBlok()).append(":\t\t\t\t")
                .append("€ ").append(String.format("%.2f", factuur.getLESGELD())).append("\n");
        sb.append("(aantal lessen: ").append(factuur.getLEERLING().getAantalLessen()).append( ")\n\n");
        sb.append("BTW bedrag: Vrijgesteld\n");

        return sb.toString();

//        System.out.println(factuur.getDocent().getInstrument() + "lessen " + factuur.getMUZIEK_SCHOOL().getNAME()
//                + ", " + factuur.getSeizoen() + " " + factuur.getLesBlok() + ":\t\t\t\t"
//                + "€ " + String.format("%.2f", factuur.getLESGELD()));
//        System.out.println("(aantal lessen: " + factuur.getLEERLING().getAantalLessen() + ")");
//        System.out.println();
//        System.out.println("BTW bedrag: Vrijgesteld");
    }

    private String printTotaalbedragFactuur(Factuur factuur){
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------------------------------------------\n");
        sb.append("Totaal:\t\t\t\t\t\t\t\t\t\t\t\t\t€ ").append(String.format("%.2f", factuur.getLESGELD()));

        return sb.toString();

//        System.out.println("-----------------------------------------------------------------------------------------");
//        System.out.println("Totaal:\t\t\t\t\t\t\t\t\t\t\t\t" + "€ " + String.format("%.2f", factuur.getLESGELD()));
    }

    private String printFooterFactuur(Factuur factuur){
        StringBuilder sb = new StringBuilder();

        sb.append("\n\nU wordt vriendelijk verzocht binnen 3 weken na ontvangst van deze brief " +
                "de betaling over te maken op ").append(factuur.getDocent().getRekeningNummer())
                .append(" t.n.v. ").append(factuur.getDocent().getBedrijfsNaam()).append(" te ")
                .append(factuur.getDocent().getVerstigingsStadBedrijf()).append(" onder vermelding van ");
        sb.append("Factuurnummer: ").append(factuur.getFactuurNummer());

        return sb.toString();
//        System.out.println();
//        System.out.println();
//        System.out.println("U wordt vriendelijk verzocht binnen 3 weken na ontvangst van deze brief\n" +
//                "de betaling over te maken op " + factuur.getDocent().getRekeningNummer() + " t.n.v.\n" +
//                factuur.getDocent().getBedrijfsNaam() + " te " + factuur.getDocent().getVerstigingsStadBedrijf() +
//                " onder vermelding van");
//        System.out.println("Factuurnummer: " + factuur.getFactuurNummer());
    }

//    private void printStippellijn(){
//        System.out.println("**************************************");
//        System.out.println();
//    }





}
