package fr.macsf.arender.dateordre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.macsf.arender.dateordre.GenereDateOrdre.DateOrdre;

public class GenereDateOrdreTest
{
    private static final Logger LOG = Logger.getLogger(GenereDateOrdreTest.class);

    private static final int NUMBER_OF_SECOND_IN_HOUR = 3600;

    private static final int NUMBER_OF_SECOND_IN_MINUTE = 60;

    private static final int MAX_TEST = 10;

    private static final int YEAR_TEST = 2014;

    private static final int MONTH_TEST = 1;

    private static final int DAY_TEST = 1;

    private static final int HOUR_TEST = 8;

    private static final int MINUTE_TEST = 30;

    private static final int SECOND_TEST = 0;

    private GenereDateOrdre generateur;

    @Before
    public void init()
    {
        generateur = new GenereDateOrdre();
        generateur.setMax(1000);
    }

    @Test
    public void testNumOrdre() throws Exception
    {

        String chaineTest = generateur.genereDateOrdre();

        Date maDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(maDate);

        String[] chaineSplit = chaineTest.split("-");
        long numOrdre = Long.parseLong(chaineSplit[1]);

        int hours = (int) (numOrdre / NUMBER_OF_SECOND_IN_HOUR);
        int minute = (int) ((numOrdre - hours * NUMBER_OF_SECOND_IN_HOUR) / NUMBER_OF_SECOND_IN_MINUTE);

        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), hours);
        assertEquals(calendar.get(Calendar.MINUTE), minute);

    }

    @Test
    public void testDate() throws Exception
    {

        String chaineTest = generateur.genereDateOrdre();

        String[] chaineSplit = chaineTest.split("-");

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.parseObject(chaineSplit[0]);

    }

    @Test
    public void testUniqueId() throws Exception
    {

        ArrayList<String> listID = new ArrayList<String>();
        String dateOrdre;

        for (int i = 0; i < generateur.getMax(); i++)
        {

            dateOrdre = generateur.genereDateOrdre();
            assertTrue(!listID.contains(dateOrdre));
            listID.add(dateOrdre);
        }

    }

    @Test(expected = Exception.class)
    public void testMax() throws Exception
    {
        generateur.setMax(MAX_TEST);

        for (int i = 0; i < generateur.getMax() + 1; i++)
        {
            generateur.genereDateOrdre();
        }
    }

    @Test
    public void testTwoSeconds()
    {

        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_TEST, MONTH_TEST, DAY_TEST, HOUR_TEST, MINUTE_TEST, SECOND_TEST);

        int uniqueIdInDay = generateur.getSecondsSinceMidnight(calendar);

        calendar.add(Calendar.SECOND, 2);

        int uniqueIdInDay2 = generateur.getSecondsSinceMidnight(calendar);

        assertTrue(uniqueIdInDay != uniqueIdInDay2);
    }

    @Test
    public void testSameSecond()
    {

        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_TEST, MONTH_TEST, DAY_TEST, HOUR_TEST, MINUTE_TEST, SECOND_TEST);

        int uniqueIdInDay = generateur.getSecondsSinceMidnight(calendar);

        int uniqueIdInDay2 = generateur.getSecondsSinceMidnight(calendar);

        assertTrue(uniqueIdInDay != uniqueIdInDay2);
    }

    @Test
    public void testSameSecondOneMore()
    {

        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_TEST, MONTH_TEST, DAY_TEST, HOUR_TEST, MINUTE_TEST, SECOND_TEST);

        int uniqueIdInDay = generateur.getSecondsSinceMidnight(calendar);

        generateur.getSecondsSinceMidnight(calendar);

        calendar.add(Calendar.SECOND, 1);

        int uniqueIdInDay2 = generateur.getSecondsSinceMidnight(calendar);

        assertTrue(uniqueIdInDay != uniqueIdInDay2);
    }

    @Test
    public void testNextDay() throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_TEST, MONTH_TEST, DAY_TEST, HOUR_TEST, MINUTE_TEST, SECOND_TEST);

        generateur.incrementNumberOfCalls(calendar.getTime());
        int uniqueIdInDay = generateur.getSecondsSinceMidnight(calendar);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        generateur.incrementNumberOfCalls(calendar.getTime());
        int uniqueIdInDay2 = generateur.getSecondsSinceMidnight(calendar);

        assertEquals(uniqueIdInDay, uniqueIdInDay2);
    }

    @Test
    public void testPrefixeOrdre_Prefixe0() throws Exception
    {
        generateur.setMax(100000);
        generateur.setPrefixeOrdre(0);

        DateOrdre dt = generateur.generateDateOrdreObject();

        LOG.info("Ordre : " + dt.getOrdre());

        Assert.assertTrue(dt.getOrdre() >= generateur.getPrefixeOrdre());
        Assert.assertTrue(dt.getOrdre() < generateur.getPrefixeOrdre() + generateur.getMax());

    }

    @Test
    public void testPrefixeOrdre_Prefixe300k() throws Exception
    {
        generateur.setMax(100000);
        generateur.setPrefixeOrdre(300000);
        DateOrdre dt = generateur.generateDateOrdreObject();
        LOG.info("Ordre : " + dt.getOrdre());

        Assert.assertTrue(dt.getOrdre() >= generateur.getPrefixeOrdre());
        Assert.assertTrue(dt.getOrdre() < generateur.getPrefixeOrdre() + generateur.getMax());

    }
}
