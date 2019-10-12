package helperLib;

public enum correctAnswer {
    A, B, C, D;

    private correctAnswer(){}

    public String value(){ return name();}

    public static correctAnswer fromValue(String v) { return valueOf(v);}
}
