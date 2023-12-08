package com.example.MealOrder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "image", schema = "mydb", catalog = "")
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idimage")
    private Integer id;
    @Basic
    @Column(name = "link")
    private String link;
    @Basic
    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "imageEntity")
    private MenuEntity menuEntity;

    public ImageEntity(String link, String name) {
        this.link = link;
        this.name = name;
    }
}
