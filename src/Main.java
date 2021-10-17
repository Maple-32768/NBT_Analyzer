import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
//Changed here

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("File path: ");
		Path path = Paths.get(scan.nextLine());

		try {
			DataInputStream dIS = new DataInputStream(
					new BufferedInputStream(
							new GZIPInputStream(Files.newInputStream(path))));
			path = Paths.get((path.getParent().toString() + "\\" + path.getFileName().toString() + ".txt"));

			System.out.println(path);
			/*DataOutputStream dOS = new DataOutputStream(
					new BufferedOutputStream(
							new GZIPOutputStream(Files.newOutputStream(path))));*/

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path.toString())));

			byte[] buffer = new byte[1];

			while(dIS.read(buffer) != -1) {
				System.out.print(buffer[0] + ", ");
				pw.println(buffer[0] + ", ");

//				dOS.write(buffer);
			}

			dIS.close();
//			dOS.close();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.close();
	}

}
