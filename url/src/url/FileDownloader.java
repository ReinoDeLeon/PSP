package url;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public abstract class FileDownloader {

	protected final URL local_url;

	public abstract void download() throws IOException;

	protected final Path local_storage;

	public FileDownloader(String url_origin, Path storage) throws MalformedURLException {
		super();
		local_url = new URL(url_origin);
		local_storage = storage;
	}
	
	public String getName() {		
		/*
		 * We get the name of the bin we are creating
		 */
		String[] name = new File(local_storage.toString()).getName().split("[.]");
		return name[0];
	}

}