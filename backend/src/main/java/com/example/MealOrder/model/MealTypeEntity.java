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
@Table(name = "meal_type", schema = "mydb", catalog = "")
public class MealTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmeal_type")
    private Integer id;
    @Basic
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "mealTypeEntity")
    private List<MealEntity> mealEntityList;

    public MealEntity addMealToList(MealEntity meal) {
        if (getMealEntityList() == null)
            setMealEntityList(new ArrayList<MealEntity>());

        getMealEntityList().add(meal);
        meal.setMealTypeEntity(this);

        return meal;
    }

    public MealEntity deleteUserFromList(MealEntity meal) {
        getMealEntityList().remove(meal);
        meal.setMealTypeEntity(this);

        return meal;
    }
}
