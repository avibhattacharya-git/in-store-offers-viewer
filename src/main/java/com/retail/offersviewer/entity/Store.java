package com.retail.offersviewer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Embedded
    private Address address;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Coordinates coordinates;
    
    // Constructors
    public Store() {
    }
    
    public Store(String id, String name, Address address, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public Address getAddress() { return address; }
    public Coordinates getCoordinates() { return coordinates; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(Address address) { this.address = address; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    
    @Embeddable
    public static class Address {
        private String street;
        private String city;
        private String state;
        
        @Column(name = "zip_code")
        private String zipCode;
        
        public Address() {
        }
        
        public Address(String street, String city, String state, String zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
        
        public String getStreet() { return street; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getZipCode() { return zipCode; }
        
        public void setStreet(String street) { this.street = street; }
        public void setCity(String city) { this.city = city; }
        public void setState(String state) { this.state = state; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }
    
    @Embeddable
    public static class Coordinates {
        private Double latitude;
        private Double longitude;
        
        public Coordinates() {
        }
        
        public Coordinates(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        public Double getLatitude() { return latitude; }
        public Double getLongitude() { return longitude; }
        
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
    }
}
