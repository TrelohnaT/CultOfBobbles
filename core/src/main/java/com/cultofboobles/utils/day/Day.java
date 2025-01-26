package com.cultofboobles.utils.day;

public class Day {

    public int duration = 60;

    public int customerMaxCount;

    public String orderForDay;

    public int count = 0;

    public Day(int count, int customerMaxCount, String orderForDay) {
        this.customerMaxCount = customerMaxCount;
        this.orderForDay = orderForDay;
        this.count = count;

    }

}
