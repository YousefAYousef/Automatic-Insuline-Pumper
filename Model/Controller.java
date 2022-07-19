package Model;

/**
 *
 * @author Yousef Hussien
 */
public class Controller {
    
    // System Current State: Normal / Suspended / Emergency
    private SystemState state;

    // Blood Sugar Measurements
    private double currReading;
    private double prevReading;
    private double rateOfChange;

    // Safe Zone Boundaries & Emergency Reading Limit
    private final double safeZoneMin;
    private final double safeZoneMax;
    private final double emergencyLimit; //new

    // Dose Safety Constraints
    private double accumlativeDoses;
    private double maxDosesPerDay;
    private double maxDoseLimit;
    
    // Time to start a Periodic Test
    private int periodicTestTime;
    
    // Hardware Devices Objects
    private InsulinPumper insulinPumper;
    private BloodSugarSensor sugarSensor;
    private InsulinReservoirSensor reservoirSensor;
    private Battery battery;

    public Controller(double safeZoneMin, double safeZoneMax, double maxDosesPerDay, double maxDoseLimit, double emergencyLimit, int periodicTestTime) {
        this.state = SystemState.NORMAL;
        this.currReading = 0.0;
        this.prevReading = 0.0;
        this.rateOfChange = 0.0;
        this.emergencyLimit = emergencyLimit;
        this.safeZoneMin = safeZoneMin;
        this.safeZoneMax = safeZoneMax;
        this.accumlativeDoses = 0.0;
        this.maxDosesPerDay = maxDosesPerDay;
        this.maxDoseLimit = maxDoseLimit;
        this.periodicTestTime = periodicTestTime;
        
//        insulinpumper = new InsulinPumper(this);
//        bloodsugarsensor = new BloodSugarSensor(this,10);
//        insulinreservoirsensor = new InsulinReservoirSensor(this);
    }

    public SystemState getState() {
        return state;
    }
    
    public void changeState(SystemState state) {
        // If State was Emergency and changed
        if (this.state == SystemState.EMERGENCY && state != SystemState.EMERGENCY) {
            this.periodicTestTime *= 2;
            this.maxDoseLimit /= 2;
            this.maxDosesPerDay /= 2;
        }

        // If State was not Emergency and changed          
        if (this.state != SystemState.EMERGENCY && state == SystemState.EMERGENCY) {
            this.periodicTestTime /= 2;
            this.maxDoseLimit *= 2;
            this.maxDosesPerDay *= 2;
        }
        
        // If State was Suspended and changed
        if (this.state == SystemState.SUSPENDED && state != SystemState.SUSPENDED)
        {
            if(isEmergency(currReading))                    
                state = SystemState.EMERGENCY;
            else
                state = SystemState.NORMAL;
        }
        
        this.state = state;
    }

    public void resetSystem() {
        this.state = SystemState.NORMAL;
        this.currReading = 0.0;
        this.prevReading = 0.0;
        this.rateOfChange = 0.0;
        this.accumlativeDoses = 0.0;
    }

    public void selfTest() {
        // Check Blood Sugar Sensor Hardware
        boolean sugarSensor_hasIssue = sugarSensor.checkHardware();
        
        // Check Insulin Reservoir Sensor Hardware
        boolean reservoirSensor_hasIssue = reservoirSensor.checkHardware();
        
        // Check Insulin Pumper Hardware
        boolean insulinPumper_hasIssue = insulinPumper.checkHardware();
        
        // Check Battery Level
        boolean battery_hasIssue = battery.isBatteryLow();
        
        // Suspend System if any issue
        if (sugarSensor_hasIssue || reservoirSensor_hasIssue || insulinPumper_hasIssue || battery_hasIssue) {
            if(sugarSensor_hasIssue)
                System.out.println("Error in Blood Sugar Sensor");
            if(reservoirSensor_hasIssue)
                System.out.println("Error in Insulin Reservoir Sensor");
            if(insulinPumper_hasIssue)
                System.out.println("Error in Insulin Pumper");
            if(battery_hasIssue)
                System.out.println("Battery is Low!");
            
            changeState(SystemState.SUSPENDED);
        }
        else {
            if (this.state == SystemState.SUSPENDED) {
                changeState(SystemState.NORMAL);
            }
        }                 
    }
    
