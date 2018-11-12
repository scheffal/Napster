public class FileEntry{
	protected String remoteHostName;
	protected int portNumber;
	protected String remoteFileName;
	protected String description;

	public FileEntry(String remoteHostName, int portNumber, String remoteFileName, String description)
	{
		this.remoteHostName = remoteHostName;
		this.portNumber = portNumber;
		this.remoteFileName = remoteFileName;
		this.description = description;
	}


}