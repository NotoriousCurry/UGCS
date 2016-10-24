/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Model;



public class StudentCOOP {

    private String zID;
    private String fName;
    private String lName;    
    private String gEnder;
    private String aDdress;     
    private Integer cOntact;
    private String eMail;
    private String wOrkemail;    
    private String nOtes;
    private String sUbject;
    private Integer sEmestercompleted;
    private Integer mArk;
    private Integer wAm;

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
            String sUbject, Integer sEmestercompleted, Integer mArk, Integer wAm) {
         this.zID = zID;
        this.fName = fName;
        this.lName = lName;
        this.gEnder = gEnder;
        this.aDdress =aDdress;
        this.cOntact = cOntact;
        this.eMail = eMail;
        this.wOrkemail = wOrkemail;
        this.nOtes = nOtes;
        this.sUbject = sUbject;
        this.sEmestercompleted = sEmestercompleted;
        this.mArk = mArk;
        this.wAm = wAm;

    }



    public String getzID() {
        return zID;
    }

    public void setzID(String zID) {
        this.zID = zID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getgEnder() {
        return gEnder;
    }

    public void setgEnder(String gEnder) {
        this.gEnder = gEnder;
    }

    public String getaDdress() {
        return aDdress;
    }

    public void setaDdress(String aDdress) {
        this.aDdress = aDdress;
    }

    public Integer getcOntact() {
        return cOntact;
    }

    public void setcOntact(Integer cOntact) {
        this.cOntact = cOntact;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getwOrkemail() {
        return wOrkemail;
    }

    public void setwOrkemail(String wOrkemail) {
        this.wOrkemail = wOrkemail;
    }

    public String getnOtes() {
        return nOtes;
    }

    public void setnOtes(String nOtes) {
        this.nOtes = nOtes;
    }

    public String getsUbject() {
        return sUbject;
    }

    public void setsUbject(String sUbject) {
        this.sUbject = sUbject;
    }

    public Integer getsEmestercompleted() {
        return sEmestercompleted;
    }

    public void setsEmestercompleted(Integer sEmestercompleted) {
        this.sEmestercompleted = sEmestercompleted;
    }

    public Integer getmArk() {
        return mArk;
    }

    public void setmArk(Integer mArk) {
        this.mArk = mArk;
    }

    public Integer getwAm() {
        return wAm;
    }

    public void setwAm(Integer wAm) {
        this.wAm = wAm;
    }

}
