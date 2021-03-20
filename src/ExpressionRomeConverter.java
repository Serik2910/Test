import java.util.Scanner;

public class ExpressionRomeConverter implements Operation {
    private int a;
    private int b;
    private int result;
    private String[] operands;
    private DigitCulture digitCulture = DigitCulture.NotAssigned;
    private Character operation;
    public ExpressionRomeConverter(String Expression)
    {
        try {
            Validate(Expression);
        } catch (IllegalOfOperandsException e) {
            System.out.println(e.GetMessage());
        }
        catch (Exception e)
        {
            System.out.println("Некорректный ввод");
        }
    }
    public void Validate(String Expression) throws IllegalOfOperandsException {
        String operation_pattern = "[\\x2D*/+]";
        operands = Expression.split(operation_pattern);
        if(operands.length != 2)
        {
            throw new IllegalOfOperandsException("Неверное кол-во операндов");
        }
        for (int i = 0; i< operands.length;i++)
        {
            String temp = operands[i];
            operands[i] = operands[i].trim();
            if (operands[i].equals(""))
            {
                throw new IllegalOfOperandsException("Неверно расположена операция");
            }
            if (i == 1) {
                operation = Expression.charAt(Expression.indexOf(temp) - 1);
                try {
                    b = ParseRomeInteger(operands[i]);
                }
                catch (IllegalOfOperandsException ex)
                {
                    System.out.println(ex.GetMessage());
                }
            } else {
                try {
                    a = ParseRomeInteger(operands[i]);
                }
                catch (IllegalOfOperandsException ex)
                {
                    System.out.println(ex.GetMessage());
                }
            }
        }
    }
    private int ParseRomeInteger(String operand) throws IllegalOfOperandsException {
        String arabic_pattern = "[0-9]*";
        String rome_pattern = "[IVX]*";
        Scanner scanner = new Scanner(operand);
        if(scanner.hasNext(arabic_pattern))
        {
            if (digitCulture == DigitCulture.Rome)
                throw new IllegalOfOperandsException("Неверное сочетание операндов");
            digitCulture=DigitCulture.Arabic;
            int result = scanner.nextInt();
            if (result<0 || result >10)
                throw new IllegalOfOperandsException("Числа должны быть >0 и <10");
            return result;
        }
        else if(scanner.hasNext(rome_pattern)) {
            if (digitCulture == DigitCulture.Arabic)
                throw new IllegalOfOperandsException("Неверное сочетание операндов");
            digitCulture=DigitCulture.Rome;
            int current = 0; // the current Roman numeral character to Arabic
            int previous = 0; // start previous at zero, that way when
            int arabic = 0; // Arabic numeral equivalent of the part of the string
            Character prev_char = '0';
            for (int i = 0; i < operand.length(); i++) {
                char letter = operand.charAt(i);
                switch (letter) {
                    case ('I'):
                        current = 1;
                        break;
                    case ('V'):
                        current = 5;
                        if (prev_char == 'I') {
                            current = 3;
                        }
                        break;
                    case ('X'):
                        current = 10;
                        if (prev_char == 'I') {
                            current = 8;
                        }
                        break;
                    case ('L'):
                        current = 50;
                        if (prev_char == 'X') {
                            current = 30;
                        }
                        break;
                    case ('C'):
                        current = 100;
                        if (prev_char == 'X') {
                            current = 80;
                        }
                        break;
                    case ('D'):
                        current = 500;
                        if (prev_char == 'C') {
                            current = 300;
                        }
                        break;
                    case ('M'):
                        current = 1000;
                        if (prev_char == 'C') {
                            current = 800;
                        }
                        break;
                }
                arabic += current;
                previous = current;
                prev_char = letter;
            }
            return arabic;
        }
        else
        {
            throw new IllegalOfOperandsException("Не предвиденная ошибка");
        }
    }
    @Override
    public String calculate() {
        switch (operation)
        {
            case ('*'):
                this.result = a*b;
                break;
            case ('/'):
                this.result = a/b;
                break;
            case ('-'):
                this.result = a-b;
                break;
            default:
                this.result = a+b;
        }
        if (digitCulture == DigitCulture.Arabic)
        {
            return Integer.toString(this.result);
        }
        {
            Integer ArabicInt = this.result;
            int remainder = this.result;
            String ArabicStr = ArabicInt.toString();
            String RomanFinal = "";
            String PrevRomanValue = "";
            for (int i=ArabicStr.length()-1;i>=0;i--)
            {
                int ten_divider = (int)Math.pow(10,i);
                int value = remainder/ten_divider;
                remainder = remainder%ten_divider;
                String RomanValue = "";
                String RomanValueMiddle = "";
                switch (i)
                {
                    case (3):
                        RomanValue="M";
                        break;
                    case(2):
                        RomanValue="C";
                        RomanValueMiddle ="D";
                        break;
                    case(1):
                        RomanValue="X";
                        RomanValueMiddle ="L";
                        break;
                    default:
                        RomanValue="I";
                        RomanValueMiddle="V";
                }
                if(RomanValueMiddle == "")
                {
                    for (int j = 0; j<value ; j++)
                    {
                        RomanFinal+=RomanValue;
                    }
                }
                else
                {
                    int Result = value/5;
                    int RemainderHalf = value%5;
                    if (RemainderHalf == 4 ) {
                        if (Result == 1) {
                            RomanFinal += (RomanValue + PrevRomanValue);
                        } else if (Result == 0) {
                            RomanFinal += (RomanValue + RomanValueMiddle);
                        }
                    }
                    else
                    {
                        if (Result == 1) {
                            RomanFinal += RomanValueMiddle;
                        }
                        for (int j = 0; j<RemainderHalf ; j++)
                        {
                            RomanFinal+=RomanValue;
                        }
                    }
                }
                PrevRomanValue = RomanValue;
            }
            return RomanFinal;
        }
    }
}
