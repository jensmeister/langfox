package com.langfox.langfoxandroid;

//use git in Android Studio
//https://www.coursera.org/learn/internet-of-things-dragonboard/lecture/EfKqi/using-git-with-android-studio

//source of recycler view
//https://www.learn2crack.com/2016/02/image-loading-recyclerview-picasso.html

//source of Bottom Navigation Tab and Fragment
//http://blog.csdn.net/guolin_blog/article/details/13171191


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class TopLevelActivity extends AppCompatActivity implements View.OnClickListener {

    //create the fragment to display word-remembering battles
    private BattleFragment battleFragment;

    //create the fragment to display wordbooks
    private WordbookFragment wordbookFragment;

    //create the fragment to display findings
    private FindingFragment findingFragment;

    //create the fragment to display my profile
    private MeFragment meFragment;


    //create view of battle interface
    private View battleLayout;

    //create view of workbook interface
    private View workbookLayout;

    //create view of finding interface
    private View findingLayout;

    //create view of my profile interface
    private View meLayout;


    //create widget to display battle icon on Bottom Tab
    private ImageView battleImage;

    //create widget to display wordbook icon on Bottom Tab
    private ImageView wordbookImage;

    //create widget to display finding icon on Bottom Tab
    private ImageView findingImage;

    //create widget to display my profile icon on Bottom Tab
    private ImageView meImage;


    //create widget to display battle title text on Bottom Tab
    private TextView battleText;

    //create widget to display wordbook title text on Bottom Tab
    private TextView wordbookText;

    //create widget to display finding icon on Bottom Tab
    private TextView findingText;

    //create widget to display my profile icon on Bottom Tab
    private TextView meText;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CacheInit.init();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_top_level);
        // initialize the view
        initViews();
        fragmentManager = getSupportFragmentManager();
        // select the second tab when it is firstly created
        setTabSelection(0);
    }

    /**
     * obtain instance of each widget，set OnClick event
     */
    private void initViews() {

        battleLayout = findViewById(R.id.battle_layout);
        workbookLayout = findViewById(R.id.wordbook_layout);
        findingLayout = findViewById(R.id.finding_layout);
        meLayout = findViewById(R.id.me_layout);
        battleImage = (ImageView) findViewById(R.id.battle_image);
        wordbookImage = (ImageView) findViewById(R.id.wordbook_image);
        findingImage = (ImageView) findViewById(R.id.finding_image);
        meImage = (ImageView) findViewById(R.id.me_image);
        battleText = (TextView) findViewById(R.id.battle_text);
        wordbookText = (TextView) findViewById(R.id.wordbook_text);
        findingText = (TextView) findViewById(R.id.finding_text);
        meText = (TextView) findViewById(R.id.me_text);
        battleLayout.setOnClickListener(this);
        workbookLayout.setOnClickListener(this);
        findingLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.battle_layout:
                // the first tab is selected when battle layout clicked
                setTabSelection(0);
                break;
            case R.id.wordbook_layout:
                // the second tab is selected when workbook layout clicked
                setTabSelection(1);
                break;
            case R.id.finding_layout:
                // the third tab is selected when finding layout clicked
                setTabSelection(2);
                break;
            case R.id.me_layout:
                // the last tab is selected when me layout clicked
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * set selected tab according to the value of index parameter
     *
     * @param index each tab has underlying subscript 0 refers to battle, 1 wordbook, 2 finding, 3 me
     */
    private void setTabSelection(int index) {
        // clear previous selection ahead of each new selection
        clearSelection();
        // start a new Fragment event
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // First head all the fragment in order to prevent fragment overlapping on single view
        hideFragments(transaction);
        switch (index) {
            case 0:
                // When OnClick is called, change the drawable file and text color
                battleImage.setImageResource(R.drawable.battle);
                battleText.setTextColor(Color.WHITE);
                if (battleFragment == null) {
                    // create one fragment and add to activity if this fragment is null
                    battleFragment = new BattleFragment();
                    transaction.add(R.id.content, battleFragment);
                } else {
                    // show fragment directly if it is not null
                    transaction.show(battleFragment);
                }
                break;
            case 1:
                // When OnClick is called, change the drawable file and text color
                wordbookImage.setImageResource(R.drawable.wordbook);
                wordbookText.setTextColor(Color.WHITE);
                if (wordbookFragment == null) {
                    // create one fragment and add to activity if this fragment is null
                    wordbookFragment = new WordbookFragment();
                    transaction.add(R.id.content, wordbookFragment);
                } else {
                    // show fragment directly if it is not null
                    transaction.show(wordbookFragment);
                }
                break;
            case 2:
                // When OnClick is called, change the drawable file and text color
                findingImage.setImageResource(R.drawable.finding);
                findingText.setTextColor(Color.WHITE);
                if (findingFragment == null) {
                    // create one fragment and add to activity if this fragment is null
                    findingFragment = new FindingFragment();
                    transaction.add(R.id.content, findingFragment);
                } else {
                    // show fragment directly if it is not null
                    transaction.show(findingFragment);
                }
                break;
            case 3:
            default:
                // When OnClick is called, change the drawable file and text color
                meImage.setImageResource(R.drawable.me);
                meText.setTextColor(Color.WHITE);
                if (meFragment == null) {
                    // create one fragment and add to activity if this fragment is null
                    meFragment = new MeFragment();
                    transaction.add(R.id.content, meFragment);
                } else {
                    // show fragment directly if it is not null
                    transaction.show(meFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * clear status being selected
     */
    private void clearSelection() {
        battleImage.setImageResource(R.drawable.battle);
        battleText.setTextColor(Color.parseColor("#82858b"));
        wordbookImage.setImageResource(R.drawable.wordbook);
        wordbookText.setTextColor(Color.parseColor("#82858b"));
        findingImage.setImageResource(R.drawable.finding);
        findingText.setTextColor(Color.parseColor("#82858b"));
        meImage.setImageResource(R.drawable.me);
        meText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * hide all fragments
     *
     * @param transaction transaction is used to manipulate fragment
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (battleFragment != null) {
            transaction.hide(battleFragment);
        }
        if (wordbookFragment != null) {
            transaction.hide(wordbookFragment);
        }
        if (findingFragment != null) {
            transaction.hide(findingFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }
}



