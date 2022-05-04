package com.noidea.hootel.Models;

public class Name {
    protected String fName;
    protected String lName;

    public Name(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public String getName() {
        return fName + " " + lName;
    }

    public String[] getNameArr() {
        return new String[]{fName, lName};
    }

    public String getfName() {return this.fName;}

    public String getlName() {return this.lName;}
}
