package dataModel;


import java.io.*;
import java.util.*;

public class MuziekSchool {

    private final String NAME;
    private Map<String, Leerling> leerlingen = new HashMap<>();
    private Map<LesVorm, Double> Tarieven = new LinkedHashMap<>();


    public MuziekSchool(String name) {
        this.NAME = name;
//        leerlingen = loadLeerlingenOrAddNewMap(this);
    }

    public String getNAME() {
        return NAME;
    }

    public Leerling getLeerlingByName(String name){
        return leerlingen.get(name);
    }


//    private static Map<String, Leerling> loadLeerlingenOrAddNewMap(MuziekSchool muziekSchool) {
//        Map<String, Leerling> leerlingen;
//        try {
//            leerlingen = loadLeerlingen(muziekSchool);
//        } catch (Exception e){
//            leerlingen = new HashMap<>();
//            System.out.println("loading failed");
//        }
//        return leerlingen;
//    }
//
//    public static Map<String, Leerling> loadLeerlingen(MuziekSchool muziekSchool) throws ClassNotFoundException, IOException {
//        try (ObjectInputStream leerlingenFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
//                "src/main/resources/" + muziekSchool.NAME + "/leerlingen.dat")))){
//            Map<String, Leerling> leerlingen = new HashMap<>();
//            boolean eof = false;
//            while (!eof) {
//                try {
//                    leerlingen = (Map<String, Leerling>) leerlingenFile.readObject();
//                    System.out.println("leerlingen geladen");
//                } catch (EOFException e) {
//                    eof = true;
//                }
//            }
//            return leerlingen;
//        }
//    }

    public void addTarief(boolean isBovenDe21, Lesduur lesduur, boolean heeftOmDeWeekLes, double prijs) {
        addTarief(new LesVorm(isBovenDe21, lesduur, heeftOmDeWeekLes), prijs);
    }

    void addTarief(LesVorm lesvorm, double prijs){
        Tarieven.put(lesvorm, prijs);
    }

    public Map<LesVorm, Double> getTarieven() {
        return Collections.unmodifiableMap(Tarieven);
    }

    public void addLeerling(String name, Adres adres, String emailAdres, boolean isBovenDe21, Lesduur lesDuur, boolean heeftOmDeWeekLes, int aantalLessen) {
        Leerling leerling = Leerling.create(name, adres, emailAdres, isBovenDe21, lesDuur, heeftOmDeWeekLes, aantalLessen);
        if (leerling != null) {
            leerlingen.put(leerling.getNAME(), leerling);
        }
    }

    public void removeLeerling(String nameOfLeerlingToRemove) {
        Leerling leerlingToRemove = leerlingen.get(nameOfLeerlingToRemove);
        if (leerlingToRemove != null) {
            leerlingen.remove(nameOfLeerlingToRemove);
        }
    }

    public void saveLeerlingen() throws IOException {
        File file = new File("src/main/resources/" + this.NAME + "/leerlingen.dat");
        file.getParentFile().mkdirs();

        try (ObjectOutputStream leerlingenFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                file)))) {
            leerlingenFile.writeObject(leerlingen);
            }
    }

    public void loadLeerlingen() throws ClassNotFoundException, IOException {
        try (ObjectInputStream leerlingenFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
                "src/main/resources/" + this.NAME + "/leerlingen.dat")))){
            boolean eof = false;
            while (!eof) {
                try {
                    this.leerlingen = (Map<String, Leerling>) leerlingenFile.readObject();
                } catch (EOFException e) {
                    eof = true;
                }
            }
        }
    }

    public void saveTarieven() throws IOException {
        File file = new File("src/main/resources/" + this.NAME + "/tarieven.dat");
        file.getParentFile().mkdirs();

        try (ObjectOutputStream tarievenFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                file)))) {
                tarievenFile.writeObject(Tarieven);
        }
    }

    public void loadTarieven() throws ClassNotFoundException, IOException {
        try (ObjectInputStream tarievenFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
                "src/main/resources/" + this.NAME + "/tarieven.dat")))){
            boolean eof = false;
            while (!eof) {
                try {
                    this.Tarieven = (Map<LesVorm, Double>) tarievenFile.readObject();
                } catch (EOFException e) {
                    eof = true;
                }
            }
        }
    }







    private Map<String, Leerling> zetMapOpAlfabet(Map<String, Leerling> mapDieOpAlfabetGezetMoetWorden) {
        return new TreeMap<>(mapDieOpAlfabetGezetMoetWorden);
    }

    public void printTarieven(){
        System.out.println("Tarieven van : " + this.NAME + "\n");
        for (Map.Entry<LesVorm, Double> tarief : Tarieven.entrySet()) {
            System.out.println(tarief.getKey() + " : " + tarief.getValue());
        }
        System.out.println();
    }

    public void printLijstVanLeerlingen(){
        System.out.println("Lijst van leerlingen van " + this.NAME + ":");

        Map<String, Leerling> leerlingenOpAlfabet = zetMapOpAlfabet(leerlingen);

        for (Leerling leerling : leerlingenOpAlfabet.values()) {
            Factuur factuur = new Factuur(leerling, this);
            System.out.println("\n" + leerling + "\n" + factuur.getLESGELD() );
        }
        System.out.println();
    }

    public List<Factuur> maakFactuurVanAlleLeerlingen(Docent docent, String seizoen, String blok, int factuurNummerEersteFactuur) {

        List<Factuur> facturenList = new ArrayList<>();
        int factuurNummer = factuurNummerEersteFactuur;

        Map<String, Leerling> leerlingenOpAlfabet = zetMapOpAlfabet(leerlingen);

        for (Leerling leerling : leerlingenOpAlfabet.values()) {
            Factuur factuur = new Factuur(leerling, docent, this, seizoen, blok, factuurNummer);
            facturenList.add(factuur);
            factuurNummer++;
        }
        return facturenList;
    }
}