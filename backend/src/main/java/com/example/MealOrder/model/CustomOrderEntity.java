package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "custom_order", schema = "mydb", catalog = "")
public class CustomOrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcustom_order")
    private Integer id;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "idrestaurant_meal_fk", referencedColumnName = "idrestaurant_meal", nullable = false)
    private RestaurantMealEntity restaurantMealEntity;
    @ManyToOne
    @JoinColumn(name = "iduser_fk", referencedColumnName = "iduser", nullable = false)
    private UserEntity userEntity;


    public CustomOrderEntity(Integer quantity, RestaurantMealEntity restaurantMealEntity, UserEntity userEntity) {
        this.quantity = quantity;
        this.restaurantMealEntity = restaurantMealEntity;
        this.userEntity = userEntity;
    }
}
