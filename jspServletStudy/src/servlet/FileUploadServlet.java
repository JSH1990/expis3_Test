package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
//import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;



@MultipartConfig(
		location = "C:\\attaches",
		maxFileSize = -1,
		maxRequestSize = -1,
		fileSizeThreshold = 1024)
@WebServlet("/fileUpload")
public class FileUploadServlet extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
    
//	private static final String CHARSET = "utf-8";
//	private static final String ATTACHES_DIR = "C:\\attaches";
//	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
	
	private static final String CHARSET = "UTF-8";
	private static final String ATTACHES_DIR = "C:\\attaches";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	response.setContentType("text/html; charset=UTF-8");
	request.setCharacterEncoding(CHARSET);
	PrintWriter out = response.getWriter();
	String contentType = request.getContentType();
	
	if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts) {
			System.out.printf("파라미터 명 : %s, contentType : %s, size : %d bytes \n", part.getName(), part.getContentType(), part.getSize());
			
			
			if (part.getHeader("Content-Disposition").contains("filename=")) {
				String fileName = extractFileName(part.getHeader("Content-Disposition"));
				
				if(part.getSize() > 0) {
					System.out.printf("업로드 파일 명 : %s \n", fileName);
					part.write(ATTACHES_DIR + File.separator + fileName);
					part.delete();
				}
			}else {
				String formValue = request.getParameter(part.getName());
				System.out.printf("name : %s, value : %s \n", part.getName(), formValue);
			}
		}
		
		out.println("<h1>업로드 완료</h1>");
	}else {
		out.println("<h1>enctype이 multipart/form-data가 아님</h1>");
	}
}
	
	private String extractFileName(String partHeader) {
		for (String cd : partHeader.split(";")) {
			if (cd.trim().startsWith("filename")) {
			String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");;
			int index = fileName.lastIndexOf(File.separator);
			return fileName.substring(index + 1);
		}
		}
		return null;
		
		
//		response.setContentType("text/html; charset=UTF-8");
//		request.setCharacterEncoding(CHARSET);
//		PrintWriter out = response.getWriter();
//		File attachesDir = new File(ATTACHES_DIR);
//		
//		
//		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
//
//		fileItemFactory.setRepository(attachesDir);
//		fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
//		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
//		
//		
//		try {
//		List<FileItem> items = fileUpload.parseRequest(request);
//		
//		for (FileItem item : items) {
//			if (item.isFormField()) {
//				System.out.printf("파라미터 명: %s, 파라미터 값: %s \n", item.getFieldName(), item.getString(CHARSET));
//			}else {
//				System.out.printf("파라미터 명: %s, 파라미터 값: %s \n", item.getFieldName(), item.getName(), item.getSize());
//				
//			if(item.getSize() > 0) {
//				String seperator = File.separator;
//				int index = item.getName().lastIndexOf(seperator);
//				String fileName = item.getName().substring(index + 1);
//				File uploadFile = new File(ATTACHES_DIR + seperator + fileName);
//				item.write(uploadFile);
//			
//			}
//			}
//		}
//		
//		out.println("<h1> 파일 업로드 완료 </h1>");
//	} catch (Exception e) {
//		e.printStackTrace();
//		out.println("<h1> 파일 업로드 완료 </h1>");
//	}

	}


}
