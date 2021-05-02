package com.wu.lib_common_ui.recyclerview.base;

/**
 * @author wuhx
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);


}
