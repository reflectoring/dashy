package com.github.reflectoring.infiniboard.harvester.source.url;

import com.github.reflectoring.infiniboard.harvester.scheduling.SchedulingService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Date;

/**
 * job to retrieve UrlData (configured via DB)
 */
public class UrlDataRetrieveJob implements Job {

    private final static Logger LOG = LoggerFactory.getLogger(UrlDataRetrieveJob.class);

    void retrieve(String url, UrlDataRepository repository) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK != statusCode) {
            LOG.error("could not fetch url {} / status {} / reason {}", url, statusCode, response.getStatusLine().getReasonPhrase());
            return;
        }
        String content = IOUtils.toString(response.getEntity().getContent());
        repository.save(new UrlData(url, new Date(), content, statusCode));
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap configuration = context.getJobDetail().getJobDataMap();
        ApplicationContext applicationContext = (ApplicationContext) configuration.get(SchedulingService.PARAM_CONTEXT);
        UrlDataRepository urlDataRepository = applicationContext.getBean(UrlDataRepository.class);
        String url = configuration.get("url").toString();
        try {
            retrieve(url, urlDataRepository);
        } catch (IOException e) {
            LOG.error("could not fetch url {} because {}", url, e);
        }
    }
}
