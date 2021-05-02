package com.wu.lib_base.model.discory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: wuhx
 * @function:
 */
public class RecommandHeadValue implements Serializable {

    public ArrayList<String> ads;
    public ArrayList<RecommandMiddleValue> middle;
    public ArrayList<RecommandFooterValue> footer;

}
