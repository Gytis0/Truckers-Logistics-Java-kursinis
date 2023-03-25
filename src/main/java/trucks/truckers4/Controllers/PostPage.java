package trucks.truckers4.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import trucks.truckers4.Model.*;
import trucks.truckers4.hibernate.CommentHib;
import trucks.truckers4.hibernate.ForumHib;
import trucks.truckers4.utils.FXutils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class PostPage {
    public Label postAuthor;
    public Label postDate;
    public Label postTitle;

    public Button deletePostButton;
    public Button editPostButton;
    public Button editCommentButton;
    public Button deleteCommentButton;
    public Button backButton;
    public Button refreshButton;
    public Button addCommentButton;

    public Text postContent;
    public Text commentsLabel;

    public ListView commentsListView;
    public Button openCommentButton;

    EntityManagerFactory emf;
    User user;
    ForumHib forumHib;
    CommentHib commentHib;

    Forum forum;
    Comment comment;
    private Tab previousTab;

    // For setting up a post
    public void setData(EntityManagerFactory emf, User user, Forum forum, Tab tab) {
        this.emf = emf;
        this.user = user;

        this.commentHib = new CommentHib(emf);
        this.forumHib = new ForumHib(emf);
        this.forum = forum;

        previousTab = tab;

        fillPostData();
        editCommentButton.setDisable(true);
        deleteCommentButton.setDisable(true);
        if (user.getClass() == Manager.class && forum.getManagerAuthor() != null) {
            if (user.getId() == forum.getManagerAuthor().getId()) {
                editPostButton.setVisible(true);
                deletePostButton.setVisible(true);
            } else {
                editPostButton.setVisible(false);
                deletePostButton.setVisible(false);
            }
        }



        if (user.getClass() == Driver.class && forum.getDriverAuthor() != null) {
            if (user.getId() == forum.getDriverAuthor().getId()) {
                editPostButton.setVisible(true);
                deletePostButton.setVisible(true);
            } else {
                editPostButton.setVisible(false);
                deletePostButton.setVisible(false);
            }
        }

        if(user.getClass() == Admin.class){
            editPostButton.setVisible(true);
            deletePostButton.setVisible(true);
        }
    }

    public void fillPostData() {
        postContent.setText(forum.getContent());
        postTitle.setText(forum.getTitle());
        postDate.setText(forum.getPublishDate().toString());
        if (forum.getManagerAuthor() != null) {
            postAuthor.setText("[" + forum.getManagerAuthor() + "]");
        } else {
            postAuthor.setText("[" + forum.getDriverAuthor() + "]");
        }
        commentsListView.getItems().setAll(forum.getComments());
    }

    // For setting up a comment
    public void setData(EntityManagerFactory emf, User user, Comment comment) {
        this.emf = emf;
        this.user = user;

        this.commentHib = new CommentHib(emf);
        this.forumHib = new ForumHib(emf);
        this.comment = comment;

        fillCommentData();
        editCommentButton.setDisable(true);
        deleteCommentButton.setDisable(true);
        if (user.getClass() == Manager.class && comment.getManagerAuthor() != null) {
            if (user.getId() == comment.getManagerAuthor().getId()) {
                editPostButton.setVisible(true);
                deletePostButton.setVisible(true);
            } else {
                editPostButton.setVisible(false);
                deletePostButton.setVisible(false);
            }
        }

        if (user.getClass() == Driver.class && comment.getDriverAuthor() != null) {
            if (user.getId() == comment.getDriverAuthor().getId()) {
                editPostButton.setVisible(true);
                deletePostButton.setVisible(true);
            } else {
                editPostButton.setVisible(false);
                deletePostButton.setVisible(false);
            }
        }
        commentsLabel.setText("Replies");

        backButton.setOnAction(actionEvent -> {backAlt(null);});
        //refreshButton.setOnAction(actionEvent -> {refreshAlt(null);});
        refreshButton.setVisible(false);
        addCommentButton.setOnAction(actionEvent -> {openReplyCreationWindow(null);});

        addCommentButton.setText("Add a reply");
        editCommentButton.setText("Edit reply");
        deleteCommentButton.setText("Delete reply");
        openCommentButton.setText("Open reply");

        deletePostButton.setVisible(false);
        editPostButton.setVisible(false);

        editPostButton.setOnAction(actionEvent -> {editPostAlt(null);});
        deletePostButton.setOnAction(actionEvent -> {deletePostAlt(null);});
        editCommentButton.setOnAction(actionEvent -> {editReply(null);});
        deleteCommentButton.setOnAction(actionEvent -> {deleteReply(null);});

        commentsListView.setOnMouseReleased(mouseEvent -> {updateButtonsAlt(null);});
    }

    public void fillCommentData() {
        postContent.setText(comment.getContent());
        postTitle.setText("");
        postDate.setText(comment.getPublishDate().toString());
        if (comment.getManagerAuthor() != null) {
            postAuthor.setText("[" + comment.getManagerAuthor() + "]");
        } else {
            postAuthor.setText("[" + comment.getDriverAuthor() + "]");
        }
        commentsListView.getItems().setAll(comment.getReplies());
    }

    // When viewing a post
    public void back(ActionEvent actionEvent) throws IOException {
        FXutils.openHomeView((Stage) postContent.getScene().getWindow(), emf, user, previousTab);
    }

    public void refresh(ActionEvent actionEvent) {
        forum = forumHib.getForum(forum);
        fillPostData();
    }

    public void deletePost(ActionEvent actionEvent) {
        forumHib.deleteForum(forum);
        FXutils.openHomeView((Stage) postContent.getScene().getWindow(), emf, user, previousTab);
    }

    public void editPost(ActionEvent actionEvent) {
        try {
            FXutils.openPostUpdatePage((Stage) postContent.getScene().getWindow(), emf, user, forum, previousTab);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editComment(ActionEvent actionEvent) {
        try {
            FXutils.openCommentUpdateWindow(emf, user, (Comment) commentsListView.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteComment(ActionEvent actionEvent) {
        commentHib.deleteComment((Comment) commentsListView.getSelectionModel().getSelectedItem());
        FXutils.openHomeView((Stage) postContent.getScene().getWindow(), emf, user, previousTab);
    }

    public void updateButtons(MouseEvent mouseEvent) {
        Comment comment = (Comment) commentsListView.getSelectionModel().getSelectedItem();
        if (user.getClass() == Manager.class && comment.getManagerAuthor() != null) {
            if (user.getId() == comment.getManagerAuthor().getId()) {
                editCommentButton.setDisable(false);
                deleteCommentButton.setDisable(false);
            } else {
                editCommentButton.setDisable(true);
                deleteCommentButton.setDisable(true);
            }
        } else if (user.getClass() == Driver.class && user.getId() == comment.getDriverAuthor().getId()) {
            editCommentButton.setDisable(false);
            deleteCommentButton.setDisable(false);
        } else {
            editCommentButton.setDisable(true);
            deleteCommentButton.setDisable(true);
        }
    }

    // When viewing a comment

    public void backAlt(ActionEvent actionEvent) {
        if(comment.getParentComment() != null){
            FXutils.openCommentViewPage((Stage) postContent.getScene().getWindow(), emf, user, comment.getParentComment());
        }
        else{
            FXutils.openPostViewPage((Stage) postContent.getScene().getWindow(), emf, user, comment.getInForum(), previousTab);
        }
    }

    public void refreshAlt(ActionEvent actionEvent) {
        comment = commentHib.getComment(comment);
        fillCommentData();
    }

    public void deletePostAlt(ActionEvent actionEvent) {
        commentHib.deleteComment(comment);

        if(comment.getParentComment() != null) {
            FXutils.openCommentViewPage((Stage) postContent.getScene().getWindow(), emf, user, comment.getParentComment());
        }
        else{
            FXutils.openHomeView((Stage)postContent.getScene().getWindow(), emf, user, previousTab);
        }
    }

    public void editPostAlt(ActionEvent actionEvent) {
        try {
            FXutils.openCommentUpdateWindow(emf, user, comment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editReply(ActionEvent actionEvent) {
        try {
            FXutils.openCommentUpdateWindow(emf, user, (Comment) commentsListView.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReply(ActionEvent actionEvent) {
        commentHib.deleteComment((Comment) commentsListView.getSelectionModel().getSelectedItem());
    }

    public void updateButtonsAlt(MouseEvent mouseEvent) {
        Comment comment = (Comment) commentsListView.getSelectionModel().getSelectedItem();
        if (user.getClass() == Manager.class && comment.getManagerAuthor() != null) {
            if (user.getId() == comment.getManagerAuthor().getId()) {
                editCommentButton.setDisable(false);
                deleteCommentButton.setDisable(false);
            } else {
                editCommentButton.setDisable(true);
                deleteCommentButton.setDisable(true);
            }
        } else if (user.getClass() == Driver.class && user.getId() == comment.getDriverAuthor().getId()) {
            editCommentButton.setDisable(false);
            deleteCommentButton.setDisable(false);
        } else {
            editCommentButton.setDisable(true);
            deleteCommentButton.setDisable(true);
        }
    }

    public void openReplyCreationWindow(ActionEvent actionEvent) {
        FXutils.openReplyCreationWindow(emf, user, comment);
    }

    public void openCommentViewPage(ActionEvent actionEvent) throws  IOException{
        FXutils.openCommentViewPage((Stage)postContent.getScene().getWindow(), emf, user, (Comment) commentsListView.getSelectionModel().getSelectedItem());
    }

    public void openCommentCreationPage(ActionEvent actionEvent) {
        FXutils.openCommentCreationWindow(emf, user, forum);
    }
}
