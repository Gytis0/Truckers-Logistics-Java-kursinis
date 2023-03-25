package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import trucks.truckers4.Model.Forum;
import trucks.truckers4.Model.Manager;
import trucks.truckers4.Model.User;
import trucks.truckers4.hibernate.ForumHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.time.LocalDate;

public class PostCreationPage {
    public TextField postTitle;
    public TextArea postContent;
    public Button postButton;
    EntityManagerFactory emf;
    User user;
    ForumHib forumHib;
    private Tab previousTab;

    public void setData(EntityManagerFactory emf, User user, Tab tab) {
        this.emf = emf;
        this.user = user;

        previousTab = tab;

        forumHib = new ForumHib(emf);
    }

    public void setData(EntityManagerFactory emf, User user, Forum forum, Tab tab) {
        this.emf = emf;
        this.user = user;
        forumHib = new ForumHib(emf);

        previousTab = tab;

        postTitle.setText(forum.getTitle());
        postContent.setText(forum.getContent());
        postButton.setOnAction(actionEvent -> {
            update(forum);
        });
        postButton.setText("Update post");
    }

    private void update(Forum forum) {
        forum.setTitle(postTitle.getText());
        forum.setContent(postContent.getText());
        forumHib.updateForum(forum);
        cancel(null);
    }

    public void post(ActionEvent actionEvent) {
        Forum forum = new Forum(user, postTitle.getText(), postContent.getText(), LocalDate.now());
        if (forum.isValid()) {
            forumHib.createForum(forum);
            cancel(null);
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Post posted", "Your post was posted on the forums", "Congratulations!");
        } else {
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Your post is missing some fields", "Please fill in all the fields.");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        FXutils.openHomeView((Stage) postTitle.getScene().getWindow(), emf, user, previousTab);
    }
}
