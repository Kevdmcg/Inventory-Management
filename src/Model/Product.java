/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kmcgh15
 */
public class Product {
    
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(int id, String name, double price, int stock, int min, int max) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
      this.min = min;
      this.max = max;
    }
    
    public Product() {
        
    }

    public ObservableList<Part> getParts() {
        return parts;
    }

    public void setParts(ObservableList<Part> parts) {
        this.parts = parts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addPart(Part part) {
        
    }
    
    public void deletePart(Part parts) {
        
    }
    

    
    
    
}
