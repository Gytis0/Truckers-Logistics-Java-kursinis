package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import trucks.truckers4.Model.*;
import trucks.truckers4.hibernate.CommentHib;
import trucks.truckers4.hibernate.ForumHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class CommentCreationPage {
    public TextArea commentContent;
    public Button postButton;
    EntityManagerFactory emf;
    User user;
    Forum forum;
    Comment comment;
    CommentHib commentHib;
    ForumHib forumHib;

    public void setData(EntityManagerFactory emf, User user, Forum forum) {
        this.emf = emf;
        this.user = user;
        this.forum = forum;

        commentHib = new CommentHib(emf);
        forumHib = new ForumHib(emf);
    }

    public void setDataForUpdate(EntityManagerFactory emf, User user, Comment comment) {
        this.emf = emf;
        this.user = user;
        this.forum = comment.getInForum();

        commentHib = new CommentHib(emf);
        forumHib = new ForumHib(emf);

        commentContent.setText(comment.getContent());
        postButton.setOnAction(actionEvent -> {update(comment);});
        postButton.setText("Update post");
    }

    public void setDataForReply(EntityManagerFactory emf, User user, Comment comment) {
        this.emf = emf;
        this.user = user;
        this.forum = comment.getInForum();
        this.comment = comment;

        commentHib = new CommentHib(emf);
        forumHib = new ForumHib(emf);

        postButton.setOnAction(actionEvent -> {addReply(null);});
        postButton.setText("Add reply");
    }



    private void update(Comment comment) {
        comment.setContent(commentContent.getText());
        commentHib.updateComment(comment);
        cancel(null);
    }

    public void postAcomment(ActionEvent actionEvent) {
        Comment comment = new Comment(user, commentContent.getText(), forum, LocalDate.now());
        if(comment.isValid()){
            commentHib.createComment(comment);
            cancel(null);
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Comment posted", "Your comment was posted on a forum", "Congratulations!");
        }
        else{
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Your comment is missing some fields", "Please fill in all the fields.");
        }
    }

    public void addReply(ActionEvent actionEvent) {
        Comment comment = new Comment(user, commentContent.getText(), forum, LocalDate.now(), this.comment);
        if(comment.isValid()){
            commentHib.createComment(comment);
            cancel(null);
            FXutils.alertMessage(Alert.AlertType.INFORMATION, "Reply added", "Your reply was added on a comment", "Congratulations!");
        }
        else{
            FXutils.alertMessage(Alert.AlertType.ERROR, "Missing info", "Your reply is missing some fields", "Please fill in all the fields.");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        closeWindow();
    }

    public void closeWindow(){
        commentContent.getScene().getWindow().hide();
    }
}
