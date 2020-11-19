package filesManagment;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class Paker {
	
	private static Paker instance;
	
	private Paker() {}
	
	public static Paker getInstance() {
		if(instance==null)instance = new Paker();
		return instance;
	}
	
	public void unpak() {
		
	}
	
	public void pak() {
		if(System.getProperty("os.name").contains("Windows"))
			try {
				windowsPak();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
	}

	private void windowsPak() throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cmd.exe","echo cc");
		
		builder.directory(new File("packer"));
		System.out.println(new File("packer").getAbsolutePath());
		
		Process process = builder.start();
		
		StreamGobbler streamGobbler =
				  new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		
		int exitCode = process.waitFor();
		System.out.println("Here we go");
		assert exitCode == 0;
	}
	
}
