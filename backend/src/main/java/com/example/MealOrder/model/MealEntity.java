package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meal", schema = "mydb", catalog = "")
public class MealEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmeal")
    private Integer id;

    @Basic
    @Column(name = "name", unique = true)
    private String name;

    @Basic
    @Column(name = "price")
    private Integer price;

    @Basic
    @Column(name = "day_before")
    private Byte dayBefore;

    @ManyToOne
    @JoinColumn(name = "idmeal_type_fk", referencedColumnName = "idmeal_type", nullable = false)
    private MealTypeEntity mealTypeEntity;

    public MealEntity(String name, Integer price, Byte dayBefore, MealTypeEntity mealTypeEntity) {
        this.name = name;
        this.price = price;
        this.dayBefore = dayBefore;
        this.mealTypeEntity = mealTypeEntity;
    }
}
