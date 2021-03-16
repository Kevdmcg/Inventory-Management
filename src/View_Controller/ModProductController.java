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
public class ModProductController implements Initializable {
    
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();
    int prodIndex = getModProdIndex();
    private int prodId;
    
     @FXML
    private Button buttonModProdSave;

    @FXML
    private Button buttonModProdCancel;

    @FXML
    private Button buttonModProdAdd;

    @FXML
    private Button buttonModProdDelete;

    @FXML
    private Button buttonModProdSearch;
    @FXML
    private TextField textFieldModProdId;
    @FXML
    private TextField textFieldModProdName;
    @FXML
    private TextField textFieldModProdInv;
    @FXML
    private TextField textFieldModProdPrice;
    @FXML
    private TextField textFieldModProdMax;
    @FXML
    private TextField textFieldModProdMin;
    @FXML
    private TableView<Part> tableViewModProdAdd;
    @FXML
    private TableColumn<?, ?> columnModProdAddId;
    @FXML
    private TableColumn<?, ?> columnModProdAddName;
    @FXML
    private TableColumn<?, ?> columnModProdAddInv;
    @FXML
    private TableColumn<?, ?> columnModProdAddPrice;
    @FXML
    private TableView<Part> tableViewModProdDel;
    @FXML
    private TableColumn<?, ?> columnModProdDelId;
    @FXML
    private TableColumn<?, ?> columnModProdDelName;
    @FXML
    private TableColumn<?, ?> columnModProdDelInv;
    @FXML
    private TableColumn<?, ?> columnModProdDelPrice;
    @FXML
    private TextField textFieldModProdSearch;

    @FXML
    void cfAddModSave(ActionEvent event) throws IOException {
        String prodName = textFieldModProdName.getText();
        String prodInv = textFieldModProdInv.getText();
        String prodPrice = textFieldModProdPrice.getText();
        String prodMin = textFieldModProdMin.getText();
        String prodMax = textFieldModProdMax.getText();
        int maxCheck = Integer.parseInt(prodMax);
        int minCheck = Integer.parseInt(prodMin);
        
        if (maxCheck < minCheck) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error Saving Part");
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
        Inventory.updateProduct(prodIndex, newProduct);
        
        Parent modProdParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        Stage modProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modProdStage.setScene(modProdScene);
        modProdStage.show();
        }
        

    }

    @FXML
    void cfModProdAdd(ActionEvent event) {
        Part part = tableViewModProdAdd.getSelectionModel().getSelectedItem();
        thisProductParts.add(part);
        updateDeletePartsTableView();

    }

    @FXML
    void cfModProdCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Please confirm you are canceling modify product.");
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
    void cfModProdDelete(ActionEvent event) {
        Part part = tableViewModProdDel.getSelectionModel().getSelectedItem();
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
    void cfModProdSearch(ActionEvent event) {
         String searchPart = textFieldModProdSearch.getText();
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
            tableViewModProdAdd.requestFocus();
            tableViewModProdAdd.getSelectionModel().select(partIndex);
            tableViewModProdAdd.getSelectionModel().focus(partIndex);
        }

    }

    @Override
      public void initialize(URL url, ResourceBundle rb) {
        Product thisProduct;
        thisProduct = Inventory.getAllProducts().get(prodIndex);
        prodId = getAllProducts().get(prodIndex).getId();
        textFieldModProdId.setText("Auto-Gen: " + prodId);
        textFieldModProdName.setText(thisProduct.getName());
        textFieldModProdInv.setText(Integer.toString(thisProduct.getStock()));
        textFieldModProdPrice.setText(Double.toString(thisProduct.getPrice()));
        textFieldModProdMin.setText(Integer.toString(thisProduct.getMin()));
        textFieldModProdMax.setText(Integer.toString(thisProduct.getMax()));
        textFieldModProdId.setEditable(false);
        
        thisProductParts = thisProduct.getParts();
          
          
        //tableViewModProdDel.setItems(thisProductParts);  
        tableViewModProdAdd.setItems(Inventory.getAllParts());
        
        columnModProdAddId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnModProdAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnModProdAddInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnModProdAddPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        columnModProdDelId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnModProdDelName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnModProdDelInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnModProdDelPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        updateAddPartsTableView();
        updateDeletePartsTableView();
        
        
        // TODO
    } 
    public void updateAddPartsTableView() {
       tableViewModProdAdd.setItems(getAllParts());
    }
    
    public void updateDeletePartsTableView() {
        tableViewModProdDel.setItems(thisProductParts);
    }
    

}



