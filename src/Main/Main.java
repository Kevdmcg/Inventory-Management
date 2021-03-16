/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.InHousePart;
import Model.Inventory;
import Model.Part;
import Model.*;
import View_Controller.MainScreenController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kmcgh15
 */
public class Main extends Application {
    
    //addPart(Part(1, "Leg", 10.10, 12, 5, 10));
    
    // InHousePart demo = new InHousePart(1, "Leg", 10.10, 10, 5, 12);
    
     /*   private static ObservableList<Parts> allparts  =
            FXCollections.observableArrayList(
            new Part(12, "Leg", 10.10, 12, 5, 15) {},
            new Part(12, "Leg", 10.10, 12, 5, 15) {},
            new Part(12, "Leg", 10.10, 12, 5, 15) {},
            new Part(12, "Leg", 10.10, 12, 5, 15) {},
            new Part(12, "Leg", 10.10, 12, 5, 15) {},);
    */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InHousePart part1 = new InHousePart(1,"Leg", 4.10, 4, 12, 12, 101);
        InHousePart part2 = new InHousePart(2,"Arm", 3.10, 4, 12, 5, 102);
        Product product1 = new Product(201, "Wizard", 15.20, 4, 15, 25);
        Product product2 = new Product(201, "Werewolf", 15.20, 4, 15, 25);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        
        
        
        launch(args);
        
       // Inventory.addPart(new InHousePart(1, "Leg", 12.10, 10, 5, 12));
        
        
    }
    
}
