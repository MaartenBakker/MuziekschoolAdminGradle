package dataModel;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<String, MuziekSchool> mapOfMuziekScholen = new HashMap<>();

    public static void main(String[] args) {

        MuziekSchool muziekSchool = new MuziekSchool("Ceres");
        addMuziekschoolToMap(muziekSchool);

        muziekSchool = new MuziekSchool("Kec");
        addMuziekschoolToMap(muziekSchool);


        muziekSchool = getMuziekSchool("Ceres");

        Leerling leerling;


        // tarieven om de week ga uit van 6 weken, elke week ga uit van 13 weken

        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_60, false), 660.13);
        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_45, false), 509.88);
        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_30, false), 365.94);

        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_60, true), 307.81);
        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_45, true), 238.50);
        muziekSchool.addTarief(new LesVorm(false, Lesduur.LESDUUR_30, true), 172.32);

        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_60, false), 755.76);
        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_45, false), 583.86);
        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_30, false), 418.83);

        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_60, true), 351.47);
        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_45, true), 273.16);
        muziekSchool.addTarief(new LesVorm(true, Lesduur.LESDUUR_30, true), 196.99);

        try {
            muziekSchool.loadTarieven();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        muziekSchool.printTarieven();

//        try {
//            muziekSchool.saveTarieven();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }



//        Leerling leerling = Leerling.create("Demo Demonsson", new Adres("Utrecht", "1234AB",
//                        "Demostraat", "1"), "demo@maartenmusic.com", true,
//                Lesduur.LESDUUR_30, true, 6);
//
//        muziekSchool.addLeerling(leerling);
//
//        leerling = Leerling.create("Testi Testman", null, "testi@maartenmusic.com",false,
//                Lesduur.LESDUUR_45, true, 12);
//
//        muziekSchool.addLeerling(leerling);
//
//        leerling = Leerling.create("Ficto van Fictief", new Adres("Lopik", "1987GH",
//                        "Fictielaan", "234"), "ficto@maartenmusic.com", true,
//                Lesduur.LESDUUR_60, false, 13);
//
//        muziekSchool.addLeerling(leerling);



        try {
            muziekSchool.loadLeerlingen();
        } catch (Exception e) {
            System.out.println("Unable to load leerlingen" + e.getMessage());
            e.printStackTrace();
        }

        muziekSchool.printLijstVanLeerlingen();
//
//        try {
//            muziekSchool.saveLeerlingen();
//        } catch (IOException e) {
//            System.out.println("Unable to save leerlingen" + e.getMessage());
//            e.printStackTrace();
//        }


        Docent docent = new Docent("Maarten Bakker", "Basgitaar", new BedrijfsGegevens(
                "Maarten Music",  new Adres("Oudewater",
                "3456JK", "Marktstraat", "8A"), "06 1234 56 78",
                "maartenbakker@gmail.com" ,"ABCD111000222333", "NL001234567B01",
                89674523));


        muziekSchool.addDocent(docent);

        List<Factuur> factuurList = muziekSchool.maakFactuurVanAlleLeerlingen(muziekSchool.getDocentByName("Maarten Bakker"),
                "2019-2020", "Blok 3", 101);



        makePDFs(factuurList);

        mailPDFs(factuurList);

        printAlleTotalen(factuurList);

        leerling = muziekSchool.getLeerlingByName("Testi Testman");
        Factuur factuur = new Factuur(leerling, docent, muziekSchool,"2019-2020", "Blok 4",
                999);
        FactuurPrinter.getInstance().printPdfFactuur(factuur, getFactuurFile(factuur));

    }



    public static void addMuziekschoolToMap(MuziekSchool muziekSchool) {
        mapOfMuziekScholen.put(muziekSchool.getNAME(), muziekSchool);
    }

    public static MuziekSchool getMuziekSchool(String name){
        return mapOfMuziekScholen.get(name);
    }

    public static void removeMuziekSchool(String nameOfMuziekSchoolToRemove){
        MuziekSchool muziekSchoolToRemove = mapOfMuziekScholen.get(nameOfMuziekSchoolToRemove);
        if (muziekSchoolToRemove != null){
            mapOfMuziekScholen.remove(nameOfMuziekSchoolToRemove);
        }
    }

    public static void printAlleTotalen(List<Factuur> factuurList) {
        printTotaalLesgeldVanAlleFacturen(factuurList);
        printTotaalBtwBedragenVanAlleFacturen(factuurList);
        printTotaalVanAlleFacturenMinusBtw(factuurList);
    }

    private static void printTotaalLesgeldVanAlleFacturen(List<Factuur> factuurList){
        System.out.println("Totaal lesgeld: " + String.format("%.2f", somVanAlleFacturen(factuurList)));
    }

    private static void printTotaalBtwBedragenVanAlleFacturen(List<Factuur> factuurList){
        System.out.println("Totaal BTW bedrag: " + String.format("%.2f", somVanAlleBtwBedragen(factuurList)));
    }

    private static void printTotaalVanAlleFacturenMinusBtw(List<Factuur> factuurList){
        System.out.println("Totaal minus BTW: " + String.format("%.2f", (somVanAlleFacturen(factuurList) - somVanAlleBtwBedragen(factuurList))));
    }

    private static double somVanAlleFacturen(List<Factuur> factuurList){
        double somVanAlleFacturen = 0;

        for (Factuur factuur : factuurList) {
            somVanAlleFacturen += factuur.getLESGELD();
        }
        return somVanAlleFacturen;
    }

    private static double somVanAlleBtwBedragen(List<Factuur> factuurList) {
        double somVanAlleBtwBedragen = 0;

        for (Factuur factuur : factuurList) {
            double btwBedrag = factuur.getBTW_BEDRAGEN().getBTW_BEDRAG();
            somVanAlleBtwBedragen += btwBedrag;
        }
        return somVanAlleBtwBedragen;
    }

    private static void makePDFs(List<Factuur> factuurList) {
        FactuurPrinter factuurPrinter = FactuurPrinter.getInstance();
        for (Factuur factuur : factuurList) {
            File file = getFactuurFile(factuur);
            factuurPrinter.printPdfFactuur(factuur, file);
        }
    }

    private static void mailPDFs(List<Factuur> factuurList) {
        FactuurMailer factuurMailer = FactuurMailer.getInstance();


        for (Factuur factuur : factuurList) {
            MimeMessage email = null;
            File file = getFactuurFile(factuur);
            try {
                email = factuurMailer.createEmailWithAttachment(factuur, file);
            } catch (javax.mail.MessagingException e) {
                System.out.println("Creating mail of " + factuur.getFactuurNummer() + " failed");
                e.printStackTrace();
            }

            try {
                factuurMailer.sendMessage(GmailQuickstart.getAPI(),"me", email);
            } catch (IOException | GeneralSecurityException | javax.mail.MessagingException e) {
                System.out.println("Sending mail failed");
                e.printStackTrace();
            }
        }
    }

    private static File getFactuurFile(Factuur factuur) {
     return new File("Facturen" + File.separator + "factuur nr." + factuur.getFactuurNummer() + " " +
                factuur.getLEERLING().getNAME() + ".pdf");
    }

}
