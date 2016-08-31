
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author Peter
 */

public class Consultation {
   
    private  IntegerProperty consultationid;
    private final StringProperty zid;
    private final StringProperty notes;
    private final StringProperty type;
    private final StringProperty priority;
    private final StringProperty date;
    private final StringProperty time;

    // this is for finding consultations
    public Consultation(String zid1, String notes1, String type1, String priority1, String date1, String time1) {
      //  this.ID = new SimpleIntegerProperty(ID);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date = new SimpleStringProperty(date1);
    this.time = new SimpleStringProperty(time1);
    }
    // this is for creating a consultation
    public Consultation(Integer consultationid1, String zid1, String notes1, String type1, String priority1, String date1, String time1) {
        this.consultationid = new SimpleIntegerProperty(consultationid1);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date = new SimpleStringProperty(date1);
    this.time = new SimpleStringProperty(time1);
    }
    public Integer getconsultationid(){
        return consultationid.get();
    }
    public String getnotes(){
        return notes.get();
    }
    public String gettype(){
        return type.get();
    }
    public String getpriority(){
        return priority.get();
    }
    public String getdate(){
        return date.get();
    }
    public String gettime(){
        return time.get();
    }
    
    
    
    


    public IntegerProperty getConsultationid() {
        return consultationid;
    }

    public StringProperty getZid() {
        return zid;
    }

    public StringProperty getNotes() {
        return notes;
    }

    public StringProperty getType() {
        return type;
    }

    public StringProperty getPriority() {
        return priority;
    }

    public StringProperty getDate() {
        return date;
    }

    public StringProperty getTime() {
        return time;
    }
    
}
        
