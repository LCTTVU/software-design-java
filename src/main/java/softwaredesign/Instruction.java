package softwaredesign;

public class Instruction {
    private final String text;
    private String annotation;

    Instruction(String text, String annotation) {
        this.text = text;
        this.annotation = annotation;
    }

    public String getText() {
        return this.text;
    }

    public String getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(String newAnnotation) {
        this.annotation = newAnnotation;
    }



    @Override
    public String toString() {
        String note = (this.annotation == null) ? ("") : (this.annotation);
        return this.text + "\n  + Note: " + note + "\n";
    }
}
