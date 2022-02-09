package com.gabriel.restaurant.product.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "category")
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "priority", nullable = false)
    private int priority;

}