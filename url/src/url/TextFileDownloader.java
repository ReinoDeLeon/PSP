package url;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileDownloader extends FileDownloader {
	
	public TextFileDownloader(String url_origin, String destination) throws MalformedURLException {
		super(url_origin, Paths.get(System.getProperty("user.home"), "downloads", destination));
	}	
	
	@Override
	public void download() throws IOException {
		try(InputStream urlStream = local_url.openStream();){
			try(InputStreamReader urlStreamReader = new InputStreamReader(urlStream);){
				try(BufferedReader bufferedReader = new BufferedReader(urlStreamReader);){
					Path path =  Paths.get(System.getProperty("user.home"), "downloads", "index");
					try(FileWriter fileWriter = new FileWriter(path.toString())){
						try(BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
							String line;
							while ((line = bufferedReader.readLine()) != null) {
								bufferedWriter.write(line);
							}
						}
					}
				}
			}
		}
	}
}
