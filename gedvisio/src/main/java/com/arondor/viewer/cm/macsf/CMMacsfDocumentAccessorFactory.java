package com.arondor.viewer.cm.macsf;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.arondor.viewer.client.api.document.DocumentId;
import com.arondor.viewer.cm.CMDefaultDocumentAccessorFactory;

import fr.macsf.arender.cics.BeanDocumentBuilder;
import fr.macsf.arender.cics.BeanDocumentChecker;
import fr.mediassur.visionneuse.services.ServiceVisionneuse;

public class CMMacsfDocumentAccessorFactory extends CMDefaultDocumentAccessorFactory
{
    private static final Logger LOG = Logger.getLogger(CMMacsfDocumentAccessorFactory.class);

    private CMMacsfConfiguration cmMacsfConfiguration = new CMMacsfConfiguration();

    private ServiceVisionneuse serviceVisionneuse;

    private BeanDocumentBuilder beanDocumentBuilder = new BeanDocumentBuilder();

    private BeanDocumentChecker beanDocumentChecker = new BeanDocumentChecker();

    public CMMacsfDocumentAccessorFactory()
    {
        /**
         * MACSF-specific Data Types
         */
        setDataTypes(new HashMap<String, String>());
        getDataTypes().put("Gest_date_cre", java.sql.Date.class.getName());
        getDataTypes().put("Gest_index_tmp", Integer.class.getName());
    }

    @Override
    public CMMacsfDocumentAccessor instanciate(DocumentId uuid, String docId)
    {
        LOG.debug("Instanciate MACSF Document Accessor for the Arender document : " + uuid);
        CMMacsfDocumentAccessor documentAccessor = new CMMacsfDocumentAccessor(this, uuid, docId);
        addCmProperties(documentAccessor, docId);

        LOG.debug("Annotation will be instanciated by documentService: " + uuid);
        documentAccessor.setMinimalPostitWidth(getMinimalPostitWidth());
        documentAccessor.setMinimalPostitHeight(getMinimalPostitHeight());

        return documentAccessor;
    }

    public ServiceVisionneuse getServiceVisionneuse()
    {
        return serviceVisionneuse;
    }

    public void setServiceVisionneuse(ServiceVisionneuse serviceVisionneuse)
    {
        this.serviceVisionneuse = serviceVisionneuse;
    }

    public BeanDocumentBuilder getBeanDocumentBuilder()
    {
        return beanDocumentBuilder;
    }

    public void setBeanDocumentBuilder(BeanDocumentBuilder beanDocumentBuilder)
    {
        this.beanDocumentBuilder = beanDocumentBuilder;
    }

    public BeanDocumentChecker getBeanDocumentChecker()
    {
        return beanDocumentChecker;
    }

    public void setBeanDocumentChecker(BeanDocumentChecker beanDocumentChecker)
    {
        this.beanDocumentChecker = beanDocumentChecker;
    }

    public CMMacsfConfiguration getCmMacsfConfiguration()
    {
        return cmMacsfConfiguration;
    }

    public void setCmMacsfConfiguration(CMMacsfConfiguration cmMacsfConfiguration)
    {
        this.cmMacsfConfiguration = cmMacsfConfiguration;
    }

}
