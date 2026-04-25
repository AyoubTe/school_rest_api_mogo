package net.samiayoub.school_mogodb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.samiayoub.school_mogodb.entity.enums.Role;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "teachers")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Teacher extends User {

    @Field(name = "discipline")
    private String discipline;

    @DBRef(lazy = true)
    @JsonIgnore
    School school;

    @DBRef(lazy = true)
    @JsonIgnore
    private List<Course> courses;

    public Teacher() {
        super(Role.TEACHER);
    }

    public Teacher(String id, String username, String firstname, String lastname, String email, String password, String discipline) {
        super(id, username, firstname, lastname, email, password, Role.TEACHER);
        this.discipline = discipline;
    }
}
