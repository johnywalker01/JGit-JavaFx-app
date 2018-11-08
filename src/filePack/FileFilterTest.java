package filePack;

import java.io.File;

public class FileFilterTest {
	
	public void check(String filepath,String[] extensionArray) {
		File absfilePath = new File(filepath);
		FileUtils.getFilteredFiles(absfilePath, extensionArray);
	}

}
