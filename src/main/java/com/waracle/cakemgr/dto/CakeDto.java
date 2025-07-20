package com.waracle.cakemgr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CakeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotBlank(message = "Image URL must not be blank")
    @Size(max = 300, message = "Image URL must not be longer than 300 characters")
    private String image;


}
