package Controller;

import Model.Model;

public final class Jarvis {

	private static Jarvis jarvis = new Jarvis();

	private Model[] arrayComps = { new Model("Asus", "X541S", "540"), new Model("Asus", "X341S", "340"),
			new Model("Asus", "X541S", "540"), new Model("Asus", "X341S", "340"), new Model("Samsung", "R126S", "790"),
			new Model("HP", "Y126S", "390"), new Model("Samsung", "N126S", "890"), new Model("HP", "M126S", "780") };;
	private Model[] arrayMouses = { new Model("mouse", "mouse", "123"), new Model("mouse", "mouse", "321") };
	private Model[] arrayKeyboards = { new Model("eyboard", "eyboard", "1298"),
			new Model("eyboard", "eyboard", "54") };;
	private Model[] array = { new Model("Выберите список устройств", "-", "-") };

	private int counterComp = arrayComps.length;
	private int counterMouses = arrayMouses.length;
	private int counterKeyboard = arrayKeyboards.length;

	private int counter = 0;

	private String nameArray;

	private Jarvis() {
	}

	public static Jarvis getJarvis() {
		if (jarvis == null) {
			jarvis = new Jarvis();
		}
		return jarvis;
	}

	public Model[] getArray(String nameArray) {
		array = updateArray(nameArray, false);
		return array;
	}

	private Model[] updateArray(String nameArray, boolean isDataManipulation) {
		switch (nameArray) {
		case "Компьютеры":
			this.nameArray = nameArray;
			if (isDataManipulation) {
				arrayComps = array;
				counterComp = counter;
			}
			counter = counterComp;
			return arrayComps;
		case "Мышки":
			this.nameArray = nameArray;
			if (isDataManipulation) {
				arrayMouses = array;
				counterMouses = counter;
			}
			counter = counterMouses;
			return arrayMouses;
		case "Клавиатуры":
			this.nameArray = nameArray;
			if (isDataManipulation) {
				arrayKeyboards = array;
				counterKeyboard = counter;
			}
			counter = counterKeyboard;
			return arrayKeyboards;
		}
		return array;
	}

	public void addModel(Model model) {
		updateArray(nameArray, true);
		if (counter == 0) {
			Model[] newArray = new Model[1];
			newArray[0] = model;
			counter++;
			array = newArray;
		} else if (array != null && array.length > counter) {
			array[counter] = model;
			counter++;
		} else {
			Model[] newArray = new Model[counter + 5];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[counter] = model;
			counter++;
			array = newArray;
		}
		updateArray(nameArray, true);
	}

	public String search(String manufacturer) {
		if (array == null) {
			return "Ошибка: Устройство найдено не был!";
		}
		StringBuilder found = new StringBuilder("");
		int counter = 1;
		for (Model Model : array) {
			if (Model != null && manufacturer.equals(Model.getManufacturer())) {
				found.append(counter++ + "." + Model.getNumberModel() + " " + Model.getPrice() + "\n");
			}
		}
		if (found.length() > 0) {
			return found.toString();
		} else {
			return "Ошибка: Устройство найдено не был!";
		}

	}

	public String delete(Model model) {
		updateArray(nameArray, true);
		if (array == null) {
			return "Ошибка: Устройство найдено не было!";
		}
		String massage = null;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && model.equals(array[i])) {
				massage = "Устройство " + array[i].getNumberModel() + " " + array[i].getPrice() + " было удалено.";
				System.arraycopy(array, i + 1, array, i, array.length - 1 - i);
				Model[] newArray = new Model[array.length - 1];
				System.arraycopy(array, 0, newArray, 0, newArray.length);
				array = newArray;
				counter--;
				updateArray(nameArray, true);
				return massage;
			}
		}
		return "Ошибка: Устройство найдено не было!";
	}

	public Model[] getArrayComps() {
		return arrayComps;
	}

	public void setArrayComps(Model[] arrayComps) {
		this.arrayComps = arrayComps;
	}

	public Model[] getArrayMouses() {
		return arrayMouses;
	}

	public void setArrayMouses(Model[] arrayMouses) {
		this.arrayMouses = arrayMouses;
	}

	public Model[] getArrayKeyboards() {
		return arrayKeyboards;
	}

	public void setArrayKeyboards(Model[] arrayKeyboards) {
		this.arrayKeyboards = arrayKeyboards;
	}

}