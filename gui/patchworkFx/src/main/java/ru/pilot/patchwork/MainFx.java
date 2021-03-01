package ru.pilot.patchwork;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    private static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL sizeFrm = Thread.currentThread().getContextClassLoader().getResource("form/start.fxml");
        Parent root = FXMLLoader.load(sizeFrm);
        primaryStage.setTitle("Patchwork");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        setPrimaryStage(primaryStage);
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        MainFx.pStage = pStage;
    }
}
