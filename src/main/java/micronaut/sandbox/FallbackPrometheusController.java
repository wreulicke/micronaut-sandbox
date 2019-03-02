package micronaut.sandbox;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.retry.annotation.Fallback;

@Fallback(includes = RuntimeException.class)
public class FallbackPrometheusController implements Operations {
	
	@Override
	public HttpResponse<?> index() {
		return HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE, "fallbacked");
	}
	
}
