import java.util.*;
import java.io.*;
/*
This class manages the information about publications.
This class also has the main() method that takes input from the user for input and output file names
and then calls the function that converts the citations to IEEE format.
*/
public class PublicationLibrary
{
    public boolean addArea(String researchArea, Set<String> parentArea)
    {
         ResearchAreas ra=new ResearchAreas();
         boolean status=ra.addArea(researchArea,parentArea);
         return status;
    }

    public boolean addVenue(String venueName,Map<String,String> venueInformation, Set<String> researchAreas)
    {
           PublicationVenue pv=new PublicationVenue();
           boolean status=pv.addVenue(venueName,venueInformation,researchAreas);
           return status;
    }
    public boolean addPublication(String identifier,Map<String,String> publicationInformation)
    {   boolean status;
        //first check if the publication is a journal or a conference by checking the mandatory fields
        // and accordingly call the helper methods
        if(publicationInformation.containsKey("journal")||publicationInformation.containsKey("volume")||publicationInformation.containsKey("issue"))//it is a journal
        {
            Journal journal=new Journal();
            status= journal.addJournal(identifier,publicationInformation);
        }
        else //it is a conference
        {
            Conference conference=new Conference();
            status=conference.addConference(identifier,publicationInformation);
        }

        return status;
    }
    public boolean addReferences(String identifier, Set<String> references)
    {
        References r=new References();
        boolean status=r.addReferences(identifier,references);
        return status;
    }
    public  boolean addPublisher(String identifier,Map<String,String>publisherInformation)
    {
        Publisher pb=new Publisher();
        boolean status=pb.addPublisher(identifier,publisherInformation);
        return status;
    }
    public Map<String,String> getPublications(String key)
    {
        Reporter r=new Reporter();
        Map<String,String> publication_info= r.getPublications(key);
        return publication_info;
    }
    public int authorCitations(String author)
    {
        Reporter r=new Reporter();
        int numberOfCitations=r.authorCitations(author);
        return numberOfCitations;
    }

    public static void main(String args[])
    {
        //getting the names of input file and the output file
        InputStreamReader ins= new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(ins);
        System.out.println("Enter the name of input file");
        String inputFileName=new String();
        String outputFileName=new String();
        try
        {
           inputFileName=br.readLine();
        }
        catch(IOException ioe)
        {
            ioe.getMessage();
        }
        System.out.println("Enter the name of output file");
        try
        {
            outputFileName=br.readLine();
        }
        catch(IOException ioe)
        {
            ioe.getMessage();
        }
        ReferenceGenerator rg=new ReferenceGenerator();
        rg.convertToIEEEReferences(inputFileName,outputFileName);


    }

}
