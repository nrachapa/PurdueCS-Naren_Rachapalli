import java.util.ArrayList;

/**
 * Project 5 - Profile Class
 * <p>
 * Creates a user profile object. 
 * @author Henry Wellman, lab sec B02
 * @version November 22, 2020
 */
public class Profile {

	//fields
	private String userName;
	private String password;
	private boolean isOnline;
	private boolean isPublic;
	private boolean isPrivate;
	private boolean isProtected;
	private String contactInfo;
	private String interests;
	private String aboutMe;
	private ArrayList<Profile> friendsList = new ArrayList<Profile>();
	
	//constructor
	public Profile(String userName, String password, String contactInfo, String interests, String aboutMe) {
		this.userName = userName;
		this.password = password;
		this.contactInfo = contactInfo;
		this.interests = interests;
		this.aboutMe = aboutMe;
		this.isOnline = true;
		this.isPublic = true;
		this.isPrivate = false;
		this.isProtected = false;
	}
	
	//accessor methods
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
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
	
	public boolean getPublicStatus() {
		return isPublic;
	}
	
	public boolean getPrivateStatus() {
		return isPrivate;
	}
	
	public boolean getProtectedStatus() {
		return isProtected;
	}
	
	public ArrayList<Profile> getFriendsList() {
		return friendsList;
	}
	
	//mutator methods
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
	
	public void setPublicStatus(boolean status) {
		this.isPublic = status;
	}
	
	public void setPrivateStatus(boolean status) {
		this.isPrivate = status;
	}
	
	public void setProtectedStatus(boolean status) {
		this.isProtected = status;
	}
	
	//adds a profile to the users friends list.
	public void addFriend(Profile profile) {
		friendsList.add(profile);
	}
	
	//removes a profile from the users friends list.
	public void removeFriend(Profile profile) {
		friendsList.remove(profile);
	}
	
	//Writes necessary info to a CSV format to be easily parsed
	public String toCSV() {
		String answer = userName + "," + password + "," + isPublic + "," + isPrivate + "," + isProtected + "," + friendsList;
		return answer;
	}
	
	
	
}
