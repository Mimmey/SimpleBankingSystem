package banking;

import java.util.Random;

public class MathOps {
    public static final String STARTER = "400000";
    public static final int CARD_NUMBER_OF_DIGITS = 16;
    public static final int PIN_NUMBER_OF_DIGITS = 4;

    public static int checksum(String str) {
        char[] digits = str.toCharArray();
        int sum = 0;

        for (int i = 0; i < digits.length; i++) {
            digits[i] -= '0';
        }

        for (int i = 0; i < digits.length; i += 2) {
            digits[i] *= 2;
            if (digits[i] > 9) {
                digits[i] -= 9;
            }
        }

        for (char digit : digits) {
            sum += digit;
        }

        return sum;
    }

    public static String luhnAlgo(String str) {
        return str + (10 - checksum(str) % 10) % 10;
    }

    public static boolean luhnAlgoCheck(String number) {
        char last = number.charAt(number.length() - 1);
        String sbWithoutLast = number.substring(0, number.length() - 1);

        int sum = checksum(sbWithoutLast);
        sum += last - '0';

        return sum % 10 == 0;
    }

    public static String generateCardNumber() {
        StringBuilder number = new StringBuilder();
        number.append(STARTER);

        Random random = new Random(Math.round(Math.random() * 10000));

        for(int i = 0; i < CARD_NUMBER_OF_DIGITS - STARTER.length() - 1; i++) {
            number.append(random.nextInt(10));
        }

        return luhnAlgo(number.toString());
    }

    public static String generatePIN() {
        StringBuilder number = new StringBuilder();

        Random random = new Random(Math.round(Math.random() * 10000));

        for(int i = 0; i < PIN_NUMBER_OF_DIGITS; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }
}
