package ugcs.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Peter
 */
public class Consultation {

    private IntegerProperty consultationid;
    private final StringProperty zid;
    private final StringProperty notes;
    private final StringProperty type;
    private final StringProperty priority;
    private final StringProperty date1;
    private final StringProperty time1;
    private final StringProperty owner;

    // this is for finding consultations
    public Consultation(String zid1, String notes1, String type1, String priority1, String date2, String time2, String owner) {
        //  this.ID = new SimpleIntegerProperty(ID);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date1 = new SimpleStringProperty(date2);
        this.time1 = new SimpleStringProperty(time2);
        this.owner = new SimpleStringProperty(owner);
    }
    
        public Consultation(String zid1, String notes1, String type1, String priority1, String date2, String time2) {
        //  this.ID = new SimpleIntegerProperty(ID);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date1 = new SimpleStringProperty(date2);
        this.time1 = new SimpleStringProperty(time2);
        this.owner = new SimpleStringProperty("");
    }

    // this is for creating a consultation and updating
    public Consultation(Integer consultationid1, String zid1, String notes1, String type1, String priority1, String date2, String time2, String owner) {
        this.consultationid = new SimpleIntegerProperty(consultationid1);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date1 = new SimpleStringProperty(date2);
        this.time1 = new SimpleStringProperty(time2);
        this.owner = new SimpleStringProperty(owner);
    }
    
        public Consultation(Integer consultationid1, String zid1, String notes1, String type1, String priority1, String date2, String time2) {
        this.consultationid = new SimpleIntegerProperty(consultationid1);
        this.zid = new SimpleStringProperty(zid1);
        this.notes = new SimpleStringProperty(notes1);
        this.type = new SimpleStringProperty(type1);
        this.priority = new SimpleStringProperty(priority1);
        this.date1 = new SimpleStringProperty(date2);
        this.time1 = new SimpleStringProperty(time2);
        this.owner = new SimpleStringProperty("");
    }

    public Integer getConsultationid() {
        return consultationid.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public String getType() {
        return type.get();
    }

    public String getPriority() {
        return priority.get();
    }

    public String getDate1() {
        return date1.get();
    }

    public String getTime1() {
        return time1.get();
    }

    public String getZid() {
        return zid.get();
    }
    
    public String getOwner() {
        return owner.get();
    }

    public IntegerProperty consultationidProperty() {
        return consultationid;
    }

    public StringProperty zidProperty() {
        return zid;
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public StringProperty dateproperty() {
        return date1;
    }

    public StringProperty timeproperty() {
        return time1;
    }

    public StringProperty ownerProperty() {
        return owner;
    }
}
