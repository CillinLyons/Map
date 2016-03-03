package com.example.cillin.map;

import android.widget.ImageView;

/**
 * Created by Cillin on 02/03/2016.
 */
public class ChangeIcon
{
    public static void setIcon(String countyString, ImageView mIcon)
    {
        if (countyString.contains("Cork"))
             mIcon.setImageResource(R.mipmap.cork);
        else if(countyString.contains("Dublin"))
            mIcon.setImageResource(R.mipmap.dublin);
        else if(countyString.contains("Kildare"))
            mIcon.setImageResource(R.mipmap.kildare);
        else if(countyString.contains("Kilkenny"))
            mIcon.setImageResource(R.mipmap.kilkenny);
        else if(countyString.contains("Meath"))
            mIcon.setImageResource(R.mipmap.meath);
        else if(countyString.contains("Mayo"))
            mIcon.setImageResource(R.mipmap.mayo);
        else if(countyString.contains("Monaghan"))
            mIcon.setImageResource(R.mipmap.monaghan);
        else if(countyString.contains("Leitrim"))
            mIcon.setImageResource(R.mipmap.leitrim);
        else if(countyString.contains("Limerick"))
            mIcon.setImageResource(R.mipmap.limerick);
        else if(countyString.contains("Sligo"))
            mIcon.setImageResource(R.mipmap.sligo);
        else if(countyString.contains("Wicklow"))
            mIcon.setImageResource(R.mipmap.wicklow);
        else if(countyString.contains("Wexford"))
            mIcon.setImageResource(R.mipmap.wexford);
        else if(countyString.contains("Westmeath"))
            mIcon.setImageResource(R.mipmap.westmeath);
        else if(countyString.contains("Waterford"))
            mIcon.setImageResource(R.mipmap.waterford);
        else if(countyString.contains("Galway"))
            mIcon.setImageResource(R.mipmap.galway);
        else if(countyString.contains("Offaly"))
            mIcon.setImageResource(R.mipmap.offaly);
        else if(countyString.contains("Donegal"))
            mIcon.setImageResource(R.mipmap.donegal);
        else if(countyString.contains("Carlow"))
            mIcon.setImageResource(R.mipmap.carlow);
        else if(countyString.contains("Cavan"))
            mIcon.setImageResource(R.mipmap.cavan);
        else if(countyString.contains("Clare"))
            mIcon.setImageResource(R.mipmap.clare);
        else if(countyString.contains("Laois"))
            mIcon.setImageResource(R.mipmap.laois);
        else if(countyString.contains("Longford"))
            mIcon.setImageResource(R.mipmap.longford);
        else if(countyString.contains("Louth"))
            mIcon.setImageResource(R.mipmap.louth);
        else if(countyString.contains("Roscommon"))
            mIcon.setImageResource(R.mipmap.roscommon);
        else if(countyString.contains("Kerry"))
            mIcon.setImageResource(R.mipmap.kerry);
        else if(countyString.contains("Tipperary"))
            mIcon.setImageResource(R.mipmap.tipperary);
        else
            mIcon.setImageResource(R.mipmap.icondefault);
    }
}
