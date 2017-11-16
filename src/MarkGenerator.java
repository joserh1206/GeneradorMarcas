import java.util.Objects;
import java.util.Random;

/**
 * Created by Jose Luis Rodriguez on 29/10/2017.
 */

public class MarkGenerator {
    private String type;
    private String finalHour;
    private String time;
    private boolean absent = false;

    MarkGenerator(String type, String time) {
        this.type = type;
        this.time = time;
        hourGen();
    }

    public String getFinalHour() {
        return finalHour;
    }

    private void hourGen() {
        if (Objects.equals(type, "entry")) {
            if(Objects.equals(time, "onTime")) {
                finalHour = "7:30:00";
            } else if(Objects.equals(time, "late")){
                Random random = new Random();
                int randomHour = random.nextInt(9-7+1) + 7; //(max-min+1)+min
                int randomMin = random.nextInt(59-5+1) + 5;
                int randomSec = random.nextInt(59);
                finalHour = String.valueOf(randomHour) + ":" + String.valueOf(randomMin) + ":" + String.valueOf(randomSec);
            } else if(Objects.equals(time, "Absent")){
                absent = true;
                finalHour = "NR";
            } else{
                finalHour = "NR";
            }
        } else {
            if(absent){
                finalHour = "NR";
            } else {
                if (Objects.equals(time, "onTime")) {
                    finalHour = "16:30:00";
                } else if (Objects.equals(time, "late")) {
                    Random random = new Random();
                    int randomHour = random.nextInt(18 - 16 + 1) + 16;
                    int randomMin = random.nextInt(59 - 5 + 1) + 5;
                    int randomSec = random.nextInt(59);
                    finalHour = String.valueOf(randomHour) + ":" + String.valueOf(randomMin) + ":" + String.valueOf(randomSec);
                } else if(Objects.equals(time, "Omission")){
                    finalHour = "NR";
                } else{
                    finalHour = "NR";
                }
            }
        }
    }
}
