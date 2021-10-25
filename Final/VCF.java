import java.io.*; import java.util.*;
/**
 * Reads VCF file and returns a hashmap of lists
 * key is ID from vcf record
 * List format is [CHROM,POS,ID,REF,ALT,QUAL,FILTER,INFO,FORMAT,EXTRA]
 */
public class VCF
{
    private List<String> meta = new ArrayList<>();
    private HashMap<String, ArrayList> records = new HashMap<String, ArrayList>();

    /**
     * Constructor for objects of class Vread
     */
    public VCF(String PATH) throws Exception
    {

        
    }
    
    public HashMap<String, ArrayList> readVCFrecords(String PATH) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(PATH)));
        String line = new String();
        int increment = 0;
        HashMap<String, ArrayList> records = new HashMap<String, ArrayList>();
        
    }
    public List<String> readVCFmeta(String PATH) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(PATH)));
        String line = new String();
        int increment = 0;
    }
}
