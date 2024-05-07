package com.kashif.dto;

import com.kashif.entity.UserRegistration;

import java.util.List;

public class DataTable {
    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List<UserRegistration> data;

    public List<UserRegistration> getData() {
        return data;
    }

    public void setData(List<UserRegistration> data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
