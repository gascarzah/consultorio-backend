package com.gafahtec;

import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gafahtec.dto.request.EmpresaRequest;
import com.gafahtec.dto.request.RolRequest;
import com.gafahtec.dto.request.UsuarioRequest;
import com.gafahtec.service.IEmpresaService;
import com.gafahtec.service.IRolService;
import com.gafahtec.service.IUsuarioService;

@SpringBootApplication
@EnableScheduling
public class GafahtecApplication {

	public static void main(String[] args) {
		SpringApplication.run(GafahtecApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(IRolService iRolService, IEmpresaService iEmpresaService,
			IUsuarioService service) {
		return args -> {


				var rol1 = RolRequest.builder().idRol(1).nombre("ADMIN").build();
				iRolService.registrar(rol1);

				var empresa1 = EmpresaRequest.builder().idEmpresa(1).nombre("gafah").build();

				iEmpresaService.registrar(empresa1);

				var admin = UsuarioRequest.builder().nombres("GIOVANNI").numeroDocumento("41181764")
						.email("ga@correo.com").idEmpresa(1).idRol(1)
						.apellidoPaterno("ASCARZA").apellidoMaterno("HINOSTROZA")

						.build();

				var obj = service.register(admin);
				if(!Objects.isNull(obj)) {
					System.out
					.println("Admin token: " + obj);
				}
				
				

			
		};
	}
}
