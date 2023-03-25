package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "parentComment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Forum inForum;
    @ManyToOne
    private Driver driverAuthor;
    @ManyToOne
    private Manager managerAuthor;

    @Column(length = 2000)
    private String content;
    private LocalDate publishDate;

    public Comment(User author, String content, Forum inForum, LocalDate publishDate) {
        if(author.getClass() == Driver.class){
            this.driverAuthor = (Driver)author;
        }
        else if(author.getClass() == Manager.class){
            this.managerAuthor = (Manager)author;
        }
        this.inForum = inForum;
        this.content = content;
        this.publishDate = publishDate;
    }

    public Comment(User author, String content, Forum inForum, LocalDate publishDate, Comment parentComment) {
        if(author.getClass() == Driver.class){
            this.driverAuthor = (Driver)author;
        }
        else{
            this.managerAuthor = (Manager)author;
        }
        this.inForum = inForum;
        this.content = content;
        this.publishDate = publishDate;
        this.replies = new ArrayList<>();
        this.parentComment = parentComment;
    }

    @Override
    public String toString(){
        if(driverAuthor != null) {
            return "[" + this.getDriverAuthor() + "] (" + getPublishDate() + ")\n" + getContent();
        }
        else{
            return "[" + this.getManagerAuthor() + "] (" + getPublishDate() + ")\n" + getContent();
        }
    }

    public boolean isValid(){
        if((driverAuthor != null || managerAuthor != null) && inForum != null && !content.equals("") && publishDate!= null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }
}
