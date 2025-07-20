package com.waracle.cakemgr;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.model.CakeEntity;
import com.waracle.cakemgr.service.CakesService;
import com.waracle.cakemgr.utility.CakeImporterReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;


import java.util.List;

@SpringBootApplication
public class CakeManagerApplication {

	@Autowired
	private CakesService cakesService;

	@Autowired
	private CakeImporterReader cakeImporterReader;

	private static final Logger logger = LoggerFactory.getLogger(CakeManagerApplication.class);

	public static void main(String[] args) {
		logger.info("main - CakeManagerApplication.run: begin");
		SpringApplication.run(CakeManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner initializeData(@Autowired CakeRepository repository ) {
		logger.info("main - CakeManagerApplication.initializeData: begin");

		return args -> {
			List<CakeEntity> listCakes =  cakeImporterReader.importCakes();
			cakesService.saveAll(listCakes);
		};
	}



}
