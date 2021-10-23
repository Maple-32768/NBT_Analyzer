public class TestNBT {

	public static void main(String[] args) {
		TAGInt age = new TAGInt("age", 17);
		TAGFloat height = new TAGFloat("height", 169.5f);
		TAGString name = new TAGString("name", "Maple32768");
		TAGComponent[] _play_games = {new TAGString("", "Minecraft"), new TAGString("", "GenshinImpact")};
		TAGList play_games = new TAGList("play_games", 8, _play_games);
		TAGCompound root = new TAGCompound("", new TAGComponent[]{name, age, height, play_games});
		System.out.println(root);
	}

}
