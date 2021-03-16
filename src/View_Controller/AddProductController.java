/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import View_Controller.MainScreenController;
import static View_Controller.MainScreenController.getModProdIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kmcgh15
 */
public class AddProductController implements Initializable {
    
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();
    int prodIndex = getModProdIndex();
    private int prodId;

    @FXML
    private Button buttonAddProdSave;
    @FXML
    private Button buttonAddProdCancel;
    @FXML
    private Button buttonAddProdAdd;
    @FXML
    private Button buttonAddProdDelete;
    @FXML
    private Button buttonAddProdSearch;
    @FXML
    private TableColumn<Part, Integer> columnAddProdAddId;
    @FXML
    private TableColumn<Part, String> columnAddProdAddName;
    @FXML
    private TableColumn<Part, Integer> columnAddProdAddInv;
    @FXML
    private TableColumn<Part, Double> columnAddProdAddPrice;
    @FXML
    private TableColumn<Part, Integer> columnAddProdDelId;
    @FXML
    private TableColumn<Part, String> columnAddProdDelName;
    @FXML
    private TableColumn<Part, Integer> columnAddProdDelInv;
    @FXML
    private TableColumn<Part, Double> columnAddProdDelPrice;
    @FXML
    private TableView<Part> tableViewAddProdAdd;
    @FXML
    private TableView<Part> tableViewAddProdDel;
    @FXML
    private TextField textFieldAddProdID;
    @FXML
    private TextField textFieldAddProdName;
    @FXML
    private TextField textFieldAddProdInv;
    @FXML
    private TextField textFieldAddProdPrice;
    @FXML
    private TextField textFieldAddProdMax;
    @FXML
    private TextField textFieldAddProdMin;
    @FXML
    private TextField textFieldAddProdSearch;

    /**
     * Initializes the controller class.
     */
   


    @FXML
    private void cfAddProdCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Please confirm you are canceling add product.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
        Parent cancelAddParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene cancelAddScene = new Scene(cancelAddParent);
        
        Stage cancelAddStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        cancelAddStage.setScene(cancelAddScene);
        cancelAddStage.show();
    }
    }

    @FXML
    private void cfAddProdAdd(ActionEvent event) throws IOException {
        Part part = tableViewAddProdAdd.getSelectionModel().getSelectedItem();
        thisProductParts.add(part);
        updateDeletePartsTableView();
    }

    @FXML
    private void cfAddProdDelete(ActionEvent event) {
        Part part = tableViewAddProdDel.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Please Confirm " + part.getName() + " will be removed from this product.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisProductParts.remove(part);
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }

    @FXML
    private void cfAddProdSearch(ActionEvent event) {
         String searchPart = textFieldAddProdSearch.getText();
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
            tableViewAddProdAdd.requestFocus();
            tableViewAddProdAdd.getSelectionModel().select(partIndex);
            tableViewAddProdAdd.getSelectionModel().focus(partIndex);
        }
    }

    @FXML
    private void cfAddProdSave(ActionEvent event) throws IOException {
        String prodName = textFieldAddProdName.getText();
        String prodInv = textFieldAddProdInv.getText();
        String prodPrice = textFieldAddProdPrice.getText();
        String prodMin = textFieldAddProdMin.getText();
        String prodMax = textFieldAddProdMax.getText();
        int maxCheck = Integer.parseInt(prodMax);
        int minCheck = Integer.parseInt(prodMin);
        
        if (maxCheck < minCheck) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error Saving Product");
            alert.setContentText("The Maximum field cannot be smaller than the Minumum field.");
            alert.showAndWait();
        }
        else {
        
        Product newProduct = new Product();
        newProduct.setId(prodId);
        newProduct.setName(prodName);
        newProduct.setPrice(Double.parseDouble(prodPrice));
        newProduct.setMax(Integer.parseInt(prodMax));
        newProduct.setMin(Integer.parseInt(prodMin));
        newProduct.setStock(Integer.parseInt(prodInv));
        newProduct.setParts(thisProductParts);
        Inventory.addProduct(newProduct);
        
        Parent modProdParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        Stage modProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modProdStage.setScene(modProdScene);
        modProdStage.show();
        }
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          
          
        //tableViewAddProdAdd.setItems(Inventory.getAllParts());
        tableViewAddProdAdd.setItems(getAllParts());

        columnAddProdAddId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAddProdAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddProdAddInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnAddProdAddPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                
        columnAddProdDelId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAddProdDelName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddProdDelInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnAddProdDelPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        updateAddPartsTableView();
        updateDeletePartsTableView();
        
        prodId = Inventory.getProductIdNum();
        textFieldAddProdID.setText("Auto-Gen: " + prodId);
        
    }
    public void updateAddPartsTableView() {
       tableViewAddProdAdd.setItems(getAllParts());
    }
    
    public void updateDeletePartsTableView() {
        tableViewAddProdDel.setItems(thisProductParts);
    }
    
}
