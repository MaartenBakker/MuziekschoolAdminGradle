package dataModel;

public class BedrijfsGegevens {
    private String bedrijfsNaam;
    private Adres adres;
    private String telefoonNummer;
    private String emailAdres;
    private String rekeningNummer;
    private String BtwNummer;
    private int KvkNummer;

    public BedrijfsGegevens(String bedrijfsNaam, Adres adres, String telefoonNummer, String emailAdres,
                            String rekeningNummer, String btwNummer, int kvkNummer) {
        this.bedrijfsNaam = bedrijfsNaam;
        this.adres = adres;
        this.telefoonNummer = telefoonNummer;
        this.emailAdres = emailAdres;
        this.rekeningNummer = rekeningNummer;
        BtwNummer = btwNummer;
        KvkNummer = kvkNummer;
    }

    public String getBedrijfsNaam() {
        return bedrijfsNaam;
    }

    public void setBedrijfsNaam(String bedrijfsNaam) {
        this.bedrijfsNaam = bedrijfsNaam;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public void setEmailAdres(String emailAdres) {
        this.emailAdres = emailAdres;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public String getBtwNummer() {
        return BtwNummer;
    }

    public void setBtwNummer(String btwNummer) {
        BtwNummer = btwNummer;
    }

    public int getKvkNummer() {
        return KvkNummer;
    }

    public void setKvkNummer(int kvkNummer) {
        KvkNummer = kvkNummer;
    }

    @Override
    public String toString() {
        return bedrijfsNaam + '\n' +
               adres +
               "tel. " + telefoonNummer + '\n' +
               emailAdres + "\n" +
               "IBAN: " + rekeningNummer + '\n' +
               "BTW: " + BtwNummer + '\n' +
               "Kvk.: " + KvkNummer + "\n\n";
    }
}
