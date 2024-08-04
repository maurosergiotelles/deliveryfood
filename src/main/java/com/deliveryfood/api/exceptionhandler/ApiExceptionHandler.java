package com.deliveryfood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.deliveryfood.domain.exception.EntidadeChaveEstrangeiraNaoEncontradaException;
import com.deliveryfood.domain.exception.EntidadeEmUsoException;
import com.deliveryfood.domain.exception.EntidadeNaoEncontradaException;
import com.deliveryfood.domain.exception.NegocioException;
import com.deliveryfood.domain.exception.UsuarioAlterarSenhaException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String OCORREU_UM_ERRO_INTERNO = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o adminstrador.";

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ex.printStackTrace();
		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Recurso não encontrado")

				.detail(String.format("O recurso '%s', que você tentou acesar, é inexistente.", ex.getResourcePath())).build();

		System.out.println("handleTypeMismatch");

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Mensagem incompreensível")

				.detail("O corpo da requisição está inválido. Verifique o erro da sintaxe.").build();

		ex.printStackTrace();

		System.out.println("handleTypeMismatch");

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

//		ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));
//
//		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Mensagem incompreensível")

				.detail(String.format("O parâmetro da URL '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", ex.getName(), ex.getValue(),
						ex.getRequiredType().getSimpleName()))
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);
		} else if (rootCause instanceof IgnoredPropertyException) {
			return handleUnrecognizedPropertyException((IgnoredPropertyException) rootCause, headers, status, request);
		} else if (rootCause instanceof JsonParseException) {
			return handleUnrecognizedPropertyException((JsonParseException) rootCause, headers, status, request);
		}

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Mensagem incompreensível")

				.detail("O corpo da requisição está inválido. Verifique o erro da sintaxe.").build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(JsonParseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Problema na estrutura do json")

				.detail(ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(IgnoredPropertyException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Mensagem incompreensível")

				.detail(String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente", path)).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Mensagem incompreensível")

				.detail(String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente", path)).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Entidade não encontrada")

				.detail(String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. O tipo correto é %s", path, ex.getValue(), ex.getTargetType().getSimpleName())).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler({ EntidadeNaoEncontradaException.class })
	public ResponseEntity<?> tratarEstadoNaoEncontradoException(EntidadeNaoEncontradaException exception, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/entidade-nao-encontrada")

				.title("Entidade não encontrada")

				.detail(exception.getMessage()).build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeChaveEstrangeiraNaoEncontradaException.class)
	public ResponseEntity<?> tratarChaveEstrageiraNaoEncontarada(EntidadeChaveEstrangeiraNaoEncontradaException exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/bad_reques")

				.title("Entidade não encontrada")

				.detail(exception.getMessage()).build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException exception, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/bad_reques")

				.title("Entidade não encontrada")

				.detail(exception.getMessage()).build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);

	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		HttpStatus status = (HttpStatus) statusCode;

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value()).detail(ex.getMessage()).type("https://deliveryfood").build();
		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).detail(ex.getMessage()).type("https://deliveryfood").build();
		}

		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception exception, WebRequest request) {

		exception.printStackTrace();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/erro_interno")

				.title("Ocorreu um erro interno.")

				.detail(OCORREU_UM_ERRO_INTERNO)

				.userMessage(OCORREU_UM_ERRO_INTERNO)

				.timestamp(LocalDateTime.now())

				.build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();
		List<Problem.Object> problemFields = bindingResult.getAllErrors().stream().map(objectError -> {

			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				name = ((FieldError) objectError).getField();
			}

			return Problem.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/dados-invalidos")

				.title("Dados inválidos")

				.detail("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")

				.userMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")

				.objects(problemFields)

				.timestamp(LocalDateTime.now())

				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({ UsuarioAlterarSenhaException.class })
	public ResponseEntity<?> tratarUsuarioAlterarSenhaException(UsuarioAlterarSenhaException exception, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/senha-errada")

				.title("Entidade não encontrada")

				.detail(exception.getMessage()).build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<?> tratarUsuarioAlterarSenhaException(NegocioException exception, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = Problem.builder()

				.status(status.value())

				.type("https://deliveryfood/senha-errada")

				.title("Entidade com problema")

				.detail(exception.getMessage()).build();

		return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
	}

}
