package com.java.Zhimdhen_POS.utils;

import java.util.Map;

public interface IAuthenticatedUserService{
    Long getUserId();

    Map<String,Object> getAttributes();
}
