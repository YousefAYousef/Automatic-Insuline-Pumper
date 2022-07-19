package Model;

/**
 *
 * @author yousef
 */
public class Display_2 implements Display, Runnable {

    private double LastSugerReading;
    private double LastInsulinDose;

    public Display_2(double LastSugerReading, double LastInsulinDose) {
        this.LastSugerReading = LastSugerReading;
        this.LastInsulinDose = LastInsulinDose;
    }

    public double getLastSugerReading() {
        return LastSugerReading;
    }

    public double getLastInsulinDose() {
        return LastInsulinDose;
    }

    @Override
    public void display(String s) {
        try {
            System.out.print("Last Suger Reading is :" + LastSugerReading);
            System.out.print("Last Insulin Dose is :" + LastInsulinDose);
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void run() {
        display("");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
