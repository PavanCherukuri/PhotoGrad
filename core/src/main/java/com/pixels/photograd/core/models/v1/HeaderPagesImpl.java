package com.pixels.photograd.core.models.v1;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixels.photograd.core.models.HeaderPages;

@Model(adaptables = Resource.class, adapters = HeaderPages.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderPagesImpl implements HeaderPages {
	protected static final Logger LOG = LoggerFactory.getLogger(HeaderPagesImpl.class);
	
	@Inject
	@Optional
	@ChildResource
	Resource pagedetails;
	

	@Override
	public Resource getMultifieldValues() {
		return pagedetails;
	}
	
	
}
