package trucks.truckers4.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import org.hibernate.annotations.Check;
import trucks.truckers4.Controllers.*;
import trucks.truckers4.HelloApplication;

import javafx.stage.Stage;
import trucks.truckers4.Model.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class FXutils {
    public static void alertMessage(Alert.AlertType type, String title, String headerText, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void openHomeView(Stage window, EntityManagerFactory emf, User user, Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HomeView hv = fxmlLoader.getController();
        hv.setData(emf, user, tab);
        Scene scene = new Scene(parent, 1000, 600);
        window.setTitle("Home Page");
        window.setScene(scene);
        window.show();
    }

    public static void openRegistrationPage(Stage window, EntityManagerFactory emf, String username, String password) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RegistrationView rv = fxmlLoader.getController();
        rv.setData(emf, username, password);

        Scene scene = new Scene(parent);
        window.setTitle("Registration Page");
        window.setScene(scene);
        window.show();
    }

    public static void openRegistrationPage(Stage window, EntityManagerFactory emf, Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RegistrationView rv = fxmlLoader.getController();
        rv.setData(emf, tab);

        Scene scene = new Scene(parent);
        window.setTitle("Registration Page");
        window.setScene(scene);
        window.show();
    }

    public static void openLoginPage(Stage window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Login Page");
        window.setScene(scene);
        window.show();
    }

    // Truck
    public static void openTruckCreationPage(Stage window, EntityManagerFactory emf, User user, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("truck-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckCreationView tv = fxmlLoader.getController();
        tv.setData(emf, user, tab);
        Scene scene = new Scene(parent);
        window.setTitle("Truck Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openTruckUpdatePage(EntityManagerFactory emf, User user, Truck truck, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("truck-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckCreationView tv = fxmlLoader.getController();
        tv.setData(emf, user, truck, tab);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Truck Update Page");
        window.setScene(scene);
        window.show();
    }

    public static void openTruckView(Truck truck) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-truck.fxml"));
        Parent parent = fxmlLoader.load();
        ViewTruck vt = fxmlLoader.getController();
        vt.setData(truck);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Truck View Page");
        window.setScene(scene);
        window.show();
    }

    // Checkpoint
    public static void openCheckpointCreationScreen(Stage window, EntityManagerFactory emf, User user, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("checkpoint-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CheckpointCreationView tv = fxmlLoader.getController();
        tv.setData(emf, user, tab);
        Scene scene = new Scene(parent);
        window.setTitle("Checkpoint Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openCheckpointUpdateScreen(EntityManagerFactory emf, User user, Checkpoint checkpoint, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("checkpoint-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CheckpointCreationView tv = fxmlLoader.getController();
        tv.setData(emf, user, checkpoint, tab);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Checkpoint Update Page");
        window.setScene(scene);
        window.show();
    }


    // Destination
    public static void openDestinationCreationPage(Stage window, EntityManagerFactory emf, User user, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("destination-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        DestinationCreationView dv = fxmlLoader.getController();
        dv.setData(emf, user, tab);
        Scene scene = new Scene(parent);
        window.setTitle("Destination Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openDestinationUpdatePage(EntityManagerFactory emf, User user, Destination destination, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("destination-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        DestinationCreationView dv = fxmlLoader.getController();
        dv.setData(emf, user, destination, tab);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Destination Update Page");
        window.setScene(scene);
        window.show();
    }

    public static void openDestinationView(Destination destination) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-destination.fxml"));
        Parent parent = fxmlLoader.load();
        ViewDestination vd = fxmlLoader.getController();
        vd.setData(destination);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Destination View Page");
        window.setScene(scene);
        window.show();
    }

    // Cargo
    public static void openCargoCreationPage(Stage window, EntityManagerFactory emf, User user, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cargo-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoCreationView cv = fxmlLoader.getController();
        cv.setData(emf, user, tab);
        Scene scene = new Scene(parent);
        window.setTitle("Cargo Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openCargoUpdatePage(EntityManagerFactory emf, User user, Cargo cargo, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cargo-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoCreationView cv = fxmlLoader.getController();
        cv.setData(emf, user, cargo, tab);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Cargo Update Page");
        window.setScene(scene);
        window.show();
    }

    public static void openCargoView(Cargo cargo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view-cargo.fxml"));
        Parent parent = fxmlLoader.load();
        ViewCargo vc = fxmlLoader.getController();
        vc.setData(cargo);
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Cargo View Page");
        window.setScene(scene);
        window.show();
    }

    public static void openPostCreationPage(Stage window, EntityManagerFactory emf, User user, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("post-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        PostCreationPage pcp = fxmlLoader.getController();
        pcp.setData(emf, user, tab);

        Scene scene = new Scene(parent);
        window.setTitle("Post Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openPostUpdatePage(Stage window, EntityManagerFactory emf, User user, Forum forum, Tab tab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("post-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        PostCreationPage pcp = fxmlLoader.getController();
        pcp.setData(emf, user, forum, tab);

        Scene scene = new Scene(parent);
        window.setTitle("Post Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openPostViewPage(Stage window, EntityManagerFactory emf, User user, Forum forum, Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("post-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PostPage pcp = fxmlLoader.getController();
        pcp.setData(emf, user, forum, tab);

        Scene scene = new Scene(parent);
        window.setTitle("Post Page");
        window.setScene(scene);
        window.show();
    }

    // Forums

    public static void openCommentCreationWindow(EntityManagerFactory emf, User user, Forum forum) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-creation-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CommentCreationPage ccp = fxmlLoader.getController();
        ccp.setData(emf, user, forum);

        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Comment Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openReplyCreationWindow(EntityManagerFactory emf, User user, Comment comment) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-creation-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CommentCreationPage ccp = fxmlLoader.getController();
        ccp.setDataForReply(emf, user, comment);

        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Reply Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static void openCommentUpdateWindow(EntityManagerFactory emf, User user, Comment comment) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentCreationPage ccp = fxmlLoader.getController();
        ccp.setDataForUpdate(emf, user, comment);

        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.setTitle("Comment Creation Page");
        window.setScene(scene);
        window.show();
    }

    public static  void openCommentViewPage(Stage window, EntityManagerFactory emf, User currentUser, Comment comment){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("post-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PostPage pp = fxmlLoader.getController();
        pp.setData(emf, currentUser, comment);
        Scene scene = new Scene(parent);
        window.setTitle("My Account");
        window.setScene(scene);
        window.show();
    }
    // My Account
    public static void openMyAccountPage(Stage window, EntityManagerFactory emf, User currentUser, Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("my-account-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MyAccountPage map = fxmlLoader.getController();
        map.setData(emf, currentUser, tab);
        Scene scene = new Scene(parent);
        window.setTitle("My Account");
        window.setScene(scene);
        window.show();
    }

    public static void openMyAccountPage(Stage window, EntityManagerFactory emf, User currentUser, User userInfo, Tab tab) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("my-account-page.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MyAccountPage map = fxmlLoader.getController();
        map.setData(emf, currentUser, userInfo, tab);
        Scene scene = new Scene(parent);
        window.setTitle("My Account");
        window.setScene(scene);
        window.show();
    }


}
