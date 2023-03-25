package trucks.truckers4;

import trucks.truckers4.Model.Driver;
import trucks.truckers4.hibernate.UserHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Truckers");
        //UserHib userHib = new UserHib(entityManagerFactory);
        //Driver driver = new Driver("myAnotherName", "mySurname", "+370629", "myEmail@", "hello", "world", LocalDate.now(), "myMed");
        //userHib.createUser(driver);
    }
}
