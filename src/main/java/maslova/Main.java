package maslova;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // 4 -> 2 : true
    public static Boolean checkNumberIsSquare(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number should be greater than zero");
        }
        for (int i = 1; i * i <= number; i++) {
            if (i * i == number) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
    }
}