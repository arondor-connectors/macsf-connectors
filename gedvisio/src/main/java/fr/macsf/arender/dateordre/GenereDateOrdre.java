package fr.macsf.arender.dateordre;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GenereDateOrdre
{

    private static final int NUMBER_OF_SECOND_IN_HOUR = 3600;

    private static final int NUMBER_OF_SECOND_IN_MINUTE = 60;

    private static final int DEFAULT_MAX_NUMBER_OF_CALL = 100000;

    /**
     * Last ID provided by the generator
     */
    private int lastCalled = 0;

    /**
     * Maximum number of calls per day
     */
    private int maxNumberOfCalls = DEFAULT_MAX_NUMBER_OF_CALL;

    /**
     * Current number of calls in the day
     */
    private int numberOfCalls = 0;

    /**
     * Last day we generated an ID
     */
    private Date lastDate = new Date();

    /**
     * Prefixe de l'ordre
     */
    private int prefixeOrdre = 0; // 100000;
    
    public GenereDateOrdre()
    {
    }
    
    public static class DateOrdre
    {
        private final Date date;
        
        private final int ordre;
        
        public DateOrdre(Date date, int ordre)
        {
            this.date = date;
            this.ordre = ordre;
        }

        public Date getDate()
        {
            return date;
        }

        public int getOrdre()
        {
            return ordre;
        }
    }

    public synchronized DateOrdre generateDateOrdreObject()
    {
        Date maDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(maDate);

        incrementNumberOfCalls(maDate);

        int secondsSinceMidnight =  getSecondsSinceMidnight(calendar);
        DateOrdre dateOrder = new DateOrdre(maDate, secondsSinceMidnight + getPrefixeOrdre());
        
        return dateOrder;
    }
    
    public String genereDateOrdre()
    {
        DateOrdre dateOrdre = generateDateOrdreObject();
        String dateOrdreString = getDateOfDay(dateOrdre.getDate()) + "-" + dateOrdre.getOrdre();

        return dateOrdreString;
    }

    protected void incrementNumberOfCalls(Date myDate)
    {

        if (getDateOfDay(lastDate).equals(getDateOfDay(myDate)))
        {

            if (numberOfCalls >= maxNumberOfCalls)
            {
                throw new IndexOutOfBoundsException("numberOfCalls >= maxNumberOfCalls : " + numberOfCalls + " >= "
                        + maxNumberOfCalls);
            }
            numberOfCalls++;
        }

        else
        {
            numberOfCalls = 0;
            lastCalled = 0;
        }

        lastDate = myDate;

    }

    private String getDateOfDay(Date maDate)
    {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return dateFormat.format(maDate);
    }

    protected int getSecondsSinceMidnight(Calendar calendar)
    {

        final int hours = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        final int seconds = calendar.get(Calendar.SECOND);

        final int secondsSinceMidnight = (hours * NUMBER_OF_SECOND_IN_HOUR + minutes * NUMBER_OF_SECOND_IN_MINUTE + seconds);

        int uniqueIdInDay;

        if (lastCalled < secondsSinceMidnight)
        {
            uniqueIdInDay = secondsSinceMidnight;
        }
        else
        {
            lastCalled++;
            uniqueIdInDay = lastCalled;
        }
        lastCalled = uniqueIdInDay;

        return uniqueIdInDay;
    }

    public int getMax()
    {
        return maxNumberOfCalls;
    }

    public void setMax(int max)
    {
        this.maxNumberOfCalls = max;
    }

    public int getPrefixeOrdre()
    {
        return prefixeOrdre;
    }

    public void setPrefixeOrdre(int prefixeOrdre)
    {
        this.prefixeOrdre = prefixeOrdre;
    }

}
