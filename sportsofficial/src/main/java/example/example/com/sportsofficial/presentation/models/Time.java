package example.example.com.sportsofficial.presentation.models;


public class Time {
    private int mHour;
    private int mMinute;
    private int mSecond;

    public Time() {
        mHour = 0;
        mMinute = 0;
        mSecond = 0;
    }

    public Time(int hour, int minute, int second) {
        mHour = hour;
        mMinute = minute;
        mSecond = second;
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute;
    }

    public int getSecond() {
        return mSecond;
    }

    public void setHour(int hour) {
        if (hour >= 0 && hour <= 23) {
            mHour = hour;
        }
    }

    public void setMinute(int minute) {
        if (minute >= 0 && minute <= 59) {
            mMinute = minute;
        }
    }

    public void setSecond(int second) {
        if (second >= 0 && second <= 59) {
            mSecond = second;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(mHour) + ":" + Integer.toString(mMinute);
    }
}
