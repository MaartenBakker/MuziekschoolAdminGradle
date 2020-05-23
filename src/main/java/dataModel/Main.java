package dataModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<String, MuziekSchool> mapOfMuziekScholen = new HashMap<>();

    public static void main(String[] args) {

        createMuziekSchoolAndAddToList("Ceres");

// tarieven om de week ga uit van 6 weken, elke week ga uit van 13 weken

        MuziekSchool muziekSchool = getMuziekSchool("Ceres");

        muziekSchool.addTarief(false, Lesduur.LESDUUR_60, false, 660.13);
        muziekSchool.addTarief(false, Lesduur.LESDUUR_45, false, 509.88);
        muziekSchool.addTarief(false, Lesduur.LESDUUR_30, false, 365.94);

        muziekSchool.addTarief(false, Lesduur.LESDUUR_60, true, 307.81);
        muziekSchool.addTarief(false, Lesduur.LESDUUR_45, true, 238.50);
        muziekSchool.addTarief(false, Lesduur.LESDUUR_30, true, 172.32);

        muziekSchool.addTarief(true, Lesduur.LESDUUR_60, false, 755.76);
        muziekSchool.addTarief(true, Lesduur.LESDUUR_45, false, 583.86);
        muziekSchool.addTarief(true, Lesduur.LESDUUR_30, false, 418.83);

        muziekSchool.addTarief(true, Lesduur.LESDUUR_60, true, 351.47);
        muziekSchool.addTarief(true, Lesduur.LESDUUR_45, true, 273.16);
        muziekSchool.addTarief(true, Lesduur.LESDUUR_30, true, 196.99);

        muziekSchool.printTarieven();


        muziekSchool.addLeerling("Demo Demonsson", "Demostraat 1\n1234 AB Utrecht", true,
                Lesduur.LESDUUR_30, true, 6);
        muziekSchool.addLeerling("Testi Testman", "Teststraat 1\n2345 BC Woerden", false,
                Lesduur.LESDUUR_45, true, 12);
        muziekSchool.addLeerling("Ficto van Fictief", "Fictielaan 6\n1987 DF Lopik", true,
                Lesduur.LESDUUR_60, false, 13);

        muziekSchool.printLijstVanLeerlingen();


        Docent docent = new Docent("Maarten Bakker", "Contrabas", "Maarten Music",
                "ABCD111000222333", "Oudewater");

        List<Factuur> factuurList = muziekSchool.maakFactuurVanAlleLeerlingen(docent, "2019-2020",
                "Blok 2", 101);

        FactuurPrinter factuurPrinter = new FactuurPrinter();

        for (Factuur factuur : factuurList) {
            factuurPrinter.printFactuur(factuur);
            factuurPrinter.printDocFactuur(factuur);
            factuurPrinter.printPdfFactuur(factuur);
        }

        printAlleTotalen(factuurList);

//        Leerling leerling = muziekSchool.getLeerlingByName("Testi Testman");
//        Factuur factuur = new Factuur(leerling, docent, muziekSchool,"2019-2020", "Blok 3",
//                999);
//        factuurPrinter.printFactuur(factuur);

    }

    public static void createMuziekSchoolAndAddToList(String name){
        MuziekSchool muziekSchool = new MuziekSchool(name);
        mapOfMuziekScholen.put(name, muziekSchool);
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

}
