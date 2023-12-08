package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "restaurant_meal", schema = "mydb", catalog = "")
public class RestaurantMealEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idrestaurant_meal")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idcustom_meal_fk", referencedColumnName = "idcustom_meal", nullable = false)
    private CustomMealEntity customMealEntity;

    @ManyToOne
    @JoinColumn(name = "idcustom_restaurant_fk", referencedColumnName = "idcustom_restaurant", nullable = false)
    private CustomRestaurantEntity customRestaurantEntity;


    public RestaurantMealEntity(CustomMealEntity customMealEntity, CustomRestaurantEntity customRestaurantEntity) {
        this.customMealEntity = customMealEntity;
        this.customRestaurantEntity = customRestaurantEntity;
    }
}
