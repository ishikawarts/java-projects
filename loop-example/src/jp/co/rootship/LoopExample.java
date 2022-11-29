package jp.co.rootship;

import java.util.Iterator;
import java.util.List;

/**
 * ループ処理のサンプルコード
 */
public class LoopExample {

	public static void main(String[] args) {

		List<Person> persons = List.of(
				new Person("坂本 龍馬", 21),
				new Person("織田 信長", 18),
				new Person("平 清盛", 25),
				new Person("中臣 鎌足", 31),
				new Person("西郷 隆盛", 42));
		// for
		{
			for (int index = 0; index < persons.size(); index++) {

				Person person = persons.get(index);

				// 25歳以上
				if (person.age() >= 25) {

					String text = person.name() + "さん、" + person.age() + "歳";

					System.out.println(text);
				}
			}
		}
		System.out.println("-----------------------");

		// for each
		{
			for (Person person : persons) {

				// 25歳以上
				if (person.age() >= 25) {

					String text = person.name() + "さん、" + person.age() + "歳";

					System.out.println(text);
				}
			}
		}
		System.out.println("-----------------------");

		// Stream
		{
			persons.stream()
					.filter(person -> person.age() >= 25) // 25歳以上
					.map(person -> person.name() + "さん、" + person.age() + "歳")
					.forEach(text -> System.out.println(text));
		}
		System.out.println("-----------------------");

		// while
		{
			int index = 0;

			while (index < persons.size()) {

				Person person = persons.get(index);

				// 25歳以上
				if (person.age() >= 25) {

					String text = person.name() + "さん、" + person.age() + "歳";

					System.out.println(text);
				}

				index++;
			}
		}
		System.out.println("-----------------------");

		// iterator
		{
			Iterator<Person> iterator = persons.iterator();

			while (iterator.hasNext()) {

				Person person = iterator.next();

				// 25歳以上
				if (person.age() >= 25) {

					String text = person.name() + "さん、" + person.age() + "歳";

					System.out.println(text);
				}
			}
		}
	}

}
