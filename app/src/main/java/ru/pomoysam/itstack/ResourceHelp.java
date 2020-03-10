package ru.pomoysam.itstack;


public enum ResourceHelp {

    ONE_SCREEN(R.string.one, R.layout.instructionone),
    TWO_SCREEN(R.string.two, R.layout.instructiontwo),
    TREE_SCREEN(R.string.tree, R.layout.instructiontree),
    FOUR_SCREEN(R.string.four, R.layout.instructionfour),
    FIVE_SCREEN(R.string.five, R.layout.instructionfive),
    SIX_SCREEN(R.string.six, R.layout.instructionsix);

    private int mTitleResourceId;

    private int mLayoutResourceId;

    ResourceHelp( int titleResId , int layoutResId) {
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
