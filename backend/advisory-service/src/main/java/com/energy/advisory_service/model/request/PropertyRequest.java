package com.energy.advisory_service.model.request;

import com.energy.advisory_service.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyRequest {

    @NotBlank(message = "property.propertyId is required")
    private String propertyId;

    @NotBlank(message = "property.location is required")
    private String location;

    @NotNull(message = "property.propertyType is required")
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