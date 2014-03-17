import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import java.net.*;

import org.jdom2.*;

@Path("chat")
public class ChatService {

	private boolean employeeStatus;
	private HttpSession session;
	private CloudHandler cloudHandler;
	private Namespace n;
	private ServletContext context;
	private Timer logOffTimer;
	private HashMap<String, LinkedList<String>> conversationMap;
	private HashMap<String, LinkedList<String>> conversationBackMap;
	private LinkedList<String> windowsToAdd;

	public ChatService(@Context HttpServletRequest sessionrequest) {

		this.session = sessionrequest.getSession();
		this.context = session.getServletContext();

		this.n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
		cloudHandler = new CloudHandler(n);
		
		//Checks if a Timer can be loaded
		Timer tempTimer = (Timer) session.getAttribute("logTimer");
		if (tempTimer != null) {
			logOffTimer = tempTimer;
		}
		
		getConversationMapFromContext();
		getConversationBackMapFromContext();

		//System.out.println("Got maps from context");
		
		getWindowlistFromContext();
		
		getEmployeeStatusFromContext();

	}
	

	/**
	 * 
	 * Responsible for sending messages from the costumer
	 */
	@POST
	@Path("customersend")
	public String sendMessageFromCostumer(@FormParam("message") String message) {
		
		// Updates employee status
		getEmployeeStatusFromContext();
		
		System.out.println("EMP STATUS VED SEND: " + employeeStatus);

		if (!isCostumerLoggedIn()) {

			return "You need to be logged in to chat";

		} else if (!employeeStatus) {

			return "Unfortunately there is no employee present at the moment. Please come back later";

		}

		System.out.println("Customer sent message: " + message);

		String id = getCostumerID();
		
		System.out.println("CUSTOMER ID: " + id);
		
		//Checks if a conversation is already started with the costumer
		if(conversationMap.containsKey(id)){
			
			//appends message to end of linkedlist of conversations
			conversationMap.get(id).add(message);
			System.out.println("Con already started");
			
		} else {
			
			//Creates new linkedlist with convertsation and adds the new message
			LinkedList<String> tempList = new LinkedList<String>();
			tempList.add(message);
			conversationMap.put(id, tempList);
			windowsToAdd.add(id);
			
			context.setAttribute("windowList", windowsToAdd);
			
			System.out.println("Window sent to employee");
			
		}
		
		//saves conversationmap in context
		context.setAttribute("conSet", conversationMap);
		
		return "SUCESS!";
	}

	/**
	 * 
	 * Responsible for sending messages from the employee
	 */
	@POST
	@Path("employeesend")
	public String sendMessageFromEmployee(@FormParam("message") String message, @FormParam("custID") String id) {

		System.out.println("Employee sent message: " + message);

		//String id = getCostumerID();
		
		System.out.println("CUSTOMER ID: " + id);
		
		//Checks if a conversation is already started with the costumer
		if(conversationBackMap.containsKey(id)){
			
			//appends message to end of linkedlist of conversations
			conversationBackMap.get(id).add(message);
			System.out.println("Con already started");
			
		} else {
			
			//Creates new linkedlist with convertsation and adds the new message
			LinkedList<String> tempList = new LinkedList<String>();
			tempList.add(message);
			conversationBackMap.put(id, tempList);
			//windowsToAdd.add(id);
			
			//context.setAttribute("windowList", windowsToAdd);
			
			System.out.println("Window sent to employee");
			
		}
		
		//saves conversationmap in context
		context.setAttribute("conBackSet", conversationBackMap);
		
		return "SUCESS!";
	}

	/**
	 * 
	 * The employee call this to check if new window should be added
	 */
	@GET
	@Path("makewindow")
	public String addWindowToEmployee() {

		getWindowlistFromContext();

		
		if(windowsToAdd.peek() != null){
			System.out.println("WINDOW FOUND");
			return windowsToAdd.pop();	
		}
		
		return "";
		
		
	}

	/**
	 * 
	 * Checked by the windows to add messages.
	 */
	@POST
	@Path("employeemessage")
	public String sendMessagesToEmployee(@FormParam("id") String costumerID) {

		System.out.println(costumerID);
		if(conversationMap.containsKey(costumerID) && conversationMap.get(costumerID).size() > 0){
			
			String tmp = conversationMap.get(costumerID).pop();
			context.setAttribute("conSet", conversationMap);
			return tmp;
			
		}
		
		return "";
	}

