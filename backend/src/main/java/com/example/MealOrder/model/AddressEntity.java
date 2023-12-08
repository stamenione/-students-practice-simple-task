package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address", schema = "mydb", catalog = "")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idaddress")
    private Integer id;
    @Basic
    @Column(name = "street_name")
    private String streetName;
    @Basic
    @Column(name = "street_number")
    private String streetNumber;

    @OneToMany(mappedBy = "addressEntity")
    private List<UserEntity> listOfUsers;

    public AddressEntity(String streetName, String streetNumber) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public UserEntity addUserToList(UserEntity user) {
        if (getListOfUsers() == null)
            setListOfUsers(new ArrayList<UserEntity>());

        getListOfUsers().add(user);
        user.setAddressEntity(this);

        return user;
    }

    public UserEntity deleteUserFromList(UserEntity user) {
        getListOfUsers().remove(user);
        user.setAddressEntity(this);

        return user;
    }

}
