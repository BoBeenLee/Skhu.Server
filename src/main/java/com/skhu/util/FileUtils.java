package com.skhu.util;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	public static final String FILE_PATH = "G:/dev/eclipse-ee/git/CompetitionSystem/competition_system_01/src/main/webapp/file/";
	public static final String FILE_RELATIVE_PATH = "webapps/competition_system_01/file/";
	
	public static boolean uploadFile(MultipartFile uploadfile, int articleId, String userId, int isArticle){
		
		 if (uploadfile != null && uploadfile.getSize() > 0) {
	            String fileName = uploadfile.getOriginalFilename();
	            String fileUrl = FILE_PATH + articleId + "-" + userId + "-" + fileName;
	            String fileRelativeUrl = FILE_RELATIVE_PATH + articleId + "-" + userId + "-" + fileName;
	            
	            if(fileName.endsWith(".jsp") || fileName.endsWith(".php"))
	            	return false;
	            
	            File file = new File(fileRelativeUrl);
	            try {
	                //File file = new File(fileRelative.getAbsolutePath());
	                uploadfile.transferTo(file);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	    }
		return true;
	}
}
