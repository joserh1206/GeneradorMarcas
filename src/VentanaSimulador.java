import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaSimulador implements Initializable{

    @FXML private TreeTableView<ResultSimulation> tvSimulator;

    @FXML private TreeTableColumn<ResultSimulation, String> tcNombre;

    @FXML private TreeTableColumn<ResultSimulation, String> tcFecha;

    @FXML private TreeTableColumn<ResultSimulation, String> tcIngreso;

    @FXML private TreeTableColumn<ResultSimulation, String> tcSalida;

    @FXML private JFXDatePicker iniDate;

    @FXML private JFXDatePicker endDate;

    @FXML private JFXButton btnIniciar;

    @FXML private JFXRadioButton rbPrueba;

    @FXML private JFXRadioButton rbFinal;

    @FXML private JFXProgressBar pbSimulation;

    @FXML private Label lblAbsent;

    @FXML private Label lblOmision;

    @FXML private Label lbltardia;

    TreeItem<ResultSimulation> root = new TreeItem<>(new ResultSimulation("Nombre", "Fecha",
            "Hora Ingreso", "Hora Salida"));


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//            Simulator simulator = new Simulator("2017-10-25", "2017-10-30");
//            List<TreeItem> listResult = simulator.iterateDates();
//            for(TreeItem item:listResult){
////                System.out.println(item);
//                root.getChildren().add(item);
//            }
        tcNombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ResultSimulation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ResultSimulation, String> param) {
                return param.getValue().getValue().nameProperty();
            }
        });
        tcFecha.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ResultSimulation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ResultSimulation, String> param) {
                return param.getValue().getValue().dateProperty();
            }
        });

        tcIngreso.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ResultSimulation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ResultSimulation, String> param) {
                return param.getValue().getValue().startTimeProperty();
            }
        });
        tcSalida.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ResultSimulation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ResultSimulation, String> param) {
                return param.getValue().getValue().endTimeProperty();
            }
        });

        tvSimulator.setRoot(root);
        tvSimulator.setShowRoot(false);

    }

    static class ResultSimulation{
        SimpleStringProperty name;
        SimpleStringProperty date;
        SimpleStringProperty startTime;
        SimpleStringProperty endTime;


        public ResultSimulation(String empleado, String fecha, String inicio, String fin){
            this.name = new SimpleStringProperty(empleado);
            this.date = new SimpleStringProperty(fecha);
            this.startTime = new SimpleStringProperty(inicio);
            this.endTime = new SimpleStringProperty(fin);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public String getStartTime() {
            return startTime.get();
        }

        public SimpleStringProperty startTimeProperty() {
            return startTime;
        }

        public String getEndTime() {
            return endTime.get();
        }

        public SimpleStringProperty endTimeProperty() {
            return endTime;
        }

    }

    @FXML
    void iniSimulation(ActionEvent event) throws ParseException {
        root.getChildren().clear();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String datei = iniDate.getValue().toString();
        String datef = endDate.getValue().toString();
        Date date = formatter.parse(datef);
        Calendar fixDate = Calendar.getInstance();
        fixDate.setTime(date);
        fixDate.add(Calendar.DATE, 1);
        date = fixDate.getTime();
        datef = formatter.format(date);

        Simulator simulator = new Simulator(datei, datef);
        List<TreeItem> listResult = simulator.iterateDates();
        for(TreeItem item:listResult){
//                System.out.println(item);
            root.getChildren().add(item);
        }
        float p1 = simulator.getLate();
        float total = simulator.getCantidadTotal();
        float porcentaje = p1/total;
        lbltardia.setText(String.format("%.2f", porcentaje)+"%");
//        System.out.println(p1);
        p1 = simulator.getAbsent();
//        System.out.println(p1);
        porcentaje = p1/total;
        lblAbsent.setText(String.format("%.2f", porcentaje)+"%");
        p1 = simulator.getOmision();
//        System.out.println(p1);
        porcentaje = p1/total;
        lblOmision.setText(String.format("%.2f", porcentaje)+"%");
//        System.out.println(total);
//        System.out.println("Porc"+ porcentaje);
    }
}
