package ws.floodhazard.service.implementation;

import ws.floodhazard.util.Constants;

import java.util.Objects;

public abstract class AbstractService<T> {

    public String response(T tClass) {
        return Objects.nonNull(tClass) ? Constants.ADD_SUCCESS : Constants.ERROR;
    }

}
