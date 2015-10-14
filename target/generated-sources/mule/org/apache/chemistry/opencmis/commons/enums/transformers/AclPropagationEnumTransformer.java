
package org.apache.chemistry.opencmis.commons.enums.transformers;

import javax.annotation.Generated;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-10-14T01:33:34-03:00", comments = "Build UNNAMED.2613.77421cc")
public class AclPropagationEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public AclPropagationEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(AclPropagation.class);
        setName("AclPropagationEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        AclPropagation result = null;
        result = Enum.valueOf(AclPropagation.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