    public void periodicSugarTest() {
        // Run the Periodic Test in a separate Thread
        new Thread(() -> {
            // Check System is not in Suspended State
            if (state == SystemState.SUSPENDED) {
                return;
            }

            // Get Sugar Reading from Sensor
            currReading = sugarSensor.CaptureSugarLevel();

            // Check if Reading in Emergency
            if (currReading > emergencyLimit) {
                changeState(SystemState.EMERGENCY);
            }

            // Check if Reading within Safe Zone
            int safeZoneState = inSafeZone();
            // -1: Below Safe Zone
            //  0: within Safe Zone
            //  1: Above Safe Zone

            // Check if Sugar Level is Increasing
            boolean sugarIncreasing = isSugarIncreasing();

            // Check if Rate of Change is Increasing
            boolean rateIncreasing = isRateIncreasing();

            // Check Cases when Dose is needed
            boolean needInsulin;

            // Case 1 //
            // Reading in Safe Zone &
            // Sugar Level Increasing & Rate of Change Increasing
            if (safeZoneState == 0 && sugarIncreasing && rateIncreasing)
                needInsulin = true;

            // Case 2 //
            // Reading above Safe Zone &
            // One of Sugar Level or Rate of Change is Increasing
            else if (safeZoneState == 1 && (sugarIncreasing || rateIncreasing))
                needInsulin = true;
            
            // Otherwise, no Insulin needed
            else
                needInsulin = false;
            
            
            // If Patient needs insulin, Compute and Pump the Dose
            if (needInsulin) {
                double dose = computeDose();

                // Pump the Insulin Dose

            }
            
            // Reset Reading Variables for the next Test call
            setRateOfChange();
            resetReadings();
        }).start();
    }

    public boolean isEmergency(double reading) {
        if (state == SystemState.EMERGENCY)
            return true;
        return false;
    }

    public void setRateOfChange() {
        rateOfChange = currReading - prevReading;
    }

    // new
    public void resetReadings() {
        prevReading = currReading;
        currReading = 0;
    }

    //edited
    public int inSafeZone() {
        if (currReading > safeZoneMax)
            return 1;
        else if (currReading < safeZoneMin)
            return -1;
        else 
            return 0;
    }
  
    //new
    public boolean isSugarIncreasing() {
        double rate = (currReading - prevReading);

        if (rate >= 0)
            return true;
        else
            return false;
    }

    public boolean isRateIncreasing() {
        double rate = (currReading - prevReading) - rateOfChange;

        if (rate >= 0)
            return true;
        else
            return false;
    }

    public double computeDose() {
        double dose;

        dose = (currReading - prevReading) / 4;

        if (checkDoseLimit(dose)) {
            if (checkAccumlativeDoseLimit(accumlativeDoses + dose)) {         
                accumlativeDoses = accumlativeDoses + dose;
                return dose;
            } else {               
                // to compute the last allowed dose from the max accumaltive dose limit
                dose = (accumlativeDoses + dose) - maxDosesPerDay; 
                return dose;
            }
        } else {
            
            // to compute the last allowed dose from the max dose limit in the single injection
            dose = dose - maxDoseLimit; 

            if (checkAccumlativeDoseLimit(accumlativeDoses + dose)) {        
                // then to check it doenst reach the max accumlative
                accumlativeDoses = accumlativeDoses + dose;
                return dose;

            } else {               
                // to compute the last allowed dose from the max accumaltive dose limit
                dose = (accumlativeDoses + dose) - maxDosesPerDay; 
            }
            return dose;
        }
    }

    //edited
    public boolean checkDoseLimit(double dose) {
        if (dose < maxDoseLimit)
            return true;
        else
            return false;
    }
    
    //new
    public boolean checkAccumlativeDoseLimit(double Accdose) {
        if (Accdose < maxDosesPerDay)
            return true;
        else
            return false;
    }
}
