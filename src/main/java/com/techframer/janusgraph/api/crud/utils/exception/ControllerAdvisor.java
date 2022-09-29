package com.techframer.janusgraph.api.crud.utils.exception;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	
	private Logger logger = LogManager.getLogger(ControllerAdvisor.class);

	/*@ExceptionHandler(ProcessingException.class)
	public ResponseEntity<Object> internalServerException(InventoryProcessingException ex, WebRequest request){
		logger.error("Getting error on request:{}, error:{}", request.getContextPath(),
				ex.getMessage());
		ExceptionResponseWrapper exceptionWrapper = new ExceptionResponseWrapper(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage(),request.getDescription(false));

      return new ResponseEntity<>(exceptionWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}*/
}
