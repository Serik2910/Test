import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        String s = "    VII    /  III";
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        ExpressionRomeConverter express = new ExpressionRomeConverter(s);
        System.out.println(express.calculate());
    }
}
