package com.ecommerce.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CategoryDto {
    private Long id;
    @NotNull
    private String name;
    private boolean activated;
    private boolean deleted;
    private String description;

}
