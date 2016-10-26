/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class StudentCOOP {

    private final StringProperty zID;
    private final StringProperty fName;
   private final StringProperty lName;    
    private final StringProperty gEnder;
    private final StringProperty aDdress;     
    private IntegerProperty cOntact;
    private final StringProperty eMail;
    private final StringProperty wOrkemail;    
    private final StringProperty nOtes;
    private final StringProperty sUbject;
    private IntegerProperty sEmestercompleted;
    private DoubleProperty mArk;
   private DoubleProperty wAm;

    public StudentCOOP() {
        this.zID = null;
        this.fName = null;
        this.lName = null;
        this.gEnder = null;
        this.aDdress =null;
        this.cOntact = null;
        this.eMail = null;
        this.wOrkemail = null;
        this.nOtes = null;
        this.sUbject = null;
        this.sEmestercompleted = null;
        this.mArk = null;
        this.wAm = null;

    }
    public StudentCOOP(String zID, String fName, String lName, String gEnder, String aDdress, 
              Integer cOntact, String eMail, String wOrkemail, String nOtes,
            String sUbject, Integer sEmestercompleted, Double mArk, Double wAm) {
         this.zID= new SimpleStringProperty(zID);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
        this.gEnder = new SimpleStringProperty(gEnder);
        this.aDdress = new SimpleStringProperty(aDdress);
        this.cOntact = new SimpleIntegerProperty(cOntact);
        this.eMail = new SimpleStringProperty(eMail);
        this.wOrkemail =new SimpleStringProperty(wOrkemail);
        this.nOtes = new SimpleStringProperty(nOtes);
        this.sUbject = new SimpleStringProperty(sUbject);
        this.sEmestercompleted = new SimpleIntegerProperty(sEmestercompleted);
        this.mArk = new SimpleDoubleProperty(mArk);
        this.wAm = new SimpleDoubleProperty(wAm);

    }



    public String getZID() {
        return zID.get();
    }

   // public void setzID(String zID) {
     //   this.zID = zID;
    //}

    public String getFName() {
        return fName.get();
    }

  //  public void setfName(String fName) {
   //     this.fName = fName;
    //}

    public String getLName() {
        return lName.get();
    }

    //public void setlName(String lName) {
     //   this.lName = lName;
    //}

    public String getGEnder() {
        return gEnder.get();
    }

    //public void setgEnder(String gEnder) {
     //   this.gEnder = gEnder;
    //}

    public String getADdress() {
        return aDdress.get();
    }

   // public void setaDdress(String aDdress) {
   //     this.aDdress = aDdress;
   // }

    public Integer getCOntact() {
        return cOntact.get();
    }

   // public void setcOntact(Integer cOntact) {
     //   this.cOntact = cOntact;
    //}

    public String getEMail() {
        return eMail.get();
    }

   // public void seteMail(String eMail) {
    //    this.eMail = eMail;
   // }

    public String getWOrkemail() {
        return wOrkemail.get();
    }

  //  public void setwOrkemail(String wOrkemail) {
  //      this.wOrkemail = wOrkemail;
  //  }

    public String getNOtes() {
        return nOtes.get();
    }

  //  public void setnOtes(String nOtes) {
  //      this.nOtes = nOtes;
  //  }

    public String getSUbject() {
        return sUbject.get();
    }

   // public void setsUbject(String sUbject) {
   //     this.sUbject = sUbject;
   // }

    public Integer getSEmestercompleted() {
        return sEmestercompleted.get();
    }

   // public void setsEmestercompleted(Integer sEmestercompleted) {
     //   this.sEmestercompleted = sEmestercompleted;
    //}

    public Double getMArk() {
        return mArk.get();
    }

 //   public void setmArk(Integer mArk) {
   //     this.mArk = mArk;
    //}

    public Double getWAm() {
        return wAm.get();
    }

 //   public void setwAm(Integer wAm) {
  //      this.wAm = wAm;
   // }

    public void setCOntact(IntegerProperty cOntact) {
        this.cOntact = cOntact;
    }

    public void setSEmestercompleted(IntegerProperty sEmestercompleted) {
        this.sEmestercompleted = sEmestercompleted;
    }

    public void setMArk(DoubleProperty mArk) {
        this.mArk = mArk;
    }

    public void setWAm(DoubleProperty wAm) {
        this.wAm = wAm;
    }
    
}
