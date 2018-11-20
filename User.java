/*******************************************
* User.java
*
* This class sets up the structure for an 
* entry for the users table.
*******************************************/

public class User{

    protected String userName;
    protected String hostName;
    protected String speed;
    
    public User(String userName, String hostName, String speed)
    {
	this.userName = userName;
	this.hostName = hostName;
	this.speed = speed;
	
    }

}

