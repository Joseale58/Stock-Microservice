package com.emazon.stock_service.infraestructure.output.jpa.entity;

import com.emazon.stock_service.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, length = Constants.MAX_CATEGORY_NAME_LENGTH)
    private String name;

    @Column (length = Constants.MAX_CATEGORY_DESCRIPTION_LENGTH)
    private String description;
}
