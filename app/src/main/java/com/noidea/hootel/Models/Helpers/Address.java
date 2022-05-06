package com.noidea.hootel.Models.Helpers;

public class Address {
    protected String country;
    protected String address;

    public Address(String address, String country) {
        this.country = country;
        this.address = address;
    }

    public String getFullAddress() {
        return this.address + ", " + this.country;
    }

    public String getAddress() {return this.address;}

    public String getCountry() {return this.country;}

    public String[] getAddressArr() {
        return new String[]{this.address, this.country};
    }
}
