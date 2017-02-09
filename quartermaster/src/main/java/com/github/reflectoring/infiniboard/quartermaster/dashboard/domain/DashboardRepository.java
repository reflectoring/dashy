package com.github.reflectoring.infiniboard.quartermaster.dashboard.domain;

import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepository {

  private WidgetConfigService widgetConfigService;

  @Autowired
  public DashboardRepository(WidgetConfigService widgetConfigService) {
    this.widgetConfigService = widgetConfigService;
  }

  public Page<Dashboard> findAll() {
    ArrayList<Dashboard> dashboards = new ArrayList<>();
    dashboards.add(getSupportDashboardMock(1));
    return new PageImpl<>(dashboards);
  }

  private Dashboard getSupportDashboardMock(int id) {
    Dashboard dashboard = new Dashboard(id, "Development", "Just what you need");

    dashboard.setWidgetConfigs(widgetConfigService.loadWidgets());

    return dashboard;
  }

  public Dashboard find(int id) {
    return getSupportDashboardMock(id);
  }
}
