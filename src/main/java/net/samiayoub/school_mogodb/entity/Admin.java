package net.samiayoub.school_mogodb.entity;

import lombok.Getter;
import lombok.Setter;
import net.samiayoub.school_mogodb.entity.enums.Role;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "admins")
@Getter
@Setter
public class Admin extends User {

    @Field("mission")
    private String mission;

    public Admin() {
        super(Role.ADMIN);
    }
    public Admin(String id, String username, String firstname, String lastname, String email, String password, String mission) {
        super(id, username, firstname, lastname, email, password, Role.ADMIN);
        this.mission = mission;
    }
}
