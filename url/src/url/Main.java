package url;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
//		TextFileDownloader textFileDownloader = new TextFileDownloader("https://docs.oracle.com/javase/tutorial/networking/sockets/index.html", "index");
//		textFileDownloader.download();
		BinFileDownloader binFileDownloader = new BinFileDownloader("https://pbs.twimg.com/profile_images/988272404915875840/lE7ZkrO-_400x400.jpg", "google.jpg");
		binFileDownloader.download();
		BinFileDownloader binFileDownloader2 = new BinFileDownloader("https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Google_Images_2015_logo.svg/1200px-Google_Images_2015_logo.svg.png", "google.pdf.p");
		binFileDownloader2.download();
	}

}
