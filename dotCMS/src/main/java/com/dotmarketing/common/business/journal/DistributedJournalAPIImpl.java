/**
 * 
 */
package com.dotmarketing.common.business.journal;

import com.dotcms.business.CloseDBIfOpened;
import com.dotcms.business.WrapInTransaction;
import com.dotcms.exception.ExceptionUtil;
import com.dotmarketing.beans.Host;
import com.dotmarketing.beans.Identifier;
import com.dotmarketing.business.FactoryLocator;
import com.dotmarketing.db.DbConnectionFactory;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.folders.model.Folder;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * @author Jason Tesser
 * @since 1.6.5c
 *
 */
public class DistributedJournalAPIImpl<T> implements DistributedJournalAPI<T> {

	private final DistributedJournalFactory<T> distributedJournalFactory;
	
	public DistributedJournalAPIImpl() {
		this.distributedJournalFactory = (DistributedJournalFactory<T>)FactoryLocator.getDistributedJournalFactory();
	}
	
	/* (non-Javadoc)
	 * @see com.dotmarketing.business.DistributedJournalAPI#addCacheEntry(java.lang.String)
	 */
	@WrapInTransaction
	public void addCacheEntry(String key, String group) throws DotDataException {
		distributedJournalFactory.addCacheEntry(key, group);
	}

	/* (non-Javadoc)
	 * @see com.dotmarketing.business.DistributedJournalAPI#findCacheEntriesToRemove()
	 */
	@WrapInTransaction
	public List<String> findCacheEntriesToRemove() throws DotDataException {
		return distributedJournalFactory.findCacheEntriesToRemove();
	}

	@WrapInTransaction
	public void addStructureReindexEntries(T structureInode) throws DotDataException {
		distributedJournalFactory.addStructureReindexEntries(structureInode);
	}

	@WrapInTransaction
	public synchronized void addBuildNewIndexEntries() throws DotDataException {
		distributedJournalFactory.addBuildNewIndexEntries();
	}

	@CloseDBIfOpened
	public List<IndexJournal<T>> findContentReindexEntriesToReindex() throws DotDataException {
		return distributedJournalFactory.findContentReindexEntriesToReindex();
	}

	@CloseDBIfOpened
	public List<IndexJournal<T>> findContentReindexEntriesToReindex(boolean includeFailedRecords) throws DotDataException {
		return distributedJournalFactory.findContentReindexEntriesToReindex(includeFailedRecords);
	}

	@CloseDBIfOpened
	public void processJournalEntries() throws DotDataException {
		distributedJournalFactory.processJournalEntries();
	}

	@WrapInTransaction
	public void deleteReindexEntryForServer(IndexJournal<T> iJournal) throws DotDataException {
		distributedJournalFactory.deleteReindexEntryForServer(iJournal);
	}

	@CloseDBIfOpened
	public boolean areRecordsLeftToIndex() throws DotDataException {
		return distributedJournalFactory.areRecordsLeftToIndex();
	}

	@Override
	public boolean isIndexationEnabled() {
		return false; // todo: should it exists
	}

	@CloseDBIfOpened
	public long recordsLeftToIndexForServer() throws DotDataException {
	    return recordsLeftToIndexForServer(DbConnectionFactory.getConnection());
	}
	
	public long recordsLeftToIndexForServer(Connection conn) throws DotDataException {
		return distributedJournalFactory.recordsLeftToIndexForServer(conn);
	}

	@WrapInTransaction
	public void deleteLikeJournalRecords(IndexJournal<T> ijournal) throws DotDataException {
		distributedJournalFactory.deleteLikeJournalRecords(ijournal);
	}

	@Override
	public void setIndexationEnabled(boolean indexationEnabled) {
		// todo: should it exists
	}

	public String getServerId(){
		return distributedJournalFactory.getServerId();
	}

	@WrapInTransaction
	public void distReindexJournalCleanup(int time, boolean add, boolean includeInodeCheck, DateType type) throws DotDataException {
		distributedJournalFactory.distReindexJournalCleanup(time, add, includeInodeCheck, type);
		
	}

	@WrapInTransaction
	public void cleanDistReindexJournal() throws DotDataException {
		distributedJournalFactory.cleanDistReindexJournal();
	}

	@CloseDBIfOpened
	public List<IndexJournal> viewReindexJournalData() throws DotDataException {
		return distributedJournalFactory.viewReindexJournalData();
	}

	@WrapInTransaction
	public void refreshContentUnderHost(Host host) throws DotDataException {
		distributedJournalFactory.refreshContentUnderHost(host);
	}	

	@WrapInTransaction
	public void refreshContentUnderFolder(Folder folder) throws DotDataException {
		distributedJournalFactory.refreshContentUnderFolder(folder);
	}

	@WrapInTransaction
	public void refreshContentUnderFolderPath ( String hostId, String folderPath ) throws DotDataException {
		distributedJournalFactory.refreshContentUnderFolderPath(hostId, folderPath);
	}

	@WrapInTransaction
	@Override
	public void addIdentifierReindex(final String id) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addIdentifierReindex String id : " + Thread.currentThread().getName() + ", id: " + id);
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");

		this.distributedJournalFactory.addIdentifierReindex(id);
	}

	@WrapInTransaction
	@Override
	public void addReindexHighPriority(final String identifier) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addReindexHighPriority String identifier: " + Thread.currentThread().getName() + ", id: " + identifier);
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");

		this.distributedJournalFactory.addReindexHighPriority(identifier);
	}

	@WrapInTransaction
	@Override
	public int addIdentifierReindex(final Set<String> ids) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addIdentifierReindex Set<String> ids: " + Thread.currentThread().getName() + ", id: " + ids);
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");

		return this.distributedJournalFactory.addIdentifierReindex(ids);
	}

	@WrapInTransaction
	@Override
	public int addReindexHighPriority(final Set<String> ids) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addReindexHighPriority Set<String> ids : " + Thread.currentThread().getName() + ", ids: " + ids);
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");
		return this.distributedJournalFactory.addReindexHighPriority(ids);
	}

	@WrapInTransaction
	@Override
	public void addContentletReindex(final Contentlet contentlet) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addIdentifierReindex Contentlet contentlet : " + Thread.currentThread().getName() + ", id: " + contentlet.getIdentifier());
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");
		this.distributedJournalFactory.addIdentifierReindex(contentlet.getIdentifier());
	}

	@WrapInTransaction
	@Override
	public void addIdentifierReindex(final Identifier identifier) throws DotDataException {

		Logger.debug(this, ()->"\n*********----------- addIdentifierReindex Identifier identifier : " + Thread.currentThread().getName() + ", id: " + identifier);
		Logger.debug(this, ()->"*********-----------  " + DbConnectionFactory.getConnection());
		Logger.debug(this, ()->"*********-----------  " + ExceptionUtil.getCurrentStackTraceAsString(Config.getIntProperty("stacktracelimit", 10)) + "\n");
		this.distributedJournalFactory.addIdentifierReindex(identifier.getId());
	}

	@WrapInTransaction
    public void deleteReindexEntryForServer(List<IndexJournal<T>> recordsToDelete) throws DotDataException {
        distributedJournalFactory.deleteReindexEntryForServer(recordsToDelete);
    }

	@WrapInTransaction
	public void resetServerForReindexEntry ( List<IndexJournal<T>> recordsToModify ) throws DotDataException {
		distributedJournalFactory.resetServerForReindexEntry(recordsToModify);
	}

}
