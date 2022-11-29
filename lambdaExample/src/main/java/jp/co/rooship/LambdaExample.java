package jp.co.rooship;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ラムダ式を使ったサンプルコード
 */
public class LambdaExample {

	/**
	 * メイン
	 * @param args
	 */
	public static void main(String[] args) {
		// ラムダ式を使ったサンプルコードの実行
		lambdaExample();

		System.out.println("--------------------");

		// 関数型インターフェースを使ったサンプルコードの実行
		functionalInterfaceExample();

		System.out.println("--------------------");

		// Stream APIを使ったサンプルコードの実行
		streamApiExample();

	}

	/**
	 * ラムダ式を使ったサンプルコード
	 */
	private static void lambdaExample() {
		// 匿名クラス
		{
			Comparator<String> comparator = new Comparator<String>() {

				@Override
				public int compare(String a, String b) {

					return Integer.compare(a.length(), b.length());

				}
			};

			System.out.println(comparator.compare("A", "BB")); // -1
		}

		// ラムダ式
		{
			Comparator<String> comparator = (a, b) -> Integer.compare(a.length(), b.length());

			System.out.println(comparator.compare("A", "BB")); // -1
		}

	}

	/**
	 * 関数型インターフェースを使ったサンプルコード
	 */
	private static void functionalInterfaceExample() {

		// Function 1
		{
			Function<List<String>, String> function = list -> list.isEmpty() ? "list is empty" : list.get(0);

			List<String> list = Arrays.asList("apple", "amazon", "google");

			System.out.println(function.apply(list)); // apple
			
			System.out.println(function.apply(List.of())); // list is empty
			
		}

		// Function 2
		{
			Function<String, ZonedDateTime> now = zoneId -> {

				if (zoneId == null || zoneId.length() == 0) {
					return ZonedDateTime.now(Clock.systemUTC());
				}

				return ZonedDateTime.now(ZoneId.of(zoneId));

			};
			System.out.println(now.apply(null)); // UCT

			System.out.println(now.apply("Europe/Paris")); // パリの時刻

			System.out.println(now.apply("Asia/Tokyo")); // 日本の時刻

		}

		// Consumer 
		{
			Consumer<Integer> consumer = number -> System.out.println(number);

			consumer.accept(1234); // 1234

		}

		// Predicate 
		{
			Predicate<String> isEmpty = string -> string == null || string.length() == 0;

			System.out.println(isEmpty.test(null)); // true

			System.out.println(isEmpty.test("microsoft")); // false

		}

	}

	/**
	 * Stream APIを使ったサンプルコード
	 */
	private static void streamApiExample() {

		List<Person> people = Arrays.asList(new Person("田中", LocalDateTime.of(2001, 5, 12, 0, 0)),
				new Person("髙橋", LocalDateTime.of(1995, 10, 3, 0, 0)),
				new Person("伊藤", LocalDateTime.of(2003, 2, 22, 0, 0)),
				new Person("佐藤", LocalDateTime.of(1998, 3, 1, 0, 0)));
		// for
		{
			for (Person person : people) {

				if (person.birthday().getYear() > 2000) {

					String text = person.name() + "さん " + person.birthday().getYear() + "年生まれ";

					System.out.println(text);
				}
			}

			// 田中さん 2001年生まれ
			// 伊藤さん 2003年生まれ
		}

		// stream api
		{
			people.stream()
					.filter(person -> person.birthday().getYear() > 2000)
					.map(person -> person.name() + "さん " + person.birthday().getYear() + "年生まれ")
					.forEach(System.out::println);

			// 田中さん 2001年生まれ
			// 伊藤さん 2003年生まれ
		}

	}

}
