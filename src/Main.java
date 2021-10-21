import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("File path: ");
		Path path = Paths.get(scan.nextLine());

		try {
			DataInputStream dIS = new DataInputStream(
					new BufferedInputStream(
							new GZIPInputStream(Files.newInputStream(path))));


			byte[] buffer = new byte[1];
			List<Byte> data = new ArrayList<>();

			while(dIS.read(buffer) != -1) {
				data.add(buffer[0]);
			}
			byte[] data_array = new byte[data.size()];
			for (int i = 0; i < data.size(); i++) {
				data_array[i] = data.get(i);
			}
			TAGComponent c = TAGComponent.Analyze(data_array);
			System.out.println((c.getHeader().tag_name != null ? c.getHeader().tag_name + " : " : "") + c);
			dIS.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.close();
	}

}
