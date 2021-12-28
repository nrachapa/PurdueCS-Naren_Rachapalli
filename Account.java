import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project 5 - Account Class
 * <p>
 * Creates a user account that has a username, password, and arraylist of profiles. 
 * @author Henry Wellman, lab sec B02
 * @version November 22, 2020
 */
public class Account implements Serializable {

	//fields
	private String userName;
	private String password;
	private ArrayList<Profile> profileList = new ArrayList<Profile>();
        private ArrayList<Account> accountList = new ArrayList<Account>();
	
	//constructor
	public Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	//accessor methods
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public ArrayList<Account> getAccountList() {
		return accountList;
	}
	
	public ArrayList<Profile> getProfileList() {
		return profileList;
	}
	
	//mutator methods
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	//adds a new profile to the account's profile list
	public void addNewProfile(Profile profile) {
		profileList.add(profile);
	}
	
	//removes a profile from the account's profile lsit
	public void deleteProfile(Profile profile) {
		profileList.remove(profile);
	}
	
	//adds a new account to the account list
	public void addNewAccount(Account account) {
		accountList.add(account);
	}
		
	//removes an account from the account list
	public void deleteAccount(Account account) {
		accountList.remove(account);
	}
	
	//puts the fields into a CSV format so easy to parse
	public String toCSV() {
		String answer = userName + "," + password;
		return answer;
	}
	
	
	
	
}
