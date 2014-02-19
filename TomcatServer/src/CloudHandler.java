import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.Element;
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
	public HttpURLConnection connect(boolean post, String addedURL) {
		try {
			URL url = new URL(baseURL + addedURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setDoInput(true);
			if (post == true) {
				con.setRequestMethod("POST");
			} else {
				con.setRequestMethod("GET");
			}
			con.connect();

			return con;
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

	}

	// For backward combatiblity
	public HttpURLConnection connect(String addedURL) {

		return connect(true, addedURL);

	}

	public Document getItemDoc() {

		Document res = new Document();

		Element e = new Element("HRELLOW");
		res.setRootElement(e);
		HttpURLConnection con = connect(false, "/listItems?shopID=488");

		getResponse(false, con, res, XMLHandler.getOutputter(),
				XMLHandler.getSAXBuilder());

		return res;

	}

	// Sends a Document and returns the response
	public Document getResponse(boolean post, HttpURLConnection con,
			Document d, XMLOutputter out, SAXBuilder b) {
		try {
			if (post == true) {
				out.output(d, con.getOutputStream());
			}
			lastResponse = con.getResponseCode();
			lastResponseMessage = con.getResponseMessage();
			InputStream response = con.getInputStream();

			Document itemID = b.build(response);
			return itemID;
		} catch (IOException ioe) {
			System.out.println("FORBINDELSESOPRETTELSESFEJL!!!!!!!");
			return null;
		} catch (JDOMException jdome) {
			return null;
		}

	}

	// For backward comatiblity
	public Document getResponse(HttpURLConnection con, Document d,
			XMLOutputter out, SAXBuilder b) {

		return getResponse(true, con, d, out, b);

	}

	public int getLastResponse() {
		return lastResponse;
	}

	public String getLastResponseMessage() {
		return lastResponseMessage;
	}

}
