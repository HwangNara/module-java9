import java.util.Base64;
import java.util.Base64.Encoder;
import sun.util.calendar.CalendarUtils;

public class Application {

    public static void main(String[] args) {
        Encoder enc = Base64.getEncoder();
        CalendarUtils.isGregorianLeapYear(2018);
    }

}