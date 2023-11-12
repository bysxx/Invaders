package engine;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetworkManager {
  private static NetworkManager instance;

  /**
	 * @return Shared instance of NetworkManager.
	 */
	public static NetworkManager getInstance() {
		if (instance == null)
			instance = new NetworkManager();
		return instance;
	}

  public static void getWorkRank() {
    try {
      URL url = new URL("http://localhost:3200/test");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // success
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println(response.toString());
      } else {
        System.out.println("GET request not worked");
      }
    } catch (Exception e) {
      System.out.println("GET request not worked");
    }
  }
}

