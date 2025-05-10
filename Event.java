/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacymanagementsystem;

import java.sql.Date;

/**
 *
 * @author WINDOWS 10
 */
public class Event {
    
    private Integer EventID;
    private String EventName;
    private Integer AvaliableTickets;
    private String category;
    private String status;
    private Double price;
    private Date date;
    private String image;
    
    
    // MAKE SURE YOU  WILL FOLLOW THESE DATA TYPES
    public Event(Integer EventID, String EventName ,Integer AvaliableTickets, String category
            , String status ,Double price, String image, Date date){
        this.EventID = EventID;
        this.EventName = EventName;
        this.AvaliableTickets = AvaliableTickets;
        this.category = category;
        this.status = status;
        this.price = price;
        this.date = date;
        this.image = image;
    }
    
    public Event(String string) {
		// TODO Auto-generated constructor stub
	}

	public Integer getEventID(){
        return EventID;
    }
    public String getEventName(){
        return EventName;
    }
    public Integer getProductName(){
        return AvaliableTickets;
    }
    public String getCategory(){
        return category;
    }
    public String getStatus(){
        return status;
    }
    public Double getPrice(){
        return price;
    }
    public String getImage(){
        return image;
    }
    public Date getDate(){
        return date;
    }
    
}