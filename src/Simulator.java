import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jose Luis Rodriguez on 29/10/2017.
 */

public class Simulator {

    private Date initialDate;
    private Date finalDate;
    private float late = 0;
    private float omision = 0;
    private float absent = 0;
    private float cantidadTotal = 0;

    public Simulator(String startDate, String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.initialDate = formatter.parse(startDate);
        this.finalDate = formatter.parse(endDate);
    }

    public List<TreeItem> iterateDates() {
        List<TreeItem> listResult = new ArrayList<>();
        String[] empleados = {"Josue", "Rolando", "Feoli", "Jose"};
        Calendar start = Calendar.getInstance();
        start.setTime(initialDate);
        Calendar end = Calendar.getInstance();
        end.setTime(finalDate);
        String[] time = { "onTime", "onTime", "late", "onTime", "Absent", "onTime", "Omission", "onTime", "onTime"};
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            for (Object obj : empleados) {
                cantidadTotal++;
                Random random = new Random();
                String timeRandom = time[random.nextInt(time.length)];
                proporcion(timeRandom);
                MarkGenerator initial = new MarkGenerator("entry", timeRandom);
                timeRandom = time[random.nextInt(time.length)];
                MarkGenerator ending = new MarkGenerator("end", timeRandom);
                String startHour = initial.getFinalHour();
                String endHour = ending.getFinalHour();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                TreeItem<VentanaSimulador.ResultSimulation> rsimulator = new TreeItem<VentanaSimulador.ResultSimulation>
                        (new VentanaSimulador.ResultSimulation(String.valueOf(obj), formatter.format(date), startHour,
                                endHour));
                listResult.add(rsimulator);
            }
        }
        return listResult;
    }

    private void proporcion(String time){
        if(Objects.equals(time, "late")){
            late++;
        } else if (Objects.equals(time, "Absent")){
            absent++;
        } else if(Objects.equals(time, "Omission")){
            omision++;
        }
    }

    public float getLate() {
        return late;
    }

    public float getOmision() {
        return omision;
    }

    public float getAbsent() {
        return absent;
    }

    public float getCantidadTotal() {
        return cantidadTotal;
    }
}
