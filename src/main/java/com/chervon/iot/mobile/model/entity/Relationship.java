package com.chervon.iot.mobile.model.entity;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Admin on 2017/7/12.
 */
@Component
public class Relationship {
    private Creator creator;

    public Creator getCreator() {

        return creator;
    }
    public Relationship() {

    }
    public Relationship(Creator creator) {
        this.creator = creator;
    }
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public class Creator {
        private Map<String,String> links;
        private Map<String,String> data;

        public Creator(Map<String, String> links, Map<String, String> data) {
            this.links = links;
            this.data = data;
        }

        public Map<String, String> getLinks() {
            return links;
        }

        public void setLinks(Map<String, String> links) {
            this.links = links;
        }

        public Map<String, String> getData() {
            return data;
        }

        public void setData(Map<String, String> data) {
            this.data = data;
        }
    }


}
