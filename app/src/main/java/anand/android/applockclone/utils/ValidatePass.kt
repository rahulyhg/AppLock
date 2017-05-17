package anand.android.applockclone.utils

import java.util.ArrayList

/**
 * Created by anandm on 09/02/17.
 * This class compares two arrray list of pass and validates if both are same
 */

object ValidatePass {

    //the name of the method explains it well...
    fun isTwoArrayListsWithSameValues(list1: ArrayList<Int>?, list2: ArrayList<Int>?): Boolean {
        //null checking
        if (list1 == null && list2 == null)
            return true
        if (list1 == null && list2 != null || list1 != null && list2 == null)
            return false

        //Size check
        if (list1!!.size != list2!!.size)
            return false

        //Compare each integer at each index
        for (i in list1.indices) {
            val i1 = list1[i]
            val i2 = list2[i]
            if (i1 != i2)
                return false
        }

        return true
    }
}
