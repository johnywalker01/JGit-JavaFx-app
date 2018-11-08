package filePack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	/**
	 * Return a filtered {@link File} Array from a Directory, <b>Non-recursive</b>
	 * 
	 * @param filePath       as {@link File}
	 * @param extensionArray as {@link String} Array. eg. <code>{"txt","pdf"}</code>
	 * @return {@link File} Array of listed files in the <b>filepath</b>
	 */
	public static File[] getFilteredFiles(File filePath, String[] extensionArray) {
		FilenameFilter filter = new MyFileFilterClass(extensionArray);

		// a list that contains only files with the specified extension
		File[] fileList = filePath.listFiles(filter);

		if (fileList.length > 0) {
			System.out.println("Found " + fileList.length + " files with following types " + extensionArray.toString());
		} else {
			System.out.println(filePath + " found empty with following types " + extensionArray.toString());
		}

		return fileList;
	}
	
	/**
	 * Return a filtered {@link List} from a Directory, <b>Recursive</b>
	 */
	public static List<File> getFilteredFiles(String directory, String[] extensionArray) {
		List<String> allFiles = getAllFileNames(directory);
		List<String> filtfileNames = getFilteredFileNames(allFiles, extensionArray);

		List<File> files = new ArrayList<>();
		for (String fileName : filtfileNames) {
			files.add(new File(fileName));
		}
		return files;
	}

	public static List<String> getRelativeNameOfFiles(List<File> all, String absolutePath) {
		List<String> relFileNames = new ArrayList<>();
		for (File file : all) {
			String absFileName = file.getAbsolutePath();
			if (absolutePath != null) {

				// removes the absolute directory path
				String relativeFileName = absFileName.replace(absolutePath, "");

				// replace the '\' to '/'
				relativeFileName = relativeFileName.replaceAll("\\\\", "/");

				// Storing the the result.
				// removing the beginning '\' from the relative file name.
				relFileNames.add(relativeFileName.substring(1, relativeFileName.length()));
			}
		}

		return relFileNames;
	}

	public static List<String> getFilteredFileNames(List<String> allFiles, String[] extensionArray) {
		List<String> files = new ArrayList<>();

		for (String file : allFiles) {
			for (String name : extensionArray) {
				if (file.toLowerCase().endsWith(name)) {
					files.add(file);
				}
			}
		}
		if (extensionArray.length == 0) {
			return allFiles;
		} else {
			return files;
		}
	}

	public static List<String> getAllFileNames(String directory) {

		List<String> files = new ArrayList<>();

		File[] faFiles = new File(directory).listFiles();
		for (File file : faFiles) {
			if (file.getName().matches("^(.*?)")) {
				files.add(file.getAbsolutePath());
			}
			if (file.isDirectory()) {
				files.addAll(getAllFileNames(file.getAbsolutePath()));
			}
		}
		return files;
	}

	/**
	 * Read a text file, and will return a {@link String} value of matchKey.
	 * 
	 * @param fileName in {@link String} eg. <code>C:\test-folder\sonar.properties</code>
	 * @param matchKey as {@link String} eg. <code>sonar.projectBaseDir</code>
	 * @return {@link String} value of matchKey.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFromPropertiesFile(String fileName, String matchKey) throws FileNotFoundException, IOException {
		String projectBaseDir = new String();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.toLowerCase().contains(matchKey.toLowerCase())) {
					System.out.println("MATCH found " + line);
					String[] data = line.split("=");
					if (data.length > 1) {
						// System.out.println(data[1]);
						projectBaseDir = data[1];
					}
				}
			}
		}
		return projectBaseDir;
	}

	public static File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

		if (!(temp.delete())) { throw new IOException("Could not delete temp file: " + temp.getAbsolutePath()); }

		if (!(temp.mkdir())) { throw new IOException("Could not create temp directory: " + temp.getAbsolutePath()); }

		return (temp);
	}

}
