import {Component, OnInit} from 'angular2/core';
import {JenkinsService} from './jenkins.service';
import {JenkinsJob} from './jenkins-job';
import {WidgetComponent} from '../widget-component';

@Component({
  selector: 'jenkins-job-widget',
  templateUrl: 'app/widget/jenkins/jenkins-job-widget.component.html',
})
export class JenkinsJobWidgetComponent extends WidgetComponent implements OnInit {

  public job: JenkinsJob;
  private _jenkinsService: JenkinsService;

  constructor(_jenkinsService: JenkinsService) {
    super();
    this._jenkinsService = _jenkinsService;
  }

  ngOnInit() {
    this._jenkinsService.getJob('Infiniboard').then(
      job => this.job = job
    );
  }

}
