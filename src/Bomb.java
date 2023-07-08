import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Bomb {
    public static final String PASSWORD_FILE = "src/passwords.txt";
    public static final String BOMB_FILE = "src/Bomb.java";

    public static void main(String[] args) {
        checkBombFileNotModified();
        List<String> passwords = readFile(PASSWORD_FILE);
        phase0(passwords.get(0));
        phase1(passwords.get(1));
        phase2(passwords.get(2));
    }

    private static void phase0(String password) {
        System.out.println("Begin phase 0");
        System.out.println("You entered the password " + password);
        String correctPassword = shuffle("hello", "phase0");
        if (!password.equals(correctPassword)) {
            System.err.println("Phase 0 went BOOM!");
            System.exit(0);
        }
        System.out.println("You passed Phase 0!");
    }

    private static void phase1(String password) {
        System.out.println("Begin phase 1");
        System.out.println("You entered the password " + password);
        int[] ar1 = shuffle("there", "phase1").codePoints().toArray();
        int[] ar2 = parseInts(password);
        if (!Arrays.equals(ar1, ar2)) {
            System.err.println("Phase 1 went BOOM!");
            System.exit(0);
        }
        System.out.println("You passed Phase 1!");
    }

    private static void phase2(String password) {
        System.out.println("Begin phase 2");
        System.out.println("You entered the password " + password);
        int[] ar = parseInts(password);

        int r = hashBomb("phase2");
        int[] numbers = new int[100000];
        for (int a = 0; a < numbers.length; a++) {
            r = lfsr(r);
            numbers[a] = r;
        }
        Arrays.sort(numbers);

        boolean correct = false;
        int i = 0;
        for (int number: numbers) {
            if (i == 1337 && ar[i] == number) {
                correct = true;
            }
            i += 1;
        }
        if (!correct) {
            System.err.println("Phase 2 went BOOM!");
            System.exit(0);
        }
        System.out.println("You passed Phase 2!");
    }

    /**
     * Randomly shuffles the letters in the original string to make a new string
     * For example, abcdefg may be shuffled into dcgfeba
     */
    private static String shuffle(String original, String seedString) {
        char[] chars = original.toCharArray();
        int seed = Math.abs(hashBomb(seedString));
        for (int a = 0; a < chars.length - 1; a++) {
            int remaining = chars.length - a;
            char h = chars[a];
            chars[a] = chars[seed % remaining];
            chars[seed % remaining] = h;
            seed /= remaining;
        }
        return new String(chars);
    }

    /**
     * Turns a string like "1 2 3" into an array like [1, 2, 3]
     */
    private static int[] parseInts(String s) {
        String[] values = s.split(" ");
        int[] intValues = new int[values.length];
        for (int a = 0; a < values.length; a++) {
            intValues[a] = Integer.parseInt(values[a]);
        }
        return intValues;
    }

    /**
     * 32-bit linear feedback shift register, with taps at 32, 22, 2, 1
     * corresponds to the polynomial x^32 + x^22 + x^2 + x + 1
     */
    private static int lfsr(int input) {
        int bit = (input ^ (input >> 10) ^ (input >> 30) ^ (input >> 31)) & 1;
        return (input >>> 1) | (bit << 31);
    }

    private static void checkBombFileNotModified() {
        if (hashBomb("check") % 100000 != 40052) {
            System.err.println("Bomb has been tampered with! Restore Bomb.java to continue.");
            System.exit(0);
        }
    }

    private static int hashBomb(String delimiter) {
        return String.join(delimiter, readFile(BOMB_FILE)).hashCode();
    }

    public static List<String> readFile(String filename) {
        FileReader fr = null;
        try {
             fr = new FileReader(filename);
        } catch (IOException e) {
            System.err.println("File does not exist: " + filename);
            System.exit(0);
        }
        BufferedReader br = new BufferedReader(fr);
        return br.lines().toList();
    }
}
