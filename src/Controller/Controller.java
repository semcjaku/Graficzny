package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import lab1.*;

import static MainApp.MainAppLauncher.showErrorDialog;
import static javafx.geometry.Side.BOTTOM;
import static javafx.geometry.Side.LEFT;

public class Controller
{
    //load tab
    @FXML
    private TextField filePathField;
    @FXML
    private TextField typesField;
    @FXML
    private Label textLoadSuccess;

    //lazy load tab
    @FXML
    private Label showingSign;
    @FXML
    private ListView<String> DFDisplayList;

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

    //bar chart tab
    @FXML
    private TextField textXAxisB;
    @FXML
    private TextField textYAxisB;
    @FXML
    private BarChart barChart;
    @FXML
    private CheckBox checkHorizontal;
    @FXML
    private CheckBox checkStacked;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;


    public DataFrame dataBase; //the df itself
    public boolean clearDiagram; //line chart tab
    private ObservableList<String> listItems; //first records to display
    private ObservableList<String> bigData; //remaining records to display
    private int lazyStep = 100; //step of lazy load
    private int start=0,step=lazyStep-1; //scrolling controls for lazy load

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
            Column xAxisCol = dataBase.Get(xAxisColName);
            Column yAxisCol = dataBase.Get(yAxisColName);
            for(int i=0;i<dataBase.Size();i++)
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

    @FXML
    private void DisplayData()
    {
        showingSign.setText("Showing 0-"+(lazyStep-1)+"/"+dataBase.Size());
        listItems = FXCollections.observableArrayList();
        for(int i=0;i<lazyStep;i++)
            listItems.add(dataBase.RowAsString(i));
        DFDisplayList.setItems(listItems);

        PopulateBigData();

        ScrollBar listViewScrollBar = getListViewScrollBar(DFDisplayList);
        listViewScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            double position = newValue.doubleValue();
            ScrollBar scrollBar = getListViewScrollBar(DFDisplayList);
            if (position == scrollBar.getMax()) {
                if (step+lazyStep > bigData.size()-1)
                {
                    listItems.addAll(bigData.subList(start, bigData.size()-1));
                    start = step;
                    step = bigData.size()-1;
                    showingSign.setText("Showing Everything ("+dataBase.Size()+" records)");
                }
                 else if (lazyStep <= bigData.size()) {
                    listItems.addAll(bigData.subList(start, step));
                    start = step;
                    step += lazyStep;
                    showingSign.setText("Showing 0-"+step+"/"+dataBase.Size());
                }
                listViewScrollBar.valueProperty().setValue((double)start/(double)step);
            }
        });
    }

    private void PopulateBigData() {
        bigData = FXCollections.observableArrayList();
        for(int i=lazyStep;i<dataBase.Size();i++)
            bigData.add(dataBase.RowAsString(i));
    }

    private ScrollBar getListViewScrollBar(ListView<?> listView) {
        ScrollBar scrollbar = null;
        for (Node node : listView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) node;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    scrollbar = bar;
                }
            }
        }
        return scrollbar;
    }

    @FXML
    private void AddScatterPlot()
    {
        try{
            String xAxisColName = textXAxisS.getText();
            String yAxisColName = textYAxisS.getText();
            XYChart.Series<Number,Number> series = new XYChart.Series<>();
            series.setName(yAxisColName+" dependent on "+xAxisColName);
            Column xAxisCol = dataBase.Get(xAxisColName);
            Column yAxisCol = dataBase.Get(yAxisColName);
            for(int i=0;i<dataBase.Size();i++)
                series.getData().add(new XYChart.Data<>((Number)xAxisCol.col.get(i).Get(),(Number)yAxisCol.col.get(i).Get()));
            scatterChart.getData().add(series);
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void RemoveScatterPlot()
    {
        try{
            scatterChart.getData().remove(scatterChart.getData().size()-1);
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @FXML
    private void AddBarSeries()
    {
        if(checkHorizontal.isSelected())
        {
            barChart.getXAxis().sideProperty().setValue(LEFT);
            barChart.getYAxis().sideProperty().setValue(BOTTOM);
        }

        barChart.getXAxis().setLabel(textXAxisB.getText());
        barChart.getYAxis().setLabel(textYAxisB.getText());

        String xAxisColName = textXAxisB.getText();
        String yAxisColName = textYAxisB.getText();
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("napis");
        Column xAxisCol = dataBase.Iloc(0,99).Get(xAxisColName);
        Column yAxisCol = dataBase.Iloc(0,99).Get(yAxisColName);
        for(int i=0;i<dataBase.Iloc(0,99).Size();i++)
            series.getData().add(new XYChart.Data<>(xAxisCol.col.get(i).toString(),(Number)yAxisCol.col.get(i).Get()));
        barChart.getData().add(series);
    }
}
