package micronaut.sandbox;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.configuration.hystrix.annotation.Hystrix;
import io.micronaut.configuration.hystrix.annotation.HystrixCommand;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.hateos.JsonError;
import io.micronaut.retry.annotation.Fallback;

@Controller("/prometheus")
@Hystrix(wrapExceptions = true)
public class PrometheusController implements Operations{
	
	private final PrometheusMeterRegistry registry;
	
	private final AtomicInteger integer = new AtomicInteger();
	
	private static final Logger log = LoggerFactory.getLogger(PrometheusController.class);
	
	PrometheusController(PrometheusMeterRegistry registry) {
		this.registry = registry;
	}
	
	@Override
	@Get("/")
	@HystrixCommand(group = "getMetrics",
		properties = @Property(name = "circuitBreaker.requestVolumeThreshold", value = "3"))
	public HttpResponse<?> index() {
		if (integer.incrementAndGet() > 3) {
			log.error("error occured");
			throw new RuntimeException("error");
		}
		return HttpResponse.ok(registry.scrape());
	}
}
