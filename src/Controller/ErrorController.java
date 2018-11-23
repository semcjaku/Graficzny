package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController {
    @FXML
    private Label errorMessage ;
    @FXML
    private Label errorStack ;

    public void setErrorText(String text) {
        errorMessage.setText(text);
    }

    public void setErrorStackTrace(String text) {
        errorStack.setText(text);
    }

    @FXML
    private void close() {
        errorMessage.getScene().getWindow().hide();
    }
}
