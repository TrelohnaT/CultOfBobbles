package com.cultofboobles.utils.day;

public class Day {

    public int duration = 20;

    public int customerMaxCount;

    public String orderForDay;

    public Day(int customerMaxCount, String orderForDay) {
        this.customerMaxCount = customerMaxCount;
        this.orderForDay = orderForDay;
    }

}
