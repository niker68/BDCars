package Model;

import java.io.Serializable;

public class Car implements Serializable {
int id=0;
private String gosnomer;
private String model;
private String color;
private int year =0;

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public String getGosnomer() {
        return gosnomer;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }



    public Car() {
    }
    public Car(String json){

    }

    public Car(int id) {
        this.id = id;
    }

    public Car(int id, String gosnomer, String model, String color,int year) {
        this.id = id;
        this.gosnomer = gosnomer;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGosnomer(String gosnomer) {
        this.gosnomer = gosnomer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString(){
    return "{" +"\n"+
            "   \"id\""+" : \""+this.getId()+"\"\n"+
            "   \"gosnomer\""+" : \""+this.getGosnomer()+"\"\n"+
            "   \"model\""+" : \""+this.getModel()+"\"\n"+
            "   \"color\""+" : \""+this.getColor()+"\"\n"+
            "   \"year\""+" : \""+this.getYear()+"\"\n"+
            "}";
    }
}
