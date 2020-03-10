package ru.pomoysam.itstack;


public enum ResourcesModel {

    FIRST_SCREEN(R.string.newPass, R.layout.newsone),
    SECOND_SCREEN(R.string.newPassText, R.layout.newstwo),
    ;

    private int mTitleResourceId;

    private int mLayoutResourceId;

    ResourcesModel( int titleResId , int layoutResId) {
        mTitleResourceId = titleResId;
        mLayoutResourceId = layoutResId;
    }

    public int getLayoutResourceId() {
        return mLayoutResourceId;

    }


    public int getTitleResourceId() {
        return mTitleResourceId;
    }
}
