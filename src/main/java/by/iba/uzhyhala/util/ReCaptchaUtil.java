package by.iba.uzhyhala.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class ReCaptchaUtil {
    private static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET = "6LfAwFAUAAAAAP9YGQNaD3jOzgN7YiN7qD5b4cJd";
    public static final String PUBLIC = "6LfAwFAUAAAAAJYuBmiEiv8Io3FMUHmlKzUPXIQ3";
    private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            URL url = new URL(URL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            // add request header
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpsURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send post request
            httpsURLConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
            wr.writeBytes("secret=" + SECRET + "&response=" + gRecaptchaResponse);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //parse JSON response and return 'success' value
            JsonElement jsonElement = new JsonParser().parse(response.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            return jsonObject.get("success").getAsBoolean();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            ex.printStackTrace();
            return false;
        }
    }
}