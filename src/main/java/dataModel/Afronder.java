package dataModel;

public class Afronder {

    public static double bedragInEurosAfronden(double bedrag){
        return Math.round(bedrag * 100.0) / 100.0;
    }

}
