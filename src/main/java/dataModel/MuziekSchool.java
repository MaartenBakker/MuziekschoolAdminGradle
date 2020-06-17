package dataModel;


import java.io.*;
import java.util.*;

public class MuziekSchool {

    private final String NAME;
    private Map<String, Leerling> leerlingen = new HashMap<>();
    private Map<LesVorm, Double> Tarieven = new LinkedHashMap<>();
    private Map<String, Docent> docenten = new HashMap<>();


    public MuziekSchool(String name) {
        this.NAME = name;
    }

    public String getNAME() {
        return NAME;
    }

    public Leerling getLeerlingByName(String name){
        return leerlingen.get(name);
    }

    public Docent getDocentByName(String name) { return docenten.get(name); }

    void addTarief(LesVorm lesvorm, double prijs){
        Tarieven.put(lesvorm, prijs);
    }

    public Map<LesVorm, Double> getTarieven() {
        return Collections.unmodifiableMap(Tarieven);
    }

    public void addLeerling(Leerling leerling) {
        if (leerling != null) {
            leerlingen.put(leerling.getNAME(), leerling);
        } else {
            System.out.println("Cannot add Leerling, Leerling == null");
        }
    }

    public void addDocent(Docent docent){
        if (docent != null) {
            docenten.put(docent.getNAME(), docent);
        } else {
            System.out.println("Cannot add Docent, Docent == null");
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