package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_order", schema = "mydb", catalog = "")
public class UserOrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idorder")
    private int id;

    @Basic
    @Column(name = "quantity")
    private int quantity;

    @Basic
    @Column(name = "payed")
    private byte payed;


    @ManyToOne
    @JoinColumn(name = "idmenu_meal_fk", referencedColumnName = "idmenu_meal", nullable = false)
    private MenuMealEntity menuMealEntity;
    @ManyToOne
    @JoinColumn(name = "iduser_fk", referencedColumnName = "iduser", nullable = false)
    private UserEntity userEntity;

    public UserOrderEntity(int quantity, MenuMealEntity menuMealEntity, UserEntity userEntity) {
        this.quantity = quantity;
        this.menuMealEntity = menuMealEntity;
        this.userEntity = userEntity;
    }
}
