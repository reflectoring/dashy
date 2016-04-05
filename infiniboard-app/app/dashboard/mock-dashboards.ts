import {Dashboard} from './dashboard';
import {WidgetConfig} from 'widget-config';

export var DASHBOARDS: Dashboard[] = [
  new Dashboard(1, 'Development', 'source metrics', true, [new WidgetConfig('dev', 'platform-status')]),
  new Dashboard(2, 'Support', 'call center metrics', false, []),
];
