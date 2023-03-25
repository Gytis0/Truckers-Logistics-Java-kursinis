package trucks.truckers4.Controllers;

import javafx.scene.control.Label;
import trucks.truckers4.Model.Truck;

public class ViewTruck {
    public Label nameValue;
    public Label plateNumberValue;
    public Label weightValue;

    public void setData(Truck truck){
        this.nameValue.setText(truck.getModel());
        this.plateNumberValue.setText(truck.getPlateNumber());
        this.weightValue.setText(truck.getWeight() + " kg.");
    }
}
