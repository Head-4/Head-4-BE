package head4.notify.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initFirebase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/firebaseKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FIREBASE_CONFIG_ERROR);
        }
    }
}
