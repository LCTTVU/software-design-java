package softwaredesign;

public class Instruction {
    String text;
    String note;

    Instruction(String text, String note) {
        this.text = text;
        this.note = note;
    }


    @Override
    public String toString() {
        return this.text + "\n";
    }
}
