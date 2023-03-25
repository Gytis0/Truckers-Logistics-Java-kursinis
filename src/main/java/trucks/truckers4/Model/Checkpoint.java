package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "checkpoint", cascade = CascadeType.ALL)
    private List<Destination> inDestinations;

    private String address;
    private LocalDate arrivalDate, departureDate;

    public Checkpoint(String address, LocalDate arrivalDate, LocalDate departureDate) {
        this.address = address;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        if(arrivalDate == null) this.arrivalDate = LocalDate.of(1, 1, 1);
        if(departureDate == null) this.departureDate = LocalDate.of(1, 1, 1);
    }

    @Override
    public String toString() {
        if (arrivalDate.equals(LocalDate.of(1, 1, 1))){
            return this.getAddress() + " (Departing: " + this.getDepartureDate() + ")";
        }
        else if(departureDate.equals(LocalDate.of(1, 1, 1))){
            return this.getAddress() + " (Arriving: " + this.getArrivalDate() + ")";
        }
        else{
            return this.getAddress() + " (" + this.getArrivalDate() + "; " + this.getDepartureDate() + ")";
        }
    }

    public boolean isValid() {
        if (!address.equals("") && (arrivalDate != null || departureDate != null)) {
            return true;
        } else {
            return false;
        }
    }
}
