import java.io.*;
public class Main {
    public static void main(String[] args) {
    	double eps = 0.001;// точность вычисления вероятности ошибки декодера
    	int l = 6;// длина кодовой последовательности
    	StringBuilder text = new StringBuilder();// формирование вектора 
    	boolean[] arG = {true, true, false, true};//порождающий многочлен
        Polynom g = new Polynom(3, arG);//порождающий многочлен
        Per Perror = new Per(g, l, eps);// класс для вычисления вероятности ошибки декодера
        for (double p = 0; p < 1; p += 0.01) { // изменение вероятности появления ошибки в канале
            double Pe = Perror.pe(p); // вероятность ошибки декодера при заданной вероятности ошибки в канале
            text.append(Pe);
            text.append(' ');
        }
    	 try(FileWriter writer = new FileWriter("/home/jane/eclipse-workspace/lab1/src/output6_0.001.txt")) {
    	     System.out.println(text.toString());// вывод сформированного вектора в файл для построения графика в матлаб
    		 writer.write(text.toString());
             writer.flush();
             }
             catch(IOException ex){
                 System.out.println(ex.getMessage());
             }
        }   
}
