package ws.floodhazard.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtils {

    public static String getDate(Date date) {
        log.debug("Date {}", date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
        date = date != null ? date : new Date();
        log.debug("New Input Date {}", dateFormat.format(date));
        return dateFormat.format(date);
    }
}
