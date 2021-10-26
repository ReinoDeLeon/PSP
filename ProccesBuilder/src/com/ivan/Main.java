package com.ivan;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String path = (System.getProperty("user.dir") + File.separator + "bin" + File.separator);

		ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", path, Adder.class.getName(), "1", "100", "Proceso 1")
				.inheritIO();

		processBuilder.start();
		ProcessBuilder processBuilder2 = new ProcessBuilder("java", "-cp", path, Adder.class.getName(), "3", "150", "Proceso 2")
				.inheritIO();

		processBuilder2.start();
	}

}