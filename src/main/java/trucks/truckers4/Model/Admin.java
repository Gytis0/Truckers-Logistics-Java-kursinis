package trucks.truckers4.Model;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(){
        super("admin", "admin", null, null, "admin", "admin", LocalDate.now());
    }
}
