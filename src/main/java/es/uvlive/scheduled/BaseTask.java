package es.uvlive.scheduled;

import es.uvlive.exceptions.ErrorManager;
import es.uvlive.model.UVLiveModel;

public class BaseTask {

    protected UVLiveModel uvLiveModel = UVLiveModel.getInstance();

    protected int getErrorCode(Exception e) {
        return ErrorManager.getErrorCode(getClass(),e);
    }
}
