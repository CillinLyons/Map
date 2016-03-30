package com.example.cillin.map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Cillin on 19/03/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter
{
    public PagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0)
    {
        switch (arg0)
        {
            case 0:
                return new InformationPage1();
            case 1:
                return new InformationPage2();
            case 2:
                return new InformationPage3();
            case 3:
                return new InformationPage4();
            case 4:
                return new InformationPage5();
            case 5:
                return new InformationPage6();
            case 6:
                return new InformationPage7();
            case 7:
                return new InformationPage8();
            case 8:
                return new InformationPage9();
            case 9:
                return new InformationPage10();
            case 10:
                return new InformationPage11();
            case 11:
                return new InformationPage12();
            case 12:
                return new InformationPage13();
            case 13:
                return new InformationPage14();
            case 14:
                return new InformationPage15();
            case 15:
                return new InformationPage16();
            case 16:
                return new InformationPage17();
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 17;
    }
}
