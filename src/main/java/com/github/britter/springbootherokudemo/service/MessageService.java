package com.github.britter.springbootherokudemo.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

    public String get(String code, Object[] args){
        return accessor.getMessage(code, args);
    }

}
