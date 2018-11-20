package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab1.*;

public class Controller
{
    @FXML
    private TextField filePathField;
    @FXML
    private Label textLoadSuccess;

    public DataFrame dataBase;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public Controller() {}

    @FXML
    private void initialize() {}

    @FXML
    private void LoadDataFrame()
    {
        Class<? extends Value>[] types = (Class<? extends Value>[]) new Class<?>[4];
        types[0] = StringValue.class;
        types[1] = DateTimeValue.class;
        types[2] = DoubleValue.class;
        types[3] = DoubleValue.class;
        dataBase = new DataFrame(filePathField.getText(),types);
        textLoadSuccess.visibleProperty().setValue(true);
    }
}
