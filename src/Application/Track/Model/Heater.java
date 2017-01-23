package Application.Track.Model;

import java.util.Random;

/**
 * Created by adzun_000 on 1/21/2017.
 */
public class Heater {

    private Integer heaterNumber;
    private boolean active;
    private Double desiredTemp;
    private Double railTemp;
    private Double heatRate = 2.0;

    /***
     *
     */
    public Heater(Integer heaterNumber){
        this.heaterNumber = heaterNumber;
        this.active = false;
        this.railTemp = generateRailTemp();
    }

    public Heater(Integer heaterNumber, double railTemp){
        this.heaterNumber = heaterNumber;
        this.active = false;
        this.railTemp = railTemp;
    }

    public Integer getHeaterNumber() {
        return heaterNumber;
    }

    public void setHeaterNumber(Integer heaterNumber) {
        this.heaterNumber = heaterNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /***
     * Toggle the heater on and off
     */
    public void toggleActive(){
        this.active = !this.active;
    }

    public double getDesiredTemp() {
        return desiredTemp;
    }

    public void setDesiredTemp(double desiredTemp) {
        this.desiredTemp = desiredTemp;
    }

    public double getRailTemp() {
        return railTemp;
    }

    public void setRailTemp(double railTemp) {
        this.railTemp = railTemp;
    }

    public double getHeatRate() {
        return heatRate;
    }

    public void setHeatRate(double heatRate) {
        this.heatRate = heatRate;
    }

    private Double generateRailTemp() {
        return new Double(new Random().nextInt(100));
    }

}
