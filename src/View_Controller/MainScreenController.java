/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author kmcgh15
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button buttonMainSearchPart;
    @FXML
    private Button buttonMainAddPart;
    @FXML
    private Button buttonMainModPart;
    @FXML
    private Button buttonMainDelPart;
    @FXML
    private Button buttonMainSearchProd;
    @FXML
    private Button buttonMainAddProd;
    @FXML
    private Button buttonMainModProd;
    @FXML
    private Button buttonMainDelProd;
    @FXML
    private Button buttonMainExit;
    @FXML
    private TableView<Part> tableViewMainParts;
    @FXML
    private TableColumn<Part,Integer> columnPartPartID;
    @FXML
    private TableColumn<Part, String> columnPartPartName;
    @FXML
    private TableColumn<Part, Integer> columnPartInventory;
    @FXML
    private TableColumn<Part, Double> columnPartPrice;
    @FXML
    private TableView<Product> tableViewMainProds;
    @FXML
    private TableColumn<Product, Integer> columnProdPartID;
    @FXML
    private TableColumn<Product, String> columnProdPartName;
    @FXML
    private TableColumn<Product, Integer> columnProdInventory;
    @FXML
    private TableColumn<Product, Double> columnProdPrice;
    @FXML
    private TextField txtSearchPart;
    @FXML
    private TextField txtSearchProd;
    
    private static Part modPart;
    private static int modPartIndex;
    private static Product modProd;
    private static int modProdIndex;
    
    public static int getModPartIndex() {
        return modPartIndex;
    }
    
    public static int getModProdIndex() {
        return modProdIndex;
    }

    
    
    /**
     *
     */
    public void updateTableViewParts() {
        tableViewMainParts.setItems(getAllParts());
    }
    
    public void updateTableViewProds() {
        tableViewMainProds.setItems(Inventory.getAllProducts());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewMainParts.setItems(Inventory.getAllParts());
        tableViewMainProds.setItems(Inventory.getAllProducts());
        
        columnPartPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnProdPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProdInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateTableViewParts();
        updateTableViewProds();
        // TODO
    }    
    
    
    // Part side of the screen
    
    
    @FXML
    private void cfMainSearchPart(ActionEvent event) {
        String searchPart = txtSearchPart.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("There are no matching parts");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }
        else {
            partIndex = Inventory.lookupPart(searchPart);
            //Part tempPart = Inventory.getAllParts().get(partIndex);
            tableViewMainParts.requestFocus();
            tableViewMainParts.getSelectionModel().select(partIndex);
            tableViewMainParts.getSelectionModel().focus(partIndex);
        }
    }
    @FXML
    private void cfMainAddPart(ActionEvent event) throws IOException {
        
        Parent addPartParent = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        
        Stage addPartStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    @FXML
    private void cfMainModPart(ActionEvent event) throws IOException {
        modPart = tableViewMainParts.getSelectionModel().getSelectedItem();
        modPartIndex = getAllParts().indexOf(modPart);
    
        Parent modPartParent = FXMLLoader.load(getClass().getResource("modifyPart.fxml"));
        Scene modPartScene = new Scene(modPartParent);
        
        Stage modPartStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        modPartStage.setScene(modPartScene);
        modPartStage.show();
    }
    @FXML
    private void cfMainDelPart(ActionEvent event) {
            Part part = tableViewMainParts.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deletePart(part);
                updateTableViewParts();
            }
            
    }

    // Product Side Items

    @FXML
    private void cfMainSearchProd(ActionEvent event) {
            String searchProduct = txtSearchProd.getText();
        int prodIndex = -1;
        if (Inventory.lookupProduct(searchProduct) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search term entered does not match any known products.");
            alert.showAndWait();
        }
        else {
            prodIndex = Inventory.lookupProduct(searchProduct);
            //Product tempProduct = Inventory.getAllProducts().get(prodIndex);
            tableViewMainProds.requestFocus();
            tableViewMainProds.getSelectionModel().select(prodIndex);
            tableViewMainProds.getSelectionModel().focus(prodIndex);
            
          
            
        }
    }
    
    @FXML
    private void cfMainAddProd(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    @FXML
    private void cfMainModProd(ActionEvent event) throws IOException {
        modProd = tableViewMainProds.getSelectionModel().getSelectedItem();
        modProdIndex = getAllProducts().indexOf(modProd);
        
        Parent modProdParent = FXMLLoader.load(getClass().getResource("modProduct.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        
        Stage modProdStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        modProdStage.setScene(modProdScene);
        modProdStage.show();
    }

    @FXML
    private void cfMainDelProd(ActionEvent event) {
        
            Product product = tableViewMainProds.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + product.getName() + " product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deleteProduct(product);
                updateTableViewProds();
            }
    }

    // Exit Button
    @FXML
    private void cfMainExit(ActionEvent event) {
        System.exit(0);
    }
    
}
