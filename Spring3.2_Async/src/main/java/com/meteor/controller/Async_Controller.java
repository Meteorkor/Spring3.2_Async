package com.meteor.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

@Controller
public class Async_Controller {

	
	@RequestMapping(value = "/callable", method = RequestMethod.GET)
	public Callable<String> callable(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
					System.out.println("Callable~!");
				return "home";
			}
		};
	}
	
	//////////////
	@RequestMapping(value = "/webasync", method = RequestMethod.GET)
	public WebAsyncTask<String> webasynctask(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return new WebAsyncTask<String>(30000L, new Callable<String>() {

			@Override
			public String call() throws Exception {

				System.out.println("webasync");
				
				return "home";
			}
		});
	}
	//////////////
	
	@RequestMapping(value = "/def", method = RequestMethod.GET)
	public DeferredResult<String> deferredresult(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		DeferredResult<String> deferredResult = new DeferredResult<String>(30000L);
		
		System.out.println("def");
		
		return deferredResult;
	}
	
	/////////////
}
