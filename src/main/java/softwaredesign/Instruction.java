package softwaredesign;

public class Instruction {
    String instruction;
    String note;

    Instruction(String instruction, String note) {
        this.instruction = instruction;
        this.note = note;
    }


    @Override
    public String toString() {
        return "{" + this.instruction + "," + this.note + "}";
    }
}
