
package org.mule.module.cmis.processors;

import java.util.List;
import javax.annotation.Generated;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.module.cmis.CMISCloudConnector;
import org.mule.module.cmis.connectivity.CMISCloudConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * ApplyAclMessageProcessor invokes the {@link org.mule.module.cmis.CMISCloudConnector#applyAcl(org.apache.chemistry.opencmis.client.api.CmisObject, java.lang.String, java.util.List, java.util.List, org.apache.chemistry.opencmis.commons.enums.AclPropagation)} method in {@link CMISCloudConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class ApplyAclMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object cmisObject;
    protected CmisObject _cmisObjectType;
    protected Object objectId;
    protected String _objectIdType;
    protected Object addAces;
    protected List<Ace> _addAcesType;
    protected Object removeAces;
    protected List<Ace> _removeAcesType;
    protected Object aclPropagation;
    protected AclPropagation _aclPropagationType;

    public ApplyAclMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets addAces
     * 
     * @param value Value to set
     */
    public void setAddAces(Object value) {
        this.addAces = value;
    }

    /**
     * Sets aclPropagation
     * 
     * @param value Value to set
     */
    public void setAclPropagation(Object value) {
        this.aclPropagation = value;
    }

    /**
     * Sets objectId
     * 
     * @param value Value to set
     */
    public void setObjectId(Object value) {
        this.objectId = value;
    }

    /**
     * Sets cmisObject
     * 
     * @param value Value to set
     */
    public void setCmisObject(Object value) {
        this.cmisObject = value;
    }

    /**
     * Sets removeAces
     * 
     * @param value Value to set
     */
    public void setRemoveAces(Object value) {
        this.removeAces = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(CMISCloudConnectorConnectionManager.class, true, event);
            final CmisObject _transformedCmisObject = ((CmisObject) evaluateAndTransform(getMuleContext(), event, ApplyAclMessageProcessor.class.getDeclaredField("_cmisObjectType").getGenericType(), null, cmisObject));
            final String _transformedObjectId = ((String) evaluateAndTransform(getMuleContext(), event, ApplyAclMessageProcessor.class.getDeclaredField("_objectIdType").getGenericType(), null, objectId));
            final List<Ace> _transformedAddAces = ((List<Ace> ) evaluateAndTransform(getMuleContext(), event, ApplyAclMessageProcessor.class.getDeclaredField("_addAcesType").getGenericType(), null, addAces));
            final List<Ace> _transformedRemoveAces = ((List<Ace> ) evaluateAndTransform(getMuleContext(), event, ApplyAclMessageProcessor.class.getDeclaredField("_removeAcesType").getGenericType(), null, removeAces));
            final AclPropagation _transformedAclPropagation = ((AclPropagation) evaluateAndTransform(getMuleContext(), event, ApplyAclMessageProcessor.class.getDeclaredField("_aclPropagationType").getGenericType(), null, aclPropagation));
            Object resultPayload;
            final ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return null;
                }

                public boolean isProtected() {
                    return false;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((CMISCloudConnector) object).applyAcl(_transformedCmisObject, _transformedObjectId, _transformedAddAces, _transformedRemoveAces, _transformedAclPropagation);
                }

            }
            , this, event);
            event.getMessage().setPayload(resultPayload);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(Acl.class)));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(CMISCloudConnectorConnectionManager.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at CMISCloudConnector at applyAcl retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}