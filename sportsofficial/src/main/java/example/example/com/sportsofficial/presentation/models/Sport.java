package example.example.com.sportsofficial.presentation.models;


public class Sport {
    private int mId;
    private String mName;
    private int mSingleClick;
    private int mDoubleClick;
    private int mLongClick;
    private int mIcon;
    private boolean mHasLeagues;
    private boolean mHasTournaments;
    private boolean mHasMatches;

    public Sport() {
        mId = 0;
        mName = null;
        mSingleClick = -1;
        mDoubleClick = -1;
        mLongClick = -1;
        mIcon = -1;
        mHasLeagues = false;
        mHasTournaments = false;
        mHasMatches = false;
    }

    public Sport(String name, int icon, boolean hasLeagues, boolean hasTournaments, boolean hasMatches) {
        mId = -1;
        mName = name;
        mSingleClick = -1;
        mDoubleClick = -1;
        mLongClick = -1;
        mIcon = icon;
        mHasLeagues = hasLeagues;
        mHasTournaments = hasTournaments;
        mHasMatches = hasMatches;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getSingleClick() {
        return mSingleClick;
    }

    public int getDoubleClick() {
        return mDoubleClick;
    }

    public int getLongClick() {
        return mLongClick;
    }

    public int getIcon() {
        return mIcon;
    }

    public boolean hasLeagues () {
        return mHasLeagues;
    }

    public boolean hasTournaments() {
        return mHasTournaments;
    }

    public boolean hasMatches() {
        return mHasMatches;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setSingleClick(int singleClick) {
        mSingleClick = singleClick;
    }

    public void setDoubleClick(int doubleClick) {
        mDoubleClick = doubleClick;
    }

    public void setLongClick(int longClick) {
        mLongClick = longClick;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public void setHasTournaments(boolean hasTournaments) {
        mHasTournaments = hasTournaments;
    }

    public void setHasLeagues(boolean hasLeagues) {
        mHasLeagues = hasLeagues;
    }

    public void setHasMatches(boolean hasMatches) {
        mHasMatches = hasMatches;
    }
}