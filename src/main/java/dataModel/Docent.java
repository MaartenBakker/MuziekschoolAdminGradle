package dataModel;

public class Docent {
    private final String NAME;
    private String instrument;
    private BedrijfsGegevens bedrijfsGegevens;

    public Docent(String NAME, String instrument, BedrijfsGegevens bedrijfsGegevens) {
        this.NAME = NAME;
        this.instrument = instrument;
        this.bedrijfsGegevens = bedrijfsGegevens;
    }


    public String getNAME() {
        return NAME;
    }

    public String getInstrument() {
        return instrument;
    }

    public BedrijfsGegevens getBedrijfsGegevens() {
        return bedrijfsGegevens;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setBedrijfsGegevens(BedrijfsGegevens bedrijfsGegevens) {
        this.bedrijfsGegevens = bedrijfsGegevens;
    }
}
