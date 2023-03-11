package softwaredesign;

public class Main {

    public static Frame currFrame;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        currFrame = GUI.getInstance().currFrame;
    }

}
