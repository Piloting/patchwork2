package ru.pilot.patchwork.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartController extends ParentController {

    @FXML
    void proceedButtonPress(ActionEvent event) {

    }

    @FXML
    void newButtonPress(ActionEvent event) {

    }

    @FXML
    void libButtonPress(ActionEvent event) {

    }

    @FXML
    void simplesButtonPress(ActionEvent event) {
        openForm("simples.fxml", "Примеры");
    }

}
