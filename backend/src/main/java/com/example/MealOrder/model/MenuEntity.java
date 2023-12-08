package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu", schema = "mydb", catalog = "")
public class MenuEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmenu")
    private Integer id;
    @Basic
    @Column(name = "start_date", unique = true)
    private LocalDate startDate;
    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "iduser_fk", referencedColumnName = "iduser", nullable = false)
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "idimage_fk", referencedColumnName = "idimage", nullable = false)
    private ImageEntity imageEntity;

}
