package filePack;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	private String path;
	private boolean appendToFile;

	public WriteFile(String filePath) {
		path = filePath;
	}

	public WriteFile(String filePath, boolean appendValue) {
		path = filePath;
		appendToFile = appendValue;
	}

	public void writeToFile(String textLines) throws IOException {
		FileWriter write = new FileWriter(path, appendToFile);
		PrintWriter print = new PrintWriter(write);
		print.printf("%s" + "%n", textLines);
		print.close();
	}

}
