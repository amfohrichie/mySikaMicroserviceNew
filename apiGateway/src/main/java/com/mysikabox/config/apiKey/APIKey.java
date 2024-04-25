package com.mysikabox.config.apiKey;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "apiKey")
public class APIKey {

    @Id
    @Column(length = 20)
    @NotBlank(message = "organizationCode is mandatory")
    @Size(max = 20, message = "organizationCode cannot be more than 20 characters")
    private String organizationCode;
    @NotBlank(message = "organizationName is mandatory")
    private String organizationName;
    private String token;
    private String generatedOn;
    @NotBlank(message = "generatedBy is mandatory")
    private String generatedBy;
    @Positive(message = "validDays must be greater than 0")
    private int validDays;
    private String expirationDate;

}
