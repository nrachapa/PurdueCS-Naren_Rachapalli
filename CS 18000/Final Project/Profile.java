import java.util.ArrayList;
import java.io.Serializable

/**
 * Project 5 - Profile Class
 * 
 * Creates a user profile object. 
 */
public class Profile implements Serializable {

	//fields
	private String userName; //the user name for the profile
	private String password; //the password for the profile
	private boolean isOnline; //states if the profile is connected to server
	private boolean friendRequests; //states if their are any outgoing, active, friend requests
	private String contactInfo; //the profile's contact info
	private String interests; //the profile's interests
	private String aboutMe; //profile's description of themselves
	private ArrayList<String> friendsList = new ArrayList<String>(); //a list of the profile's friends
	
	//constructor
	public Profile(String userName, String password, String contactInfo, String interests, String aboutMe) {
		this.userName = userName;
		this.password = password;
		this.contactInfo = contactInfo;
		this.interests = interests;
		this.aboutMe = aboutMe;
		this.isOnline = true;
		this.friendRequests = false;
	}
	
	//accessor methods
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getFriendRequests() {
		return friendRequests;
	}
	
	public String getContactInfo() {
		return contactInfo;
	}
	
	public String getInterests() {
		return interests;
	}
	
	public String aboutMe() {
		return aboutMe;
	}
	
	public boolean getOnlineStatus() {
		return isOnline;
	}
	
	public ArrayList<String> getFriendsList() {
		return friendsList;
	}
	
	//mutator methods
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setFriendRequest(boolean friendRequests) {
		this.friendRequests = friendRequests;
	}
	
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public void setOnlineStatus(boolean status) {
		this.isOnline = status;
	}
	
	//adds a profile to the users friends list.
	public void addFriend(String profileName) {
		friendsList.add(profileName);
	}
	
	//removes a profile from the users friends list.
	public void removeFriend(String profileName) {
		friendsList.remove(profileName);
	}
	
	//Writes necessary info to a CSV format to be easily parsed
	public String toCSV() {
		String answer = userName + "," + password + "," + contactInfo + "," + interests + "," +
	aboutMe + "," + friendRequests + "," + friendsList;
		return answer;
	}
	
	
	
}
