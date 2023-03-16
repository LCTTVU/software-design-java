package softwaredesign;

public class Instruction {
    String instruction;
    String note;

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    Instruction(String instruction, String note) {
        this.instruction = instruction;
        this.note = note;
    }


    @Override
    public String toString() {
        return "{" + this.instruction + "," + this.note + "}";
    }
}
