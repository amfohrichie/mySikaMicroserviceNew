package com.mysikabox.config.apiKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class APIKeyRequest {

    @NotBlank(message = "organizationCode is mandatory")
    @Size(max = 20, message = "organizationCode cannot be more than 20 characters")
    private String organizationCode;
    @NotBlank(message = "organizationName is mandatory")
    private String organizationName;
    @NotBlank(message = "generatedBy is mandatory")
    private String generatedBy;
    @Positive(message = "validDays must be greater than 0")
    private int validDays;
}
