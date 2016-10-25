/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ugcs.Model.Consultation;
import ugcs.Model.Student;
import ugcs.Queries.ConsultationQueries;
import ugcs.Queries.StudentQueries;

/**
 *
 * @author Peterrpancakes & Sgaheer
 */
public class PDFMaker {

    //static Format format = new SimpleDateFormat()
//CalendarViewController cv = new CalendarViewController();
    public byte[] PDFForm(Integer ID, String zid1, String notes1, String type1, String priority1, String date2, String time2) throws Exception {

        InputStream input = PDFMaker.class.getResourceAsStream("Resources/undegraduate-form-2.pdf");
        System.out.println("stream = " + input);
        File file = new File("Resource/undergraduate-form.pdf");
        PdfReader reader = new PdfReader(input);
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, byteout);
        AcroFields acro = stamper.getAcroFields();
        StudentQueries sq = new StudentQueries();
        ObservableList<Student> stulist = FXCollections.observableArrayList(sq.getStudents());
        for (Student s : stulist) {
            if (s.getZID().equals(zid1)) {

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

    public byte[] createAllPdf()
            throws IOException, DocumentException, SQLException {
        // Get data preped for processing
        Document doc = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        PdfWriter pdfW = null;
        List<String> dates = getDates();
        try {
            //Fonts for doc
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);

            //Intro para
            String x = readName();
            Paragraph para1 = new Paragraph("Hi " + x + ", This document provides an overview of your"
                    + "consultation schedule for the upcoming weeks.");

            // Setting up table format - ID | zid | Name | Course | Type | Priority | Date | Time
            float[] columnWidth = {2f, 4f, 10f, 10f, 10f, 5f, 4f};
            PdfPTable tab = new PdfPTable(columnWidth);
            tab.setWidthPercentage(90f);

            //Setting up Headings
            insertCell(tab, "ID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "zID", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "Name", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "Course", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "Type", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "Priority", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(tab, "Time", Element.ALIGN_CENTER, 1, bfBold12);
            tab.setHeaderRows(1);

            //Setting up Doc
            pdfW = PdfWriter.getInstance(doc, byteout);
            doc.open();

            for (String d : dates) {
               int i = 1;
               String name = "null";
               String course = "null";
               List<Consultation> cons = sortCons(d);
               insertCell(tab, "", Element.ALIGN_LEFT, 7, bfBold12);
               insertCell(tab, d, Element.ALIGN_LEFT, 7, bfBold12);
               for (Consultation c:cons) {
                   name = getName(c.getZid());
                   course = getCourse(c.getZid());
                   insertCell(tab, Integer.toString(i), Element.ALIGN_LEFT,1, bf12);
                   insertCell(tab, c.getZid(), Element.ALIGN_CENTER,1, bf12);
                   insertCell(tab, name, Element.ALIGN_CENTER,1, bf12);
                   insertCell(tab, course, Element.ALIGN_CENTER,1, bf12);
                   insertCell(tab, c.getType(), Element.ALIGN_CENTER,1, bf12);
                   insertCell(tab, c.getPriority(), Element.ALIGN_CENTER,1, bf12);
                   insertCell(tab, c.getTime1(), Element.ALIGN_CENTER,1, bf12);
                   i = i + 1;
               }
               
            }
            
            para1.add(tab);
            doc.add(para1);
        } catch (DocumentException dEx) {

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (pdfW != null) {
                //close the writer
                pdfW.close();
            }
            doc.close();
            return byteout.toByteArray();
        }
    }

    

    private void insertCell(PdfPTable tab, String txt, int align, int cols, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(txt.trim(), font));
        cell.setHorizontalAlignment(align);
        cell.setColspan(cols);
        if (txt.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        tab.addCell(cell);
    }

    private String readName() {
        String fName = "temp.txt";
        String line = null;
        String name = "";

        try {
            FileReader fReader = new FileReader(fName);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            String[] sect = line.split(",");
            name = sect[0];

            bReader.close();
            System.out.println(name);

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file");
        } catch (IOException ex) {
            System.out.println("Error Reading File");
        }
        return name;
    }
    
    private List<String> getDates() {
        List<String> dates = new ArrayList<String>();
        ConsultationQueries cq = new ConsultationQueries();
        List<Consultation> cons = cq.getConsultations();
        
        for (Consultation c:cons) {
            String d = c.getDate1();
            if (dates.contains(d)) {
                // do nothing
            } else {
                dates.add(d);
                System.out.println(d);
            }
        }
        Collections.sort(dates);
        return dates;
    }
    
    private List<Consultation> sortCons(String Date) {
        ConsultationQueries cq = new ConsultationQueries();
        List<Consultation> original = cq.getConsultations();
        List<Consultation> sorted = new ArrayList<Consultation>();
        
        for (Consultation c:original) {
            if (c.getDate1().equals(Date)) {
                sorted.add(c);
            }
        }
        
        return sorted;
    }
    
    private String getName(String z) {
        StudentQueries sq = new StudentQueries();
        List<Student> studs = sq.getStudents();
        String name = "";
        
        for (Student s:studs) {
            if (s.getZID().equals(z)) {
                name = s.getFName() + " " + s.getLName();
            }
        }
        
        return name;
    }
    
        private String getCourse(String z) {
        StudentQueries sq = new StudentQueries();
        List<Student> studs = sq.getStudents();
        String name = "";
        
        for (Student s:studs) {
            if (s.getZID().equals(z)) {
                name = s.getCourse();
            }
        }
        
        return name;
    }

}
