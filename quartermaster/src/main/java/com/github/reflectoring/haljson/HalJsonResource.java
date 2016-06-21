package com.github.reflectoring.haljson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HalJsonResource {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    private Map<String, List<HalJsonResource>> embedded;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_links")
    private Map<String, List<Link>> links;

    private Map<String, Object> properties;

    public HalJsonResource() {
        super();
        this.embedded = new HashMap<>();
        this.links = new HashMap<>();
        this.properties = new HashMap<>();
    }

    public void add(Link link) {
        checkLinkNotExists(link);

        List<Link> links = this.links.get(link.getRel());
        if (links == null) {
            links = new ArrayList<>();
        }

        links.add(link);
        this.links.put(link.getRel(), links);
    }

    private void checkLinkNotExists(Link link) {
        this.links.values().forEach(l -> {
            if (l.equals(link)) {
                throw new IllegalArgumentException("This link is already added.");
            }
        });
    }

    public void add(List<Link> links) {
        links.forEach(link -> add(link));
    }

    public List<Link> getLinks(String rel) {

        List<Link> links = this.links.get(rel);

        if (links == null) {
            return new ArrayList<>();
        }

        return links;
    }

    public void add(String rel, HalJsonResource embeddedResource) {

        checkEmbeddedResourceNotExists(embeddedResource);

        List<HalJsonResource> embeddedResources = this.embedded.get(rel);
        if (embeddedResources == null) {
            embeddedResources = new ArrayList<>();
        }
        embeddedResources.add(embeddedResource);
        this.embedded.put(rel, embeddedResources);
    }

    private void checkEmbeddedResourceNotExists(HalJsonResource embeddedResource) {
        this.embedded.values().forEach(resource -> {
            if (resource.equals(embeddedResource)) {
                throw new IllegalArgumentException("This embedded resource is already added.");
            }
        });
    }

    public void add(String rel, List<HalJsonResource> embeddedResources) {
        embeddedResources.forEach(resource -> add(rel, resource));
    }


    public List<HalJsonResource> getEmbeddedRessources(String rel) {
        List<HalJsonResource> resources = this.embedded.get(rel);

        if (resources == null) {
            return new ArrayList<>();
        }

        return resources;
    }


    @JsonAnySetter
    public void add(String name, Object object) {
        checkPropertyNotExists(name);

        this.properties.put(name, object);
    }

    private void checkPropertyNotExists(String name) {
        if (this.properties.containsKey(name)) {
            throw new IllegalArgumentException();
        }
    }

    public Object getProperty(String name) {
        if (!(this.properties.containsKey(name))) {
            throw new IllegalArgumentException();
        }

        return this.properties.get(name);
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return this.properties;
    }

}
