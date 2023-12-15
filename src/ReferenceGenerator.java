import java.io.*;
import java.util.*;
import java.util.regex.*;

/*This is a helper class that has the method to convert the citations into IEEE references*/
public class ReferenceGenerator
{
    public void convertToIEEEReferences(String inputFileName,String outputFileName)
    {
        Map<String,Integer> citationsMap=new HashMap<>(); //this map stores and keeps tracks of the citations encountered
        // as well as the citation's corresponding reference number
        int referenceNumber=1;
        try
        {
            FileReader fr=new FileReader(inputFileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuilder requiredOutput = new StringBuilder();
            //first finding the \cite{aplhanumerics} pattern, tracking the reference number by storing the information in a map
            //and then replacing the \cite{aplhanumerics} pattern with the corresponding numbered references
            while ((line = br.readLine()) != null)
            {
                //creating the pattern to match \cite{aplhanumerics} using  Pattern class of Regex API
                Pattern pattern = Pattern.compile("\\\\cite\\{([a-zA-Z0-9]+(,\\s*[a-zA-Z0-9]+)*)\\}");
                //now matching and finding the \cite{aplhanumerics}  pattern using the Matcher class of Regex API
                Matcher matcher = pattern.matcher(line);
                while (matcher.find())
                {
                    StringBuilder correspondingReferences = new StringBuilder();
                    //extracting the group match for alphanumeric identifiers and splitting them into individual strings
                    String identifiers[] = matcher.group(1).split(",\\s*");
                    //now replacing the identifiers with the corresponding reference number and keeping track of it
                    // by storing the identifier and the corresponding reference number in the citationsMap
                    for (int i=0;i<=identifiers.length-1;i++)
                    {
                        if (citationsMap.containsKey(identifiers[i])) //reference for the identifier already exists
                        {
                            correspondingReferences.append(", ").append(citationsMap.get(identifiers[i]));//add it to the string of number references
                        }
                        else//first add the identifier and its corresponding  reference number in the citationsMap
                        // and then add it to the string of number references
                        {
                            citationsMap.put(identifiers[i], referenceNumber);
                            correspondingReferences.append(", "+referenceNumber);
                            referenceNumber=referenceNumber+1;
                        }
                    }
                    //now retrieve the entire regular expression\cite{alphanumeric} and replace it with the corresponding
                    // string of numbered references
                    String citation = matcher.group(0);
                    String reference = "[" + correspondingReferences.substring(2) + "]";
                    line = line.replace(citation, reference);
                }

                //append the modified line with numbered references to the required output
                requiredOutput.append(line+"\n");
            }
            br.close();
            //now creating the reference list to be appended at the bottom
            requiredOutput.append("\nReferences:\n");

            for(String identifier: citationsMap.keySet())
            {
                //get all the publication information for this identifier
                PublicationLibrary pl=new PublicationLibrary();
                Map<String,String> publicationInfo=pl.getPublications(identifier);
                //getting the authors and abbreviating the first name (and middle name, if it exists)
                String authors[]=publicationInfo.get("authors").split(",");
                StringBuilder abbreviatedNames=new StringBuilder();
                for(int i=0;i<=authors.length-1;i++)
                {
                     List<String> partsOfName;
                     partsOfName= List.of(authors[i].split(" ")); //converting the string array into arraylist for flexibility
                    if(partsOfName.size()==3)//there is a middle name
                    {
                        abbreviatedNames.append(partsOfName.get(0).charAt(0)+"."); //getting initial of the first name
                        abbreviatedNames.append(partsOfName.get(1).charAt(0)+".");//getting initial of the second name
                        abbreviatedNames.append(partsOfName.get(2)+",");
                    }
                    else if(partsOfName.size()==2) //author just has the first and last name
                    {
                        abbreviatedNames.append(partsOfName.get(0).charAt(0)+"."); //getting initial of the first name
                        abbreviatedNames.append(partsOfName.get(1)+",");
                    }
                    else //author just has the first name
                    {
                        abbreviatedNames.append(partsOfName.get(0)+",");
                    }
                }
                int reference_number=citationsMap.get(identifier);
                requiredOutput.append("["+reference_number+"]  ");
                requiredOutput.append(abbreviatedNames+" ");
                if(publicationInfo.containsKey("journal")) //publication is a journal
                {
                    requiredOutput.append("\""+publicationInfo.get("title")+"\""+",");
                    requiredOutput.append(publicationInfo.get("journal")+",");
                    requiredOutput.append(publicationInfo.get("volume")+",");
                    requiredOutput.append(publicationInfo.get("issue")+",");
                    requiredOutput.append(publicationInfo.get("pages")+",");
                    requiredOutput.append(publicationInfo.get("month")+",");
                    requiredOutput.append(publicationInfo.get("year")+".");
                }
                else //its a conference paper
                {
                    requiredOutput.append("\""+publicationInfo.get("title")+"\""+",");
                    requiredOutput.append(publicationInfo.get("conference")+",");
                    requiredOutput.append(publicationInfo.get("location")+",");
                    requiredOutput.append(publicationInfo.get("year")+",");
                    requiredOutput.append(publicationInfo.get("pages")+".");
                }
                requiredOutput.append("\n");
            }
            FileWriter fw=new FileWriter(outputFileName);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(String.valueOf(requiredOutput));
            bw.close();
            fw.close();
        }
        catch(IOException ioe)
        {
            ioe.getMessage();
        }
         catch(IllegalStateException e)
        {
            e.getMessage();
        }
         catch(Exception e)
        {
            e.getMessage();
        }
    }

}
