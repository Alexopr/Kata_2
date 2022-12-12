import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);

        System.out.print("Введите выражение(каждый элемент через пробел):");
        String output = scn.nextLine();

        System.out.println(calc(output));
    }


    public static String calc(String input) throws Exception {


        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        int [] arabianInt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        boolean indA = false;

        //Определяем есть ли арифметическое действие:
        int actionIndex=-1;
        for (int i = 0; i < actions.length; i++) {
            if(input.contains(actions[i])){
                actionIndex = i;
                break;
            }
        }
        //Делим строчку по пробелу
        String[] data = input.split(" ");
        //Если не нашли арифметического действия и ввели больше или меньше двух элементов, завершаем программу
        if(actionIndex==-1 || data.length != 3){
            throw new Exception("Некорректное выражение");
               }

        //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
        if(converter.isRoman(data[0]) == converter.isRoman(data[2])){
            int a,b;
            //Определяем, римские ли это числа
            boolean isRoman = converter.isRoman(data[0]);
            if(isRoman){
                //если римские, то конвертируем их в арабские
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[2]);

            }else{
                //если арабские, конвертируем их из строки в число
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[2]);
            }


            for (int k : arabianInt) {
                for (int i : arabianInt)
                    if (i == a & k == b) {


                        //выполняем с числами арифметическое действие
                        int result = switch (data[1]) {
                            case "+" -> a + b;
                            case "-" -> a - b;
                            case "*" -> a * b;
                            default -> a / b;
                        };
                        //если числа были арабские, возвращаем результат в арабском числе
                        if (isRoman) {
                            //если числа были римские, возвращаем результат в римском числе
                            if (result > 0) {
                                //System.out.println(converter.intToRoman(result));
                                return String.valueOf(converter.intToRoman(result));

                            } else {
                                throw new Exception("Результат вычислений римских чисел не может быть меньне 1");
                            }

                        } else {
                            //System.out.println(result);
                            return String.valueOf(result);
                        }
                    }
            }
        }else{
            throw new Exception("Числа должны быть в одном формате");

        }
        if (!indA){
            throw new Exception("Ты ввел больше 10 или меньше 1");

        }


        return input;
    }
}




    class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);


            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

        }


        boolean isRoman(String number){
            return romanKeyMap.containsKey(number.charAt(0));
        }


        String intToRoman(int number) {
            StringBuilder roman = new StringBuilder();
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman.append(arabianKeyMap.get(arabianKey));
                number -= arabianKey;
            } while (number != 0);
            return roman.toString();


        }

        int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }
            }
            return result;
        }
    }
