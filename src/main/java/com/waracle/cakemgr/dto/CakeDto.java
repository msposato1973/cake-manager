package com.waracle.cakemgr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class CakeDto implements Comparable<CakeDto>, Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotBlank(message = "Image URL must not be blank")
    @Size(max = 300, message = "Image URL must not be longer than 300 characters")
    private String image;


    public CakeDto(String title, String description, String image) {
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.image = image;
    }

	@Override
	public int compareTo(CakeDto o) {
        return (this.employeeId != null && o.employeeId != null) ?  this.employeeId.compareTo(o.employeeId) :0;
	}


}
