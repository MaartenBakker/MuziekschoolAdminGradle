package dataModel;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class Factuur implements Comparable<Factuur>{

    private final Leerling LEERLING;
    private Docent docent;
    private final MuziekSchool MUZIEK_SCHOOL;
    private String factuurNummer;
    private String factuurDatum;
    private String seizoen;
    private String lesBlok;
    private final double LESGELD;
    private final BtwBedragen BTW_BEDRAGEN;


    public Factuur(Leerling leerling, Docent docent, MuziekSchool muziekSchool, String seizoen, String lesBlok,
                   int factuurNummer) {
        this.LEERLING = leerling;
        this.docent = docent;
        this.MUZIEK_SCHOOL = muziekSchool;
        this.seizoen = seizoen;
        this.lesBlok = lesBlok;
        this.factuurNummer = Year.now().getValue() + "." + String.format("%03d", factuurNummer);
        this.factuurDatum = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.LESGELD = lesgeldTotaalAantalLessen(leerling);
        this.BTW_BEDRAGEN = new BtwBedragen(LESGELD);
    }

    public Factuur(Leerling leering, MuziekSchool muziekSchool){
        this.LEERLING = leering;
        this.MUZIEK_SCHOOL = muziekSchool;
        this.LESGELD = lesgeldTotaalAantalLessen(LEERLING);
        this.BTW_BEDRAGEN = new BtwBedragen(LESGELD);
    }

    public Leerling getLEERLING() {
        return LEERLING;
    }

    public Docent getDocent() {
        return docent;
    }

    public MuziekSchool getMUZIEK_SCHOOL() {
        return MUZIEK_SCHOOL;
    }

    public String getFactuurNummer() {
        return factuurNummer;
    }

    public String getFactuurDatum() {
        return factuurDatum;
    }

    public String getSeizoen() {
        return seizoen;
    }

    public String getLesBlok() {
        return lesBlok;
    }

    public double getLESGELD() {
        return LESGELD;
    }

    public BtwBedragen getBTW_BEDRAGEN() {
        return BTW_BEDRAGEN;
    }

    private double lesgeldEnkeleLes(Leerling leerling) {
        LesVorm lesVorm = leerling.getLesVorm();
        double tarief = MUZIEK_SCHOOL.getTARIEVEN().get(lesVorm);

        if (lesVorm.HEEFT_OM_DE_WEEK_LES()){
            return (tarief / 6);
        } else {
            return (tarief / 13);
        }
    }

    public double lesgeldTotaalAantalLessen(Leerling leerling){
        double totaalLesgeld = lesgeldEnkeleLes(leerling) * leerling.getAantalLessen();
        return Afronder.bedragInEurosAfronden(totaalLesgeld);
    }

    @Override
    public int compareTo(Factuur o) {
        return LEERLING.getNAME().compareTo(o.getLEERLING().getNAME());
    }

    @Override
    public String toString() {
        return "Factuur{" +
                "leerling=" + LEERLING +
                ", docent=" + docent +
                ", muziekSchool=" + MUZIEK_SCHOOL +
                ", factuurNummer='" + factuurNummer + '\'' +
                ", factuurDatum='" + factuurDatum + '\'' +
                ", lesBlok='" + lesBlok + '\'' +
                ", seizoen='" + seizoen + '\'' +
                ", lesGeld=" + LESGELD +
                ", btwBedragen=" + BTW_BEDRAGEN +
                '}';
    }
}
