package trucks.truckers4.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "inForum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @ManyToOne
    private Driver driverAuthor;
    @ManyToOne
    private Manager managerAuthor;

    private String title;
    @Column(length = 2000)
    private String content;
    private LocalDate publishDate;

    public Forum(User user, String title, String content, LocalDate publishDate) {
        if(user.getClass() == Driver.class){
            driverAuthor = (Driver)user;
        }
        else{
            managerAuthor = (Manager)user;
        }
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    @Override
    public String toString(){
        if(driverAuthor != null){
            return this.getTitle() + ". [" + this.getDriverAuthor() +"]\n" + this.getContent();
        }
        else{
            return this.getTitle() + ". [" + this.getManagerAuthor() +"]\n" + this.getContent();
        }
    }

    public boolean isValid(){
        if((driverAuthor != null || managerAuthor != null) && !title.equals("") && !content.equals("") && publishDate!= null){
            return true;
        }
        else{
            return false;
        }
    }

    public List<Comment> getComments(){
        List<Comment> ans = new ArrayList<>();

        for(Comment c : comments){
            if(c.getParentComment() == null){
                ans.add(c);
            }
        }
        return ans;
    }
}
