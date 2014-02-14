
package org.mule.module.cmis.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.module.cmis.NavigationOptions;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:47-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class NavigationOptionsEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public NavigationOptionsEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(NavigationOptions.class);
        setName("NavigationOptionsEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        NavigationOptions result = null;
        result = Enum.valueOf(NavigationOptions.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
