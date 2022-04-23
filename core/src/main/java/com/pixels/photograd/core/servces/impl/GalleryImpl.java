package com.pixels.photograd.core.servces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.pixels.photograd.core.services.AdminResourceResolver;
import com.pixels.photograd.core.services.Gallery;

@Component(service = Gallery.class, immediate = true)
public class GalleryImpl implements Gallery {

	protected static final Logger LOG = LoggerFactory.getLogger(GalleryImpl.class);

	@Reference
	QueryBuilder queryBuilder;

	@Reference
	AdminResourceResolver res;


	@Override
	public List<String> getAssetPaths() {
		LOG.info("entered osgi");
		List<String> pathList = new ArrayList<>();

		ResourceResolver resourceResolver = res.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		Map<String, String> predicate = new HashMap<>();
		predicate.put("path", "/content/dam/photograd/us/en/home/submissions");
		predicate.put("type", "dam:Asset");
		predicate.put("p.limit", "-1");
		Query query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);
		SearchResult searchResult = query.getResult();
		LOG.info("hits size : {}",searchResult.getHits().size());
		for (Hit hit : searchResult.getHits()) {
			try {
				Resource resource = hit.getResource();
				String path = resource.getPath();
				pathList.add(path);
			} catch (RepositoryException e) {
				LOG.error("Error while fetching from DAM : {}", e.getMessage());
			}
		}
		LOG.info("path list size : {}",pathList.size());
		return pathList;
	}

}
