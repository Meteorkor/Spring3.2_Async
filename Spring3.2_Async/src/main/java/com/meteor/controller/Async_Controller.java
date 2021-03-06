package com.meteor.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

@Controller
public class Async_Controller {

	private static ConcurrentLinkedQueue<DeferredResult<String>> queue = new ConcurrentLinkedQueue<DeferredResult<String>>();
	
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
	
	/**
	 * DeferredResult 초기 값 Time Out 까지 setResult 를 기다림
	 * Time Out 되면 DisConnection
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/def", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody DeferredResult<String> deferredresult(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//DeferredResult<String> deferredResult = new DeferredResult<String>(3000);
		DeferredResult<String> deferredResult = new DeferredResult<String>( Long.MAX_VALUE );
		
		System.out.println("def");

		this.queue.add( deferredResult );
		
		return deferredResult;
	}
	
	//@Scheduled(fixedRate=1000)
	//@Scheduled(fixedDelay=2000)
	public void process_queue(){
		
		if( this.queue.isEmpty() ){
			
		}else{
			this.queue.poll().setResult("처리 완료");
		}
		System.out.println( "처리 수행" );
	}
	@RequestMapping(value="process",method=RequestMethod.GET)
	public void process(){
		
		if( this.queue.isEmpty() ){
			
		}else{
			this.queue.poll().setResult("처리 완료");
		}
		System.out.println( "처리 수행" );
	}
	
	/////////////
}
