package com.efe.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.bean.PlannerDetail;
import com.efe.core.constants.PlannerLocationConstants;
import com.efe.core.models.PlannerList;
import com.efe.core.models.multifield.Link;
import com.efe.core.utils.EFEUtil;
import com.efe.core.utils.ResourceUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Class PlannerListImpl.
 */
@Model(adaptables = {SlingHttpServletRequest.class,Resource.class}, adapters = PlannerList.class, resourceType = {
        PlannerListImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PlannerListImpl implements PlannerList {

    /** The Constant RESOURCE_TYPE. */
    public static final String RESOURCE_TYPE="efe/components/plannerlist";


    public static final  String LOCATION_PATH ="/content/dam/efe/cf/plannerlocation/locations";

    public static final String PLANNER_PATH ="/content/efe/us/en/plannerdata";


    @Inject
    SlingHttpServletRequest request;

     private List<PlannerDetail> plannerDetails = new ArrayList<>();

    /** The resource resolver. */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The current resource.
     */
    @Self
    private Resource resource;

    /** The id. */
    @ValueMapValue
    private String id;

    /** The Planner target. */
    @ValueMapValue
    private String plannerTarget;

    /** The CTA Label. */
    @ValueMapValue
    private String ctaLabel;

    /** The Planner title. */
    @ValueMapValue
    private String plannerTitle;

    private String STATE ;

    private String CITY ;


    /** The header list. */
    @Inject
    private List<Link> headerList;


    @PostConstruct
    protected void init(){
    String[] selectors = request.getRequestPathInfo().getSelectors();
    if(selectors.length == 2 ) {
        List<String> cf_list = new ArrayList<>();
        STATE = selectors[0];
        CITY = selectors[1];
        String locationPath = LOCATION_PATH+ PlannerLocationConstants.FORWARD_SLASH + STATE + PlannerLocationConstants.FORWARD_SLASH + CITY;
        Resource resource = resourceResolver.getResource(locationPath);
        if(Objects.nonNull(resource)){
            for (Resource item : resource.getChildren()) {

                if (item.isResourceType("dam:Asset")) {

                    Resource masterResource = resourceResolver.getResource(item.getPath() + PlannerLocationConstants.MASTER_NODE);
                    String[] plannerList = ResourceUtil.getProperties(resourceResolver,masterResource.getPath(),"planners");
                    for(String list : plannerList){
                        cf_list.add(list);
                    }

                }

            }
        }
        if(Objects.nonNull(cf_list)){

            for(String item : cf_list){
                PlannerDetail plannerObj = new PlannerDetail();
                Resource planner = resourceResolver.getResource(item);
                if (Objects.nonNull(planner)){
                    Resource plannerMaster = resourceResolver.getResource(planner.getPath() +PlannerLocationConstants.MASTER_NODE);
                    String First_Name = ResourceUtil.getProperty(resourceResolver,plannerMaster.getPath(),"firstName");
                    String Last_Name = ResourceUtil.getProperty(resourceResolver,plannerMaster.getPath(),"lastName");
                    String Title = ResourceUtil.getProperty(resourceResolver,plannerMaster.getPath(),"title");
                    String Image_url = ResourceUtil.getProperty(resourceResolver,plannerMaster.getPath(),"desktopImageurl");
                    String Planner_id = ResourceUtil.getProperty(resourceResolver,plannerMaster.getPath(),"id");
                  //  PlannerDetail plannerObj = new PlannerDetail(First_Name,Last_Name,Title,Image_url);

                    plannerObj.setFirstName(First_Name);
                    plannerObj.setLastName(Last_Name);
                    plannerObj.setTitle(Title);
                    plannerObj.setDesktopurl(Image_url);
                    plannerObj.setButtonurl(PLANNER_PATH+PlannerLocationConstants.DOT+First_Name+PlannerLocationConstants.DOT+Last_Name+PlannerLocationConstants.DOT+Planner_id+".html");
                }
                plannerDetails.add(plannerObj);
            }
        }




    }
    }
    /**
     * Gets the id.
     *
     * @return the id
     */

    @Override
    public String getId() {
        if (id == null) {
            id = EFEUtil.getId(resource);
        }
        return id;
    }

    /**
     * Gets the planner target.
     *
     * @return the planner target
     */

    @Override
    public String getPlannerTarget() {
        return plannerTarget;
    }

    /**
     * Gets the CTA label.
     *
     * @return the CTA label
     */

    @Override
    public String getCtaLabel() {
        return ctaLabel;
    }

    /**
     * Gets the Planner title.
     *
     * @return the Planner title
     */

    @Override
    public String getPlannerTitle() {
        return plannerTitle;
    }
    /**
     * Gets the State.
     *
     * @return the State
     */
    @Override
    public String getState() {
        return STATE.toUpperCase();
    }
    /**
     * Gets the City.
     *
     * @return the City
     */
    @Override
    public String getCity() {
        return CITY.toUpperCase();
    }


    @Override
    public List<PlannerDetail> getPlannerList() {
        if (Objects.nonNull(plannerDetails)) {
            return new ArrayList<>(plannerDetails);
        }
        return Collections.emptyList();
    }
}
