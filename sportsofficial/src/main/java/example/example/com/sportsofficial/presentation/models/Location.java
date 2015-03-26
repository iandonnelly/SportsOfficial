package example.example.com.sportsofficial.presentation.models;

public class Location {
    private String mCountry;
    private String mState;
    private String mCity;

    public Location () {
        mCountry = null;
        mState = null;
        mCity = null;
    }

    public Location (String country, String city) {
        setCountry(country);
        setCity(city);
        mState = null;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getState() {
        return mState;
    }

    public String getCity() {
        return mCity;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public void setState(String state) {
        mState = state;
    }

    public void setCity(String city) {
        mCity = city;
    }

    @Override
    public String toString() {
        if (mState == null) {
            return mCity;
        } else {
            return mCity + ", " + mState;
        }
    }
}
