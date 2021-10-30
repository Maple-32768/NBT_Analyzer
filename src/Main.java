import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main {

	public static void main(String[] args) {
		TAGInt age = new TAGInt("age", 17);
		TAGFloat height = new TAGFloat("height", 169.5f);
		TAGString name = new TAGString("name", "Maple32768");
		TAGComponent[] _play_games = {new TAGString("", "Minecraft"), new TAGString("", "GenshinImpact")};
		TAGList play_games = new TAGList("play_games", 8, _play_games);
		TAGCompound root = new TAGCompound("", new TAGComponent[]{name, age, height, play_games});
		System.out.println(root);

		Scanner scan = new Scanner(System.in);
		System.out.print("File path: ");
		Path path = Paths.get(scan.nextLine());

//		TAGComponent c = Input(path);
//		System.out.println((c.getHeader()) + c.toString());

		Output(path, root);

		scan.close();
	}

	public static TAGComponent Input(Path path){
		TAGComponent c = null;
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
			c = TAGComponent.Analyze(data_array);
			dIS.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static void Output(Path path, TAGComponent c){
		byte[] data = c.getBytes();
		for (int i = 0; i < data.length; i++) {
			System.out.printf("%02x ", data[i]);
			if((i + 1) % 12 == 0) System.out.println();
		}
		try{
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(Files.newOutputStream(path))));
			dos.write(c.getBytes());
			dos.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
