package com.gafahtec.consultorio;

import java.util.Objects;

import com.gafahtec.consultorio.service.IEmpresaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gafahtec.consultorio.dto.request.EmpresaRequest;
import com.gafahtec.consultorio.dto.request.RolRequest;
import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.service.IRolService;
import com.gafahtec.consultorio.service.IUsuarioService;


@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
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
