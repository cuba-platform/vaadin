package com.vaadin.ui;

import java.util.List;

/**
 * An interface that must be implemented by all {@link Component}s that contain
 * additional dependencies that must be added to the page
 */
public interface HasDependencies {

    /**
     * @return a list of dependencies
     */
    List<ClientDependency> getDependencies();

    class ClientDependency {

        protected String path;
        protected Dependency.Type type;

        public ClientDependency(String path) {
            this.path = path;
        }

        public ClientDependency(String path, Dependency.Type type) {
            this.path = path;
            this.type = type;
        }

        /**
         * @return a dependency path
         */
        public String getPath() {
            return path;
        }

        /**
         * @return a dependency type
         */
        public Dependency.Type getType() {
            return type;
        }
    }
}
