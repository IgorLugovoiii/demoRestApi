package com.RestApiExample.demo.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description" ,length = 500)
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Food> food;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
