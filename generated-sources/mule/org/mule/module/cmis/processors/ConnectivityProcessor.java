
package org.mule.module.cmis.processors;

import java.lang.reflect.Type;
import javax.annotation.Generated;


/**
 * Interface used to unify all message processors (those which use (or not) pagination) from the ManagedConnectionProcessInterceptor
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public interface ConnectivityProcessor {


    public Object getUsername();

    public Object getPassword();

    public Object getBaseUrl();

    public Object getRepositoryId();

    public Object getEndpoint();

    public Object getConnectionTimeout();

    public Object getUseAlfrescoExtension();

    public Object getCxfPortProvider();

    public Object getUseCookies();

    /**
     * Retrieves the concrete java.lang.reflect.Type of a connectivity argument, needed for the @Connect
     * 
     * @param fieldName
     *     Name of the field to look for
     * @return
     *     The {@link java.lang.reflect.Type} associated with the field {@code fieldName}
     * @throws NoSuchFieldException
     *     Thrown when the {@code fieldName} is not a field from the current class
     */
    public Type typeFor(String fieldName)
        throws NoSuchFieldException
    ;

}
