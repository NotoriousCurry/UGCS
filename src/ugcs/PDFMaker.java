/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ugcs.Model.Student;
import ugcs.Queries.StudentQueries;

/**
 *
 * @author Peterrpancakes
 */
public class PDFMaker {
    //static Format format = new SimpleDateFormat()
    

//CalendarViewController cv = new CalendarViewController();



public byte[] PDFForm(Integer ID, String zid1, String notes1, String type1, String priority1, String date2, String time2)throws Exception{
    
    InputStream input = PDFMaker.class.getResourceAsStream("Resources/undegraduate-form-2.pdf");
    System.out.println("stream = " + input);
    File file = new File("Resource/undergraduate-form.pdf");
    PdfReader reader = new PdfReader(input);
    ByteArrayOutputStream byteout = new ByteArrayOutputStream();
    PdfStamper stamper = new PdfStamper(reader,byteout);
    AcroFields acro = stamper.getAcroFields();
    StudentQueries sq = new StudentQueries();
    ObservableList<Student> stulist = FXCollections.observableArrayList(sq.getStudents());
    for(Student s : stulist){
        if(s.getZID().equals(zid1)){
            
        
           acro.setField("fname7", s.getFName());
           acro.setField("lname7", s.getLName());
           acro.setField("email7", s.getEMail());
           acro.setField("course7", s.getCourse());
           acro.setField("date7", date2);
           acro.setField("time7", time2);
           acro.setField("category7", type1);
           acro.setField("priority7", priority1);
           acro.setField("notes7", notes1);
           acro.setField("num7", ID.toString());

           stamper.close();
           reader.close();
      
        }
    }
return byteout.toByteArray();

}






}



    //Connecting PDF to this
    
    
    





