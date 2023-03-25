package trucks.truckers4.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    protected String username;
    protected String name, surname, phone, email, password;
    protected LocalDate birthDate;

    public User(String name, String surname, String phone, String email, String username, String password, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname();
    }

    public boolean isValid() {
        if (!name.equals("") && !surname.equals("") && !phone.equals("") && !email.equals("") && !username.equals("") && !password.equals("") && birthDate != null) {
            return true;
        } else {
            return false;
        }
    }
}
