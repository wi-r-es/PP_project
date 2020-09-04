/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


import org.json.simple.JSONObject;

public class testitem {
    private final String Reference;
    private String Description;
    private String TransportationType;
    private String Customer;
    private String Destination;
    private String ItemStatus;
    private int Depth;
    private int Height;
    private final int Length;
    private final int Volume;
    private final double Weight;

    public testitem(String reference, String var1) {
        Reference = reference;
        Description = var1;
        TransportationType = "testtranst";
        Customer = "testcostu";
        Destination = "testD";
        ItemStatus = "testStatus";
        Depth = 10;
        Height = 10;
        Length = 10;
        Volume = 10;
        Weight = 200;
    }

    public JSONObject getObject(){
        JSONObject o1 = new JSONObject();
        o1.put("description", this.Description);
        /*o1.put("roll", new Integer(12));
        o1.put("total_marks", new Double(684.50));
        obj.put("pass", new Boolean(true));*/
    return o1;

    }
}
