import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


//Google provided code for Auth Access
public class CalendarQuickstart {
        private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        private static final String TOKENS_DIRECTORY_PATH = "tokens";

        /**
         * Global instance of the scopes required by this quickstart.
         * If modifying these scopes, delete your previously saved tokens/ folder.
         */
        private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
        private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

        /**
         * Creates an authorized Credential object.
         * @param HTTP_TRANSPORT The network HTTP Transport.
         * @return An authorized Credential object.
         * @throws IOException If the credentials.json file cannot be found.
         */
        private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
            // Load client secrets.
            InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        }


    /**
     *
     * @param summary Event Title, UI from name field in GUI
     * @param bodyPart UI from bodypart CB in GUI
     * @param movements UI from movements CB in GUI
     * @param weight UI from the weight TB in GUI
     * @param startingDate UI from the start date spinner in GUI
     * @param endingDate UI from the end date spinner in GUI
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static Event myNewEvent(String summary, String bodyPart, String movements, int weight, String startingDate, String endingDate) throws IOException, GeneralSecurityException {

        //Google AUTH, gets credential and authorizes a request using the credentials.json file in resources
        //Gives full access to the "primary" calendar
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //Creates a new event for the calander, uses parameters passed from the GUI to populate the event
        Event myEvent = new Event()
                .setSummary(summary)
                .setDescription("Bodypart: " + bodyPart + ", with movements: " + movements + ", using: " + weight + "lbs ");

        DateTime startDateTime = new DateTime(startingDate);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
                //.setTimeZone("America/Chicago");
        myEvent.setStart(start);

        DateTime endDateTime = new DateTime(endingDate);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
                //.setTimeZone("America/Chicago");
        myEvent.setEnd(end);

        //Uses the Primary calendar of the user
        String calId = "primary";

        //executes a new event through the UI of the user
        myEvent = service.events().insert(calId, myEvent).execute();

        return myEvent;


    }

    }

