package dataModel;

import java.io.Serializable;

public class Adres implements Serializable {
    private String plaatsNaam;
    private String postcode;
    private String straatNaam;
    private String huisNummer;

    static final long serialVersionUID = 2L;



    public Adres(String plaatsNaam, String postcode, String straatNaam, String huisNummer) {
        this.plaatsNaam = plaatsNaam;
        this.postcode = postcode;
        this.straatNaam = straatNaam;
        this.huisNummer = huisNummer;
    }

    public String getPlaatsNaam() {
        return plaatsNaam;
    }

    public void setPlaatsNaam(String plaatsNaam) {
        this.plaatsNaam = plaatsNaam;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraatNaam() {
        return straatNaam;
    }

    public void setStraatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
    }

    public String getHuisNummer() {
        return huisNummer;
    }

    public void setHuisNummer(String huisNummer) {
        this.huisNummer = huisNummer;
    }

    @Override
    public String toString() {
        return straatNaam + " " + huisNummer + "\n" +
                postcode + " " + plaatsNaam + "\n";

    }
}
