package softwaredesign;

public class Instruction {
    String text;
    String annotation;

    Instruction(String text, String annotation) {
        this.text = text;
        this.annotation = annotation;
    }


    @Override
    public String toString() {
        return this.text + "\n";
    }
}
