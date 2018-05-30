package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IdMatch {

	private static final File file = new File("C:\\Users\\Karlo Mendoza\\Downloads\\enoviaPlzDontKillMe.txt");

	private static final File fileNames = new File("C:\\Users\\Karlo Mendoza\\Downloads\\enoviaNamesDWID.txt");

	public static void main(String... strings) throws IOException {

		Map<String, String> map = new HashMap<>();
		Map<String, String> map2 = new HashMap<>();
		try (BufferedReader read = new BufferedReader(new FileReader(fileNames))) {

			int i = 0;
			String line;
			while ((line = read.readLine()) != null) {
				// System.out.println(i++);
				String[] split = line.split("	");
				map.put(split[2].trim(), split[0].trim());
				map2.put(split[1].trim(), split[2].trim());
			}
		}

		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] split = line.split("	");
				// System.out.println(i++);
				String number = split[0].trim();
				String description = split[1].trim();

				String matchingKey = "";
				for (String name : map2.keySet()) {
					if (name.contains(description)) {
						// if (name.equals(number)) {
						matchingKey = map2.get(name);

						if (matchingKey.contains(number))
							System.out.println(split[0].trim() + "	" + map.get(name) + "	" + name);

						// break;
					}
				}

				// for (String name : map.keySet()) {
				// if (name.contains(number)) {
				// // if (name.equals(number)) {
				// System.out.println(split[0].trim() + " " + map.get(name) + " " + name);
				// }
				// }

			}
		}
	}
}
