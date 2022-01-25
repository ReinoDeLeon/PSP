package url;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinFileDownloader extends FileDownloader {

	private static final String PNG = ".png";

	final private String JPG = ".jpg";

	private byte[] bytes;


	public BinFileDownloader(String url_origin, String storage) throws MalformedURLException {
		super(url_origin, Paths.get(System.getProperty("user.home"), "downloads", storage));
	}

	@Override
	public void download() throws IOException {
		/*
		 * we check the image resource extension so we output a file with the same extension
		 * */
		if (local_url.getPath().contains(JPG)) {
			try(InputStream urlStream = local_url.openStream();){
				bytes = urlStream.readAllBytes(); //We get all bytes from the url image
				File image = new File(Paths.get(System.getProperty("user.home"), "downloads", getName() + JPG).toString()); //We create the image file
				Files.write(image.toPath(), bytes); //We write the image on the already created file
			}
		}
		if (local_url.getPath().contains(PNG)) {
			try(InputStream urlStream = local_url.openStream();){
				bytes = urlStream.readAllBytes(); //We get all bytes from the url image
				File image = new File(Paths.get(System.getProperty("user.home"), "downloads", getName() + PNG).toString()); //We create the image file
				Files.write(image.toPath(), bytes); //We write the image on the already created file
			}
		}
	}

}
