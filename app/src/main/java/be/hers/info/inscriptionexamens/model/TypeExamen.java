package be.hers.info.inscriptionexamens.model;

public enum TypeExamen
{
    EcritPapier("Ecrit papier"),
    EcritPc("Ecrit sur PC"),
    Oral("Oral");

    public final String label;

    TypeExamen(String s)
    {
        this.label = s;
    }

    public static TypeExamen findByLabel(String s){
        for(TypeExamen el : values())
        {
            if( el.label.equals(s))
                return el;
        }
        return null;
    }
}
