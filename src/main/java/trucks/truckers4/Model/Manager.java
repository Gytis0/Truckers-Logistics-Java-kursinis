package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class Manager extends User{

    @ManyToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destination> inDestinations;
    @OneToMany(mappedBy = "managerAuthor", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Forum> forums;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "managerAuthor", cascade = CascadeType.ALL)
    private List<Comment> comments;

    private String depId;
    private int destCount;
    public Manager(String name, String surname, String phone, String email, String username, String password, LocalDate birthDate, String depId){
        super(name, surname, phone, email, username, password, birthDate);
        this.depId = depId;
    }

    public Manager(String name, String surname, String phone, String email, String username, String password, LocalDate birthDate){
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
