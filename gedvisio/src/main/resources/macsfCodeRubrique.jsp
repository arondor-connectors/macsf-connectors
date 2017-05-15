<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
    <%@ page import="java.io.*"%>
    
<%
        String codeAppli = request.getParameter("codeAppli");
        String fileCSV = "/home/michel/Téléchargements/Arender_RS.csv";
        String thisLine = "";
        
        InputStream is = new FileInputStream(fileCSV);
        BufferedReader myInput = new BufferedReader(
                new InputStreamReader(is, "UTF-8"));

        try
        {

            int lineNumber = 0;
            String resultJson = null;

            if (codeAppli != null)
            {

                while ((thisLine = myInput.readLine()) != null)
                {
                    lineNumber++;

                    if (lineNumber == 1)
                    {
                        continue;
                    }

                    String keyValue[] = thisLine.split(",");

                    if (keyValue[0].contains(codeAppli))
                    {

                        if (resultJson == null)
                        {
                            resultJson = "[{\"key\":\"No data given\", " + "\"value\":\"A sélectionner\"},";
                        }
                        else
                        {
                            resultJson += ",";
                        }
                        resultJson += "{\"key\":\"" + keyValue[1] + "\", " + "\"value\":\"" + keyValue[2] + "\"}";
                    }
                }
            }
            resultJson += "]";
            out.write(resultJson);

        }

        finally
        {
            myInput.close();
        }
    %>