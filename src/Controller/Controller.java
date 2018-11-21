package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    @FXML
    private TextField textGroupbyField;
    @FXML
    private TextArea resultOfOperationText;

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

    @FXML
    private void MaximumOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.max().asString());
    }

    @FXML
    private void MinimumOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.min().asString());
    }

    @FXML
    private void SumOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.sum().asString());
    }

    @FXML
    private void MeanOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.mean().asString());
    }

    @FXML
    private void VarianceOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.min().asString());
    }

    @FXML
    private void DeviationOfGroup()
    {
        String[] colNames = textGroupbyField.getText().split(",");
        GroupWrapper group = dataBase.groupby(colNames);
        resultOfOperationText.setText(group.min().asString());
    }
}
