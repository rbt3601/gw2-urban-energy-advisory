package com.energy.advisory_service.model.common;

import com.energy.advisory_service.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Property {

    @NotBlank(message = "propertyId is required")
    @Size(max = 100, message = "propertyId must not exceed 100 characters")
    private String propertyId;

    @NotBlank(message = "location is required")
    @Size(max = 120, message = "location must not exceed 120 characters")
    private String location;

    @NotNull(message = "propertyType is required")
    private PropertyType propertyType;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
}
