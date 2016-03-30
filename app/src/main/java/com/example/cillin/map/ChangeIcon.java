package com.example.cillin.map;

import android.widget.ImageView;

/**
*The change icon class is used to set the flag image on the markers menu and its items.
 * Each flag is set according to the marker the county is on.
 * The county string and imageview of the current XML file must be sent to this function.
 * The function sets the image view to a image stored in mipmap based on the name of the county string.
 **/
public class ChangeIcon
{
    //Takes in the current county string and image view ID
    public static void setIcon(String countyString, ImageView mIcon)
    {
        /**
         * If the county string is cork, set the image view to the cork flag
         */
        if (countyString.contains("Cork"))
             mIcon.setImageResource(R.mipmap.cork);
        /**
         * If the county string is dublin or a dublin district, set the image view to the dublin flag
         */
        else if(countyString.contains("Dublin") || countyString.contains("D1") || countyString.contains("D2") || countyString.contains("D3") ||
                countyString.contains("D4") || countyString.contains("D5") || countyString.contains("D6") || countyString.contains("D7") ||
                countyString.contains("D8") || countyString.contains("D9") || countyString.contains("D10") || countyString.contains("D11") ||
                countyString.contains("D12") || countyString.contains("D13") || countyString.contains("D14") || countyString.contains("D15") ||
                countyString.contains("D16") || countyString.contains("D17") || countyString.contains("D18") || countyString.contains("D19") ||
                countyString.contains("D20") || countyString.contains("D22") || countyString.contains("D24"))
            mIcon.setImageResource(R.mipmap.dublin);
        /**
         * If the county string is kildare, set the image view to the kildare flag
         */
        else if(countyString.contains("Kildare"))
            mIcon.setImageResource(R.mipmap.kildare);
        /**
         * If the county string is kilkenny, set the image view to the kilkenny flag
         */
        else if(countyString.contains("Kilkenny"))
            mIcon.setImageResource(R.mipmap.kilkenny);
        /**
         * If the county string is meath, set the image view to the meath flag
         */
        else if(countyString.contains("Meath"))
            mIcon.setImageResource(R.mipmap.meath);
        /**
         * If the county string is mayo, set the image view to the mayo flag
         */
        else if(countyString.contains("Mayo"))
            mIcon.setImageResource(R.mipmap.mayo);
        /**
         * If the county string is monaghan, set the image view to the monaghan flag
         */
        else if(countyString.contains("Monaghan"))
            mIcon.setImageResource(R.mipmap.monaghan);
        /**
         * If the county string is leitrim, set the image view to the leitrim flag
         */
        else if(countyString.contains("Leitrim"))
            mIcon.setImageResource(R.mipmap.leitrim);
        /**
         * If the county string is limerick, set the image view to the limerick flag
         */
        else if(countyString.contains("Limerick"))
            mIcon.setImageResource(R.mipmap.limerick);
        /**
         * If the county string is sligo, set the image view to the sligo flag
         */
        else if(countyString.contains("Sligo"))
            mIcon.setImageResource(R.mipmap.sligo);
        /**
         * If the county string is wicklow, set the image view to the wicklow flag
         */
        else if(countyString.contains("Wicklow"))
            mIcon.setImageResource(R.mipmap.wicklow);
        /**
         * If the county string is wexford, set the image view to the wexford flag
         */
        else if(countyString.contains("Wexford"))
            mIcon.setImageResource(R.mipmap.wexford);
        /**
         * If the county string is westmeath, set the image view to the westmeath flag
         */
        else if(countyString.contains("Westmeath"))
            mIcon.setImageResource(R.mipmap.westmeath);
        /**
         * If the county string is waterford, set the image view to the waterford flag
         */
        else if(countyString.contains("Waterford"))
            mIcon.setImageResource(R.mipmap.waterford);
        /**
         * If the county string is galway, set the image view to the galway flag
         */
        else if(countyString.contains("Galway"))
            mIcon.setImageResource(R.mipmap.galway);
        /**
         * If the county string is offaly, set the image view to the offaly flag
         */
        else if(countyString.contains("Offaly"))
            mIcon.setImageResource(R.mipmap.offaly);
        /**
         * If the county string is donegal, set the image view to the donegal flag
         */
        else if(countyString.contains("Donegal"))
            mIcon.setImageResource(R.mipmap.donegal);
        /**
         * If the county string is carlow, set the image view to the carlow flag
         */
        else if(countyString.contains("Carlow"))
            mIcon.setImageResource(R.mipmap.carlow);
        /**
         * If the county string is cavan, set the image view to the cavan flag
         */
        else if(countyString.contains("Cavan"))
            mIcon.setImageResource(R.mipmap.cavan);
        /**
         * If the county string is clare, set the image view to the clare flag
         */
        else if(countyString.contains("Clare"))
            mIcon.setImageResource(R.mipmap.clare);
        /**
         * If the county string is laois, set the image view to the laois flag
         */
        else if(countyString.contains("Laois"))
            mIcon.setImageResource(R.mipmap.laois);
        /**
         * If the county string is longford, set the image view to the longford flag
         */
        else if(countyString.contains("Longford"))
            mIcon.setImageResource(R.mipmap.longford);
        /**
         * If the county string is louth, set the image view to the louth flag
         */
        else if(countyString.contains("Louth"))
            mIcon.setImageResource(R.mipmap.louth);
        /**
         * If the county string is roscommon, set the image view to the roscommon flag
         */
        else if(countyString.contains("Roscommon"))
            mIcon.setImageResource(R.mipmap.roscommon);
        /**
         * If the county string is kerry, set the image view to the kerry flag
         */
        else if(countyString.contains("Kerry"))
            mIcon.setImageResource(R.mipmap.kerry);
        /**
         * If the county string is tipperary, set the image view to the tipperary flag
         */
        else if(countyString.contains("Tipperary"))
            mIcon.setImageResource(R.mipmap.tipperary);
        /**
         * Else set the icon to an image of a flag
         */
        else
            mIcon.setImageResource(R.mipmap.icondefault);
    }
}
