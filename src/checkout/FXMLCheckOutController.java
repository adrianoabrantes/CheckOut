package checkout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author adrianoabrantesdeandrade
 */
public class FXMLCheckOutController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private ImageView imgParametro;
    @FXML
    private ImageView imgValidando;
    @FXML
    private Pane pnDisplay;
    @FXML
    private Label lblStatus;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setImgParametro(ImageView imgParametro) {
        this.imgParametro = imgParametro;
    }

    public void setImgValidando(ImageView imgValidando) {
        this.imgValidando = imgValidando;
    }
    
}
