package head4.notify;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@OpenAPIDefinition(servers = {
		@Server(url = "http://localhost:8080", description = "로컬 URL"),
		@Server(url = "https://server.univ-on.com", description = "서버 URL")
})
@EnableJpaAuditing
@SpringBootApplication
public class NotifyApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		SpringApplication.run(NotifyApplication.class, args);
	}

}
