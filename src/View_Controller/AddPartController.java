/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author kmcgh15
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton radAddPartInHouse;
    @FXML
    private RadioButton radAddPartOutsource;
    @FXML
    private Button buttonAddPartSave;
    @FXML
    private Button buttonAddpartCancel;
    @FXML
    private TextField txtPartName;
    @FXML
    private TextField txtInv;
    @FXML
    private TextField txtPartPrice;
    @FXML
    private TextField txtPartMax;
    @FXML
    private TextField txtPartMin;
    @FXML
    private TextField txtPartRadio;
    @FXML
    private Label labelAddRadio;
    @FXML
    private TextField txtAddPartId;
    
    private boolean isOutsourced;
    private int partId;
           

    @FXML
    private void cfAddPartInHouse(ActionEvent event) {
        isOutsourced = false;
        labelAddRadio.setText("Machine ID");
        txtPartRadio.setPromptText("Machine ID");
        radAddPartOutsource.setSelected(false);
    }

    @FXML
    private void cfAddPartOutsource(ActionEvent event) {
        isOutsourced = true;
        labelAddRadio.setText("Company Name");
        txtPartRadio.setPromptText("Company Name");
        radAddPartInHouse.setSelected(false);
    }

    @FXML
    private void cfAddPartSave(ActionEvent event) throws IOException {
        int pID = partId;
        String partName = txtPartName.getText();
        String partInventory = txtInv.getText();
        String partPrice = txtPartPrice.getText();
        String partMax = txtPartMax.getText();
        String partMin = txtPartMin.getText();
        String partRadio = txtPartRadio.getText();
        int maxCheck = Integer.parseInt(partMax);
        int minCheck = Integer.parseInt(partMin);
        
        if (maxCheck < minCheck) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error Saving Part");
            alert.setContentText("The Maximum field cannot be smaller than the Minumum field.");
            alert.showAndWait();
        }
        else {
        
        if (isOutsourced == false) {
                    System.out.println("Part name: " + partName);
                    InHousePart inPart = new InHousePart();
                    inPart.setid(pID);
                    inPart.setName(partName);
                    inPart.setPrice(Double.parseDouble(partPrice));
                    inPart.setStock(Integer.parseInt(partInventory));
                    inPart.setMin(Integer.parseInt(partMin));
                    inPart.setMax(Integer.parseInt(partMax));
                    inPart.setMachineId(Integer.parseInt(partRadio));
                    Inventory.addPart(inPart);
            } 
        else {
                    System.out.println("Part name: " + partName);
                    OutsourcedPart outPart = new OutsourcedPart();
                    outPart.setid(partId);
                    outPart.setName(partName);
                    outPart.setPrice(Double.parseDouble(partPrice));
                    outPart.setStock(Integer.parseInt(partInventory));
                    outPart.setMin(Integer.parseInt(partMin));
                    outPart.setMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partRadio);
                    Inventory.addPart(outPart);
                    
           }
        Parent modProdParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        Stage modProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modProdStage.setScene(modProdScene);
        modProdStage.show();
        }
        
        }
    @FXML
    private void cfAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Please confirm you are canceling add part.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
        Parent cancelAddPartParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene cancelAddPartScene = new Scene(cancelAddPartParent);
        
        Stage cancelAddPartStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        cancelAddPartStage.setScene(cancelAddPartScene);
        cancelAddPartStage.show();
        }
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        partId = Inventory.getPartIdNum();
        txtAddPartId.setText("Auto-Gen : " + partId);
        txtAddPartId.setEditable(false);
    } 
}
