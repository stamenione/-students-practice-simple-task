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
@Table(name = "user", schema = "mydb", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iduser")
    private Integer id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "the_choosen_one")
    private byte theChoosenOne;

    @ManyToOne
    @JoinColumn(name = "idaddress_fk", referencedColumnName = "idaddress", nullable = false)
    private AddressEntity addressEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<UserOrderEntity> userOrderEntityList;

    @ManyToOne
    @JoinColumn(name = "idrole_fk", referencedColumnName = "idrole", nullable = false)
    private RoleEntity roleEntity;


    @OneToMany(mappedBy = "userEntity")
    private List<MenuEntity> menuEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<CustomRestaurantEntity> customRestaurantEntityList;

    @OneToMany(mappedBy = "userEntity")
    private List<CustomOrderEntity> customOrderEntityList;


    public UserEntity(String firstName, String lastName, String email, String password, String number, AddressEntity addressEntity, RoleEntity roleEntity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.number = number;
        this.addressEntity = addressEntity;
        this.roleEntity = roleEntity;
    }

    public UserOrderEntity addOrderToList(UserOrderEntity order) {
        if (getUserOrderEntityList() == null)
            setUserOrderEntityList(new ArrayList<UserOrderEntity>());

        getUserOrderEntityList().add(order);
        order.setUserEntity(this);

        return order;
    }

    public UserOrderEntity deleteOrderFromList(UserOrderEntity order) {
        getUserOrderEntityList().remove(order);
        order.setUserEntity(this);

        return order;
    }

}
