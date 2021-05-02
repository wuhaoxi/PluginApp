package com.wu.lib_base.ft_home.service.impl;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.lib_base.ft_home.service.HomeService;
import java.lang.Object;
import java.lang.Override;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class HomeImpl$$ARouter$$Autowired implements ISyringe {
  private SerializationService serializationService;

  @Override
  public void inject(Object target) {
    serializationService = ARouter.getInstance().navigation(SerializationService.class);
    HomeImpl substitute = (HomeImpl)target;
    substitute.mHomeService = (HomeService)ARouter.getInstance().build("/home/home_service").navigation();
  }
}
