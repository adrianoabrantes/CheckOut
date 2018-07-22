package checkout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author adrianoabrantesdeandrade
 */
public class FXMLCheckOutController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label lblStatus;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private Pane pnDisplay;
    @FXML
    private ImageView imgParametro;
    @FXML
    private ImageView imgValidando;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mudarDisplay(false);
    }

    private void setImgParametro(String local) {
        try {
            FileInputStream inputStream = new FileInputStream(local);
            imgParametro.setImage(new Image(inputStream));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setImgValidando(String local) {
        try {
            FileInputStream inputStream = new FileInputStream(local);
            imgValidando.setImage(new Image(inputStream));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void mudarDisplay(boolean mudar) {
        String verde = "-fx-background-color:#00FF00";
        String vermelho = "-fx-background-color:#FF0000";
        DropShadow brilho = new DropShadow();
        brilho.setHeight(194.0);
        brilho.setWidth(194.0);
        lblStatus.setTextFill(Color.WHITE);

        if (mudar) {
            brilho.setSpread(0.60);
            brilho.setColor(Color.GREEN);
            pnDisplay.setEffect(brilho);
            pnDisplay.setStyle(verde);
            lblStatus.setLayoutX(130);
            lblStatus.setText("QR CODE OK");
        } else {
            brilho.setSpread(0.40);
            brilho.setColor(Color.RED);
            pnDisplay.setEffect(brilho);
            pnDisplay.setStyle(vermelho);
            lblStatus.setLayoutX(120);
            lblStatus.setText("INCOMPATIVEL");
        }
    }
}
