package es.uvlive.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendBroadcastTask extends BaseTask {

    private static final int SECOND_MILLISECONDS = 1000;
    private static final int MINUTE_SECONDS = 60;
    private static final int HOUR_MINUTE = 60;
    private static final int DAY_HOUR = 24;


    @Scheduled(fixedRate = 3 * MINUTE_SECONDS * SECOND_MILLISECONDS)  // every 3 minutes
    public void sendBroadcast() {
        try {
            uvLiveModel.sendBroadcasts();
        } catch (Exception e) {
            System.out.println("[" + getClass().getSimpleName().toString() + "] Error code: " + getErrorCode(e));
        }
    }

}