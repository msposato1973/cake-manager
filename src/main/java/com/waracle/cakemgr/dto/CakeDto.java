package com.waracle.cakemgr.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CakeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer employeeId;
    private String title;

    private String description;
    private String image;

    public Integer getEmployeeId() { return employeeId; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
}
