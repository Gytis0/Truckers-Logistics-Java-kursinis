package trucks.truckers4.Model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class Driver extends User implements Serializable {
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Destination> inDestinations;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "driverAuthor", cascade = CascadeType.ALL)
    private List<Forum> forums;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "driverAuthor", cascade = CascadeType.ALL)
    private List<Comment> comments;

    private String med;

    public Driver(String name, String surname, String phone, String email, String username, String password, LocalDate birthDate, String med){
        super(name, surname, phone, email, username, password, birthDate);
        this.med = med;
    }

    public Driver(String name, String surname, String phone, String email, String username, String password, LocalDate birthDate){
        super(name, surname, phone, email, username, password, birthDate);
    }

    public Destination findDestination(int id){
        for(Destination d : inDestinations){
            if(d.getId() == id){
                return d;
            }
        }
        return null;
    }

    public boolean isValid(){
        if(!name.equals("") && !surname.equals("")&& !phone.equals("")&& !email.equals("")&& !username.equals("")&& !password.equals("")&& birthDate != null){
            return true;
        }
        else{
            return false;
        }
    }
}