	/**
	 * 
	 * Checked by the windows to add messages.
	 */
	@POST
	@Path("custMes")
	public String sendMessagesToCostumer() {

		if (!isCostumerLoggedIn()) {

			return "You need to be logged in to chat";

		}

		String costumerID = getCostumerID();

		System.out.println(costumerID);
		if(conversationBackMap.containsKey(costumerID) && conversationBackMap.get(costumerID).size() > 0){
			
			String tmp = conversationBackMap.get(costumerID).pop();
			context.setAttribute("conBackSet", conversationBackMap);
			return tmp;
			
		}
		
		return "";
	}

	/**
	 * Called in javascript loop to keep the employee logged in
	 */

	@POST
	@Path("checkinemployee")
	public void checkEmployeeIn() {
		reScheduleLogOff();

	}

	/**
	 * Logs of employee if it hasnt been contacted for 3 seconds
	 */

	private void reScheduleLogOff() {

		System.out.println("Reschedule RAN");

		if (logOffTimer != null) {
			logOffTimer.cancel();

		} else {
			System.out.println("TIMER NOT FOUND");
		}

		logOffTimer = new Timer();
		TimerTask t = createTimerTask();
		logOffTimer.schedule(t, 5000);

		// Saves timer to the session
		session.setAttribute("logTimer", logOffTimer);

	}

	/**
	 * 
	 * @return Task that logoff empleoyee
	 */
	private TimerTask createTimerTask() {

		return new TimerTask() {

			@Override
			public void run() {

				employeeStatus = false;

				//Resets all conversations
				context.setAttribute("conSet", null);
				context.setAttribute("conBackSet", null);
				
				
				// Saves employee state to context
				context.setAttribute("empStatus", employeeStatus);
				System.out.println("Employee logged of automatically");

			}
		};

	}


	@POST
	@Path("setonline")
	public void setEmployeeStatus(@FormParam("status") boolean employeeStatus) {

		this.employeeStatus = employeeStatus;

		context.setAttribute("empStatus", employeeStatus);

		reScheduleLogOff();

		System.out.println("STATUS ved EMP LOGIN: " + employeeStatus);

	}

	@POST
	@Path("checkcustomerlogin")
	public Boolean checkCustomerLogin() {

		return isCostumerLoggedIn();

	}
	
	@POST
	@Path("checkc")
	public Boolean checkCostumerBougth(){
		
		return hascostumerbought();
		
	}
	

	private void getEmployeeStatusFromContext() {

		// Get status of employee
		Boolean tempstatus = (Boolean) context.getAttribute("empStatus");
		if (tempstatus == null) {
			employeeStatus = false;
		} else {
			employeeStatus = tempstatus;
		}
	}

	private void getConversationMapFromContext(){
		

		//Checks weather the conversationmap can be loaded if no creates a new map
		HashMap<String, LinkedList<String>> tempset = (HashMap<String, LinkedList<String>>) context.getAttribute("conSet");
		if(tempset == null){
			conversationMap = new HashMap<String, LinkedList<String>>();
			} else {
				conversationMap = tempset;
			}
		
	}
	
	private void getConversationBackMapFromContext(){
		

		//Checks weather the conversationmap can be loaded if no creates a new map
		HashMap<String, LinkedList<String>> tempset = (HashMap<String, LinkedList<String>>) context.getAttribute("conBackSet");
		if(tempset == null){
			conversationBackMap = new HashMap<String, LinkedList<String>>();
		} else {
			conversationBackMap = tempset;
		}
		
	}

	private void getWindowlistFromContext(){
		
		//Checks if the windowlist can be loaded if nots creates a empty list
				LinkedList<String> tempList = (LinkedList<String>) context.getAttribute("windowList");
				if(tempList == null){
					windowsToAdd = new LinkedList<String>();
				} else {
					windowsToAdd = tempList;
					
				}
	}
	
	private String getCostumerID() {

		if (isCostumerLoggedIn()) {
			return (String) session.getAttribute("id");
		}

		return null;

	}

	private boolean isCostumerLoggedIn() {

		if (session.getAttribute("id") == null
				|| ((String) session.getAttribute("id")).equals("")) {

			return false;

		}

		return true;

	}
	
	private boolean hascostumerbought(){
		
		HttpURLConnection con = cloudHandler.connect(false, "/listCustomerSales?customerID=" + getCostumerID());
		Document response = cloudHandler.getResponse(false, con, null, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		if(response == null || response.getRootElement().getChildren().size() == 0)
		{
			return false;
		}
		
		return true;
		
	}

}
