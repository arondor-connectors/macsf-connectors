package com.arondor.viewer.cm.macsf;

import fr.macsf.arender.dateordre.GenereDateOrdre;

public class CMMacsfConfiguration
{
    private GenereDateOrdre genereDateOrdre = new GenereDateOrdre();

    private String dateOrdreParameterName = "DATE_ORDRE";

    private String dateOrdreDateParameter = "Gest_date_cre";

    private String dateOrdreDateFormat = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    private String referenceFinaleDateFormat = "dd.MM.yyyy";

    private String referenceFinaleOrdreFormat = "000000000";
    
    private String referenceFinaleSeparator = "";

    private String dateOrdreIndexParameter = "Gest_index_tmp";
    
    public CMMacsfConfiguration()
    {
        
    }

    public GenereDateOrdre getGenereDateOrdre()
    {
        return genereDateOrdre;
    }

    public void setGenereDateOrdre(GenereDateOrdre genereDateOrdre)
    {
        this.genereDateOrdre = genereDateOrdre;
    }

    public String getDateOrdreParameterName()
    {
        return dateOrdreParameterName;
    }

    public void setDateOrdreParameterName(String dateOrdreParameterName)
    {
        this.dateOrdreParameterName = dateOrdreParameterName;
    }

    public String getDateOrdreDateParameter()
    {
        return dateOrdreDateParameter;
    }

    public void setDateOrdreDateParameter(String dateOrdreDateParameter)
    {
        this.dateOrdreDateParameter = dateOrdreDateParameter;
    }

    public String getDateOrdreDateFormat()
    {
        return dateOrdreDateFormat;
    }

    public void setDateOrdreDateFormat(String dateOrdreDateFormat)
    {
        this.dateOrdreDateFormat = dateOrdreDateFormat;
    }

    public String getReferenceFinaleDateFormat()
    {
        return referenceFinaleDateFormat;
    }

    public void setReferenceFinaleDateFormat(String referenceFinaleDateFormat)
    {
        this.referenceFinaleDateFormat = referenceFinaleDateFormat;
    }

    public String getDateOrdreIndexParameter()
    {
        return dateOrdreIndexParameter;
    }

    public void setDateOrdreIndexParameter(String dateOrdreIndexParameter)
    {
        this.dateOrdreIndexParameter = dateOrdreIndexParameter;
    }

    public String getReferenceFinaleSeparator()
    {
        return referenceFinaleSeparator;
    }

    public void setReferenceFinaleSeparator(String referenceFinaleSeparator)
    {
        this.referenceFinaleSeparator = referenceFinaleSeparator;
    }

    public String getReferenceFinaleOrdreFormat()
    {
        return referenceFinaleOrdreFormat;
    }

    public void setReferenceFinaleOrdreFormat(String referenceFinaleOrdreFormat)
    {
        this.referenceFinaleOrdreFormat = referenceFinaleOrdreFormat;
    }


}
