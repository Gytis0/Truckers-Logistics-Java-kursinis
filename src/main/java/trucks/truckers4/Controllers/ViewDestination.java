package trucks.truckers4.Controllers;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import trucks.truckers4.Model.Checkpoint;
import trucks.truckers4.Model.Destination;
import trucks.truckers4.Model.Manager;

public class ViewDestination {
    public Label driverValue;
    public Label truckValue;
    public Label cargoValue;
    public Label statusValue;
    public ListView<Checkpoint> checkpointList;
    public ListView<Manager> managersList;

    public void setData(Destination destination){
        this.managersList.getItems().setAll(destination.getManager());
        this.driverValue.setText(destination.getDriver().toString());
        this.truckValue.setText(destination.getTruck().toString());
        this.cargoValue.setText(destination.getCargo().toString());
        this.statusValue.setText(destination.getStatus().toString());
        this.checkpointList.getItems().setAll(destination.getCheckpoint());
    }
}
