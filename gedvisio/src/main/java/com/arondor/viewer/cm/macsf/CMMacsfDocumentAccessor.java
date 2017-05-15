package com.arondor.viewer.cm.macsf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.resource.ResourceException;

import org.apache.log4j.Logger;

import com.arondor.viewer.client.api.document.DocumentFormatNotSupportedException;
import com.arondor.viewer.client.api.document.DocumentId;
import com.arondor.viewer.client.api.document.DocumentNotAvailableException;
import com.arondor.viewer.client.api.document.altercontent.AlterContentDescription;
import com.arondor.viewer.client.api.document.altercontent.AlterContentDescriptionMultiSplit;
import com.arondor.viewer.client.api.document.altercontent.AlterContentOperationException;
import com.arondor.viewer.client.api.document.altercontent.AlterContentOperationNotSupportedException;
import com.arondor.viewer.client.api.document.metadata.DocumentMetadata;
import com.arondor.viewer.client.api.document.metadata.DocumentProperty;
import com.arondor.viewer.cm.CMDocumentAccessor;
import com.arondor.viewer.rendition.api.document.DocumentAccessor;
import com.arondor.viewer.rendition.api.document.DocumentService;

import fr.macsf.arender.cics.BeanDocumentBuilder;
import fr.macsf.arender.cics.BeanDocumentChecker;
import fr.macsf.arender.dateordre.GenereDateOrdre.DateOrdre;
import fr.mediassur.visionneuse.services.BeanDocument;
import fr.mediassur.visionneuse.services.ServiceVisionneuse;
import fr.mediassur.visionneuse.services.common.ServiceCICSException;

public class CMMacsfDocumentAccessor extends CMDocumentAccessor
{
    private static final Logger LOG = Logger.getLogger(CMMacsfDocumentAccessor.class);

    private static final long serialVersionUID = 9026374940087483843L;

    private final CMMacsfConfiguration configuration;

    private final ServiceVisionneuse serviceVisionneuse;

    private final BeanDocumentBuilder beanDocumentBuilder;

    private final BeanDocumentChecker beanDocumentChecker;

    public CMMacsfDocumentAccessor(CMMacsfDocumentAccessorFactory factory, DocumentId documentId, String docId)
    {
        super(factory, documentId, docId);
        this.configuration = factory.getCmMacsfConfiguration();
        this.serviceVisionneuse = factory.getServiceVisionneuse();
        this.beanDocumentBuilder = factory.getBeanDocumentBuilder();
        this.beanDocumentChecker = factory.getBeanDocumentChecker();
    }

    @Override
    public DocumentAccessor updateDocumentContent(DocumentService documentService, DocumentId source,
            AlterContentDescription alterContentDescription) throws DocumentNotAvailableException, IOException,
            AlterContentOperationNotSupportedException, DocumentFormatNotSupportedException,
            AlterContentOperationException
    {
        LOG.info("updateDocumentContent() with sourceId=" + source);

        DocumentMetadata metadatas = ((AlterContentDescriptionMultiSplit) alterContentDescription)
                .getDocumentDescriptions().get(0).getDocumentMetadata();

        prepareDocumentMetata(metadatas);

        LOG.debug("Call update Document Content from the default Document Accessor");
        DocumentAccessor cmUpdatedDocumentAccessor = super.updateDocumentContent(documentService, source,
                alterContentDescription);

        LOG.debug("Call BeanDocumentBuilder");
        BeanDocument beanIn = beanDocumentBuilder.build(metadatas);

        BeanDocument beanOut = null;
        try
        {
            /**
             * Appel au CICS COBOL
             */
            LOG.debug("Call ServiceVisionneuse");
            beanOut = serviceVisionneuse.execute(beanIn);
            LOG.debug("Call ServiceVisionneuse OK");
        }
        catch (ResourceException e)
        {
            throw new RuntimeException("Unable to use the bean Document", e);
        }
        catch (ServiceCICSException e)
        {
            throw new RuntimeException("Problem during the execution", e);
        }
        LOG.debug("Call BeanDocumentChecker");
        beanDocumentChecker.check(beanOut);

        return cmUpdatedDocumentAccessor;
    }

    protected void prepareDocumentMetata(DocumentMetadata metadatas)
    {
        DateOrdre dateOrdre = configuration.getGenereDateOrdre().generateDateOrdreObject();
        LOG.debug("Adding Date Ordre to the ARender metadatas: date Ordre : " + dateOrdre.getDate() + ", ordre="
                + dateOrdre.getOrdre());

        SimpleDateFormat ordreDateFormater = new SimpleDateFormat(configuration.getDateOrdreDateFormat());
        String dateStringForCm = ordreDateFormater.format(dateOrdre.getDate());
        LOG.debug("Set the Date Ordre as String : " + dateStringForCm);

        metadatas.addDocumentProperty(buildProperty(configuration.getDateOrdreDateParameter(), dateStringForCm));

        String index = Integer.toString(dateOrdre.getOrdre());
        metadatas.addDocumentProperty(buildProperty(configuration.getDateOrdreIndexParameter(), index));

        if (!metadatas.hasDocumentProperty(configuration.getDateOrdreParameterName()))
        {
            String dateString = new SimpleDateFormat(configuration.getReferenceFinaleDateFormat()).format(dateOrdre
                    .getDate());
            String ordreString = new DecimalFormat(configuration.getReferenceFinaleOrdreFormat()).format(dateOrdre
                    .getOrdre());
            String name = dateString + configuration.getReferenceFinaleSeparator() + ordreString;
            LOG.debug("Setting " + configuration.getDateOrdreParameterName() + " : " + name);
            metadatas.addDocumentProperty(buildProperty(configuration.getDateOrdreParameterName(), name));
        }
        else
        {
            LOG.debug("Skipping parameter " + configuration.getDateOrdreParameterName());
        }
    }

    private DocumentProperty buildProperty(String symbolicName, String value)
    {
        LOG.debug("Building document property for symbolicName: " + symbolicName + " and value: " + value);
        DocumentProperty ppt = new DocumentProperty(symbolicName);
        ppt.setValue(value);
        return ppt;
    }

}
