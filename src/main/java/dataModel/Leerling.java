package dataModel;

public class Leerling {

    private final String NAME;
    private String adres;
    private LesVorm lesVorm;
    private int aantalLessen;

    // Factory methods

    static Leerling create(String name, String adres, boolean bovenDe21, Lesduur lesDuur, boolean omDeWeek,
                           int aantalLessen){

        if (aantalLessen <=0){
            return null;
        }

        LesVorm lesVorm = new LesVorm(bovenDe21, lesDuur, omDeWeek);
        return new Leerling(name, adres, lesVorm, aantalLessen);
    }

    // Constructors

    private Leerling(String NAME, String adres, LesVorm lesVorm, int aantalLessen) {
        this.NAME = NAME;
        this.adres = adres;
        this.lesVorm = lesVorm;
        this.aantalLessen = aantalLessen;
    }

    // Getters & Setters

    public String getNAME() {
        return NAME;
    }

    public String getAdres() {
        return adres;
    }

    public LesVorm getLesVorm() {
        return lesVorm;
    }

    public void setLesVorm(boolean bovenDe21, Lesduur lesDuur, boolean omdDeWeek) {
        this.lesVorm = new LesVorm(bovenDe21,lesDuur, omdDeWeek );
    }

    public int getAantalLessen() {
        return aantalLessen;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setAantalLessen(int aantalLessen) {
        this.aantalLessen = aantalLessen;
    }

    // Methods

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        sb.append(NAME);
        sb.append(" : ");
        sb.append("\n");
        sb.append(adres);
        sb.append(!adres.equals("") ? "\n" : "");
        sb.append("Aantal lessen : ");
        sb.append(aantalLessen);
        sb.append(", ");
        sb.append(lesVorm);

        return sb.toString();
        }
}
