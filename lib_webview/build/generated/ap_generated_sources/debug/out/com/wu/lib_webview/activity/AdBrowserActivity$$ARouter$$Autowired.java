package com.wu.lib_webview.activity;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;
import java.lang.Object;
import java.lang.Override;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class AdBrowserActivity$$ARouter$$Autowired implements ISyringe {
  private SerializationService serializationService;

  @Override
  public void inject(Object target) {
    serializationService = ARouter.getInstance().navigation(SerializationService.class);
    AdBrowserActivity substitute = (AdBrowserActivity)target;
    substitute.mUrl = substitute.getIntent().getStringExtra("url");
  }
}
