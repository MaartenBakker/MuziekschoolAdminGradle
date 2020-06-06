package dataModel;

import java.io.Serializable;

public class Leerling implements Serializable {

    private final String NAME;
    private Adres adres;
    private String emailAdres;
    private LesVorm lesVorm;
    private int aantalLessen;

    static final long serialVersionUID = 1L;

    // Factory methods

    static Leerling create(String name, Adres adres, String emailAdres, boolean bovenDe21, Lesduur lesDuur, boolean omDeWeek,
                           int aantalLessen){

        if (aantalLessen <=0){
            return null;
        }

        LesVorm lesVorm = new LesVorm(bovenDe21, lesDuur, omDeWeek);
        return new Leerling(name, adres, emailAdres, lesVorm, aantalLessen);
    }

    // Constructors

    private Leerling(String NAME, Adres adres, String emailAdres, LesVorm lesVorm, int aantalLessen) {
        this.NAME = NAME;
        this.adres = adres;
        this.emailAdres = emailAdres;
        this.lesVorm = lesVorm;
        this.aantalLessen = aantalLessen;
    }

    // Getters & Setters

    public String getNAME() {
        return NAME;
    }

    public Adres getAdres() {
        return adres;
    }

    public String getEmailAdres() {
        return emailAdres;
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

    public void setAdres(Adres adres) {
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
        if (adres != null) {
            sb.append(adres);
        }
        sb.append("Aantal lessen : ");
        sb.append(aantalLessen);
        sb.append(", ");
        sb.append(lesVorm);

        return sb.toString();
        }
}
