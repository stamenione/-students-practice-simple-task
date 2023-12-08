package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "mydb", catalog = "")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idrole")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roleEntity")
    private List<UserEntity> listOfUsers;


}
