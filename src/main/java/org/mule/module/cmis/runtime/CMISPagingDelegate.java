/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.module.cmis.runtime;

import com.google.common.collect.Lists;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.jetbrains.annotations.NotNull;
import org.mule.api.MuleException;
import org.mule.module.cmis.CMISConnector;
import org.mule.streaming.ProviderAwarePagingDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CMISPagingDelegate extends ProviderAwarePagingDelegate<QueryResult, CMISConnector> {

    int skipTo = 0;
    private final String query;
    private final String filter;
    private final String orderBy;
    private final Session session;
    private final Integer pageSize;
    private boolean lastPage = false;
    private final boolean allVersions;

    public CMISPagingDelegate(Session session, String query, boolean allVersions, String filter, String orderBy, Integer pageSize) {
        this.session = session;
        this.query = query;
        this.allVersions = allVersions;
        this.filter = filter;
        this.orderBy = orderBy;
        this.pageSize = pageSize;
    }

    @Override
    public List<QueryResult> getPage(@NotNull CMISConnector connector) throws Exception {
        if (session != null && !lastPage) {
            OperationContext ctx = ChemistryCMISFacade.createOperationContext(filter, orderBy);
            ctx.setMaxItemsPerPage(pageSize);
            final ItemIterable<QueryResult> queryResults = session.query(query, allVersions, ctx);
            final ItemIterable<QueryResult> page = queryResults.skipTo(skipTo).getPage();
            final List<QueryResult> results = Lists.newArrayList(page);
            if (!page.getHasMoreItems()) {
                lastPage = true;
            }
            skipTo += page.getPageNumItems();
            return results;
        }
        lastPage = false;
        return Collections.emptyList();
    }

    @Override
    public int getTotalResults(@NotNull CMISConnector connector) throws Exception {
        long size = -1;
        long skipResultTo = pageSize * 100L;
        while (session != null && !lastPage) {
            OperationContext ctx = ChemistryCMISFacade.createOperationContext(filter, orderBy);
            final ItemIterable<QueryResult> queryResults = session.query(query, allVersions, ctx);
            final ItemIterable<QueryResult> page = queryResults.skipTo(skipResultTo).getPage();
            if (!page.getHasMoreItems()) {
                lastPage = true;
                size = skipResultTo + page.getTotalNumItems();
            }
            skipResultTo += skipResultTo;
        }
        lastPage = false;
        return (int) size;
    }

    public void close() throws MuleException {
        skipTo = 0;
        lastPage = false;
    }
}
