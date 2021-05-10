package com.vy.testing.entity;

public class Phone {
    public String number;
    private Extension extension;
    public Phone(String number,String  extension) {
        this.number = number;
        this.extension = new Extension(extension);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }
}