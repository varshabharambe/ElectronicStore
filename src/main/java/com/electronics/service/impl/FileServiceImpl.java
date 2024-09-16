package com.electronics.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronics.exception.BadApiRequestException;
import com.electronics.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFileName = file.getOriginalFilename();
		logger.info("originalFileName {}",originalFileName);
		String randomFileName = UUID.randomUUID().toString();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileNameWithExtension = randomFileName + extension;
		String fullFilePath = path + fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
			//upload file
			File folder = new File(path);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			Files.copy(file.getInputStream(), Paths.get(fullFilePath));
			return fileNameWithExtension;
			
		}else {
			throw new BadApiRequestException("File with " + extension + " extension not allowed");
		}
		
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		String fullFilePath = path + File.separator + name;
		InputStream inputStream = new FileInputStream(fullFilePath);
		return inputStream;
	}

}
