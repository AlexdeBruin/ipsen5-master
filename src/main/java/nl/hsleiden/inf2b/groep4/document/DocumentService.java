package nl.hsleiden.inf2b.groep4.document;

import com.google.inject.Inject;
import javax.inject.Singleton;
import java.io.*;

@Singleton
public class DocumentService {

	private String baseString;

	@Inject
	public DocumentService() {
		String os = System.getProperty("os.name");
		if (os.equals("Windows")) {
			this.baseString = "";
		} else {
			this.baseString = "";
		}
	}

	public boolean uploadFile(InputStream fileInputStream, String filename) {
		String filePathScrFrontend = baseString + filename + ".pdf";
		return saveFile(fileInputStream, filePathScrFrontend);
	}

	private boolean saveFile(InputStream uploadedInputStream,
	                         String serverLocation) {
		try {
			File file = new File(serverLocation);
			OutputStream outputStream = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];

			outputStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public File getDocument(String filename) {
		return new File(baseString + filename + ".pdf");
	}
}
