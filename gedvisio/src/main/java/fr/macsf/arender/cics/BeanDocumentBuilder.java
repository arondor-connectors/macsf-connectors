package fr.macsf.arender.cics;

import org.apache.log4j.Logger;

import com.arondor.viewer.client.api.document.metadata.DocumentMetadata;
import com.arondor.viewer.cm.macsf.CMMacsfDocumentAccessor;

import fr.mediassur.visionneuse.services.BeanDocument;
import fr.mediassur.visionneuse.services.BeanMultiDocument;
import fr.mediassur.visionneuse.services.common.ServiceCICSException;

public class BeanDocumentBuilder
{
    private static final Logger LOG = Logger.getLogger(CMMacsfDocumentAccessor.class);

    private String userIdParameterName = "USER_ID";

    private String co_Action = "UNIQUE";

    private String co_Prog_Appelant = "ARender";

    private String co_Prog_Appele = "PSVISSER";

    private String codeApplication = "RS";

    private String codeApplicationParameterName = "CODE_APPLICATION";

    private String dateOrdreParameterName = "DATE_ORDRE";

    private String rubriqueParameterName = "Rubrique";

    private String titreParameterName = "DocumentTitle";

    private String referenceOrigineParameterName = "REFERENCE_ORIGINE";

    public BeanDocument build(DocumentMetadata documentMetadata)
    {
        BeanDocument beanIn = new BeanDocument();

        String user = documentMetadata.getDocumentProperty(getUserIdParameterName()).getValue();
        LOG.debug("Set user=" + user);
        beanIn.setCo_User(user);

        beanIn.setCo_Action(co_Action);
        LOG.debug("Set co_Action=" + co_Action);

        beanIn.setCo_Prog_Appelant(co_Prog_Appelant);
        LOG.debug("Set co_Prog_Appelant=" + co_Prog_Appelant);

        beanIn.setCo_Prog_Appele(co_Prog_Appele);
        LOG.debug("Set co_Prog_Appele=" + co_Prog_Appele);

        if (documentMetadata.hasDocumentProperty(getCodeApplicationParameterName()))
        {
            String codeApplication = documentMetadata.getDocumentProperty(getCodeApplicationParameterName()).getValue();
            beanIn.setCodeApplication(codeApplication);
            LOG.debug("Set codeApplication=" + codeApplication);
        }
        else
        {
            beanIn.setCodeApplication(codeApplication);
            LOG.debug("Set default codeApplication=" + codeApplication);
        }

        String referenceOrigine = documentMetadata.getDocumentProperty(getReferenceOrigineParameterName()).getValue();
        LOG.debug("Set referenceOrigine=" + referenceOrigine);
        beanIn.setReferenceOrigine(referenceOrigine);

        BeanMultiDocument multiDocument = new BeanMultiDocument();

        String referenceFinale = documentMetadata.getDocumentProperty(getDateOrdreParameterName()).getValue();
        LOG.debug("Set referenceFinale=" + referenceFinale);
        multiDocument.setReferenceFinale(referenceFinale);

        String rubrique = documentMetadata.getDocumentProperty(getRubriqueParameterName()).getValue();
        rubrique = rubrique.substring(0, Math.min(rubrique.length(), 10));
        LOG.debug("Set rubrique=" + rubrique);
        multiDocument.setRubrique(rubrique);

        String titre = documentMetadata.getDocumentProperty(getTitreParameterName()).getValue();
        LOG.debug("Set titre=" + titre);
        multiDocument.setTitre(titre);

        try
        {
            beanIn.setMultiDocument(multiDocument, 0);
        }
        catch (ServiceCICSException e)
        {
            throw new RuntimeException("Unable to add multiDocument in the bean document", e);
        }

        return beanIn;
    }

    /**
     * @return the co_Action
     */
    public String getCo_Action()
    {
        return co_Action;
    }

    /**
     * @param co_Action
     *            the co_Action to set
     */
    public void setCo_Action(String co_Action)
    {
        this.co_Action = co_Action;
    }

    /**
     * @return the co_Prog_Appelant
     */
    public String getCo_Prog_Appelant()
    {
        return co_Prog_Appelant;
    }

    /**
     * @param co_Prog_Appelant
     *            the co_Prog_Appelant to set
     */
    public void setCo_Prog_Appelant(String co_Prog_Appelant)
    {
        this.co_Prog_Appelant = co_Prog_Appelant;
    }

    /**
     * @return the co_Prog_Appele
     */
    public String getCo_Prog_Appele()
    {
        return co_Prog_Appele;
    }

    /**
     * @param co_Prog_Appele
     *            the co_Prog_Appele to set
     */
    public void setCo_Prog_Appele(String co_Prog_Appele)
    {
        this.co_Prog_Appele = co_Prog_Appele;
    }

    /**
     * @return the codeApplication
     */
    public String getCodeApplication()
    {
        return codeApplication;
    }

    /**
     * @param codeApplication
     *            the codeApplication to set
     */
    public void setCodeApplication(String codeApplication)
    {
        this.codeApplication = codeApplication;
    }

    public String getDateOrdreParameterName()
    {
        return dateOrdreParameterName;
    }

    public void setDateOrdreParameterName(String dateOrdreParameterName)
    {
        this.dateOrdreParameterName = dateOrdreParameterName;
    }

    public String getTitreParameterName()
    {
        return titreParameterName;
    }

    public void setTitreParameterName(String titreParameterName)
    {
        this.titreParameterName = titreParameterName;
    }

    public String getRubriqueParameterName()
    {
        return rubriqueParameterName;
    }

    public void setRubriqueParameterName(String rubriqueParameterName)
    {
        this.rubriqueParameterName = rubriqueParameterName;
    }

    public String getUserIdParameterName()
    {
        return userIdParameterName;
    }

    public void setUserIdParameterName(String userIdParameterName)
    {
        this.userIdParameterName = userIdParameterName;
    }

    public String getReferenceOrigineParameterName()
    {
        return referenceOrigineParameterName;
    }

    public void setReferenceOrigineParameterName(String referenceOrigineParameterName)
    {
        this.referenceOrigineParameterName = referenceOrigineParameterName;
    }

    public String getCodeApplicationParameterName()
    {
        return codeApplicationParameterName;
    }

    public void setCodeApplicationParameterName(String codeApplicationParameterName)
    {
        this.codeApplicationParameterName = codeApplicationParameterName;
    }

}
