public class File{
	protected String remoteHostName;
	protected int portNumber;
	protected String remoteFileName;
	protected String description;

	public File(String remoteHostName, int portNumber, String remoteFileName, String description)
	{
		this.remoteHostName = remoteHostName;
		this.portNumber = portNumber;
		this.remoteFileName = remoteFileName;
		this.description = description;
	}


}