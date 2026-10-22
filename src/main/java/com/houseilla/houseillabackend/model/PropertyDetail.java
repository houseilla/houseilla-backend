package com.houseilla.houseillabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "property_details")
@Inheritance(strategy = InheritanceType.JOINED)
@Data // Generates getters, setters, toString, equals, and hashCode automatically
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "typeIndicator" // The JSON field the frontend uses to declare its type
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Flat.class, name = "flat"),
        @JsonSubTypes.Type(value = House.class, name = "house"),
        @JsonSubTypes.Type(value = Land.class, name = "land"),
        @JsonSubTypes.Type(value = Office.class, name = "office"),
        @JsonSubTypes.Type(value = Shop.class, name = "shop"),
        @JsonSubTypes.Type(value = Warehouse.class, name = "warehouse")
})
public abstract class PropertyDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int detailId;

    @JsonIgnore
    @OneToOne(mappedBy = "propertyDetail", fetch = FetchType.LAZY)
    private Advertisement advertisement;
}
