package helperLib;

public enum categories {
    CatA, CatB, CatC, CatD;

    private categories(){}

    public String value(){ return name();}

    public static categories fromValue(String v) { return valueOf(v);}
}
