package helperLib;

public enum userTypes {
    Admin, SuperAdmin, Applicant;

    private userTypes(){}

    public String value(){ return name();}

    public static userTypes fromValue(String v) { return valueOf(v);}
}
