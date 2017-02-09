package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.widget.rest.WidgetController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DashboardResourceAssembler
    extends ResourceAssemblerSupport<Dashboard, DashboardResource> {

  public DashboardResourceAssembler() {
    super(DashboardController.class, DashboardResource.class);
  }

  @Override
  public DashboardResource toResource(Dashboard entity) {
    DashboardResource resource = new DashboardResource();
    resource.setNumber(entity.getId());
    resource.setName(entity.getName());
    resource.setDescription(entity.getDescription());
    resource.add(
        linkTo(methodOn(DashboardController.class).getDashboard(entity.getId())).withRel("self"));
    resource.add(
        linkTo(methodOn(WidgetController.class).getWidgets(entity.getId(), null, null))
            .withRel("widgets"));
    resource.add(
        linkTo(methodOn(WidgetController.class).getAllWidgets(entity.getId()))
            .withRel("all-widgets"));
    return resource;
  }
}
