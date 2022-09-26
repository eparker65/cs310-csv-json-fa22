package edu.jsu.mcis.cs310;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
        
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and JSON.simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            // Initialize CSV Reader and Iterator
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
           JSONObject jobject2 = new JSONObject();
            JSONArray col = new JSONArray();
            JSONArray row = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray container;
            String[] data2 = iterator.next();
            
            
            
            for(int i = 0; i < data2.length; i++)
            {
                
                col.add(data2[i]);
                
            }
            
            
            
            while(iterator.hasNext())
            {
                
                container = new JSONArray();
                data2 = iterator.next();
                row.add(data2[0]);
                
                for(int i = 1; i < data2.length; i++)
                {
                    int hold = Integer.parseInt(data2[i]);
                    container.add(hold);
                }
                
                data.add(container);
                
            }
            
            
            
            jobject2.put("rowHeaders", row);
            jobject2.put("colHeaders", col);
            jobject2.put("data", data);
            
            //adding elements to container
            
            results = JSONValue.toJSONString(jobject2);
            
            //
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return JSON String
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            
            
            JSONParser parser = new JSONParser();
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            JSONObject jobject = (JSONObject)parser.parse(jsonString);
            JSONArray row = (JSONArray)jobject.get("rowHeaders");
            JSONArray col = (JSONArray)jobject.get("colHeaders");
            JSONArray data = (JSONArray)jobject.get("data");
            JSONArray container;
            String[] data1 = new String[col.size()];
            
            //containers for json to csv conversion
            
            for(int i = 0; i < col.size(); i++)
            {
                data1[i] = (String)col.get(i);
                
            }
            
            //for loop
            
            csvWriter.writeNext(data1);
            
            //using csvWriter to write to data1
            
            for(int i = 0; i < data.size(); i++)
            {
                
                container = (JSONArray) data.get(i);
                data1 = new String[container.size()+1];
                data1[0] = (String) row.get(i);
                
                for(int j = 0; j < container.size(); j++)
                {
                    data1[j+1] = Long.toString((long)container.get(j));
                    
                }
                
                //for loop to fill container
                
                csvWriter.writeNext(data1);
                
                //write data to data1 using csvWriter
                
            }
            
            
            
            results = writer.toString();
            
            
        
            
        }
        catch(Exception e) { e.printStackTrace(); }
        
        // Return CSV String
        
        return results.trim();
        
    }
	
}