package com.example.flagapplication.models;

import java.util.ArrayList;
import java.util.List;

public class MonthItem {
    private int mYear;
    private int month;
    private List<WeekItem> mWeeks = new ArrayList<>();

    // region Constructor

    public MonthItem(int year, int month) {
        this.mYear = year;
        this.month = month;
    }
    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public List<WeekItem> getWeeks() {
        return mWeeks;
    }

    public void setWeeks(List<WeekItem> weeks) {
        this.mWeeks = weeks;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    // endregion

    @Override
    public String toString() {
        return "MonthItem{"
                + "year='"
                + mYear
                + '\''
                + ", month="
                + month
                + '}';
    }
}
