
package org.apache.chemistry.opencmis.commons.enums.transformers;

import javax.annotation.Generated;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:14:14-05:00", comments = "Build master.1915.dd1962d")
public class UnfileObjectEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public UnfileObjectEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(UnfileObject.class);
        setName("UnfileObjectEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        UnfileObject result = null;
        result = Enum.valueOf(UnfileObject.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
