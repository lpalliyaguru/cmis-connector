
package org.mule.module.cmis.processors;

import java.util.List;
import javax.annotation.Generated;
import org.apache.chemistry.opencmis.client.api.Folder;
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
import org.mule.module.cmis.NavigationOptions;
import org.mule.module.cmis.connectivity.CMISCloudConnectorConnectionManager;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * FolderMessageProcessor invokes the {@link org.mule.module.cmis.CMISCloudConnector#folder(org.apache.chemistry.opencmis.client.api.Folder, java.lang.String, org.mule.module.cmis.NavigationOptions, java.lang.Integer, java.lang.String, java.lang.String)} method in {@link CMISCloudConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class FolderMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object folder;
    protected Folder _folderType;
    protected Object folderId;
    protected String _folderIdType;
    protected Object get;
    protected NavigationOptions _getType;
    protected Object depth;
    protected Integer _depthType;
    protected Object filter;
    protected String _filterType;
    protected Object orderBy;
    protected String _orderByType;

    public FolderMessageProcessor(String operationName) {
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
     * Sets folder
     * 
     * @param value Value to set
     */
    public void setFolder(Object value) {
        this.folder = value;
    }

    /**
     * Sets orderBy
     * 
     * @param value Value to set
     */
    public void setOrderBy(Object value) {
        this.orderBy = value;
    }

    /**
     * Sets get
     * 
     * @param value Value to set
     */
    public void setGet(Object value) {
        this.get = value;
    }

    /**
     * Sets filter
     * 
     * @param value Value to set
     */
    public void setFilter(Object value) {
        this.filter = value;
    }

    /**
     * Sets depth
     * 
     * @param value Value to set
     */
    public void setDepth(Object value) {
        this.depth = value;
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
            final Folder _transformedFolder = ((Folder) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_folderType").getGenericType(), null, folder));
            final String _transformedFolderId = ((String) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_folderIdType").getGenericType(), null, folderId));
            final NavigationOptions _transformedGet = ((NavigationOptions) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_getType").getGenericType(), null, get));
            final Integer _transformedDepth = ((Integer) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_depthType").getGenericType(), null, depth));
            final String _transformedFilter = ((String) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_filterType").getGenericType(), null, filter));
            final String _transformedOrderBy = ((String) evaluateAndTransform(getMuleContext(), event, FolderMessageProcessor.class.getDeclaredField("_orderByType").getGenericType(), null, orderBy));
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
                    return ((CMISCloudConnector) object).folder(_transformedFolder, _transformedFolderId, _transformedGet, _transformedDepth, _transformedFilter, _transformedOrderBy);
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
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(Object.class)));
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
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at CMISCloudConnector at folder retrieving was successful but result is null");
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
