package TrainSystem.MBO;


import java.util.ArrayList;
import 

public class TrainInfo {
    ArrayList<Train> trains;

    public TrainInfo() {
        trains = new ArrayList<Train>();
    }

    public TrainInfo(Train t) {
        trains = new ArrayList<Train>();
        trains.add(t);
    }

    public void addTrain(Train t) {
        // Add train to trains
    }

    public Train getTrain(int id) {
        // Return train with matching ID
    }

    public void deleteTrain(int id) {
        Train temp = getTrain(id);
        trains.remove(temp);
    }

    public int getTrainCount() {
        return trains.size();
    }

    public boolean trainExists(int id) {
        // Check if train id is in trains
        for(Train t : trains) {
            if(t.getId().equals(id))
                return true;
        }
        return false;
    }


}