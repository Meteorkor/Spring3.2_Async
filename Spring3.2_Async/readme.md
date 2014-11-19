##Async
1. Callable
	+ Runnabl과 유사 별도 쓰레드, 실행 결과 타입 지정(뷰이름, ModelAndView, @ResponseBody)

2. WebAsyncTask
	+ Callable 과 동일한 방식
	+ 쓰레드풀 지정
	+ Timeout 지정
	+ callable을 WebAsyncTask 를 담아서 리턴

3. DeferredResult
	+ 작업 쓰레드를 생성하지 않는다.
	+ MVC 컨트롤러 리턴값을 별개의 작업에서 지정
	+ private static ConcurrentLinkedQueue<DeferredResult<String>> queue = new ConcurrentLinkedQueue<DeferredResult<String>>();같은 곳에 담아서 이벤트 처리 하도록 사용 하기도 한다.
	+ TimeOut 에 따라 대기하는 시간이 달라진다(TimeOut을 길게 주면 오랫동안 setResult 되길 기다린다.)
	