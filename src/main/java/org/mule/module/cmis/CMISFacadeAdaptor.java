/**
 * Mule CMIS Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.cmis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMISFacadeAdaptor
{

    private static Logger logger = LoggerFactory.getLogger(CMISFacadeAdaptor.class);
    
    public static CMISFacade adapt(final CMISFacade facade)
    {
        return (CMISFacade) Proxy.newProxyInstance(
            CMISFacadeAdaptor.class.getClassLoader(), new Class[]{CMISFacade.class}, 
            new InvocationHandler() 
            {
                        
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                {
                    if (logger.isDebugEnabled()) 
                    {
                        logger.debug("Invoked method {0} with arguments {1}", method.getName(), args);
                    }
                    try
                    {
                        Object ret = method.invoke(facade, args);
                        if (logger.isDebugEnabled()) 
                        {
                            logger.debug("Returned method {0} with value {1}", ret);
                        }
                        return ret;
                    }
                    catch (InvocationTargetException e)
                    {
                        if (logger.isWarnEnabled())
                        {
                            logger.warn("Method " + method.getName() + " thew " + e.getClass(), e);
                        }
                        final Throwable cause = e.getCause();
                        if (cause instanceof RuntimeException)
                        {
                            throw e.getCause();                            
                        }
                        else
                        {
                            throw new CMISConnectorException(cause);
                        }
                    }
                    
            } });
    }
}


