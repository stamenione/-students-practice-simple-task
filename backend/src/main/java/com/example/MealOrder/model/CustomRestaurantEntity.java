package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "custom_restaurant", schema = "mydb", catalog = "")
public class CustomRestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcustom_restaurant")
    private Integer id;
    @Basic
    @Column(name = "name", unique = true)
    private String name;
    @Basic
    @Column(name = "order_time")
    private LocalTime orderTime;
    @ManyToOne
    @JoinColumn(name = "iduser_fk", referencedColumnName = "iduser", nullable = false)
    private UserEntity userEntity;


    public CustomRestaurantEntity(String name, LocalTime orderTime, UserEntity userEntity) {
        this.name = name;
        this.orderTime = orderTime;
        this.userEntity = userEntity;
    }
}
