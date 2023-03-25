package trucks.truckers4.Controllers;

import javafx.scene.control.Label;
import trucks.truckers4.Model.Cargo;

public class ViewCargo {
    public Label nameValue;
    public Label weightValue;
    public Label widthValue;
    public Label lengthValue;
    public Label heightValue;

    public void setData(Cargo cargo){
        this.nameValue.setText(cargo.getName());
        this.weightValue.setText(String.valueOf(cargo.getWeight()));
        this.widthValue.setText(String.valueOf(cargo.getWidth()));
        this.lengthValue.setText(String.valueOf(cargo.getLength()));
        this.heightValue.setText(String.valueOf(cargo.getHeight()));
    }
}
