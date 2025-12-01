package examples.helloworld;

public class HelloWorld {

    public static void main(String[] args) {
        System.out.println(getMessage(1));
    }

    /**
     * Returns "Hello, World!" repeated count times, each on a new line.
     * If count <= 0, throws an IllegalArgumentException.
     * 
     * @param count Positive integer, number of times to repeat message.
     * 
     * @return String with "Hello, World!" repeated `count` times. Each repetition is on its own line.
     * 
     * @throws IllegalArgumentException If `count <= 0`.
     */
    public static String getMessage(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be a positive integer.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("Hello, World!");
            if (i < count - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
