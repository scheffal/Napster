/*******************************************
* FileEntry.java
*
* This class sets up the structure for an 
* entry for the file table.
*******************************************/

public class FileEntry{
	protected String userName;
	protected int portNumber;
	protected String remoteFileName;
	protected String description;

	public FileEntry(String userName, int portNumber, String remoteFileName, String description)
	{
		this.userName = userName;
		this.portNumber = portNumber;
		this.remoteFileName = remoteFileName;
		this.description = description;
	}


}

