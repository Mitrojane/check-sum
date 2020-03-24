public class Polynom { // класс для хранения двоичного полинома
	public int degree; // степень многочлена
	public boolean[] polynom; // массив коэффициентов многочлена
	public Polynom(int d) { // конструктор по степень многочлена
		degree= d;
		polynom = new boolean[degree+1];
	}
	public Polynom(int d, boolean[] array) { // конструктор по степени многочлена и массиву коэффициентов многочлена
		degree= d;
		polynom = array;
	}
	public Polynom(Polynom or) { // конструктор для создание копии полинома
		degree = or.degree;
		polynom = or.polynom;
	}
	public void copy(Polynom original) { // копирование полинома
		degree= original.degree;
		polynom = original.polynom;
	}
	
	public boolean isNull() { // проверка полинома на на 0
		boolean ok = true;
		for (int i = 0; i <= degree; i++) {
			if (polynom[i] == true) {
				ok = false;
				break;
			}
		}
		return ok;
	}
}
