package net.samiayoub.school_mogodb.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import net.samiayoub.school_mogodb.entity.enums.Role;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "students")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Student extends User {
    @DBRef
    @JsonIgnore
    private List<Course> courses;

    public Student() {
        super(Role.STUDENT);
    }
    public Student(String id, String username, String firstname, String lastname, String email, String password) {
        super(id, username, firstname, lastname, email, password, Role.STUDENT);
    }
}
