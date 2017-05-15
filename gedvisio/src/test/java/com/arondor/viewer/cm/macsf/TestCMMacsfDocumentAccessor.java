package com.arondor.viewer.cm.macsf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.arondor.viewer.client.api.document.DocumentId;
import com.arondor.viewer.client.api.document.metadata.DocumentMetadata;
import com.arondor.viewer.client.api.document.metadata.DocumentProperty;

import fr.mediassur.visionneuse.services.BeanDocument;

public class TestCMMacsfDocumentAccessor
{
    private static final Logger LOG = Logger.getLogger(TestCMMacsfDocumentAccessor.class);

    @Test
    public void testCMMacsfDocumentAccessorPrepare()
    {
        CMMacsfDocumentAccessorFactory factory = new CMMacsfDocumentAccessorFactory();
        DocumentId documentId = null;
        String docId = "23";
        CMMacsfDocumentAccessor accessor = new CMMacsfDocumentAccessor(factory, documentId, docId);

        DocumentMetadata metas = new DocumentMetadata();
        String referenceOrigine = "12.10.201423";

        _add(metas, "REFERENCE_ORIGINE", referenceOrigine);
        _add(metas, "USER_ID", "UserTS");
        _add(metas, "DocumentTitle", "Hellow");
        _add(metas, "CODE_APPLICATION", "WG");
        _add(metas, "Rubrique", "CE23");

        accessor.prepareDocumentMetata(metas);

        checkDocumentProperty(metas, factory.getCmMacsfConfiguration().getDateOrdreDateParameter());
        checkDocumentProperty(metas, factory.getCmMacsfConfiguration().getDateOrdreIndexParameter());
        checkDocumentProperty(metas, factory.getCmMacsfConfiguration().getDateOrdreParameterName());

        BeanDocument beanIn = factory.getBeanDocumentBuilder().build(metas);

        Assert.assertTrue(!beanIn.getReferenceOrigine().isEmpty());

        LOG.debug("DateOrdre date "
                + metas.getDocumentProperty(factory.getCmMacsfConfiguration().getDateOrdreIndexParameter()).getValue());
        LOG.debug("DateOrdre index "
                + metas.getDocumentProperty(factory.getCmMacsfConfiguration().getDateOrdreIndexParameter()).getValue());
        LOG.debug("DateOrdre cics "
                + metas.getDocumentProperty(factory.getCmMacsfConfiguration().getDateOrdreParameterName()).getValue());
    }

    private void checkDocumentProperty(DocumentMetadata metas, String key)
    {
        assertTrue(metas.hasDocumentProperty(key));
        assertNotNull(metas.getDocumentProperty(key).getValue());
    }

    private void _add(DocumentMetadata metas, String key, String value)
    {
        metas.addDocumentProperty(new DocumentProperty(key));
        metas.getDocumentProperty(key).setValue(value);
    }
}
