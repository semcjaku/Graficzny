package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lab1.*;

import static MainApp.MainAppLauncher.showErrorDialog;

public class Controller
{
    //load tab
    @FXML
    private TextField filePathField;
    @FXML
    private TextField typesField;
    @FXML
    private Label textLoadSuccess;

    //compute tab
    @FXML
    private TextField textGroupbyField;
    @FXML
    private TextArea resultOfOperationText;

    //line chart tab
    @FXML
    private TextField textXAxis;
    @FXML
    private TextField textYAxis;
    @FXML
    private LineChart<Number,Number> diagram;
    @FXML
    private CheckBox checkUpdateDiagram;
    @FXML
    private CheckBox checkNewDiagram;

    //scatter plot tab
    @FXML
    private TextField textXAxisS;
    @FXML
    private TextField textYAxisS;
    @FXML
    private ScatterChart<Number,Number> scatterChart;


    public DataFrame dataBase; //the df itself
    public boolean clearDiagram; //line chart tab

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
        try{
            String[] declaredTypes = typesField.getText().split(",");
            Class<? extends Value>[] types = (Class<? extends Value>[]) new Class<?>[declaredTypes.length];
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
        } catch (Exception e) {
            showErrorDialog(e);
        }

    }

    @FXML
    private void MaximumOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.max().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }

    }

    @FXML
    private void MinimumOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.min().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void SumOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.sum().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void MeanOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.mean().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void VarianceOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.var().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void DeviationOfGroup()
    {
        try{
            String[] colNames = textGroupbyField.getText().split(",");
            GroupWrapper group = dataBase.groupby(colNames);
            resultOfOperationText.setText(group.std().asString());
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void CreateDiagram()
    {
        if(!checkUpdateDiagram.isSelected() && !checkNewDiagram.isSelected())
        {
            checkNewDiagram.selectedProperty().setValue(true);
            clearDiagram = true;
        }
        if(clearDiagram)
            diagram.getData().clear();

        try{
            String xAxisColName = textXAxis.getText();
            String yAxisColName = textYAxis.getText();
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(yAxisColName+" dependent on "+xAxisColName);
            Column xAxisCol = dataBase.Iloc(0,9).Get(xAxisColName);
            Column yAxisCol = dataBase.Iloc(0,9).Get(yAxisColName);
            for(int i=0;i<dataBase.Iloc(0,9).Size();i++)
                series.getData().add(new XYChart.Data<>((Number)xAxisCol.col.get(i).Get(),(Number)yAxisCol.col.get(i).Get()));
            diagram.getData().add(series);
            diagram.visibleProperty().setValue(true);
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void ClearDiagramOn()
    {
        checkUpdateDiagram.selectedProperty().setValue(false);
        clearDiagram = true;
    }

    @FXML
    private void ClearDiagramOff()
    {
        checkNewDiagram.selectedProperty().setValue(false);
        clearDiagram = false;
    }
}
