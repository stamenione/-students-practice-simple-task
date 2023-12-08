package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu_meal", schema = "mydb", catalog = "")
public class MenuMealEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmenu_meal")
    private Integer id;

    @Basic
    @Column(name = "meal_date")
    private LocalDate mealDate;

    @ManyToOne
    @JoinColumn(name = "idmeal_fk", referencedColumnName = "idmeal", nullable = false)
    private MealEntity mealEntity;

    @ManyToOne
    @JoinColumn(name = "idmenu_fk", referencedColumnName = "idmenu", nullable = false)
    private MenuEntity menuEntity;

    @OneToMany(mappedBy = "menuMealEntity")
    private List<UserOrderEntity> userOrderEntityList;


    public MenuMealEntity(LocalDate mealDate, MealEntity mealEntity, MenuEntity menuEntity) {
        this.mealDate = mealDate;
        this.mealEntity = mealEntity;
        this.menuEntity = menuEntity;
    }

    public UserOrderEntity addOrderToList(UserOrderEntity order) {
        if (getUserOrderEntityList() == null)
            setUserOrderEntityList(new ArrayList<UserOrderEntity>());

        getUserOrderEntityList().add(order);
        order.setMenuMealEntity(this);

        return order;
    }

    public UserOrderEntity deleteOrderFromList(UserOrderEntity order) {
        getUserOrderEntityList().remove(order);
        order.setMenuMealEntity(this);

        return order;
    }
}
