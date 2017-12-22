package application;

import java.io.File;

public class FormData {

	/**
	 * File that holds all the information, typically an excel file, only supported
	 * xlsx and xls
	 */
	private File metaDataFile;
	/**
	 * Directory where all the documents are.
	 */
	private File directoryWithFile;

	/**
	 * Directory where the results will be saved too
	 */
	private File resultsDirectoryFile;

	/**
	 * Column name in the metaDataFile that has the extension of the documents
	 */
	private String fileExtensionColumn;
	/**
	 * Column name in the metaDataFile that has the name of the documents
	 */
	private String fileNameColumn;
	/**
	 * after how many rows shall the meteDafaFile will be splited into another
	 * document
	 */
	private String splitMetaDataEachRows;

	/**
	 * Type of object in the agile environment Any of the following "CHANGE",
	 * "DECLARATION", "FILEFOLDER", "ITEM", "MFR", "MFR_PART", "COMMODITY", "PRICE",
	 * "PSR", "ACTIVITY", "QCR", "RFQ", "RESPONSE", "PROJECT", "SPECIFICATION",
	 * "SUBSTANCE", "SUPPLIER"
	 */
	private String objecType;

	/**
	 * if creating the indexFile Column name in the metaDataFile that has the Title
	 * block number (id)
	 */
	private String numberColumn;
	/**
	 * if creating the indexFile Column name in the metaDataFile that has the Title
	 * block revision
	 */
	private String revisionColumn;

	/**
	 * if creating the indexFile from the file vault what's the path to access the
	 * files
	 */
	private String pathToFileFromFileVault;

	/**
	 * if creating the indexFile one of the following values "FILE", "INPLACE"
	 */
	private String importType;
	/**
	 * if creating the indexFile Column name in the metaDataFile that has the
	 * description
	 */
	private String descriptionColumn;

	/**
	 * Tells if we are going to create the index file
	 */
	private boolean createIndexFile;

	/**
	 * If creating Change orders, the username for the agile server used to connect
	 */
	private String userId;

	/**
	 * If creating Change orders, the password for the agile server used to connect
	 */
	private String password;

	/**
	 * If creating Change orders, the url of the agile server
	 */
	private String url;

	/**
	 * If creating Change orders, the workflowname that the CO is going to be
	 * assigned
	 */
	private String workflowName;

	/**
	 * Telss if we are going to create change orders
	 */
	private boolean createChangeOrders;

	/**
	 * indicates if the tools is run for testing, if so, it will prepend all title
	 * block numbers with a timestamp it will also just make a copy of the files
	 * instead of moving them, indexFile will have the appropiated prepend also. The
	 * idea is that you can make multiple tests with "different" data on agile
	 */
	private boolean forTesting;

	public FormData(File metaDataFile, File directoryWithFile, String fileExtensionColumn, String fileNameColumn,
			String splitMetaDataEachRows, String objecType, String numberColumn, String revisionColumn,
			String pathToFileFromFileVault, String importType, String descriptionColumn, boolean createIndexFile,
			String userId, String password, String url, String workflowName, boolean createChangeOrders,
			boolean forTesting, File resultsDirectoryFile) {
		this.metaDataFile = metaDataFile;
		this.directoryWithFile = directoryWithFile;
		this.fileExtensionColumn = fileExtensionColumn;
		this.fileNameColumn = fileNameColumn;
		this.splitMetaDataEachRows = splitMetaDataEachRows;
		this.objecType = objecType;
		this.numberColumn = numberColumn;
		this.revisionColumn = revisionColumn;
		this.pathToFileFromFileVault = pathToFileFromFileVault;
		this.importType = importType;
		this.descriptionColumn = descriptionColumn;
		this.createIndexFile = createIndexFile;
		this.userId = userId;
		this.password = password;
		this.url = url;
		this.workflowName = workflowName;
		this.createChangeOrders = createChangeOrders;
		this.forTesting = forTesting;
		this.resultsDirectoryFile = resultsDirectoryFile;
	}

	public File getMetaDataFile() {
		return metaDataFile;
	}

	public void setMetaDataFile(File metaDataFile) {
		this.metaDataFile = metaDataFile;
	}

	public File getDirectoryWithFile() {
		return directoryWithFile;
	}

	public void setDirectoryWithFile(File directoryWithFile) {
		this.directoryWithFile = directoryWithFile;
	}

	public String getFileExtensionColumn() {
		return fileExtensionColumn;
	}

	public void setFileExtensionColumn(String fileExtensionColumn) {
		this.fileExtensionColumn = fileExtensionColumn;
	}

	public String getFileNameColumn() {
		return fileNameColumn;
	}

	public void setFileNameColumn(String fileNameColumn) {
		this.fileNameColumn = fileNameColumn;
	}

	public String getSplitMetaDataEachRows() {
		return splitMetaDataEachRows;
	}

	public void setSplitMetaDataEachRows(String splitMetaDataEachRows) {
		this.splitMetaDataEachRows = splitMetaDataEachRows;
	}

	public String getObjecType() {
		return objecType;
	}

	public void setObjecType(String objecType) {
		this.objecType = objecType;
	}

	public String getNumberColumn() {
		return numberColumn;
	}

	public void setNumberColumn(String numberColumn) {
		this.numberColumn = numberColumn;
	}

	public String getRevisionColumn() {
		return revisionColumn;
	}

	public void setRevisionColumn(String revisionColumn) {
		this.revisionColumn = revisionColumn;
	}

	public String getPathToFileFromFileVault() {
		return pathToFileFromFileVault;
	}

	public void setPathToFileFromFileVault(String pathToFileFromFileVault) {
		this.pathToFileFromFileVault = pathToFileFromFileVault;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public String getDescriptionColumn() {
		return descriptionColumn;
	}

	public void setDescriptionColumn(String descriptionColumn) {
		this.descriptionColumn = descriptionColumn;
	}

	public boolean isCreateIndexFile() {
		return createIndexFile;
	}

	public void setCreateIndexFile(boolean createIndexFile) {
		this.createIndexFile = createIndexFile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public boolean isCreateChangeOrders() {
		return createChangeOrders;
	}

	public void setCreateChangeOrders(boolean createChangeOrders) {
		this.createChangeOrders = createChangeOrders;
	}

	public boolean isForTesting() {
		return forTesting;
	}

	public void setForTesting(boolean forTesting) {
		this.forTesting = forTesting;
	}

	public File getResultsDirectoryFile() {
		return resultsDirectoryFile;
	}

	public void setResultsDirectoryFile(File resultsDirectoryFile) {
		this.resultsDirectoryFile = resultsDirectoryFile;
	}
}
