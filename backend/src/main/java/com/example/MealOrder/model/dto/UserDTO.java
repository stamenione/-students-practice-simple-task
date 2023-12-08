package com.example.MealOrder.model.dto;

import com.example.MealOrder.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {

    @FirstNameConstraint
    private String firstName;

    @LastNameConstraint
    private String lastName;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    @NumberConstraint
    private String number;

    @StreetNameConstraint
    private String streetName;

    @StreetNumberConstraint
    private String streetNumber;

}
