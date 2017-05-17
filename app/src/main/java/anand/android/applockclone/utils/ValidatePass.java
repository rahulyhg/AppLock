package anand.android.applockclone.utils;

import java.util.ArrayList;

/**
 * Created by anandm on 09/02/17.
 *
 * This class compares two arrray list of pass and validates if both are same
 */

public class ValidatePass {

    //the name of the method explains it well...
    public static boolean isTwoArrayListsWithSameValues(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        //Size check
        if(list1.size()!=list2.size())
            return false;

        //Compare each integer at each index
        for(int i = 0; i < list1.size(); i++)
        {
            int i1 = list1.get(i);
            int i2 = list2.get(i);
            if(!(i1 == i2))
                return false;
        }

        return true;
    }
}
