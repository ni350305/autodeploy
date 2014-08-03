import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class LinuxCommandTest {
	
	public static void main(String[] args) throws IOException {
	
		Process process = Runtime.getRuntime().exec(args[0], null, new File(args[1]));
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
		LineNumberReader input = new LineNumberReader(ir);
		String line;
		while ((line = input.readLine()) != null) {
			System.out.println(line);
		}
	}
}
