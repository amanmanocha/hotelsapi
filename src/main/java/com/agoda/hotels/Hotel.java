package com.agoda.hotels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hotels")
public class Hotel {
  @Id
  @Column(name = "hotelid")
  private String hotelId;
  @Column(name = "city")
  private String city;
  @Column(name = "room")
  private String room;
  @Column(name = "price")
  private int price;

  Hotel() {
  }

  public Hotel(String hotelId, String city, String room, int price) {
    super();
    this.hotelId = hotelId;
    this.city = city;
    this.room = room;
    this.price = price;
  }

  public String getHotelId() {
    return hotelId;
  }

  public String getCity() {
    return city;
  }

  public String getRoom() {
    return room;
  }

  public int getPrice() {
    return price;
  }

}
