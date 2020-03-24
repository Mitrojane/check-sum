public class Per{ // класс для вычисления вероятности ошибки декодера
	Polynom g; // порождающий полином
	int mesLength; // длина кодовой последовательности
	int eps; // точность вычисления вероятности ошибки декодера
	public Per(Polynom or, int length, double e) {
		g = or;
		mesLength = length;
    	double r = 1/e;
    	eps = (int) r;
    	
	}
	public Polynom division(Polynom left, Polynom right) {// деление многочлена на многочлен
		boolean end = false;
		Polynom rightCopy = new Polynom(right);
		Polynom leftCopy = new Polynom(left);
		Polynom res = new Polynom(leftCopy.degree);// переменная для хранения отстатка от деления
		while (res.degree >= right.degree) { // делим, пока степень частного больше степени делителя
			shifting(rightCopy, leftCopy.degree - rightCopy.degree);// сдвиг делителя до старшей степени делимого
			for (int i = leftCopy.degree; i >= 0; i--) {
				res.polynom[i]=leftCopy.polynom[i] ^ rightCopy.polynom[i]; // xor 
			}
			int shift = res.degree;//удадение лишних нулей перед старшей степенью в остатке от деления
			while (res.polynom[shift] == false) {
				shift--;
				if (shift < 0) {
					end = true;	// если все коэффициенты полинома равны 0, то меняем флаг на true
					break;
				}
			}
			if (end) { // если все коэффициенты полинома равны 0, то выходим из функции и возвращаем null
				res = null;
				break;
			}
			Polynom temp = new Polynom(shift);
			for (int i = shift; i >= 0; i--) {
				temp.polynom[i] = res.polynom[i];
			}
			res.copy(temp);// перезапись переменных для поддержания цикла
			leftCopy.copy(temp);
			rightCopy.copy(right);
		}
		return res;
	}
	
	public void shifting (Polynom cur, int size) { // функция сдвига полинома на size позиций влево
		Polynom tmp = new Polynom(cur.degree + size);
		for (int i = tmp.degree; i >= size; i--) {// запись старших разрядов
			tmp.polynom[i] = cur.polynom[i - size];
		}
		for (int i = size - 1; i >= 0; i--) {// заполнение младших разрядов нулями
			tmp.polynom[i] = false;
		}
		cur.copy(tmp);
	}
	
	public void rand(boolean[] ar, int size, double p) {// формирование случайного вектора длины size с вероятностью появления "1" равной p
        for (int i = 0; i < size; i++) {
        	double a = Math.random();
        	if (a >= p) ar[i] = false;
        	else ar[i] = true;
        }
	}
	
	public Polynom sum(Polynom left, Polynom right) { // сложение векторов
		if (right == null) return left; // если один из векторов равен 0, то возвращаем второй
		if (left == null) return right;
		else {
			Polynom res = new Polynom(Math.max(left.degree, right.degree));// степень суммарного вектора будет равна максимальной степени слагаемых векторов
			if (left.degree == right.degree) { // если степени векторов равны
				for (int i = 0; i <= left.degree; i++) {
					res.polynom[i] = left.polynom[i] ^ right.polynom[i];
				}
			}
			else {
			if (left.degree <= right.degree) { // если степень первого многочлена меньше
				for (int i = 0; i <= left.degree; i++) {
					res.polynom[i] = left.polynom[i] ^ right.polynom[i];
				}
				for (int i = left.degree+1; i <= right.degree; i++)
					res.polynom[i] = false ^ right.polynom[i];
			}
			else {// если степень первого многочлена больше
					for (int i = 0; i <= right.degree; i++) {
						res.polynom[i] = left.polynom[i] ^ right.polynom[i];
					}
					for (int i = right.degree+1; i <= left.degree; i++)
						res.polynom[i] = false ^ left.polynom[i];
					}	
			}
			return res;
		}

	}
	
	public double pe(double p) { // функция вычисления вероятности ошибки декодера при заданной вероятности p ошибки в канале
		int Ne = 0;// число ошибок декодера
		int N;// число экспериментов
		boolean[] array = new boolean[mesLength];

    	for (N = 0; N < eps; N++) {
    		rand(array, mesLength, 0.5);// формирование случайной кодовой последовательности
    		Polynom message = new Polynom(mesLength-1, array);// полином кодовой последовательности
	        Polynom mcopy = new Polynom(message); // копия полинома кодой пос-ти
	        shifting(mcopy, g.degree);// формирование контрольной суммы
	        Polynom c = division(mcopy, g);// формирование контрольной суммы
	        Polynom  a = sum(mcopy, c);// кодовое слово
	        
	        boolean[] e = new boolean[a.degree + 1];// вектор ошибки
	        rand(e, a.degree + 1, p);// формирование вектора ошибок с вероятностью появления ошибки равной p
	        Polynom error = new Polynom (a.degree, e);
	        Polynom b = sum(a, error);// принятое сообщение
	        Polynom res = division(b, g);// проверка декодера по утверждению 1
	        if (res == null & !error.isNull()){// подсчет ошибок декодера
	        	Ne++;
	        }
    	}
    return (double)Ne/(double)N;// возвращаем вероятноть ошибки декодера
	}	
}
