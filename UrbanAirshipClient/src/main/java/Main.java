import com.urbanairship.api.client.APIClient;
import com.urbanairship.api.client.APIClientResponse;
import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.client.APIRequestException;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    static Logger logger =  LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        String appKey = "applicationKey";
        String appSecret = "applicationMasterSecret";

        // Build and configure an APIClient
        APIClient apiClient = APIClient.newBuilder().setKey(appKey)
                .setSecret(appSecret)
                .build();
        Selector selector = Selectors.deviceToken("aaa");
        // Setup a payload for the message you want to send
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(selector)
                .setNotification(Notifications.alert("API v3"))
                .setPlatforms(PlatformData.of(Platform.ANDROID))
                .build();
        // Try/Catch for any issues, any non 200 response, or non library
        // related exceptions
        try {
            APIClientResponse<APIPushResponse> response = apiClient.push(payload);
            logger.debug(String.format("Response %s", response.toString()));
        }
        catch (APIRequestException ex){
            logger.error(String.format("APIRequestException " + ex));
            logger.error("Something wrong with the request " + ex.toString());
        }
        catch (IOException e){
            logger.error("IOException in API request " + e.getMessage());
        }
    }
}
