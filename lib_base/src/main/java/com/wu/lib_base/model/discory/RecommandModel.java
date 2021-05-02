package com.wu.lib_base.model.discory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author wuhaoxuan
 * @文件描述：产品实体
 */
public class RecommandModel implements Serializable {

  public ArrayList<RecommandBodyValue> list;
  public RecommandHeadValue head;
}
