package be.hers.info.inscriptionexamens.model;

import java.util.Date;

public class MyDate extends Date
{
    public MyDate()
    {
        super();
    }

    public MyDate(int year, int month, int date, int hrs, int min)
    {
        // Problème de dépréciation
        super((year - 1900), month, date, hrs, min);
    }

    public MyDate(Date date)
    {
        super(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());
    }

    @Override
    public void setYear(int year)
    {
        // Problème de dépréciation
        super.setYear(year - 1901);
    }
}
