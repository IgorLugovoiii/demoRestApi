package com.RestApiExample.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "price", nullable = false)
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY)  //LAZY, щоб не завантажувати категорію автоматично
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
