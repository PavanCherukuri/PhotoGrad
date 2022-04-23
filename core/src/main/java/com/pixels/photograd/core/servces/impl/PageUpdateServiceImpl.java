package com.pixels.photograd.core.servces.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.pixels.photograd.core.services.AdminResourceResolver;
import com.pixels.photograd.core.services.PageUpdateService;

@Component(service = PageUpdateService.class, immediate = true)
public class PageUpdateServiceImpl implements PageUpdateService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PageUpdateServiceImpl.class);

	@Reference
	private AdminResourceResolver resolverFactory;

	@Override
	public void updatePageData() {
		// Reading the JSON File from DAM.
		Resource original;
		String myJSON = "";

		LOGGER.info("before factory..");
		try {
			ResourceResolver resolver = resolverFactory.getResourceResolver();
			Resource resource = resolver.getResource("/content/dam/photograd/us/en/home/crondata.json");
			Asset asset = resource.adaptTo(Asset.class);
			original = asset.getOriginal();
			InputStream content = original.adaptTo(InputStream.class);

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			JSONObject jsonObj = new JSONObject(sb.toString()); // In jsonObj I will get the data from the JSON file
																// from DAM.
			myJSON = jsonObj.get("description").toString();
			PageManager pageManager = resolver.adaptTo(PageManager.class);
			Session session = resolver.adaptTo(Session.class);
			Page page = pageManager.getPage("/content/photograd/language-masters/en/home");
			Node newNode = page.adaptTo(Node.class);
			Node cont = newNode.getNode("jcr:content");
			Node parNode = JcrUtils.getNodeIfExists(cont, "root");
			Node textNode = JcrUtils.getNodeIfExists(parNode, "text_image");
			textNode.setProperty("title", myJSON);
			session.save();
		} catch (JSONException | RepositoryException | IOException e1) {
			LOGGER.info("EXCEPTION");
		}
	}

}
