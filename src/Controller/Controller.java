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
    private TextField typesField;
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
        String[] declaredTypes = typesField.getText().split(",");
        Class<? extends Value>[] types = (Class<? extends Value>[]) new Class<?>[4];
        for(int i=0;i<declaredTypes.length;i++)
        {
            if(declaredTypes[i].equals("int"))
                types[i] = IntegerValue.class;
            else if(declaredTypes[i].equals("float"))
                types[i] = FloatValue.class;
            else if(declaredTypes[i].equals("double"))
                types[i] = DoubleValue.class;
            else if(declaredTypes[i].equals("date"))
                types[i] = DateTimeValue.class;
            else if(declaredTypes[i].equals("string"))
                types[i] = StringValue.class;
            else
                throw new IllegalArgumentException("Unsupported type or formatting");
        }
        dataBase = new DataFrame(filePathField.getText(),types);
        textLoadSuccess.visibleProperty().setValue(true);
    }
}
