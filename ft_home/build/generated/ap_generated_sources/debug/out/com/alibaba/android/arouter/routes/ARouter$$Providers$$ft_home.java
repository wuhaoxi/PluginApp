package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.wu.ft_home.service.HomeServiceImpl;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Providers$$ft_home implements IProviderGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> providers) {
    providers.put("com.wu.lib_base.ft_home.service.HomeService", RouteMeta.build(RouteType.PROVIDER, HomeServiceImpl.class, "/home/home_service", "home", null, -1, -2147483648));
  }
}
