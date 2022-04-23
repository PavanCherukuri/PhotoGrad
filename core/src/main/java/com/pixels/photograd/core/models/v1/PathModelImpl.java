package com.pixels.photograd.core.models.v1;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixels.photograd.core.models.PathModel;
import com.pixels.photograd.core.services.Gallery;

@Model(adaptables = Resource.class, adapters = PathModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PathModelImpl implements PathModel {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@OSGiService
	Gallery gallery;

	@Override
	public List<String> getAssetPathsModel() {
		logger.info("before calling osgi");
		return gallery.getAssetPaths();
	}

}
