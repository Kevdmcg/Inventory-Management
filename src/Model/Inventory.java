/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import View_Controller.MainScreenController;

/**
 *
 * @author kmcgh15
 */
public class Inventory {
       
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partIdCount = 2;
    private static int productIdCount = 201;
    
    public static void addPart(Part part) {
        allParts.add(part);
    }
    
     public static void addProduct(Product product) {
         allProducts.add(product);
         
     }
     
     public static int lookupPart(String searchTerm ) {
           boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allParts.get(i).getId()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allParts.size(); i++) {
                if (searchTerm.equals(allParts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }

        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
    } 
     
    public static int lookupProduct(String searchTerm) {
           boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allProducts.get(i).getId()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (searchTerm.equals(allProducts.get(i).getName())) {
                    index = i;
                    isFound = true;
                }
            }
        }

        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
         
    }

     public static int getPartIdNum() {
         partIdCount++;
         return partIdCount;
     }
     
     public static int getProductIdNum() {
        productIdCount++;
        return productIdCount;
    }
     
     public static void updatePart(int index, Part selectedPart) {
         allParts.set(index, selectedPart);
         
     }
     
     public static void updateProduct(int index, Product selectedProduct) {
         allProducts.set(index, selectedProduct);
         
     }
     
     public static void deletePart(Part selectedPart) {
         allParts.remove(selectedPart);
         
     }
     
     public static void deleteProduct(Product selectedProduct) {
         allProducts.remove(selectedProduct);
         
     }
     
     public static ObservableList<Part> getAllParts() {
         return allParts;
         
     }
     
     public static ObservableList<Product> getAllProducts() {
        return allProducts; 
     }

public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
        


        
    



     
