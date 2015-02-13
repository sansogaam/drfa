package com.drfa.engine;

import java.io.File;

/**
 * Created by Sanjiv on 2/12/2015.
 */
public interface Comparator {

    public void compare(int primaryKeyIndex, File base, File target);

}
