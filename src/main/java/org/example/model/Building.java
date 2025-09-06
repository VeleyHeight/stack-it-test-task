package org.example.model;

public class Building {
    String address;
    String nextId;
    public Building(String address, String nextId) {
        this.address = address;
        this.nextId = nextId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNextId() {
        return nextId;
    }
    public void setNextId(String nextId) {
        this.nextId = nextId;
    }
}
