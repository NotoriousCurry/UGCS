/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ugcs.Model;

import java.time.LocalDate;
import java.util.GregorianCalendar;

/**
 *
 * @author Peterrpancakes
 */
public class Event {

    GregorianCalendar startTime;
    GregorianCalendar endTime;
    LocalDate startlocaldate;
    LocalDate endlocaldate;
    
    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }
    public LocalDate getstartlocaldate(){
        return startlocaldate;
    }
    
        public void setstartlocaldate(LocalDate startlocaldate){
            this.startlocaldate = startlocaldate;
        }
        
        public LocalDate getendlocaldate(){
        return endlocaldate;
    }
    
        public void setendlocaldate(LocalDate endlocaldate){
            this.endlocaldate = endlocaldate;
        }
        
}
