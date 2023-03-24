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
        String note;
        if (this.annotation == null) note = "";
        else note = this.annotation;
        return this.text + "\nNote: " + note + "\n";
    }
}
