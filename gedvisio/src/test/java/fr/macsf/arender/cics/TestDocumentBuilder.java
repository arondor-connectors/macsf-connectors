package fr.macsf.arender.cics;

import org.junit.Assert;
import org.junit.Test;

import com.arondor.viewer.client.api.document.metadata.DocumentMetadata;
import com.arondor.viewer.client.api.document.metadata.DocumentProperty;

import fr.mediassur.visionneuse.services.BeanDocument;
import fr.mediassur.visionneuse.services.common.ServiceCICSException;

public class TestDocumentBuilder
{
    private void _add(DocumentMetadata metas, String key, String value)
    {
        metas.addDocumentProperty(key, new DocumentProperty(key, key));
        metas.getDocumentProperty(key).setValue(value);
    }

    @Test
    public void testDocumentBuilder() throws ServiceCICSException
    {
        BeanDocumentBuilder beanDocumentBuilder = new BeanDocumentBuilder();

        DocumentMetadata metas = new DocumentMetadata();

        String referenceOrigine = "12.10.201423";
        String dateOrdre = "12.10.201417";

        _add(metas, "REFERENCE_ORIGINE", referenceOrigine);
        _add(metas, "DATE_ORDRE", dateOrdre);
        _add(metas, "USER_ID", "UserTS");
        _add(metas, "DocumentTitle", "Hellow");
        _add(metas, "Rubrique", "CE23");

        BeanDocument beanIn = beanDocumentBuilder.build(metas);

        Assert.assertEquals(referenceOrigine, beanIn.getReferenceOrigine().trim());
        Assert.assertEquals(dateOrdre, beanIn.getMultiDocument(0).getReferenceFinale().trim());

        Assert.assertEquals("RS", beanIn.getCodeApplication().trim());
    }

    @Test
    public void testDocumentBuilder_CodeApplication() throws ServiceCICSException
    {
        BeanDocumentBuilder beanDocumentBuilder = new BeanDocumentBuilder();

        DocumentMetadata metas = new DocumentMetadata();

        String referenceOrigine = "12.10.201423";
        String dateOrdre = "12.10.201417";

        _add(metas, "REFERENCE_ORIGINE", referenceOrigine);
        _add(metas, "DATE_ORDRE", dateOrdre);
        _add(metas, "USER_ID", "UserTS");
        _add(metas, "DocumentTitle", "Hellow");
        _add(metas, "Rubrique", "CE23");
        _add(metas, "CODE_APPLICATION", "WG");

        BeanDocument beanIn = beanDocumentBuilder.build(metas);

        Assert.assertEquals("WG", beanIn.getCodeApplication().trim());

    }
}
