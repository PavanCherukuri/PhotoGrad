package com.pixels.photograd.core.servces.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pixels.photograd.core.services.AdminResourceResolver;

@Component(service = AdminResourceResolver.class)
public class AdminResourceResolverImpl implements AdminResourceResolver{
	
	protected static final Logger LOG = LoggerFactory.getLogger(AdminResourceResolverImpl.class);
	
	@Reference
	private ResourceResolverFactory resolverFactory;

	public ResourceResolver getResourceResolver() {
		ResourceResolver resourceResolver = null;
		try {
			if (resolverFactory != null) {
				Map<String, Object> param = new HashMap<>();
				param.put(ResourceResolverFactory.SUBSERVICE, "pgtestuser");
				resourceResolver = resolverFactory.getServiceResourceResolver(param);
			}
			LOG.debug("ResourceResolver : {}", resourceResolver);

		} catch (LoginException e) {
			LOG.error("ResourceResolver Exception : {}", e.getMessage());
		}
		return resourceResolver;
	}

}
