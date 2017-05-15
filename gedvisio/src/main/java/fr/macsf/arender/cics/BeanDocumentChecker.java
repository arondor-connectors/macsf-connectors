package fr.macsf.arender.cics;

import org.apache.log4j.Logger;

import com.arondor.viewer.client.api.document.altercontent.AlterContentOperationException;

import fr.mediassur.visionneuse.services.BeanDocument;
import fr.mediassur.visionneuse.services.common.ServiceCICSException;

public class BeanDocumentChecker
{
    private static final Logger LOG = Logger.getLogger(BeanDocumentChecker.class);

    private static final String TYPEMESS_DATABASE = "DB2";

    private static final String TYPEMESS_FUNCTIONAL = "ERR";

    private static final String TYPEMESS_OK = "VAL";

    public void check(BeanDocument beanDocument) throws AlterContentOperationException
    {
        if (beanDocument.getCo_Typmess().equals(TYPEMESS_OK))
        {
            LOG.info("Request completed");
            try
            {
                LOG.info("Request completed : cleBoite=" + beanDocument.getMultiDocument(0).getCleBoite()
                        + ", referenceFinale=" + beanDocument.getMultiDocument(0).getReferenceFinale());
            }
            catch (ServiceCICSException e)
            {
                LOG.error("Service exception while trying to say everything is OK", e);
            }
            return;
        }

        if (beanDocument.getCo_Typmess().equals(TYPEMESS_FUNCTIONAL))
        {
            throw new AlterContentOperationException("Technical or fonctionnal error : "
                    + buildErrorMessge(beanDocument));
        }

        else if (beanDocument.getCo_Typmess().equals(TYPEMESS_DATABASE))
        {
            throw new AlterContentOperationException("Database error : " + buildErrorMessge(beanDocument));
        }
        else
        {
            throw new AlterContentOperationException("An unhandled error is returned from the service : "
                    + beanDocument.getCo_Typmess());
        }
    }

    /**
     * Builds error message taking care of returning a real error message
     * 
     * @param beanDocument
     *            The bean document which provides error information
     * @return The error message
     */
    protected String buildErrorMessge(BeanDocument beanDocument)
    {
        String errorType = beanDocument.getCo_Erreur(0).getCo_Typerr();
        String errorLabel = beanDocument.getCo_Erreur(0).getCo_Liberr();
        String errorMessage = beanDocument.getCo_Erreur(0).getCo_Nomerr();

        if (!isNullOrEmpty(errorType) && !isNullOrEmpty(errorLabel) && !isNullOrEmpty(errorMessage))
        {
            return errorMessage + ", with error type=" + errorType + ", and error label : " + errorLabel;
        }
        else
        {
            return beanDocument.getCo_Libmess() + ": " + beanDocument.getCo_Typmess();
        }
    }

    private boolean isNullOrEmpty(String str)
    {
        return str == null || str.trim().isEmpty();
    }
}
