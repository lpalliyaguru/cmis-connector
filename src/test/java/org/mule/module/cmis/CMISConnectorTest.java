/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.module.cmis;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.ChangeEventsImpl;
import org.apache.chemistry.opencmis.client.runtime.util.EmptyItemIterable;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.RepositoryInfoImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


/**
 * Test {@link org.mule.module.cmis.CMISConnector} internals
 */
public class CMISConnectorTest {

    @Mock
    ObjectId objectId;
    private CMISConnector connector;
    @Mock
    private CMISFacade facade;
    @Mock
    private CmisObject cmisObject;
    @Mock
    private ObjectType objectType;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.connector = new CMISConnector();
        this.connector.setFacade(facade);
        this.connector.setConnectionIdentifier(UUID.randomUUID().toString());
    }

    @Test
    public void testRepositories() throws Exception {
        when(facade.repositories()).thenReturn(Collections.<Repository>emptyList());
        assertEquals(Collections.<Repository>emptyList(), connector.repositories());
    }

    @Test
    public void testRepositoryInfo() throws Exception {
        RepositoryInfo repositoryInfo = new RepositoryInfoImpl();
        when(facade.repositoryInfo()).thenReturn(repositoryInfo);
        assertEquals(repositoryInfo, connector.repositoryInfo());

    }

    @Test
    public void testChangelog() throws Exception {
        ChangeEvents changeEvents = new ChangeEventsImpl();
        when(facade.changelog(anyString(), anyBoolean())).thenReturn(changeEvents);
        assertEquals(changeEvents, connector.changelog("fooId", false));
    }

    @Test
    public void testGetObjectById() throws Exception {
        when(facade.getObjectById(anyString())).thenReturn(cmisObject);
        assertEquals(cmisObject, connector.getObjectById("fooId"));
    }

    @Test
    public void testGetObjectByPath() throws Exception {
        when(facade.getObjectByPath(anyString())).thenReturn(cmisObject);
        assertEquals(cmisObject, connector.getObjectByPath("foo/oof"));
    }

    @Test
    public void testCreateDocumentByPath() throws Exception {
        when(facade.createDocumentByPath(anyString(), anyString(), anyObject(), anyString(), any(VersioningState.class), anyString(), anyMap(), anyBoolean())).thenReturn(objectId);
        assertEquals(objectId, connector.createDocumentByPath("/mule-demo", "foo", "This is a mock test", "text/plain;charset=UTF-8", VersioningState.NONE, "D:cmiscustom:document", new HashMap<String, Object>(5), false));
    }

    @Test
    public void testGetOrCreateFolderByPath() throws Exception {
        when(facade.getOrCreateFolderByPath(anyString())).thenReturn(cmisObject);
        assertEquals(cmisObject, connector.getOrCreateFolderByPath("/mule-demo"));
    }

    @Test
    public void testCreateDocumentById() throws Exception {
        when(facade.createDocumentById(anyString(), anyString(), anyObject(), anyString(), any(VersioningState.class), anyString(), anyMap())).thenReturn(objectId);
        assertEquals(objectId, connector.createDocumentById("/mule-demo", "foo", "This is a mock test", "text/plain;charset=UTF-8", VersioningState.NONE, "D:cmiscustom:document", new HashMap<String, Object>(5)));
    }

    @Test
    public void testCreateFolder() throws Exception {
        when(facade.createFolder(anyString(), anyString())).thenReturn(objectId);
        assertEquals(objectId, connector.createFolder("/mule-demo", "000A000B000C"));
    }

    @Test
    public void testGetTypeDefinition() throws Exception {
        when(facade.getTypeDefinition(anyString())).thenReturn(objectType);
        assertEquals(objectType, connector.getTypeDefinition("cmis:relationship"));
    }

    @Test
    public void testGetCheckoutDocs() throws Exception {
        ItemIterable<Document> documents = new EmptyItemIterable<Document>();
        when(facade.getCheckoutDocs(anyString(), anyString())).thenReturn(documents);
        assertEquals(documents, connector.getCheckoutDocs("foo", "ASC"));
    }

    @Test
    public void testQuery() throws Exception {
        ItemIterable<QueryResult> results = new EmptyItemIterable<QueryResult>();
        when(facade.query(anyString(), anyBoolean(), anyString(), anyString())).thenReturn(results);
        assertEquals(results, connector.query("foo", false, "mule", "ASC"));
    }

    @Test
    public void testParentFolders() throws Exception {
        List<Folder> folders = new ArrayList<Folder>(5);
        when(facade.getParentFolders(any(CmisObject.class), anyString())).thenReturn(folders);
        assertEquals(folders, connector.getParentFolders(cmisObject, "fooId"));
    }

    @Test
    public void testFolder() throws Exception {
        when(facade.folder(any(Folder.class), anyString(), any(NavigationOptions.class), anyInt(), anyString(), anyString())).thenReturn(cmisObject);
        assertEquals(cmisObject, connector.folder(Mockito.mock(Folder.class), "fooId", NavigationOptions.PARENT, 0, "mule", "ASC"));
    }

    @Test
    public void testGetContentStream() throws Exception {
        ContentStream contentStream = Mockito.mock(ContentStream.class);
        when(facade.getContentStream(any(CmisObject.class), anyString())).thenReturn(contentStream);
        assertEquals(contentStream, connector.getContentStream(cmisObject, "fooId"));
    }

    @Test
    public void testMoveObject() throws Exception {
        FileableCmisObject fileableCmisObject = Mockito.mock(FileableCmisObject.class);
        when(facade.moveObject(any(FileableCmisObject.class), anyString(), anyString(), anyString())).thenReturn(fileableCmisObject);
        assertEquals(fileableCmisObject, connector.moveObject(fileableCmisObject, "fooId", "sourceId", "targetId"));
    }

    @Test
    public void testUpdateObjectProperties() throws Exception {
        when(facade.updateObjectProperties(any(CmisObject.class), anyString(), anyMap())).thenReturn(cmisObject);
        assertEquals(cmisObject, connector.updateObjectProperties(cmisObject, "fooId", new HashMap<String, Object>()));
    }

    @Test
    public void testGetObjectRelationships() throws Exception {
        List<Relationship> relationships = Arrays.asList(Mockito.mock(Relationship.class), Mockito.mock(Relationship.class), Mockito.mock(Relationship.class));
        when(facade.getObjectRelationships(any(CmisObject.class), anyString())).thenReturn(relationships);
        assertEquals(relationships, connector.getObjectRelationships(cmisObject, "fooId"));
    }

    @Test
    public void testGetAcl() throws Exception {
        Acl acl = Mockito.mock(Acl.class);
        when(facade.getAcl(any(CmisObject.class), anyString())).thenReturn(acl);
        assertEquals(acl, connector.getAcl(cmisObject, "fooId"));
    }

    @Test
    public void testGetAllVersions() throws Exception {
        List<Document> documents = Arrays.asList(Mockito.mock(Document.class), Mockito.mock(Document.class), Mockito.mock(Document.class));
        when(facade.getAllVersions(any(CmisObject.class), anyString(), anyString(), anyString())).thenReturn(documents);
        assertEquals(documents, connector.getAllVersions(cmisObject, "docId", "filter", "ASC"));
    }

    @Test
    public void testCheckOut() throws Exception {
        when(facade.checkOut(any(CmisObject.class), anyString())).thenReturn(objectId);
        assertEquals(objectId, connector.checkOut(cmisObject, "docId"));
    }

    @Test
    public void testCancelCheckOut() throws Exception {
        doNothing().when(facade).cancelCheckOut(any(CmisObject.class), anyString());
        connector.cancelCheckOut(cmisObject, "docId");
    }

    @Test
    public void testCheckIn() throws Exception {
        when(facade.checkIn(any(CmisObject.class), anyString(), anyObject(), anyString(), anyString(), anyBoolean(), anyString(), anyMap())).thenReturn(objectId);
        assertEquals(objectId, connector.checkIn(cmisObject, "docId", "This is a mock test", "foo.txt", "text/plain;charset=UTF-8", false, "Test Checkin Comments", new HashMap<String, Object>()));
    }

    @Test
    public void testApplyAcl() throws Exception {
        Acl acl = Mockito.mock(Acl.class);
        when(facade.applyAcl(any(CmisObject.class), anyString(), anyList(), anyList(), any(AclPropagation.class))).thenReturn(acl);
        assertEquals(acl, connector.applyAcl(cmisObject, "fooId", new ArrayList<Ace>(), new ArrayList<Ace>(), AclPropagation.OBJECTONLY));
    }

    @Test
    public void testAppliedPolicies() throws Exception {
        List<Policy> policies = Arrays.asList(Mockito.mock(Policy.class), Mockito.mock(Policy.class), Mockito.mock(Policy.class));
        when(facade.getAppliedPolicies(any(CmisObject.class), anyString())).thenReturn(policies);
        assertEquals(policies, connector.getAppliedPolicies(cmisObject, "objectId"));
    }

    @Test
    public void testApplyPolicy() throws Exception {
        doNothing().when(facade).applyPolicy(any(CmisObject.class), anyString(), anyList());
        connector.applyPolicy(cmisObject, "objectId", new ArrayList<ObjectId>());
    }

    @Test
    public void testApplyAspect() throws Exception {
        doNothing().when(facade).applyAspect(anyString(), anyString(), anyMap());
        connector.applyAspect("objectId", "aspectName", new HashMap<String, Object>());
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(facade).delete(any(CmisObject.class), anyString(), anyBoolean());
        connector.delete(cmisObject, "fooId", false);
    }

    @Test
    public void testDeleteTree() throws Exception {
        List<String> strings = Arrays.asList("foo", "mule", "anypoint");
        when(facade.deleteTree(any(CmisObject.class), anyString(), anyBoolean(), any(UnfileObject.class), anyBoolean())).thenReturn(strings);
        assertEquals(strings, connector.deleteTree(cmisObject, "folderId", false, UnfileObject.DELETE, false));
    }

    @Test
    public void testCreateRelationship() throws Exception {
        when(facade.createRelationship(anyString(), anyString(), anyString())).thenReturn(objectId);
        assertEquals(objectId, connector.createRelationship("parentId", "childId", "relType"));
    }

    /**
     * Connector Implementation Methods Test
     */
}