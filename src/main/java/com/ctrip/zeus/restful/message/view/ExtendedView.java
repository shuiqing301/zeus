package com.ctrip.zeus.restful.message.view;

import com.ctrip.zeus.model.entity.*;
import com.ctrip.zeus.tag.entity.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;

import java.util.List;

/**
 * Created by zhoumy on 2016/7/25.
 */
public interface ExtendedView<T> {

    Long getId();

    void setTags(List<String> tags);

    void setProperties(List<Property> properties);

    @JsonView(ViewConstraints.Extended.class)
    List<String> getTags();

    @JsonView(ViewConstraints.Extended.class)
    List<Property> getProperties();

    @JsonIgnore
    T getInstance();

    class ExtendedGroup extends GroupView implements ExtendedView<Group> {
        private List<String> tags;
        private List<Property> properties;
        private Group instance;

        public ExtendedGroup() {
            this(new Group());
        }

        public ExtendedGroup(Group instance) {
            this.instance = instance;
        }

        @Override
        public Long getId() {
            return instance.getId();
        }

        @Override
        public String getName() {
            return instance.getName();
        }

        @Override
        public String getAppId() {
            return instance.getAppId();
        }


        @Override
        HealthCheck getHealthCheck() {
            return instance.getHealthCheck();
        }

        @Override
        LoadBalancingMethod getLoadBalancingMethod() {
            return instance.getLoadBalancingMethod();
        }

        @Override
        Boolean getSsl() {
            return instance.getSsl();
        }

        @Override
        Integer getVersion() {
            return instance.getVersion();
        }

        @Override
        Boolean getVirtual() {
            return instance.getVirtual();
        }

        @Override
        List<GroupServer> getGroupServers() {
            return instance.getGroupServers();
        }

        @Override
        List<GroupVirtualServer> getGroupVirtualServers() {
            for (GroupVirtualServer gvs : instance.getGroupVirtualServers()) {
                ExtendedVs.renderVirtualServer(gvs.getVirtualServer());
            }
            return instance.getGroupVirtualServers();
        }

        @Override
        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        @Override
        public List<String> getTags() {
            return tags;
        }

        @Override
        public List<Property> getProperties() {
            return properties;
        }

        @Override
        public Group getInstance() {
            return instance;
        }
    }

    class ExtendedVs extends VsView implements ExtendedView<VirtualServer> {
        private static DynamicBooleanProperty n2nEnabled = DynamicPropertyFactory.getInstance().getBooleanProperty("slb.slb-vs-n2n.enabled", false);

        private List<String> tags;
        private List<Property> properties;
        private VirtualServer instance;

        public ExtendedVs() {
            this(new VirtualServer());
        }

        public ExtendedVs(VirtualServer instance) {
            this.instance = instance;
        }

        @Override
        public Long getId() {
            return instance.getId();
        }

        @Override
        String getName() {
            return instance.getName();
        }

        @Override
        String getPort() {
            return instance.getPort();
        }

        @Override
        Long getSlbId() {
            if (n2nEnabled.get()) {
                return null;
            } else {
                if (instance.getSlbId() == null && instance.getSlbIds().size() > 0) {
                    instance.setSlbId(instance.getSlbIds().get(0));
                }
                return instance.getSlbId();
            }
        }

        @Override
        List<Long> getSlbIds() {
            if (n2nEnabled.get()) {
                if (instance.getSlbIds().size() == 0 && instance.getSlbId() != null) {
                    instance.getSlbIds().add(instance.getSlbId());
                }
                return instance.getSlbIds();
            } else {
                return null;
            }
        }

        @Override
        Boolean getSsl() {
            return instance.getSsl();
        }

        @Override
        Integer getVersion() {
            return instance.getVersion();
        }

        @Override
        List<Domain> getDomains() {
            return instance.getDomains();
        }

        @Override
        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        @Override
        public List<String> getTags() {
            return tags;
        }

        @Override
        public List<Property> getProperties() {
            return properties;
        }

        @Override
        public VirtualServer getInstance() {
            return null;
        }

        public static void renderVirtualServer(VirtualServer vs) {
            if (n2nEnabled.get()) {
                if (vs.getSlbIds().size() == 0 && vs.getSlbId() != null) {
                    vs.getSlbIds().add(vs.getSlbId());
                }
                vs.setSlbId(null);
            } else {
                if (vs.getSlbId() == null && vs.getSlbIds().size() > 0) {
                    vs.setSlbId(vs.getSlbIds().get(0));
                }
                vs.getSlbIds().clear();
            }
        }
    }

    class ExtendedSlb extends SlbView implements ExtendedView<Slb> {
        private List<String> tags;
        private List<Property> properties;
        private Slb instance;

        public ExtendedSlb() {
            this(new Slb());
        }

        public ExtendedSlb(Slb instance) {
            this.instance = instance;
        }

        @Override
        public Long getId() {
            return instance.getId();
        }

        @Override
        String getName() {
            return instance.getName();
        }

        @Override
        String getNginxBin() {
            return instance.getNginxBin();
        }

        @Override
        String getNginxConf() {
            return instance.getNginxConf();
        }

        @Override
        Integer getNginxWorkerProcesses() {
            return instance.getNginxWorkerProcesses();
        }

        @Override
        List<SlbServer> getSlbServers() {
            return instance.getSlbServers();
        }

        @Override
        String getStatus() {
            return instance.getStatus();
        }

        @Override
        Integer getVersion() {
            return instance.getVersion();
        }

        @Override
        List<Vip> getVips() {
            return instance.getVips();
        }

        @Override
        List<VirtualServer> getVirtualServers() {
            for (VirtualServer vs : instance.getVirtualServers()) {
                ExtendedVs.renderVirtualServer(vs);
            }
            return instance.getVirtualServers();
        }

        @Override
        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        @Override
        public List<String> getTags() {
            return tags;
        }

        @Override
        public List<Property> getProperties() {
            return properties;
        }

        @Override
        public Slb getInstance() {
            return null;
        }
    }
}
