package com.hans.sses.cms.board.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class noticeDownloadView extends AbstractView{

	public void Download(){                 
		setContentType("application/download; utf-8");             
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileName = "";
		String filePath = (String) model.get("filePath");

		File file = new File(filePath); 
				
		response.setContentType(getContentType());        
		response.setContentLength((int)file.length());                 
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;   
		boolean ie11 = userAgent.indexOf("Trident") > -1;
		boolean chrom = userAgent.indexOf("Chrome") > -1;
		
		if (ie || chrom || ie11) {              
			fileName = URLEncoder.encode(file.getName(), "utf-8");                                 
		}
		else {                         
			fileName = new String(file.getName().getBytes("utf-8"));                          
		}                  

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");                 
		response.setHeader("Content-Transfer-Encoding", "binary");                 
		
		OutputStream out = response.getOutputStream();                 
		FileInputStream fis = null;                 
		
		try {                         
			fis = new FileInputStream(file);                         
			FileCopyUtils.copy(fis, out);
		}catch(Exception e){                         
			}finally{ 
				if(fis != null){try{fis.close();}catch(Exception e){}
			}                     
		}                 
		out.flush();             
	}
	
}