package com.gym_membership.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	 String uploadProfileImage(MultipartFile file);

}
