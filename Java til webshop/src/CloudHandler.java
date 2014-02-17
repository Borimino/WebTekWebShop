import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class CloudHandler {

	private String baseURL = "http://services.brics.dk/java4/cloud";
	private int lastResponse;
	private String lastResponseMessage;
	private Namespace n;
	
	public CloudHandler(Namespace n) {
		
		this.n = n;
	}

	// Starts a connection
	public HttpURLConnection connect(String addedURL) {
		try {
			URL url = new URL(baseURL + addedURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.connect();

			return con;
		} catch (MalformedURLException murle) {
			return null;
		} catch (IOException ioe) {
			return null;
		}

	}

	// Sends a Document and returns the response
	public Document getResponse(HttpURLConnection con, Document d,
			XMLOutputter out, SAXBuilder b) {
		try {
			out.output(d, con.getOutputStream());

			lastResponse = con.getResponseCode();
			lastResponseMessage = con.getResponseMessage();
			InputStream response = con.getInputStream();

			Document itemID = b.build(response);
			return itemID;
		} catch (IOException ioe) {
			return null;
		} catch (JDOMException jdome) {
			return null;
		}

	}

	public int getLastResponse() {
		return lastResponse;
	}

	public String getLastResponseMessage() {
		return lastResponseMessage;
	}

}
