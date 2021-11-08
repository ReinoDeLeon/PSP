package com.ivan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		String path = (System.getProperty("user.dir") + File.separator + "bin" + File.separator);

		ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", path, Adder.class.getName(), "1", "3", "Proceso 1");
		ProcessBuilder processBuilder2 = new ProcessBuilder("java", "-cp", path, Adder.class.getName(), "3", "5", "Proceso 2");
		
		Process process1 = processBuilder.start();
		Process process2 = processBuilder2.start();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
		String firstProcessResult = bufferedReader.readLine();
		System.out.println(String.format("Process 1 result is: %s", firstProcessResult));

		BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
		String secondProcessResult = bufferedReader2.readLine();
		System.out.println(String.format("Process 2 result is: %s", secondProcessResult));

		
		System.out.println("Finished parent process");
	}
}