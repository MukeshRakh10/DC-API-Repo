package in.mk.co.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.mk.co.exception.handler.CustomHandler;
import in.mk.co.exception.info.ExceptionInfo;

@RestControllerAdvice
public class GlobalHandler {

	@ExceptionHandler(value = CustomHandler.class)
	public ResponseEntity<ExceptionInfo> handleAppCustomException(CustomHandler app) {
		String exMsg = app.getMessage();
		ExceptionInfo ex = new ExceptionInfo();
		ex.setErrorCode("EX0001867");
		ex.setErrorMsg(exMsg);
		return new ResponseEntity<>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<ExceptionInfo> handleNPE(NullPointerException app) {
		String exMsg = app.getMessage();
		ExceptionInfo ex = new ExceptionInfo();
		ex.setErrorCode("EX0001868");
		ex.setErrorMsg(exMsg);
		return new ResponseEntity<>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
