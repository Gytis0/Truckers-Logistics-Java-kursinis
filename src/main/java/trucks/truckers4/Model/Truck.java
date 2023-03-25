package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL)
    private List<Destination> inDestinations;

    private String model, plateNumber;
    private int weight;

    public Truck(String model, String plateNumber, int weight) {
        this.model = model;
        this.plateNumber = plateNumber;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return this.getModel() + " (" + this.getPlateNumber() + ")";
    }

    public boolean isValid(){
        if(!model.equals("") && !plateNumber.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
}
