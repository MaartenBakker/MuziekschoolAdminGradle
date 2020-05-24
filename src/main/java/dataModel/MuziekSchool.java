package dataModel;

import java.util.*;

public class MuziekSchool {

    private final String NAME;
    private final Map<String, Leerling> LEERLINGEN = new HashMap<>();
    private final Map<LesVorm, Double> TARIEVEN = new LinkedHashMap<>();


    public MuziekSchool(String name) {
        this.NAME = name;
    }

    public String getNAME() {
        return NAME;
    }

    public Leerling getLeerlingByName(String name){
        return LEERLINGEN.get(name);
    }


    public void addTarief(boolean isBovenDe21, Lesduur lesduur, boolean heeftOmDeWeekLes, double prijs) {
        addTarief(new LesVorm(isBovenDe21, lesduur, heeftOmDeWeekLes), prijs);
    }

    void addTarief(LesVorm lesvorm, double prijs){
        TARIEVEN.put(lesvorm, prijs);
    }

    public Map<LesVorm, Double> getTARIEVEN() {
        return Collections.unmodifiableMap(TARIEVEN);
    }

    public void addLeerling(String name, Adres adres, boolean isBovenDe21, Lesduur lesDuur, boolean heeftOmDeWeekLes, int aantalLessen) {
        Leerling leerling = Leerling.create(name, adres, isBovenDe21, lesDuur, heeftOmDeWeekLes, aantalLessen);
        if (leerling != null) {
            LEERLINGEN.put(leerling.getNAME(), leerling);
        }
    }

    public void removeLeerling(String nameOfLeerlingToRemove) {
        Leerling leerlingToRemove = LEERLINGEN.get(nameOfLeerlingToRemove);
        if (leerlingToRemove != null) {
            LEERLINGEN.remove(nameOfLeerlingToRemove);
        }
    }

    public void saveLeerlingen(){


    }

    public void loadLeerlingen(){

    }







    private Map<String, Leerling> zetMapOpAlfabet(Map<String, Leerling> mapDieOpAlfabetGezetMoetWorden) {
        return new TreeMap<>(mapDieOpAlfabetGezetMoetWorden);
    }

    public void printTarieven(){
        System.out.println("Tarieven van : " + this.NAME + "\n");
        for (Map.Entry<LesVorm, Double> tarief : TARIEVEN.entrySet()) {
            System.out.println(tarief.getKey() + " : " + tarief.getValue());
        }
        System.out.println();
    }

    public void printLijstVanLeerlingen(){
        System.out.println("Lijst van leerlingen van " + this.NAME + ":");

        Map<String, Leerling> leerlingenOpAlfabet = zetMapOpAlfabet(LEERLINGEN);

        for (Leerling leerling : leerlingenOpAlfabet.values()) {
            Factuur factuur = new Factuur(leerling, this);
            System.out.println("\n" + leerling + "\n" + factuur.getLESGELD() );
        }
        System.out.println();
    }

    public List<Factuur> maakFactuurVanAlleLeerlingen(Docent docent, String seizoen, String blok, int factuurNummerEersteFactuur) {

        List<Factuur> facturenList = new ArrayList<>();
        int factuurNummer = factuurNummerEersteFactuur;

        Map<String, Leerling> leerlingenOpAlfabet = zetMapOpAlfabet(LEERLINGEN);

        for (Leerling leerling : leerlingenOpAlfabet.values()) {
            Factuur factuur = new Factuur(leerling, docent, this, seizoen, blok, factuurNummer);
            facturenList.add(factuur);
            factuurNummer++;
        }
        return facturenList;
    }
}