package net.samiayoub.school_mogodb.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "grade_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GradeDetails {

    @Id
    private String id;

    @Field(name = "first_grade")
    private int gradeOne;

    @Field(name = "second_grade")
    private int gradeTwo;

    @Field(name = "third_grade")
    private int gradeThree;

    @DBRef
    @JsonIgnore
    private StudentCourseDetails studentCourseDetails;
}
