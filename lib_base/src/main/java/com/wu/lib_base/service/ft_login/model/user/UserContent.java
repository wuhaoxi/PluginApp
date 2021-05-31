package com.wu.lib_base.service.ft_login.model.user;


import android.os.Parcel;
import android.os.Parcelable;

import com.wu.lib_base.BaseModel;

/**
 * 用户真正实体数据
 */
public class UserContent extends BaseModel implements Parcelable {

  public String userId; //用户唯一标识符
  public String photoUrl;
  public String name;
  public String tick;
  public String mobile;
  public String platform;

  protected UserContent(Parcel in) {
    userId = in.readString();
    photoUrl = in.readString();
    name = in.readString();
    tick = in.readString();
    mobile = in.readString();
    platform = in.readString();
  }

  public static final Creator<UserContent> CREATOR = new Creator<UserContent>() {
    @Override
    public UserContent createFromParcel(Parcel in) {
      return new UserContent(in);
    }

    @Override
    public UserContent[] newArray(int size) {
      return new UserContent[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userId);
    dest.writeString(photoUrl);
    dest.writeString(name);
    dest.writeString(tick);
    dest.writeString(mobile);
    dest.writeString(platform);
  }
}
