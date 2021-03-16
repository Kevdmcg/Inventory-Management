/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHousePart;
import Model.Part;
import Model.Inventory;
import View_Controller.MainScreenController;
import static Model.Inventory.getAllParts;
import Model.OutsourcedPart;
import static View_Controller.MainScreenController.getModPartIndex;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kmcgh15
 */
public class ModifyPartController implements Initializable {
    private boolean isOutsourced;
    int partIndex = getModPartIndex();
    private int partID;
    
    
    @FXML
    private RadioButton radModPartInHouse;

    @FXML
    private RadioButton radModPartOutsource;

    @FXML
    private Button buttonModPartSave;

    @FXML
    private Button buttonModPartCancel;
    @FXML
    private Label labelModPartId;
    @FXML
    private Label labelModPartCompany;
    @FXML
    private TextField txtModPartId;
    @FXML
    private TextField txtModPartName;
    @FXML
    private TextField txtModPartInv;
    @FXML
    private TextField txtModPartPrice;
    @FXML
    private TextField txtModPartMax;
    @FXML
    private TextField txtModPartMin;
    @FXML
    private TextField txtModPartCompany;

    @FXML
    void cfModPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Please confirm you are canceling modify part.");
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
    void cfModPartInHouse(ActionEvent event) {
        isOutsourced = false;
        radModPartOutsource.setSelected(false);
        labelModPartCompany.setText("Machine ID");
        txtModPartCompany.setPromptText("Machine ID");
        
    }

    @FXML
    void cfModPartOutsource(ActionEvent event) {
        isOutsourced = true;
        labelModPartCompany.setText("Company");
        txtModPartCompany.setPromptText("Company Name");
        radModPartInHouse.setSelected(false);

    }

    @FXML
    void cfModPartSave(ActionEvent event) throws IOException {
        String modPartName = txtModPartName.getText();
        String modPartInv = txtModPartInv.getText();
        String modPartPrice = txtModPartPrice.getText();
        String modPartMin = txtModPartMin.getText();
        String modPartMax = txtModPartMax.getText();
        String modPartCompany = txtModPartCompany.getText();
        int maxCheck = Integer.parseInt(modPartMax);
        int minCheck = Integer.parseInt(modPartMin);
        
        if (maxCheck < minCheck) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error Saving Part");
            alert.setContentText("The Maximum field cannot be smaller than the Minumum field.");
            alert.showAndWait();
        }
        else {
        
        if (isOutsourced == false) {
            InHousePart inPart = new InHousePart();
            inPart.setid(partID);
            inPart.setName(modPartName);
            inPart.setStock(Integer.parseInt(modPartInv));
            inPart.setPrice(Double.parseDouble(modPartPrice));
            inPart.setMin(Integer.parseInt(modPartMin));
            inPart.setMax(Integer.parseInt(modPartMax));
            inPart.setMachineId(Integer.parseInt(modPartCompany));
            Inventory.updatePart(partIndex, inPart);
        }
        else {
            OutsourcedPart outPart = new OutsourcedPart();
            outPart.setid(partID);
            outPart.setName(modPartName);
            outPart.setStock(Integer.parseInt(modPartInv));
            outPart.setPrice(Double.parseDouble(modPartPrice));
            outPart.setMin(Integer.parseInt(modPartMin));
            outPart.setMax(Integer.parseInt(modPartMax));
            outPart.setCompanyName(modPartCompany);
            Inventory.updatePart(partIndex, outPart);
        }
        Parent modProdParent = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Scene modProdScene = new Scene(modProdParent);
        Stage modProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modProdStage.setScene(modProdScene);
        modProdStage.show();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part;
        part = Inventory.getAllParts().get(partIndex);
        partID = getAllParts().get(partIndex).getId();
        txtModPartId.setText("Auto-Gen: " + partID);
        txtModPartName.setText(part.getName());
        txtModPartInv.setText(Integer.toString(part.getStock()));
        txtModPartPrice.setText(Double.toString(part.getPrice()));
        txtModPartMin.setText(Integer.toString(part.getMin()));
        txtModPartMax.setText(Integer.toString(part.getMax()));
        txtModPartId.setEditable(false);
        if (part instanceof InHousePart) {
            labelModPartCompany.setText("Machine ID");
            txtModPartCompany.setText(Integer.toString(((InHousePart) getAllParts().get(partIndex)).getMachineId()));
            radModPartInHouse.setSelected(true);
            isOutsourced = false;
        }
        else {
            labelModPartCompany.setText("Company Name");
            txtModPartCompany.setText(((OutsourcedPart) getAllParts().get(partIndex)).getCompanyName());
            radModPartOutsource.setSelected(true);
            isOutsourced = true;

        }
        // TODO
    } 
}





    

