package com.example.demo.constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendMessage {

public static void message(String mobiles, String message) {

// Your authentication key
String authkey = ApiConstants.authKey;

// Sender ID,While using route4 sender id should be 6 characters long.
String senderId = ApiConstants.senderId;

// define route
String route = "4";

// Prepare Url
URLConnection myURLConnection = null;
URL myURL = null;
BufferedReader reader = null;

// encoding message
String encoded_message = URLEncoder.encode(message);

// Send SMS API
String mainUrl = "http://api.msg91.com/api/sendhttp.php?";

// Prepare parameter string
StringBuilder sbPostData = new StringBuilder(mainUrl);
sbPostData.append("authkey=" + authkey);
sbPostData.append("&mobiles=" + mobiles);
sbPostData.append("&message=" + encoded_message);
sbPostData.append("&route=" + route);
sbPostData.append("&sender=" + senderId);

// final string
mainUrl = sbPostData.toString();
try {
// prepare connection
myURL = new URL(mainUrl);
myURLConnection = myURL.openConnection();
myURLConnection.connect();
reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

System.out.println(mainUrl);
// reading response
String response;
while ((response = reader.readLine()) != null)
// print response
System.out.println(response);

// finally close connection
reader.close();
} catch (IOException e) {
e.printStackTrace();
}

}

public static void main(String[] args) {
SendMessage.message("9507320032", "Ankit Test Sms");
}

}