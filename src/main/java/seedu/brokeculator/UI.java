package seedu.brokeculator;

public class UI {
    private static final String STRING_DECORATION = "------------------------------------";
    public static String prettify(String message, String topLineDecoration, String bottomLineDecoration) {
        return topLineDecoration + System.lineSeparator()
                + message + System.lineSeparator() + bottomLineDecoration;
    }

    /**
     *  prints out message to stdout with the decorators ------ at the top and bottom of the file
     * @param message the message to print
     */
    public static void print(String message){
        System.out.println(prettify(message, STRING_DECORATION, STRING_DECORATION));
    }
}
