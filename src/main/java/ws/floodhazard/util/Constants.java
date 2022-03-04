package ws.floodhazard.util;

public class Constants {

    // RESPONSE
    public static final String ADD_SUCCESS = "Record has been successfully added!";
    public static final String ERROR = "Failed to save/update the record.";
    // ENDPOINTS
    public static final String INIT = "/init";
    public static final String VIEW = "/view";
    public static final String ADMIN = "/admin";
    public static final String LOCATION = "/location";
    public static final String SENSITIVITY_CONDITION = "/sensitivitycondition";
    public static final String RAIN_INTENSITY = "/rainintensity";
    public static final String RAIN_INTENSITIES = "/rainintensities";
    public static final String RAIN_TYPE = "/raintype";
    public static final String FORECAST = "/forecast";
    public static final String WARNINGS = "/warnings";

    public static final String VIOLENT_RAIN = "Violent Rain";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final int BUTTONS_TO_SHOW = 3;
    public static final int INITIAL_PAGE = 0;
    public static final int INITIAL_PAGE_SIZE = 12;
    public static final int[] PAGE_SIZES = {6, 12, 24};

}
