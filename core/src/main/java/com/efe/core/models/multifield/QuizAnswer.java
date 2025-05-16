package com.efe.core.models.multifield;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuizAnswer {

    @ValueMapValue
	private String logic;

    // @ValueMapValue
	// private String icon;

    // @ValueMapValue
	// private String headline; 

    // @ValueMapValue
	// private String bodyCopy;

    // @ValueMapValue
	// private String cta;


    public String getLogic() {
        return logic;
    }
    

    
}




