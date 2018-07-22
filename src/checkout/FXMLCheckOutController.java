package checkout;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author adrianoabrantesdeandrade
 */
public class FXMLCheckOutController implements Initializable {

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
    @FXML
    private Pane pnAtivacao;
    @FXML
    private TextField txtChave;
    @FXML
    private Button btnValidar;
    @FXML
    private Label lblStatusLicenca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compararEtiquetas("qrCode.png", "qrCode.png");
        identificarHardware();
        validarLicenca();
    }

    private String setImgParametro(String local) {
        try {
            FileInputStream inputStream = new FileInputStream(local);
            imgParametro.setImage(new Image(inputStream));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return local;
    }

    private String setImgValidando(String local) {
        try {
            FileInputStream inputStream = new FileInputStream(local);
            imgValidando.setImage(new Image(inputStream));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return local;
    }

    public ImageView getImgParametro() {
        return imgParametro;
    }

    public ImageView getImgValidando() {
        return imgValidando;
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

    private String lerEtiqueta(String nome) {
        String resultado = "";
        try {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(nome)))));
            Result codificador = new MultiFormatReader().decode(bitmap);
            resultado = codificador.getText();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NotFoundException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setImgValidando(nome);
        return resultado;
    }

    private void compararEtiquetas(String parametro, String validando) {
        if (lerEtiqueta(setImgParametro(parametro)).equals(lerEtiqueta(setImgValidando(validando)))) {
            mudarDisplay(true);
        } else {
            mudarDisplay(false);
        }
    }

    private String identificarHardware() {
        String macAdress = "";

        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                macAdress += String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : "");
            }

        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(FXMLCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return macAdress;
    }

    @FXML
    private void eventoValidarChave(ActionEvent event) {
        if (txtChave.getText().isEmpty()) {
            pnPrincipal.setVisible(true);
        }
    }

    static boolean control = false;
    static boolean r = false;

    @FXML
    private void keyPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) {
            control = true;
        }
        if (event.getCode() == KeyCode.R) {
            r = true;
            txtChave.setText("");
        }

        if (control && r) {
            pnPrincipal.setVisible(false);
        }
        if (!txtChave.getText().isEmpty()) {
            btnValidar.setText("Validar");
        } else {
            btnValidar.setText("Cancelar");

        }
    }

    private void validarLicenca() {
        String endrecoMac = identificarHardware();
        int[] licenca = new int[6];
        int a = 0;
        int b = 2;

        for (int i = 0; i < licenca.length; i++) {
            licenca[i] = Integer.parseInt(endrecoMac.substring(a, b), 16);
            a += 3;
            b += 3;
        }

        String key = "";

        for (int i : licenca) {
            key += i;
        }

        if (!txtChave.getText().equals(key)) {
           // pnPrincipal.setVisible(false);
        }
    }
}
