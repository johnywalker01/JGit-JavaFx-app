package filePack;

import java.io.File;
import java.io.FilenameFilter;

public class MyFileFilterClass implements FilenameFilter {

	private String[] extensionArray = { "txt", "pdf" };

	public MyFileFilterClass(String[] extnArray) {
		this.extensionArray = extnArray;
	}

	@Override
	public boolean accept(File dir, String name) {

		if (dir.exists()) {
			for (String ext : extensionArray) {
				if (name.endsWith(ext))
					return true;
			}
		}
		return false;
	}

}