package com.emazon.stock_service.infraestructure.output.jpa.entity;

import com.emazon.stock_service.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, length = Constants.MAX_BRAND_NAME_LENGTH )
    private String name;

    @Column (length = Constants.MAX_BRAND_DESCRIPTION_LENGTH)
    private String description;
}
