/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

/**
 *
 * @author yousef
 */
public class PeriodicTesting {
    private final double LastSugerReading;
    private final double LastInsulinDose;
    public PeriodicTesting(double LastSugerReading , double LastInsulinDose)
    {
        this.LastSugerReading = LastSugerReading;
        this.LastInsulinDose = LastInsulinDose;
    }

    public double getLastSugerReading() {
        return LastSugerReading;
    }

    public double getLastInsulinDose() {
        return LastInsulinDose;
    }
    
}
