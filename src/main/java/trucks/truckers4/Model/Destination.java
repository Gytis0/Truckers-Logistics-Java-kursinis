package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Truck truck;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Checkpoint> checkpoint;
    @ManyToOne
    private Driver driver;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Manager> manager;
    @ManyToOne
    private Cargo cargo;

    private Status status;


    public Destination(Truck truck, List<Checkpoint> checkpoint, Driver driver, Manager manager, Cargo cargo, Status status) {
        this.truck = truck;
        this.checkpoint = checkpoint;
        this.driver = driver;
        this.manager = new ArrayList<>();
        this.manager.add(manager);
        this.cargo = cargo;
        this.status = status;
    }

    public Destination(Truck truck, List<Checkpoint> checkpoint, Driver driver, List<Manager> manager, Cargo cargo, Status status) {
        this.truck = truck;
        this.checkpoint = checkpoint;
        this.driver = driver;
        this.manager = manager;
        this.cargo = cargo;
        this.status = status;
    }

    public void setManager(List<Manager> manager) {
        this.manager = manager;
    }

    public void setManager(Manager manager){
        this.manager.clear();
        this.manager.add(manager);
    }

    public void setAsDelivered(){
        status = Status.DELIVERED;
    }

    public Checkpoint getLastCheckpoint(){
        return checkpoint.get(checkpoint.size() - 1);
    }

    public Checkpoint getFirstCheckpoint() {return checkpoint.get(0);}

    @Override
    public String toString(){
        LocalDate arrivalDate = checkpoint.get(checkpoint.size() - 1).getArrivalDate();
        return cargo.getName() + ", " + driver.getSurname() + ", " + arrivalDate + ". [" + status.toString() + "]";
    }

    public boolean isValid(){
        if(truck != null && checkpoint.size() > 0 && driver != null && manager != null && cargo!= null && status!= null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {

        try {
            Destination destination  = (Destination) obj;
            return this.getId() == destination.getId();
        }
        catch (Exception e)
        {
            return false;
        }

    }
}
