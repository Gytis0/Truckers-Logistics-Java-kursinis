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
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    private List<Destination> inDestinations;

    private String name;
    private int weight, length, width, height;

    public Cargo(String name, int weight, int length, int width, int height) {
        this.name = name;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString(){
        return this.getName() + " (" + this.getWeight() + " kg.)";
    }

    public boolean isValid(){
        if(name != null){
            return true;
        }
        else{
            return false;
        }
    }
}
