package dataModel;

public class Docent {
    private final String NAME;
    private String instrument;
    private String bedrijfsNaam;
    private String rekeningNummer;
    private String verstigingsStadBedrijf;

    public Docent(String NAME, String instrument, String bedrijfsNaam, String rekeningNummer, String verstigingsStadBedrijf) {
        this.NAME = NAME;
        this.instrument = instrument;
        this.bedrijfsNaam = bedrijfsNaam;
        this.rekeningNummer = rekeningNummer;
        this.verstigingsStadBedrijf = verstigingsStadBedrijf;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setBedrijfsNaam(String bedrijfsNaam) {
        this.bedrijfsNaam = bedrijfsNaam;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public void setVerstigingsStadBedrijf(String verstigingsStadBedrijf) {
        this.verstigingsStadBedrijf = verstigingsStadBedrijf;
    }

    public String getNAME() {
        return NAME;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getBedrijfsNaam() {
        return bedrijfsNaam;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public String getVerstigingsStadBedrijf() {
        return verstigingsStadBedrijf;
    }


}
