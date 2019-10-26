package com.projectdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.projectdemo.model.User;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface CommonController<T> {
    public ModelAndView index();
    public ModelAndView create();
    public ModelAndView edit(@RequestParam String id);

    public User store(@RequestBody T entity);
    public String update(HttpServletRequest request);
    public String delete(@RequestParam String id);
    public List<T> getAll();
    public T getOne(@RequestParam String id);
}