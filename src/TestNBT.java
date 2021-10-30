public class TestNBT {

	public static void main(String[] args) {
		TAGInt age = new TAGInt("age", 17);
		TAGFloat height = new TAGFloat("height", 169.5f);
		TAGString name = new TAGString("name", "Maple32768");
		TAGList play_games = new TAGList("play_games");
		play_games.add(new TAGString("", "Minecraft"));
		play_games.add(new TAGString("", "GenshinImpact"));
		TAGCompound root = new TAGCompound();
		root.put(age);
		root.put(height);
		root.put(name);
		root.put(play_games);
		System.out.println(root);
		System.out.println(root.getValue().get("age"));
		byte[] data = root.getBytes();
		for (int i = 0; i < data.length; i++) {
			System.out.printf("%02x ", data[i]);
			if((i + 1) % 12 == 0) System.out.println();
		}
	}

}
