package org.mule.module.cmis;

public enum VersioningState
{

    NONE("none"), MAJOR("major"), MINOR("minor"), CHECKEDOUT("checkedout");
    private final String value;

    VersioningState(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static VersioningState fromValue(String v)
    {
        for (VersioningState c : VersioningState.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}