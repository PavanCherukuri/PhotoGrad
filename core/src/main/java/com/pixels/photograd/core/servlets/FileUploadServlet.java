package com.pixels.photograd.core.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.pixels.photograd.core.services.AdminResourceResolver;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/uploadServlet", "sling.servlet.extensions=" + "json" })
public class FileUploadServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Reference
	AdminResourceResolver res;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		final org.apache.sling.api.request.RequestParameter parameter = request.getRequestParameter("file");
		Long size = parameter.getSize();
		Long sizeInKiloBytes = size / 1024;
		String contentType = parameter.getContentType();
		String fileName = parameter.getFileName();
		InputStream is=parameter.getInputStream();
		ResourceResolver resolver=res.getResourceResolver();
		com.day.cq.dam.api.AssetManager assetMgr = resolver.adaptTo(com.day.cq.dam.api.AssetManager.class);
		assetMgr.createAsset("/content/dam/photograd/us/en/home/submissions/"+fileName, is,contentType, true);
	    resolver.commit();
		
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3> Your File is submitted succesfully!</h3><br>");
		out.println("<h3> Here are the details </h3><br>");
		out.println(" <table>");
		out.println("<tr>");
		out.println(" <th>File Name</th>");
		out.println("<th>File Size</th>");
		out.println("<th>Content Type</th>");
		out.println(" </tr>");
		out.println("<tr>");
		out.println("<td>" + fileName + "</td>");
		out.println("<td>" + sizeInKiloBytes + "Kb</td>");
		out.println("<td>" + contentType + "</td>");
		out.println(" </tr>");
		out.println(" <table>");
		out.println("</body>");
		out.println("</html>");
	}
}
