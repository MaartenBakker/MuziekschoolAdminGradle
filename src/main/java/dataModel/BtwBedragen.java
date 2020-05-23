package dataModel;

public class BtwBedragen{
    static final private double BTW_PERCENTAGE = 21.0;
    private final double BEDRAG_ZONDER_BTW;
    private final double BTW_BEDRAG;

    public BtwBedragen(double lesgeld) {

        double bedragZonderBTW = lesgeld / (1 + (BTW_PERCENTAGE / 100.0));
        double btwBedrag = bedragZonderBTW * (BTW_PERCENTAGE / 100.0);
        this.BEDRAG_ZONDER_BTW = Afronder.bedragInEurosAfronden(bedragZonderBTW);
        this.BTW_BEDRAG = Afronder.bedragInEurosAfronden(btwBedrag);

    }

    public static double getBtwPercentage() {
        return BTW_PERCENTAGE;
    }

    public double getBEDRAG_ZONDER_BTW() {
        return BEDRAG_ZONDER_BTW;
    }

    public double getBTW_BEDRAG() {
        return BTW_BEDRAG;
    }


}