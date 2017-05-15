package fr.macsf.arender.cics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import fr.mediassur.visionneuse.services.BeanDocument;
import fr.mediassur.visionneuse.services.BeanErreurDocument;

public class BeanDocumentCheckerTest
{
    private BeanDocumentChecker checker;

    @Before
    public void setUp()
    {
        checker = new BeanDocumentChecker();
    }

    @Test
    public void testGetErrorMessageForNormalError()
    {
        BeanDocument beanDocument = mockDocumentWithError("type", "label", "error mess");
        assertEquals("error mess, with error type=type, and error label : label", checker.buildErrorMessge(beanDocument));
    }

    @Test
    public void testGetErrorMessageForErrorWithEmptyType()
    {
        BeanDocument beanDocument = mockDocumentWithError("", "label", "error mess");
        when(beanDocument.getCo_Libmess()).thenReturn("DB2");
        when(beanDocument.getCo_Typmess()).thenReturn("SELECT-TSRBOIT SQLCODE = -904");

        assertEquals("DB2: SELECT-TSRBOIT SQLCODE = -904", checker.buildErrorMessge(beanDocument));
    }

    @Test
    public void testGetErrorMessageForErrorWithNullType()
    {
        BeanDocument beanDocument = mockDocumentWithError(null, "label", "error mess");
        when(beanDocument.getCo_Libmess()).thenReturn("DB2");
        when(beanDocument.getCo_Typmess()).thenReturn("SELECT-TSRBOIT SQLCODE = -904");

        assertEquals("DB2: SELECT-TSRBOIT SQLCODE = -904", checker.buildErrorMessge(beanDocument));
    }

    @Test
    public void testGetErrorMessageForErrorWithEmptyLabel()
    {
        BeanDocument beanDocument = new BeanDocument();
        assertEquals("                                                       :    ",
                checker.buildErrorMessge(beanDocument));
    }

    private BeanDocument mockDocumentWithError(String type, String label, String message)
    {
        BeanErreurDocument error = mock(BeanErreurDocument.class);
        when(error.getCo_Typerr()).thenReturn(type);
        when(error.getCo_Liberr()).thenReturn(label);
        when(error.getCo_Nomerr()).thenReturn(message);
        BeanDocument beanDocument = mock(BeanDocument.class);
        when(beanDocument.getCo_Erreur(0)).thenReturn(error);
        return beanDocument;
    }
}
