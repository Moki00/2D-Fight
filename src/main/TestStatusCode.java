package main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestStatusCode {

	public static void main(String[] args) {
		try {
			URL url = new URL("https://www.google.com");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int statusCode = connection.getResponseCode();

			if (statusCode == 200) {
				System.out.println("The request to " + url + " was successful: " + statusCode);
			}
//			else {
//				System.out.println("The request failed. Status code: " + statusCode);
//			}
		} catch (IOException e) {

			System.out.println("The request failed. Cannot reach status code");
			e.printStackTrace();
		}

	}

}
