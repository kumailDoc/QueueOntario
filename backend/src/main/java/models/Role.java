package models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    private models.ERole name;

    public Role() {

    }

    public Role(models.ERole name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public models.ERole getName() {
        return name;
    }

    public void setName(models.ERole name) {
        this.name = name;
    }
}
