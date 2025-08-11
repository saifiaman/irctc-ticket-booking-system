package irctc.ticket.services;

import java.util.List;
import java.util.stream.Collectors;

import irctc.ticket.entities.Train;

public class TrainService {

    private List<Train> trainList;

    public TrainService(List<Train> trainList) {
        this.trainList = trainList;
    }

    public TrainService() {
        // Default constructor
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    public boolean validTrain(Train train, String source, String destination) {
        List<String> stations = train.getStations();

        int sourceIndex = stations.indexOf(source.toLowerCase());
        int destinationIndex = stations.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

}
