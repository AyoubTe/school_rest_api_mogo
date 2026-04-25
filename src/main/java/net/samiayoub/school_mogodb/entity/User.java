package net.samiayoub.school_mogodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.samiayoub.school_mogodb.entity.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User  {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    private String email;

    @Indexed()
    private String password;

    @Field("role")
    private Role role;

    public User(Role role) {
        this.role = role;
    }
}
