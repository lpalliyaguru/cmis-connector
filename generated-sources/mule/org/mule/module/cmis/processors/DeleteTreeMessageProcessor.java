
package org.mule.module.cmis.processors;

import java.util.List;
import javax.annotation.Generated;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
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
import org.mule.common.metadata.DefaultListMetaDataModel;
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
 * DeleteTreeMessageProcessor invokes the {@link org.mule.module.cmis.CMISCloudConnector#deleteTree(org.apache.chemistry.opencmis.client.api.CmisObject, java.lang.String, boolean, org.apache.chemistry.opencmis.commons.enums.UnfileObject, boolean)} method in {@link CMISCloudConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:47-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class DeleteTreeMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object folder;
    protected CmisObject _folderType;
    protected Object folderId;
    protected String _folderIdType;
    protected Object allversions;
    protected boolean _allversionsType;
    protected Object unfile;
    protected UnfileObject _unfileType;
    protected Object continueOnFailure;
    protected boolean _continueOnFailureType;

    public DeleteTreeMessageProcessor(String operationName) {
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
     * Sets continueOnFailure
     * 
     * @param value Value to set
     */
    public void setContinueOnFailure(Object value) {
        this.continueOnFailure = value;
    }

    /**
     * Sets unfile
     * 
     * @param value Value to set
     */
    public void setUnfile(Object value) {
        this.unfile = value;
    }

    /**
     * Sets folder
     * 
     * @param value Value to set
     */
    public void setFolder(Object value) {
        this.folder = value;
    }

    /**
     * Sets allversions
     * 
     * @param value Value to set
     */
    public void setAllversions(Object value) {
        this.allversions = value;
    }

    /**
     * Sets folderId
     * 
     * @param value Value to set
     */
    public void setFolderId(Object value) {
        this.folderId = value;
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
            final CmisObject _transformedFolder = ((CmisObject) evaluateAndTransform(getMuleContext(), event, DeleteTreeMessageProcessor.class.getDeclaredField("_folderType").getGenericType(), null, folder));
            final String _transformedFolderId = ((String) evaluateAndTransform(getMuleContext(), event, DeleteTreeMessageProcessor.class.getDeclaredField("_folderIdType").getGenericType(), null, folderId));
            final Boolean _transformedAllversions = ((Boolean) evaluateAndTransform(getMuleContext(), event, DeleteTreeMessageProcessor.class.getDeclaredField("_allversionsType").getGenericType(), null, allversions));
            final UnfileObject _transformedUnfile = ((UnfileObject) evaluateAndTransform(getMuleContext(), event, DeleteTreeMessageProcessor.class.getDeclaredField("_unfileType").getGenericType(), null, unfile));
            final Boolean _transformedContinueOnFailure = ((Boolean) evaluateAndTransform(getMuleContext(), event, DeleteTreeMessageProcessor.class.getDeclaredField("_continueOnFailureType").getGenericType(), null, continueOnFailure));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
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
                    return ((CMISCloudConnector) object).deleteTree(_transformedFolder, _transformedFolderId, _transformedAllversions, _transformedUnfile, _transformedContinueOnFailure);
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
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(String.class))));
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
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at CMISCloudConnector at deleteTree retrieving was successful but result is null");
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
